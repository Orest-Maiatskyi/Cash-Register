package com.epam.cashregister.services.dao;

import com.epam.cashregister.entities.UserBean;

public interface UserDao {

    boolean addUser(UserBean userBean);
    boolean updateUser(UserBean userBean);
    boolean removeUser(UserBean userBean);

    UserBean[] getAllUsers();
}
