package com.practice.website.account.dao;

import com.practice.website.account.domain.User;
import com.practice.website.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    String path;

    public UserDao(String path) {
        this.path = path;
    }

    public void insert(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.dbConnect(path);
            String sql = createQueryForInsert();
            pstmt = conn.prepareStatement(sql);
            setValuesForInsert(user, pstmt);

            pstmt.executeUpdate();
        } finally {
            DBUtil.dbClose(conn, null, null);
        }
    }

    private void setValuesForInsert(User user, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getEmail());
    }

    private String createQueryForInsert() {
        return "INSERT INTO USERS VALUES (User_SEQ.nextval, ?, ?, ?)";
    }

    public User findByEmail(String email) throws SQLException {
        User user = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.dbConnect(path);
            String sql = createQueryForFindByEmail();
            pstmt = conn.prepareStatement(sql);
            setValuesForFindByEmail(email, pstmt);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                user =  User.builder()
                        .nid(rs.getLong("user_seq"))
                        .name(rs.getString("name"))
                        .password(rs.getString("password"))
                        .email(rs.getString("email"))
                        .build();
            }
        } finally {
            DBUtil.dbClose(conn, pstmt, rs);
        }

        return user;
    }

    private void setValuesForFindByEmail(String email, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, email);
    }

    private String createQueryForFindByEmail() {
        return "SELECT * FROM USERS WHERE EMAIL = ?";
    }
}
