package com.epam.cashregister.services.connection_pool;


import java.util.Iterator;
import java.util.Vector;

public class ConnectionPool {

    public static class Manager implements Runnable {

        public static long expirationTime = 1000 * 60 * 5; // 5 minutes in milliseconds by default

        private synchronized void checkExpirationTime() {

            long currentTime = System.currentTimeMillis();

            /*
            for (Connection pooledConnection : pool) {
                if (pooledConnection.getCreationTime() + expirationTime < currentTime && !pooledConnection.isInUse()) {
                    pooledConnection.close();
                    pool.remove(pooledConnection);

                    System.out.println("ONE CONNECTION CLOSED ! CURRENT COUNT: " + pool.size());
                }
            }
            */

            for (Iterator<Connection> iterator = pool.iterator(); iterator.hasNext();) {
                Connection pooledConnection = iterator.next();
                if (pooledConnection.getCreationTime() + expirationTime < currentTime && !pooledConnection.isInUse()) {
                    pooledConnection.close();
                    iterator.remove();
                }
            }
        }

        @Override
        public void run() {
            while (pool.size() > 0) {
                checkExpirationTime();
                try {
                    Thread.sleep(expirationTime);
                    checkExpirationTime();
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
            manager = null;
        }

    }

    public static final Vector<Connection> pool = new Vector<>();
    public static Manager manager;

    public static synchronized java.sql.Connection borrowConnection() {
        // if (pool == null) pool = new Vector<>();

        Connection loanConnection = null;

        for (Connection pooledConnection : pool) {
            if (!pooledConnection.isInUse()) {
                pooledConnection.setInUse(true);
                loanConnection = pooledConnection;
                break;
            }
        }

        if (loanConnection == null) {
            loanConnection = new Connection();
            loanConnection.setInUse(true);
            pool.add(loanConnection);
        }

        if (manager == null) {
            manager = new Manager();
            Thread currentManager = new Thread(manager);
            currentManager.setDaemon(true);
            currentManager.start();
        }

        return loanConnection.getConnection();
    }

    public static synchronized void returnConnection(java.sql.Connection connection) {
        // if (pool != null) {
            for (Connection pooledConnection : pool) {
                if (pooledConnection.getConnection() == connection) {
                    pooledConnection.setInUse(false);
                    break;
                }
            }
        // }

    }

}
