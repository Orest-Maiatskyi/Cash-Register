package com.epam.cashregister.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class UserBean implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String password_1;
    private String password_2;
    private String passwordHash;
    private String avatar;
    private String role;
    private int roleId;
    private LinkedHashMap<String, String[]> actions;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword_1() {
        return password_1;
    }

    public String getPassword_2() {
        return password_2;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getRole() {
        return role;
    }

    public int getRoleId() {
        return roleId;
    }

    public HashMap<String, String[]> getActions() {
        return actions;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword_1(String password_1) {
        this.password_1 = password_1;
    }

    public void setPassword_2(String password_2) {
        this.password_2 = password_2;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setActions(LinkedHashMap<String, String[]> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return
                "firstName: " + getFirstName() + "\n" +
                "lastName: " + getLastName() + "\n" +
                "email: " + getEmail() + "\n" +
                "avatar: " + getAvatar() + "\n" +
                "role: " + getRole() + "\n" +
                "actions: " + getActions() + "\n";
    }
}
