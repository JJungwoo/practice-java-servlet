package com.practice.website.account.dao;

import com.practice.website.account.domain.User;
import com.practice.website.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MemberDao {

    String path;

    public MemberDao(String path) {
        this.path = path;
    }

    public void insert(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.dbConnect(path);
            String sql = createQueryForInsert();
            pstmt = conn.prepareStatement(sql);
        } finally {
            DBUtil.dbClose(conn, null, null);
        }
    }

    private void setValuesForInsert(User user, PreparedStatement pstmt) throws SQLException {

    }

    private String createQueryForInsert() {
        return "INSERT INTO MEMBERS VALUES (Members_SEQ.nextval, ?, ?, ?, ?, ?)";
    }
}
