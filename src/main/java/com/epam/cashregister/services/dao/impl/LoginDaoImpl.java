package com.epam.cashregister.services.dao.impl;

import com.epam.cashregister.entities.LoginBean;
import com.epam.cashregister.entities.UserBean;
import com.epam.cashregister.services.connection_pool.ConnectionPool;
import com.epam.cashregister.services.crypto.PBKDF2;
import com.epam.cashregister.services.crypto.Util;
import com.epam.cashregister.services.dao.LoginDao;
import com.epam.cashregister.services.dao.queries.LoginQueries;
import com.epam.cashregister.services.utils.PropertiesManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class LoginDaoImpl implements LoginDao {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @Override
    public UserBean authenticate(LoginBean loginBean) {

        String sql = LoginQueries.selectUserByEmail;
        UserBean user = null;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, loginBean.getEmail());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getString("password_hash").equals(
                        Util.encodeBase64(
                                PBKDF2.generateDefaultHash(
                                        loginBean.getPassword(),
                                        Util.decodeBase64(PropertiesManager.getPropertyFile("db.properties").getProperty("db.base64salt")))))) {

                    user = new UserBean();
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setEmail(loginBean.getEmail());
                    user.setAvatar(resultSet.getString("avatar"));
                    user.setRole(resultSet.getString("role"));

                    LinkedHashMap<String, String[]> actions = new LinkedHashMap<>();
                    JSONObject actionsJson = new JSONObject(resultSet.getString("actions"));

                    for (String category : JSONObject.getNames(actionsJson)) {
                        JSONArray actionsArray = actionsJson.getJSONArray(category);
                        String[] tempActions = new String[actionsArray.length()];
                        for (int i = 0; i < actionsArray.length(); i++)
                            tempActions[i] = (String) actionsArray.get(i);

                        if (category.equals("MAIN") && actions.size() != 0) {
                            LinkedHashMap<String, String[]> newMap = (LinkedHashMap<String, String[]>) actions.clone();
                            actions.clear();
                            actions.put(category, tempActions);
                            actions.putAll(newMap);
                        } else actions.put(category, tempActions);
                    }


                    user.setActions(actions);
                }
            }

        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return user;
    }
}
