/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hazik
 */
public class SessionUtils {

    public static final String USER_ID_SESSION_ATTRIBUTE = "userId";

    /**
     * Creates a new session and stores the given user ID in the session.
     *
     * @param request The HttpServletRequest object.
     * @param userId The ID of the logged-in user.
     * @return The created HttpSession object.
     */
    public static HttpSession createSession(HttpServletRequest request, int userId) {
        HttpSession session = request.getSession(true);
        session.setAttribute(USER_ID_SESSION_ATTRIBUTE, userId);
        return session;
    }

    /**
     * Retrieves the user ID from the current session.
     *
     * @param request The HttpServletRequest object.
     * @return The user ID stored in the session, or null if no user ID is found.
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
     * Invalidates the current session.
     *
     * @param request The HttpServletRequest object.
     */
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}