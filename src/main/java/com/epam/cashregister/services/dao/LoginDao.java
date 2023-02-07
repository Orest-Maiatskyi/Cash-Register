package com.epam.cashregister.services.dao;

import com.epam.cashregister.entities.LoginBean;
import com.epam.cashregister.entities.UserBean;

public interface LoginDao {
    UserBean authenticate(LoginBean loginBean);
}
