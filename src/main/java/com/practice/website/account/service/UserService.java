package com.practice.website.account.service;

import com.practice.website.account.dao.UserDao;
import com.practice.website.account.domain.User;

import java.sql.SQLException;

public class UserService {

    private UserDao userDao;
    private String path;

    public UserService(String path) {
        this.path = path;
        userDao = new UserDao(path);
    }

    public User findByEmail(String email) throws Exception {
        User user = null;
        try {
            user = userDao.findByEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void insert(User user) throws Exception {
        userDao.insert(user);
    }
}
