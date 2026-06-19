package com.template.servlet;

import com.template.dao.StudentDao;
import com.template.entity.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/students/*")
public class StudentServlet extends HttpServlet {

    private StudentDao dao;

    @Override
    public void init() {
        dao = new StudentDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Student> students = dao.findAll();
            req.setAttribute("students", students);
            req.getRequestDispatcher("/WEB-INF/views/students.jsp").forward(req, resp);

        } else if (pathInfo.equals("/new")) {
            req.getRequestDispatcher("/WEB-INF/views/student-form.jsp").forward(req, resp);

        } else if (pathInfo.matches("/\\d+/edit")) {
            Long id = extractId(pathInfo);
            req.setAttribute("student", dao.findById(id));
            req.getRequestDispatcher("/WEB-INF/views/student-form.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            Student s = buildFromRequest(req);
            dao.save(s);
            req.getSession().setAttribute("successMsg", "Student created.");
            resp.sendRedirect(req.getContextPath() + "/students/");

        } else if (pathInfo.matches("/\\d+/delete")) {
            dao.delete(extractId(pathInfo));
            resp.sendRedirect(req.getContextPath() + "/students/");
        }
    }

    private Student buildFromRequest(HttpServletRequest req) {
        Student s = new Student();
        s.setFirstName(req.getParameter("firstName").trim());
        s.setLastName(req.getParameter("lastName").trim());
        s.setEmail(req.getParameter("email").trim());
        String dob = req.getParameter("dob");
        if (dob != null && !dob.isBlank()) {
            s.setDob(LocalDate.parse(dob));
        }
        return s;
    }

    private Long extractId(String pathInfo) {
        return Long.parseLong(pathInfo.replaceAll("[^0-9]", ""));
    }
}
