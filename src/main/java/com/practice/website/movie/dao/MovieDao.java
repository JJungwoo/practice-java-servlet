package com.practice.website.movie.dao;

import com.practice.website.movie.domain.Movie;
import com.practice.website.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDao {

    private String path;

    public MovieDao(String path) {
        this.path = path;
    }

    public List<Movie> selectAll() throws SQLException {
        List<Movie> movieList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.dbConnect(path);
            String sql = createQueryForSelectAll();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                movieList.add(Movie.builder()
                        .id(rs.getLong("Movie_SEQ"))
                        .title(rs.getString("title"))
                        .openDate(rs.getDate("open_date").toLocalDate())
                        .genre(rs.getString("genre"))
                        .runTime(rs.getInt("run_time"))
                        .limitedAge(rs.getInt("limited_age"))
                        .detail(rs.getString("detail"))
                        .posterUrl(rs.getString("poster_url"))
                        .originName(rs.getString("origin_name"))
                        .nation(rs.getString("nation"))
                        .build());
            }

        } finally {
            DBUtil.dbClose(conn, null, null);
        }

        return movieList;
    }

    private String createQueryForSelectAll() {
        return "SELECT * FROM MOVIES";
    }

    public void insert(Movie movie) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.dbConnect(path);
            String sql = createQueryForInsert();
            pstmt = conn.prepareStatement(sql);
            setValuesForInsert(movie, pstmt);

            pstmt.executeUpdate();
        } finally {
            DBUtil.dbClose(conn, null, null);
        }
    }

    private void setValuesForInsert(Movie movie, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, movie.getTitle());
        pstmt.setDate(2, Date.valueOf(movie.getOpenDate()));
        pstmt.setString(3, movie.getGenre());
        pstmt.setInt(4, movie.getRunTime());
        pstmt.setInt(5, movie.getLimitedAge());
        pstmt.setString(6, movie.getDetail());
        pstmt.setString(7, movie.getPosterUrl());
        pstmt.setString(8, movie.getOriginName());
        pstmt.setString(9, movie.getNation());
    }

    private String createQueryForInsert() {
        return "INSERT INTO MOVIES VALUES (Movie_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public Optional<Movie> findById(Long id) throws SQLException {
        Movie movie = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.dbConnect(path);
            String sql = createQueryForFindById();
            pstmt = conn.prepareStatement(sql);
            setValuesForFindById(id, pstmt);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                movie =  Movie.builder()
                        .id(rs.getLong("Movie_SEQ"))
                        .title(rs.getString("title"))
                        .openDate(rs.getDate("open_date").toLocalDate())
                        .genre(rs.getString("genre"))
                        .runTime(rs.getInt("run_time"))
                        .limitedAge(rs.getInt("limited_age"))
                        .detail(rs.getString("detail"))
                        .posterUrl(rs.getString("poster_url"))
                        .originName(rs.getString("origin_name"))
                        .nation(rs.getString("nation"))
                        .build();
            }
        } finally {
            DBUtil.dbClose(conn, pstmt, rs);
        }

        return Optional.ofNullable(movie);
    }

    private void setValuesForFindById(Long id, PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, id);
    }

    private String createQueryForFindById() {
        return "SELECT * FROM MOVIES WHERE ID = ?";
    }
}
