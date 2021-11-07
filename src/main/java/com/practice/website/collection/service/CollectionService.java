package com.practice.website.collection.service;

import com.practice.website.collection.dao.CollectionDao;
import com.practice.website.collection.domain.Collection;
import com.practice.website.collection.exception.NotFoundCollectionException;
import com.practice.website.movie.dao.MovieDao;
import com.practice.website.movie.domain.Movie;
import com.practice.website.movie.exception.NotFoundMovieException;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionService {

    private final CollectionDao collectionDao;
    private final MovieDao movieDao;

    public CollectionService(String path) {
        collectionDao = new CollectionDao(path);
        movieDao = new MovieDao(path);
    }

    public List<Movie> findByIdReturnMovieList(Long id) throws Exception {
        Collection collection = collectionDao.findById(id).orElseThrow(() -> new NotFoundCollectionException("찾을 수 없는 컬렉션 id 입니다."));

        Set<Long> movieIdSet = parsingStrToMovieIdSet(collection.getMovieList());
        if (movieIdSet.size() == 0) {
            throw new NotFoundCollectionException("찾을 수 없는 컬렉션 id 입니다.");
        }

        List<Movie> movieList = movieDao.selectAll();
        if (movieList.size() == 0) {
            throw new NotFoundMovieException("영화 정보가 하나도 없습니다.");
        }

        List<Movie> selectedMovieList = movieList
                                .stream().filter(movie -> movieIdSet.contains(movie.getId()))
                                .collect(Collectors.toList());

        if (selectedMovieList.size() == 0) {
            throw new NotFoundCollectionException("해당 컬렉션에 소속된 영화가 없습니다.");
        }

        return selectedMovieList;
    }

    public Set<Long> parsingStrToMovieIdSet(String movieStrList) {
        Set<Long> movieIdSet = new HashSet<>();

        StringTokenizer stringTokenizer = new StringTokenizer(movieStrList, ",");
        while (stringTokenizer.hasMoreTokens()) {
            movieIdSet.add(Long.parseLong(stringTokenizer.nextToken()));
        }
        return movieIdSet;
    }
}
