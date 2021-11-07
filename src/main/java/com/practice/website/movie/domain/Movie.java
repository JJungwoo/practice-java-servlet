package com.practice.website.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Movie {

    private Long id;
    private String title;
    private LocalDate openDate;
    private String genre;
    private Integer runTime;
    private Integer limitedAge;
    private String detail;
    private String posterUrl;
    private String originName;
    private String nation;

}
