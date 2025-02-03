package staff.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import jbcrypt.BCrypt;
import user.Caretaker;
import user.CaretakerBabysitter;
import user.CaretakerElder;
import userDAO.CaretakerDAO;
import userDAO.CaretakerBabysitterDAO;
import userDAO.CaretakerElderDAO;
import utils.SessionUtils;

/**
 *
 * @author hazik
 */

public class CaretakerManageServlet extends HttpServlet {
    private static final String LOGIN_PAGE = "login.html?error=invalidSession";
    private static final String CARETAKER_LIST_PAGE = "/staff/manage-caretaker.jsp";
    private static final String CARETAKER_DETAILS_PAGE = "/staff/caretaker-details.jsp";
    private static final Logger logger = Logger.getLogger(CaretakerManageServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Integer staffId = SessionUtils.getUserIdFromSession(request);
        if (staffId == null) {
            response.sendRedirect(LOGIN_PAGE);
            return;
        }

        String action = request.getParameter("action");
        CaretakerDAO caretakerDAO = new CaretakerDAO();

        try {
            switch (action) {
                case "viewCaretakerList":
                    handleViewCaretakerList(request, response, caretakerDAO);
                    break;
                case "viewCaretakerInfo":
                    handleViewCaretakerInfo(request, response, caretakerDAO);
                    break;
                case "updateCaretaker":
                    handleUpdateCaretaker(request, response, caretakerDAO);
                    break;
                case "disableCaretaker":
                    handleDisableCaretaker(request, response, caretakerDAO);
                    break;
                case "enableCaretaker":
                    handleEnableCaretaker(request, response, caretakerDAO);
                    break;
                case "editCaretaker":
                    handleEditCaretaker(request, response, caretakerDAO);
                    break;
                case "insertCaretaker":
                    handleInsertCaretaker(request, response, caretakerDAO);
                default:
                    sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
            }
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private void handleViewCaretakerList(HttpServletRequest request, HttpServletResponse response, CaretakerDAO caretakerDAO)
            throws ServletException, IOException {
        List<Caretaker> caretakerList = caretakerDAO.selectAllCaretakers();
        request.setAttribute("caretakerList", caretakerList);
        request.getRequestDispatcher(CARETAKER_LIST_PAGE).forward(request, response);
    }

    private void handleViewCaretakerInfo(HttpServletRequest request, HttpServletResponse response, CaretakerDAO caretakerDAO)
            throws ServletException, IOException {
        try {
            int caretakerId = parseCaretakerId(request);
            Caretaker caretaker = caretakerDAO.getCaretakerInfo(caretakerId);
            if (caretaker == null) {
                sendError(response, HttpServletResponse.SC_NOT_FOUND, "Caretaker with ID " + caretakerId + " not found.");
                return;
            }
            request.setAttribute("caretaker", caretaker);
            request.getRequestDispatcher(CARETAKER_DETAILS_PAGE).forward(request, response);
        } catch (NumberFormatException ex) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid caretaker ID.");
        }
    }

    private void handleInsertCaretaker(HttpServletRequest request, HttpServletResponse response, CaretakerDAO caretakerDAO)
            throws ServletException, IOException {
        try {
            Caretaker caretaker = new Caretaker();
            CaretakerBabysitter babysitter = new CaretakerBabysitter();
            CaretakerElder elder = new CaretakerElder();
            CaretakerBabysitterDAO babysitterDAO = new CaretakerBabysitterDAO();
            CaretakerElderDAO elderDAO = new CaretakerElderDAO();

            caretaker.setStaffId(Integer.parseInt(request.getParameter("staff_id")));
            caretaker.setName(request.getParameter("caretaker_name"));
            caretaker.setPhone(request.getParameter("caretaker_phone"));
            caretaker.setIC(request.getParameter("caretaker_ic_number"));
            caretaker.setProfileDescription(request.getParameter("profile_description"));

            // Password hashing  
            String rawPassword = request.getParameter("caretaker_password");
            if (rawPassword != null && !rawPassword.trim().isEmpty()) {
                String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
                caretaker.setPassword(hashedPassword);
            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Password cannot be empty");
                return;
            }

            // Insert caretaker and check if insertion was successful  
            boolean inserted = caretakerDAO.insertCaretaker(caretaker);
            if (inserted) {
                String type = request.getParameter("caretaker_type");
                if ("babysitter".equals(type)) {
                    babysitter.setCaretakerId(caretakerDAO.getLatestCaretakerId());
                    babysitter.setExperienceYears(Integer.parseInt(request.getParameter("babysitter_experience_years")));
                    babysitter.setHourlyRate(Double.parseDouble(request.getParameter("babysitter_hourly_rate")));

                    boolean done = babysitterDAO.insertCaretakerBabysitter(babysitter);

                    if (done) {
                        response.sendRedirect("staff/staff_dashboard.jsp?status=success");
                    } else {
                        sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to insert babysitter");
                    }

                }
                if ("eldercaretaker".equals(type)) {
                    elder.setCaretakerId(caretakerDAO.getLatestCaretakerId());
                    elder.setExperienceYears(Integer.parseInt(request.getParameter("eldercare_experience_years")));
                    elder.setHourlyRate(Double.parseDouble(request.getParameter("eldercare_hourly_rate")));

                    // Handling the uploaded file
                    Part filePart = request.getPart("eldercare_certification");
                    InputStream certificationStream = filePart.getInputStream(); // Convert to InputStream
                    elder.setCertificationStream(certificationStream);

                    boolean done = elderDAO.insertCaretakerElder(elder);

                    if (done) {
                        response.sendRedirect("staff/staff_dashboard.jsp?status=success");
                    } else {
                        sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to insert eldercaretaker");
                    }
                }

            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to insert caretaker");
            }
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Error at handleInsertCaretaker");
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private void handleUpdateCaretaker(HttpServletRequest request, HttpServletResponse response, CaretakerDAO caretakerDAO)
            throws ServletException, IOException {
        try {
            int caretakerId = parseCaretakerId(request);
            Caretaker caretaker = createCaretakerFromRequest(request, caretakerId);

            if (caretakerDAO.updateCaretaker(caretaker)) {
                response.sendRedirect("caretaker_manageProcess.jsp?action=caretakerList&success=true");
            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to update caretaker with ID " + caretakerId);
            }
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private void handleDisableCaretaker(HttpServletRequest request, HttpServletResponse response, CaretakerDAO caretakerDAO)
            throws ServletException, IOException {
        try {
            int caretakerId = parseCaretakerId(request);
            System.out.print(caretakerId);
            if (caretakerDAO.disableCaretaker(caretakerId)) {
                response.sendRedirect("caretaker_manageProcess.jsp?action=caretakerList&success=true");
            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to disable caretaker with ID " + caretakerId);
            }
        } catch (NumberFormatException ex) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid caretaker ID.");
        }
    }

    private void handleEnableCaretaker(HttpServletRequest request, HttpServletResponse response, CaretakerDAO caretakerDAO)
            throws ServletException, IOException {
        try {
            int caretakerId = parseCaretakerId(request);

            if (caretakerDAO.enableCaretaker(caretakerId)) {
                response.sendRedirect("caretaker_manageProcess.jsp?action=caretakerList&success=true");
            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to enable caretaker with ID " + caretakerId);
            }
        } catch (NumberFormatException ex) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid caretaker ID.");
        }
    }

    private void handleEditCaretaker(HttpServletRequest request, HttpServletResponse response, CaretakerDAO caretakerDAO)
            throws ServletException, IOException {
        try {
            int caretakerId = parseCaretakerId(request);
            int statusId = Integer.parseInt(request.getParameter("statusId"));

            Timestamp banDate = null;
            if (statusId != 1) { // If status is not "Active"  
                banDate = new Timestamp(System.currentTimeMillis()); // Current timestamp  
            }

            // Call the editCaretaker method with the appropriate parameters  
            if (caretakerDAO.editCaretaker(caretakerId, banDate, statusId)) {
                response.sendRedirect("caretaker_manageProcess.jsp?action=caretakerList&success=true");
            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Failed to update caretaker status for ID " + caretakerId);
            }
        } catch (NumberFormatException ex) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid status ID.");
        } catch (Exception ex) {
            handleException(response, ex);
        }
    }

    private int parseCaretakerId(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("caretakerId"));
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid caretaker ID format", e);
            throw e;
        }
    }

    private Caretaker createCaretakerFromRequest(HttpServletRequest request, int caretakerId) {
        Caretaker caretaker = new Caretaker();
        caretaker.setCaretakerId(caretakerId);
        caretaker.setName(request.getParameter("caretakerName"));
        caretaker.setPhone(request.getParameter("caretakerPhone"));
        caretaker.setAvailabilityStatus(request.getParameter("availabilityStatus"));
        caretaker.setIC(request.getParameter("ic"));
        caretaker.setStaffId(Integer.parseInt(request.getParameter("staffId")));
        caretaker.setStatusId(Integer.parseInt(request.getParameter("statusId")));
        
        return caretaker;
    }

    private void sendError(HttpServletResponse response, int statusCode, String message) throws IOException {
        if (!response.isCommitted()) {
            response.sendError(statusCode, message);
        }
    }

    private void handleException(HttpServletResponse response, Exception ex) throws IOException {
        logger.log(Level.SEVERE, "Exception occurred", ex);
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Caretaker management servlet handling CRUD operations based on action.";
    }
}
