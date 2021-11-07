package com.practice.website.collection.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Collection {

    private Long id;
    private String name;
    private String desc;
    private String movieList;

}
