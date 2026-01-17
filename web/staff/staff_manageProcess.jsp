<%-- 
    Document   : Staff_manageProcess
    Created on : Jan 17, 2025, 12:31:07 AM
    Author     : hazik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>  
<!DOCTYPE html>  
<html>  
<head>  
    <title>Staff Management Process</title>  
</head>  
<body>  

<%  
    String action = request.getParameter("action");  
    String targetServlet = "../StaffManageServlet";  

    try {  
        if ("staffList".equals(action)) {  
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=viewStaffList");  
            dispatcher.forward(request, response);  
        } else if ("viewStaff".equals(action)) {  
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=viewStaffInfo");  
            dispatcher.forward(request, response);  
        } else if ("updateStaff".equals(action)) {  
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=updateStaff");  
            dispatcher.forward(request, response);  
        } else if ("deleteStaff".equals(action)) {  
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=deleteStaff");  
            dispatcher.forward(request, response);  
        } else if ("editStaff".equals(action)) {  
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=editStaff");  
            dispatcher.forward(request, response);  
        } else if ("disableStaff".equals(action)) {  
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=disableStaff");  
            dispatcher.forward(request, response);  
        } else if ("enableStaff".equals(action)) {  
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetServlet + "?action=enableStaff");  
            dispatcher.forward(request, response);  
        } else {  
            out.println("Invalid action specified.");  
        }  
    } catch (Exception e) {  
        e.printStackTrace(); // Log the error  
        out.println("An error occurred while processing your request: " + e.getMessage());  
    }  
%>  

</body>  
</html>


