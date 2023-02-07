package com.epam.cashregister.services.dao.queries;

public class LoginQueries {

    public static String selectUserByEmail = "SELECT users.first_name, users.last_name, users.avatar, users.password_hash, roles.role, roles.actions FROM users INNER JOIN roles ON users.role_id = roles.id WHERE email = ?";

}
