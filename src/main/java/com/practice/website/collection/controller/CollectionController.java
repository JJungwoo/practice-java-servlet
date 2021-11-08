package com.practice.website.collection.controller;

import com.practice.website.collection.service.CollectionService;
import com.practice.website.movie.controller.MovieController;
import com.practice.website.movie.domain.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CollectionController", value = "/collection/*")
public class CollectionController extends HttpServlet {

    private Logger logger;
    private CollectionService collectionService;

    @Override
    public void init() throws ServletException {
        super.init();
        collectionService = new CollectionService(getServletContext().getRealPath("."));
        logger = LogManager.getLogger(MovieController.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    // GET /collection/{id}
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

        Long collectionId = Long.valueOf(tokens[1]);
        List<Movie> movieList = null;

        try {
            movieList = collectionService.findByIdReturnMovieList(collectionId);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NO_CONTENT);
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        request.setAttribute("movieList", movieList);

        request.getRequestDispatcher("/movieListView.jsp").forward(request, response);
    }
}
