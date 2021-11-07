package com.practice.website.collection.dao;

import com.practice.website.collection.domain.Collection;
import com.practice.website.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CollectionDao {

    private String path;

    public CollectionDao(String path) {
        this.path = path;
    }

    public Optional<Collection> findById(Long id) throws SQLException {
        Collection collection = null;
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
                collection = Collection.builder()
                        .id(rs.getLong("coll_no"))
                        .name(rs.getString("name"))
                        .desc(rs.getString("description"))
                        .movieList(rs.getString("movie_list"))
                        .build();
            }
        } finally {
            DBUtil.dbClose(conn, pstmt, rs);
        }

        return Optional.ofNullable(collection);
    }

    private void setValuesForFindById(Long id, PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, id);
    }

    private String createQueryForFindById() {
        return "SELECT * FROM MOVIE_COLLECTIONS WHERE COLL_NO = ?";
    }
}
