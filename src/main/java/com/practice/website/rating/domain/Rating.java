package com.practice.website.rating.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Rating {

    private Long id;
    private Long uid;
    private Long mid;
    private Long score;
    private String comment;
}
