package com.epam.cashregister.services.dao.queries;

public class UserQueries {

    public static String insertUser = "INSERT INTO users (role_id, first_name, last_name, email, avatar, password_hash) VALUES(?, ?, ?, ?, ?, ?)";
    public static String deleteUser = "DELETE FROM users WHERE email = ?";
    public static String selectAllUsers = "SELECT * FROM users";

}
