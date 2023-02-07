package com.epam.cashregister.services.connection_pool.util;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtil extends MySQLConnectionUtil {

    public static Connection getConnection() {
        return getMySQLConnection();
    }

    public static void closeConnection(Connection connection) {
        try { connection.close(); }
        catch (SQLException e) { throw new RuntimeException(e); }
    }

}
