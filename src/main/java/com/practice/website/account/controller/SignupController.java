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
import java.io.IOException;

@WebServlet(name = "SignupController",  value = "/signup")
public class SignupController extends HttpServlet {

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
        String name = request.getParameter("name");

        logger.info("request {} {} {} ", email, password, name);

        try {
            userService.insert(User.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
