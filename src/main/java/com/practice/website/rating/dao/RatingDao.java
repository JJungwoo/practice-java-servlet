package com.practice.website.rating.dao;

import com.practice.website.rating.domain.Rating;
import com.practice.website.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingDao {

    private final String path;

    public RatingDao(String path) {
        this.path = path;
    }

    public Rating findByUidAndMid(Long uid, Long mid) throws SQLException {
        Rating rating = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.dbConnect(path);
            String sql = createQueryForFindByUidANDMid();
            pstmt = conn.prepareStatement(sql);
            setValuesForFindByUidAndMid(uid, mid, pstmt);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                rating =  Rating.builder()
                        .id(rs.getLong("Rating_SEQ"))
                        .uid(rs.getLong("user_id"))
                        .mid(rs.getLong("movie_id"))
                        .score(rs.getLong("rscore"))
                        .comment(rs.getString("rcomment"))
                        .build();
            }
        } finally {
            DBUtil.dbClose(conn, pstmt, rs);
        }

        return rating;
    }

    private void setValuesForFindByUidAndMid(Long uid, Long mid, PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, uid);
        pstmt.setLong(2, mid);
    }

    private String createQueryForFindByUidANDMid() {
        return "select * from RATING where user_id = ? and movie_id = ?;";
    }

}
