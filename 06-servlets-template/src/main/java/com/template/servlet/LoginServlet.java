package com.template.servlet;

import com.template.dao.UserDao;
import com.template.entity.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {

    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDao();
    }

    @Override
    protected void handleGet(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        if (req.getSession(false) != null &&
            req.getSession().getAttribute("currentUser") != null) {
            redirect(req, resp, "/dashboard");
            return;
        }
        forward(req, resp, "auth/login");
    }

    @Override
    protected void handlePost(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        String email    = param(req, "email");
        String password = param(req, "password");

        User user = userDao.findByEmail(email);

        if (user == null || !user.passwordMatches(password)) {
            req.setAttribute("error", "Invalid email or password.");
            forward(req, resp, "auth/login");
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("currentUser", user);
        session.setMaxInactiveInterval(30 * 60);   // 30 minutes

        redirect(req, resp, "/dashboard");
    }
}
