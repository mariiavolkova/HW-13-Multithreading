package com.volkova.service;

import com.volkova.model.User;

import java.util.List;

public interface UserService {

    boolean login(String username, String password);

    boolean register(String username, String password);

    void update(User user);

    void delete(User user);

    void blockUser(long id);

    void unBlockUser(long id);

    User findByName(String name);

    User findById(long id);

    List<User> findAll();

    User getActiveUser();

    void setActiveUser(User user);
}