package com.practice.website.rating.controller;

import com.practice.website.movie.controller.MovieController;
import com.practice.website.movie.domain.Movie;
import com.practice.website.movie.service.MovieService;
import com.practice.website.rating.domain.Rating;
import com.practice.website.rating.service.RatingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RatingController", value = "/rating/*")
public class RatingController extends HttpServlet {

    private Logger logger;
    private RatingService ratingService;

    @Override
    public void init() throws ServletException {
        super.init();
        ratingService = new RatingService(getServletContext().getRealPath("."));
        logger = LogManager.getLogger(RatingController.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    // GET /rating/{uid}/{mid}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            return;
        }

        String[] tokens = pathInfo.split("/");
        if (tokens.length != 3) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Long userId = Long.valueOf(tokens[1]);
        Long movieId = Long.valueOf(tokens[2]);
        Rating rating = null;

        try {
            rating = ratingService.findById(userId, movieId);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NO_CONTENT);
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        request.setAttribute("rating", rating);

        logger.info("GET Rating {} {}", rating.getId(), rating.getScore());

        request.getRequestDispatcher("/ratingResult.jsp").forward(request, response);
    }
}
