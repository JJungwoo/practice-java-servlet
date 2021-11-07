package com.practice.website.collection.controller;

import com.practice.website.collection.service.CollectionService;
import com.practice.website.movie.controller.MovieController;
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
import java.util.List;

@WebServlet(name = "CollectionController", value = "/col")
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
        List<Movie> movieList = null;
        try {
            movieList = collectionService.findByIdReturnMovieList(17L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Movie m : movieList) {
            System.out.println(m.getTitle());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
