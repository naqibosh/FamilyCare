/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jbcrypt.BCrypt;

/**
 *
 * @author hazik
 */
public class SessionUtils {

    public static final String USER_ID_SESSION_ATTRIBUTE = "userId";
    public static final String ENCRYPTED_SESSION_ID_ATTRIBUTE = "encryptedSessionId";

    /**
     * Create a new session and store the user ID in it.
     *
     * @param request the HTTP request
     * @param userId  the user ID to store
     * @return the created session
     */
    public static HttpSession createSession(HttpServletRequest request, int userId) {
        HttpSession session = request.getSession(true);
        session.setAttribute(USER_ID_SESSION_ATTRIBUTE, userId);

        // Encrypt the session ID and store it in the session
        String sessionId = session.getId();
        String encryptedSessionId = BCrypt.hashpw(sessionId, BCrypt.gensalt());
        session.setAttribute(ENCRYPTED_SESSION_ID_ATTRIBUTE, encryptedSessionId);

        return session;
    }

    /**
     * Retrieve the user ID from the session.
     *
     * @param request the HTTP request
     * @return the user ID or null if not found
     */
    public static Integer getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object attribute = session.getAttribute(USER_ID_SESSION_ATTRIBUTE);
            if (attribute instanceof Integer) {
                return (Integer) attribute;
            }
        }
        return null;
    }

    /**
     * Retrieve the encrypted session ID from the session.
     *
     * @param request the HTTP request
     * @return the encrypted session ID or null if not found
     */
    public static String getEncryptedSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object attribute = session.getAttribute(ENCRYPTED_SESSION_ID_ATTRIBUTE);
            if (attribute instanceof String) {
                return (String) attribute;
            }
        }
        return null;
    }

    /**
     * Invalidate the session.
     *
     * @param request the HTTP request
     */
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * Validate the encrypted session ID against the current session ID.
     *
     * @param request the HTTP request
     * @param encryptedSessionId the encrypted session ID to validate
     * @return true if valid, false otherwise
     */
    public static boolean validateEncryptedSessionId(HttpServletRequest request, String encryptedSessionId) {
        HttpSession session = request.getSession(false);
        if (session != null && encryptedSessionId != null) {
            String sessionId = session.getId();
            return BCrypt.checkpw(sessionId, encryptedSessionId);
        }
        return false;
    }
}
