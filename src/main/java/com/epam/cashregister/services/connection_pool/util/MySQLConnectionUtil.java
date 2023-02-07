package com.epam.cashregister.services.connection_pool.util;

import com.epam.cashregister.contollers.servlets.viewservlets.LoginServlet;
import com.epam.cashregister.services.utils.PropertiesManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnectionUtil {

    final static Logger logger = Logger.getLogger(LoginServlet.class);

    public static String URL;
    public static String USER;
    public static String PASS;

    static {
        Properties dbProperties = PropertiesManager.getPropertyFile("db.properties");

        if (dbProperties == null) {
            logger.error("Unable to load db.properties file from resources folder");
            throw new RuntimeException("Unable to load db.properties file from resources folder");
        }

        URL = dbProperties.getProperty("db.url");
        USER = dbProperties.getProperty("db.user");
        PASS =  dbProperties.getProperty("db.pass");
    }

    public static Connection getMySQLConnection() {

        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) { throw new RuntimeException(e); }

        try { return DriverManager.getConnection(URL, USER, PASS); }
        catch (SQLException e) { throw new RuntimeException(e); }

    }

    public static void setTestingMode() {
        Properties dbProperties = PropertiesManager.getPropertyFile("test_db.properties");

        if (dbProperties == null) {
            logger.error("Unable to load test_db.properties file from resources folder");
            throw new RuntimeException("Unable to load test_db.properties file from resources folder");
        }

        URL = dbProperties.getProperty("db.url");
        USER = dbProperties.getProperty("db.user");
        PASS =  dbProperties.getProperty("db.pass");
    }
}
