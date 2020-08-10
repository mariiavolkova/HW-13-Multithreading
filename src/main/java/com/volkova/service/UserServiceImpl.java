package com.volkova.service;

import com.volkova.dao.UserDao;
import com.volkova.dao.UserDaoIml;
import com.volkova.model.User;
import com.volkova.model.UserRole;

import java.util.List;

public class UserServiceImpl implements UserService{

    private static final UserService userService = new UserServiceImpl();

    private User activeUser;

    private final UserDao userDao = UserDaoIml.getInstance();

    private UserServiceImpl() {
    }

    public static UserService getInstance(){
        return userService;
    }

    @Override
    public boolean login(String username, String password) {
        User user = userDao.findByName(username);
        if (user != null && user.getPassword().equals(password) && user.isActive()) {
            this.activeUser = user;
            return true;
        }
        return false;
    }

    @Override
    public boolean register(String username, String password) {
        if (userDao.findByName(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(UserRole.CUSTOMER);
            user.setActive(true);
            userDao.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public void blockUser(long id) {
        User user = userDao.findById(id);
        user.setActive(false);
        userDao.update(user);
    }

    @Override
    public void unBlockUser(long id) {
        User user = userDao.findById(id);
        user.setActive(true);
        userDao.update(user);
    }

    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public User findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User getActiveUser() {
        return activeUser;
    }

    @Override
    public void setActiveUser(User user) {
        this.activeUser = user;
    }

}
