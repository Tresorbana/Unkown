package com.template.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Base servlet — enforces a consistent request-dispatch pattern.
 * Subclasses override handleGet / handlePost instead of doGet / doPost
 * so that exception handling and logging stay in one place.
 */
public abstract class BaseServlet extends HttpServlet {

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            handleGet(req, resp);
        } catch (Exception ex) {
            handleError(req, resp, ex);
        }
    }

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            handlePost(req, resp);
        } catch (Exception ex) {
            handleError(req, resp, ex);
        }
    }

    protected void handleGet(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    protected void handlePost(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    protected void forward(HttpServletRequest req, HttpServletResponse resp, String view)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/" + view + ".jsp").forward(req, resp);
    }

    protected void redirect(HttpServletRequest req, HttpServletResponse resp, String path)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + path);
    }

    protected String param(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return (v != null) ? v.trim() : "";
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, Exception ex)
            throws ServletException, IOException {
        req.setAttribute("errorMessage", ex.getMessage());
        forward(req, resp, "error/500");
    }
}
