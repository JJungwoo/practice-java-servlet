package com.practice.website.util;

import java.sql.*;
import java.util.Properties;

public class DBUtil {

    static final String applicationPropertiesFilePath = "src/main/resources/application.properties";

    public static Connection dbConnect(String path) {

        String transPath = path.replaceAll("\\\\", "/");

        Properties properties = FileIOUtil.jdbcGetPropertise(transPath.substring(0, transPath.lastIndexOf("target")) + applicationPropertiesFilePath);

        String DRIVER_CLASS_NAME = properties.getProperty("driver-class-name");
        String DB_URL = properties.getProperty("url");
        String DB_USER = properties.getProperty("userid");
        String DB_PASSWORD = properties.getProperty("password");
        Connection conn = null;

        try {
            Class.forName(DRIVER_CLASS_NAME);
            DB_URL = DB_URL + transPath + "/WEB-INF/Wallet_cometdb1";
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void dbClose(Connection conn, PreparedStatement st, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
