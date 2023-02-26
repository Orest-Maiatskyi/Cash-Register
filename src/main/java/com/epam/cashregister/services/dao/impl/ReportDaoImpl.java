package com.epam.cashregister.services.dao.impl;

import com.epam.cashregister.entities.OrderBean;
import com.epam.cashregister.services.connection_pool.ConnectionPool;
import com.epam.cashregister.services.dao.ReportDao;
import com.epam.cashregister.services.dao.queries.OrderQueries;
import com.epam.cashregister.services.dao.queries.ReportQueries;

import java.sql.*;
import java.util.ArrayList;

public class ReportDaoImpl implements ReportDao {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public ReportDaoImpl() { }

    public ReportDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public OrderBean[] getIncomeGoods() {

        String sql = ReportQueries.selectIncomeGoods;
        ArrayList<OrderBean> orderBeans = null;

        try {
            connection = connection == null ? ConnectionPool.borrowConnection() : connection;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            orderBeans = new ArrayList<>();
            while (resultSet.next()) {
                OrderBean orderBean = new OrderBean();
                orderBean.setGoodCode(resultSet.getString("code"));
                orderBean.setGoodTitle(resultSet.getString("title"));
                orderBean.setGoodPrice(resultSet.getFloat("price"));
                orderBean.setQuantity(resultSet.getFloat("quantity"));
                orderBean.setStorageAddress(resultSet.getString("address"));
                orderBean.setDateTime(resultSet.getString("date"));
                orderBeans.add(orderBean);
            }

        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return orderBeans == null ? null : orderBeans.toArray(new OrderBean[0]);

    }

    @Override
    public OrderBean[] getOutcomeGoods() {

        String sql = ReportQueries.selectOutcomeGoods;
        ArrayList<OrderBean> orderBeans = null;

        try {
            connection = connection == null ? ConnectionPool.borrowConnection() : connection;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            orderBeans = new ArrayList<>();
            while (resultSet.next()) {
                OrderBean orderBean = new OrderBean();
                orderBean.setGoodCode(resultSet.getString("code"));
                orderBean.setGoodTitle(resultSet.getString("title"));
                orderBean.setGoodPrice(resultSet.getFloat("price"));
                orderBean.setQuantity(resultSet.getFloat("quantity"));
                orderBean.setStorageAddress(resultSet.getString("address"));
                orderBean.setDateTime(resultSet.getString("date"));
                orderBeans.add(orderBean);
            }

        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return orderBeans == null ? null : orderBeans.toArray(new OrderBean[0]);
    }

    @Override
    public OrderBean[] getScrappedGoods() {

        String sql = ReportQueries.selectScrappedGoods;
        ArrayList<OrderBean> orderBeans = null;

        try {
            connection = connection == null ? ConnectionPool.borrowConnection() : connection;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            orderBeans = new ArrayList<>();
            while (resultSet.next()) {
                OrderBean orderBean = new OrderBean();
                orderBean.setGoodCode(resultSet.getString("code"));
                orderBean.setGoodTitle(resultSet.getString("title"));
                orderBean.setGoodPrice(resultSet.getFloat("price"));
                orderBean.setQuantity(resultSet.getFloat("quantity"));
                orderBean.setStorageAddress(resultSet.getString("address"));
                orderBean.setDateTime(resultSet.getString("date"));
                orderBeans.add(orderBean);
            }

        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return orderBeans == null ? null : orderBeans.toArray(new OrderBean[0]);
    }

    @Override
    public boolean closeDay() {

        boolean success = true;

        try {
            connection = connection == null ? ConnectionPool.borrowConnection() : connection;
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();
            statement.addBatch("SET FOREIGN_KEY_CHECKS = 0");
            statement.addBatch("truncate ordered");
            statement.addBatch("truncate orders");
            statement.addBatch("truncate income");
            statement.addBatch("truncate outcome");
            statement.addBatch("truncate scrapped");
            statement.addBatch("SET FOREIGN_KEY_CHECKS = 1");

            for (int i : statement.executeBatch()) if (i < 0) {
                success = false;
                break;
            }

            if (success) connection.commit();
            else connection.rollback();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
            try { connection.rollback(); }
            catch (SQLException ex) { ex.printStackTrace(); }

        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) {
                    connection.setAutoCommit(true);
                    ConnectionPool.returnConnection(connection);
                }
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return success;

    }
}
