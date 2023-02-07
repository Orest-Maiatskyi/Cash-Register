package com.epam.cashregister.entities;

import java.io.Serializable;

public class LoginBean implements Serializable {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return
                "email: " + getEmail() + "\n" +
                "password: " + getPassword() + "\n";
    }
}
