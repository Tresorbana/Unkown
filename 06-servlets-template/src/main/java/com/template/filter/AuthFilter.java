package com.template.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final Set<String> PUBLIC_PATHS = Set.of("/login", "/register", "/css", "/js", "/images");

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  httpReq  = (HttpServletRequest)  req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;

        String path = httpReq.getServletPath();

        if (isPublic(path)) {
            chain.doFilter(req, resp);
            return;
        }

        HttpSession session = httpReq.getSession(false);
        if (session != null && session.getAttribute("currentUser") != null) {
            chain.doFilter(req, resp);
        } else {
            httpResp.sendRedirect(httpReq.getContextPath() + "/login");
        }
    }

    private boolean isPublic(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
}
