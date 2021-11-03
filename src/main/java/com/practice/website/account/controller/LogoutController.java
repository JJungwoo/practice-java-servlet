package com.practice.website.account.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("LogoutServlet doGet");
        HttpSession session = request.getSession(true);
        System.out.println(String.valueOf(session.getAttribute("userid")));
        session.removeAttribute("userid");
        System.out.println(String.valueOf(session.getAttribute("userid")));
        session.invalidate();
        response.sendRedirect("/");
    }
}
