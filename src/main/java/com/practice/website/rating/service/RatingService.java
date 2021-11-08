package com.practice.website.rating.service;

import com.practice.website.rating.dao.RatingDao;
import com.practice.website.rating.domain.Rating;

import java.sql.SQLException;

public class RatingService {


    private final RatingDao ratingDao;

    public RatingService(String path) {
        ratingDao = new RatingDao(path);
    }

    public Rating findById(Long uid, Long mid) throws SQLException {
        return ratingDao.findByUidAndMid(uid, mid);
    }
}
