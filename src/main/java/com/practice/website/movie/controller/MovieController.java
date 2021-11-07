package com.practice.website.movie.controller;

import com.practice.website.movie.domain.Movie;
import com.practice.website.movie.service.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MovieController", value = "/movie/*")
public class MovieController extends HttpServlet {

    private Logger logger;
    private MovieService movieService;

    @Override
    public void init() throws ServletException {
        super.init();
        movieService = new MovieService(getServletContext().getRealPath("."));
        logger = LogManager.getLogger(MovieController.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    // GET /movie/{id}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            return;
        }

        String[] tokens = pathInfo.split("/");
        if (tokens.length != 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Long moveId = Long.valueOf(tokens[1]);
        Movie movie = null;

        try {
            movie = movieService.findMovieById(moveId);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NO_CONTENT);
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        request.setAttribute("movie", movie);

        request.getRequestDispatcher("/").forward(request, response);

    }
}
