package com.practice.website.account.controller;

import com.practice.website.account.domain.User;
import com.practice.website.account.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginController extends HttpServlet {

    private Logger logger;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService(getServletContext().getRealPath("."));
        logger = LogManager.getLogger(LoginController.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = null;

        logger.info("request email:{}, password:{}", email, password);

        try {
            user = userService.findByEmail(email);
            if (!user.getPassword().equals(password)) {
                request.setAttribute("message", "패스워드가 잘못되었습니다.");
                return;
            }

            HttpSession session = request.getSession(true);
            if (session.getAttribute("userid") == null) {
                session.setAttribute("userid", user.getName());
                session.setAttribute("oauth", "self");
                session.setAttribute("accessToken", "1");
                session.setMaxInactiveInterval(60 * 30);    // 30분
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
