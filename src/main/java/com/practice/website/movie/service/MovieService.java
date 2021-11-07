package com.practice.website.movie.service;

import com.practice.website.movie.dao.MovieDao;
import com.practice.website.movie.domain.Movie;
import com.practice.website.movie.exception.NotFoundMovieException;

import java.sql.SQLException;

public class MovieService {

    private MovieDao movieDao;
    private String path;

    public MovieService(String path) {
        this.path = path;
        movieDao = new MovieDao(path);
    }

    public Movie findMovieById(Long id) throws Exception {
        return movieDao.findById(id).orElseThrow(() -> new NotFoundMovieException("찾을 수 없는 영화 id 입니다."));
    }
}
