package utils;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarinaDB4jHelper {


    public static class SqlRunner {
        private static final String DEFAULT_DELIMITER = ";";
        private static final Pattern NEW_DELIMITER_PATTERN = Pattern.compile("(?:--|\\/\\/|\\#)?!DELIMITER=(.+)");
        private static final Pattern COMMENT_PATTERN = Pattern.compile("^(?:--|\\/\\/|\\#).+");

        public static void runScript(Connection connection, FileReader scriptInputStream) throws SQLException, IOException {
            try (BufferedReader scriptReader = new BufferedReader(scriptInputStream)) {
                StringBuffer command = null;
                String delimiter = DEFAULT_DELIMITER;
                String line = null;
                while ((line = scriptReader.readLine()) != null) {
                    if (command == null) {
                        command = new StringBuffer();
                    }
                    String trimmedLine = line.trim();
                    Matcher delimiterMatcher = NEW_DELIMITER_PATTERN.matcher(trimmedLine);
                    Matcher commentMatcher = COMMENT_PATTERN.matcher(trimmedLine);
                    if (delimiterMatcher.find()) {
                        delimiter = delimiterMatcher.group(1);
                    }
                    else if (commentMatcher.find()) {

                    }
                    else {
                        command.append(trimmedLine);
                        command.append(" ");
                        if (trimmedLine.endsWith(delimiter)) {
                            Statement statement = connection.createStatement();
                            System.out.println(command);
                            statement.execute(command.toString());
                            statement.close();
                            command = null;
                        }
                    }
                }
            }
        }
    }


    public static DBConfigurationBuilder configBuilder;
    public static DB db;

    private MarinaDB4jHelper() {  }

    public static DB openInMemoryDB() throws ManagedProcessException {
        if (db != null) return db;
        configBuilder = DBConfigurationBuilder.newBuilder();
        configBuilder.setPort(8888);
        db = DB.newEmbeddedDB(configBuilder.build());
        db.start();
        return db;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(configBuilder.getURL("test"), "root", "");
    }

    public static void closeInMemoryDB() throws ManagedProcessException {
        if (db != null) {
            db.stop();
            db = null;
        }
    }
}
