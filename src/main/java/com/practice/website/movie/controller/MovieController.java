package com.practice.website.movie.controller;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "MovieController", value = "/movie/*")
public class MovieController extends HttpServlet {

    private Logger logger;
    private MovieService movieService;
    private RatingService ratingService;

    @Override
    public void init() throws ServletException {
        super.init();
        movieService = new MovieService(getServletContext().getRealPath("."));
        ratingService = new RatingService(getServletContext().getRealPath("."));
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

        List<Rating> ratingList = new ArrayList<>();
        Long movieId = Long.valueOf(tokens[1]);
        int ratingCount = 0;
        double avgScore = 0.0;
        Movie movie = null;
        Rating rating = null;

        HttpSession session = request.getSession(true);
        String userIdStr = Optional.ofNullable(session.getAttribute("id")).map(Object::toString).orElse(null);

        try {
            if (userIdStr != null) {
                rating = ratingService.findById(Long.parseLong(userIdStr), movieId);
            }
            movie = movieService.findMovieById(movieId);
            ratingList = ratingService.selectAllByMid(movieId);
            avgScore = ratingService.getAvgMovieRating(ratingList);
            ratingCount = ratingList.size();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NO_CONTENT);
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        request.setAttribute("movie", movie);
        request.setAttribute("rating", rating);
        request.setAttribute("avgScore", avgScore);
        request.setAttribute("ratingCount", ratingCount);

        logger.info("GET Movie {} {}", movie.getId(), movie.getTitle());

        request.getRequestDispatcher("/movieDetail.jsp").forward(request, response);
    }
}
