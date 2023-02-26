package com.epam.cashregister.services.dao.impl;

import com.epam.cashregister.entities.UserBean;
import com.epam.cashregister.services.connection_pool.ConnectionPool;
import com.epam.cashregister.services.dao.UserDao;
import com.epam.cashregister.services.dao.queries.UserQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public UserDaoImpl() {  }

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addUser(UserBean userBean) {

        String sql = UserQueries.insertUser;
        boolean isCorrect = true;

        try {
            connection = connection == null ? ConnectionPool.borrowConnection() : connection;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userBean.getRoleId());
            preparedStatement.setString(2, userBean.getFirstName());
            preparedStatement.setString(3, userBean.getLastName());
            preparedStatement.setString(4, userBean.getEmail());
            preparedStatement.setString(5, userBean.getAvatar());
            preparedStatement.setString(6, userBean.getPasswordHash());

            if (preparedStatement.executeUpdate() == 0) isCorrect = false;
        } catch (SQLException e) {
            isCorrect = false;
            e.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return isCorrect;
    }

    @Override
    public boolean updateUser(UserBean userBean) {
        return false;
    }

    @Override
    public boolean removeUser(UserBean userBean) {

        String sql = UserQueries.deleteUser;
        boolean isCorrect = true;

        try {
            connection = connection == null ? ConnectionPool.borrowConnection() : connection;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userBean.getEmail());
            if (preparedStatement.executeUpdate() == 0) isCorrect = false;
        } catch (SQLException e) {
            isCorrect = false;
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return isCorrect;
    }

    @Override
    public UserBean[] getAllUsers() {

        ArrayList<UserBean> userBeans = null;
        String sql = UserQueries.selectAllUsers;

        try {
            connection = connection == null ? ConnectionPool.borrowConnection() : connection;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            userBeans = new ArrayList<>();
            while (resultSet.next()) {
                UserBean userBean = new UserBean();
                userBean.setRoleId(resultSet.getInt("role_id"));
                userBean.setFirstName(resultSet.getString("first_name"));
                userBean.setLastName(resultSet.getString("last_name"));
                userBean.setEmail(resultSet.getString("email"));
                userBean.setAvatar(resultSet.getString("avatar"));
                userBeans.add(userBean);
            }

        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return userBeans == null ? null : userBeans.toArray(new UserBean[0]);
    }
}
