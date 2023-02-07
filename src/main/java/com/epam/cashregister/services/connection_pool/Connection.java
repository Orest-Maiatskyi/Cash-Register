package com.epam.cashregister.services.connection_pool;


import com.epam.cashregister.services.connection_pool.util.ConnectionUtil;

import java.sql.SQLException;

public class Connection {

    private boolean inUse;
    private final long creationTime;
    private final java.sql.Connection connection;

    public Connection() {
        inUse = false;
        creationTime = System.currentTimeMillis();
        connection = ConnectionUtil.getConnection();
    }

    public void close() {
        try { connection.close(); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    public boolean isInUse() {
        return inUse;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public java.sql.Connection getConnection() {
        return connection;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

}
