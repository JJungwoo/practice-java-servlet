package com.practice.website.movie.dto;

import com.practice.website.movie.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class MovieListRes {

    private Long mlistId;

    private String mlistTitle;

    private List<Movie> movieList;

}
