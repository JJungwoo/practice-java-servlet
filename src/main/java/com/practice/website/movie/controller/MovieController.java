package com.practice.website.movie.controller;

import com.practice.website.movie.domain.Movie;
import com.practice.website.movie.dto.MovieListRes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "MovieController", value = "/movie/*")
public class MovieController extends HttpServlet {

    Map<Long, MovieListRes> movieList;

    public MovieController() {
        movieList = new HashMap<>();

        Random random = new Random();
        Long id1 = 1L;

        List<Movie> list = new ArrayList<>();
        list.add(Movie.builder()
                .mid(random.nextLong())
                .title("맥트리스")
                .country("미국")
                .build());

        list.add(Movie.builder()
                .mid(random.nextLong())
                .title("엑스맨")
                .country("미국")
                .build());

        list.add(Movie.builder()
                .mid(random.nextLong())
                .title("태극기휘날리며")
                .country("한국")
                .build());

        list.add(Movie.builder()
                .mid(random.nextLong())
                .title("베이비드라이버")
                .country("미국")
                .build());

        movieList.put(id1, MovieListRes.builder()
                            .mlistId(random.nextLong())
                            .mlistTitle("주간 상영 랭킹")
                            .movieList(list).build());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    // GET /movie/{id}/list
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

        Long moveListId = Long.valueOf(tokens[1]);
        if (!movieList.containsKey(moveListId)) {
            response.sendError(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        System.out.println("doGetMovieList");

        request.setAttribute("movieList", movieList);

        request.getRequestDispatcher("/test.jsp").forward(request, response);

    }
}
