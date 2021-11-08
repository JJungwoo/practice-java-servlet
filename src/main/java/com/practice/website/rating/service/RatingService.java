package com.practice.website.rating.service;

import com.practice.website.rating.dao.RatingDao;
import com.practice.website.rating.domain.Rating;

import java.sql.SQLException;
import java.util.List;

public class RatingService {


    private final RatingDao ratingDao;

    public RatingService(String path) {
        ratingDao = new RatingDao(path);
    }

    public Rating findById(Long uid, Long mid) throws SQLException {
        return ratingDao.findByUidAndMid(uid, mid);
    }

    public double getAvgMovieRating(List<Rating> ratingList) throws SQLException {
        double avgScore = 0.0;
        for (Rating rating : ratingList) {
            avgScore += rating.getScore();
        }
        return avgScore / ratingList.size();
    }

    public List<Rating> selectAllByMid(Long mid) throws SQLException {
        return ratingDao.selectAllByMid(mid);
    }
}
