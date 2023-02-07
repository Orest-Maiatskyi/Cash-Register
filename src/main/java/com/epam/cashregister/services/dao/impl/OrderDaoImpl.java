package com.epam.cashregister.services.dao.impl;

import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.entities.OrderBean;
import com.epam.cashregister.entities.StorageBean;
import com.epam.cashregister.entities.WarehouseBean;
import com.epam.cashregister.services.connection_pool.ConnectionPool;
import com.epam.cashregister.services.dao.OrderDao;
import com.epam.cashregister.services.dao.queries.OrderQueries;
import com.epam.cashregister.services.dao.queries.WarehouseQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class OrderDaoImpl implements OrderDao {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private static int lastOrderId;
    private int numOfRecords;

    @Override
    public boolean createOrder() {

        String sql_1 = OrderQueries.insertNewOrder;
        String sql_2 = OrderQueries.selectLastOrderId;
        boolean success = true;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql_1);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) success = false;
            else {
                preparedStatement.close();
                preparedStatement = connection.prepareStatement(sql_2);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) lastOrderId = resultSet.getInt("lastId");
                else success = false;
            }

        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return success;
    }

    @Override
    public boolean addGood(OrderBean orderBean) {

        String sql = OrderQueries.insertGoodIntoOrdered;
        boolean success = true;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderBean.getOrderId());
            preparedStatement.setString(2, orderBean.getGoodCode());
            preparedStatement.setFloat(3, orderBean.getQuantity());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) success = false;
        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return success;
    }

    @Override
    public boolean updateGoodQuantity(OrderBean orderBean) {

        String sql = OrderQueries.updateOrderedGoodQuantity;
        boolean success = true;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, orderBean.getQuantity());
            preparedStatement.setInt(2, orderBean.getOrderId());
            preparedStatement.setString(3, orderBean.getGoodCode());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) success = false;
        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return success;
    }

    @Override
    public boolean closeOrder(OrderBean[] orderBeans) {

        String sql = OrderQueries.updateOrderStatus;
        boolean success = true;

        try {
            connection = ConnectionPool.borrowConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 2);
            preparedStatement.setInt(2, orderBeans[0].getOrderId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) success = false;
            else {

                for (OrderBean orderBean1 : orderBeans) {
                    sql = OrderQueries.insertOrderedGoodsToOutcome;
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setFloat(1, orderBean1.getQuantity());
                    preparedStatement.setFloat(2, orderBean1.getQuantity());
                    preparedStatement.setFloat(3, orderBean1.getQuantity());
                    preparedStatement.setFloat(4, orderBean1.getQuantity());
                    preparedStatement.setString(5, orderBean1.getGoodCode());

                    rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected == 0) {
                        success = false;
                        break;
                    }
                }

                if (success) {
                    for (OrderBean orderBean1 : orderBeans) {
                        sql = OrderQueries.updateWarehouseOrderedGoodQuantity;
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, orderBean1.getGoodCode());
                        preparedStatement.setFloat(2, orderBean1.getQuantity());
                        preparedStatement.setFloat(3, orderBean1.getQuantity());

                        System.out.println(preparedStatement.toString());

                        rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected == 0) {
                            success = false;
                            break;
                        }
                    }
                }
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

    @Override
    public boolean cancelOrder(int orderId) {

        String sql = OrderQueries.updateOrderStatus;
        boolean success = true;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 3);
            preparedStatement.setInt(2, orderId);
            if (preparedStatement.executeUpdate() == 0) success = false;

        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return success;
    }

    @Override
    public boolean cancelOrderedGood(OrderBean orderBean) {

        String sql = OrderQueries.deleteOrderedGood;
        boolean success = true;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderBean.getOrderId());
            preparedStatement.setString(2, orderBean.getGoodCode());
            if (preparedStatement.executeUpdate() == 0) success = false;

        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return success;
    }

    @Override
    public OrderBean[] getSumOfOrderedGoods(String[] goodCodes) {
        // it's ok because of goodCodes come from server side
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (String code : goodCodes) stringBuilder.append("'").append(code).append("', ");
        stringBuilder.replace(stringBuilder.length()-2, stringBuilder.length(), "");
        stringBuilder.append(")");
        //System.out.println(stringBuilder);
        String[] sqlParts = OrderQueries.selectSumOfWarehouseGoodQuantity.split("<&_&>");

        String sql = sqlParts[0] + stringBuilder + sqlParts[1];
        ArrayList<OrderBean> orderBeans = null;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            orderBeans = new ArrayList<>();
            while (resultSet.next()) {
                OrderBean orderBean = new OrderBean();
                orderBean.setGoodCode(resultSet.getString("good_code"));
                orderBean.setQuantity(resultSet.getFloat("sum_quantity"));
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
    public OrderBean[] getOrdersWhichAreInProcess() {

        String sql = OrderQueries.selectOrdersByStatus;
        ArrayList<OrderBean> orderBeans = null;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            resultSet = preparedStatement.executeQuery();

            orderBeans = new ArrayList<>();
            while (resultSet.next()) {
                OrderBean orderBean = new OrderBean();
                orderBean.setOrderId(resultSet.getInt("id"));
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
    public String getOrderedGood(OrderBean orderBean) {
        String sql = OrderQueries.selectOrderedGoodCode;
        String orderedCode = null;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderBean.getOrderId());
            preparedStatement.setString(2, orderBean.getGoodCode());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) orderedCode = resultSet.getString("good_code");

        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return orderedCode;
    }

    @Override
    public String[] getOrderedGoods(int orderId) {
        String sql = OrderQueries.selectOrderedGoodCodes;
        ArrayList<String> codes = null;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderId);
            resultSet = preparedStatement.executeQuery();

            codes = new ArrayList<>();
            while (resultSet.next())
                codes.add(resultSet.getString("good_code"));

        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return codes == null ? null : codes.toArray(new String[0]);
    }

    @Override
    public OrderBean[] getOrderedGoods(OrderBean orderBean) {

        String sql = OrderQueries.selectOrderedGoods;
        ArrayList<OrderBean> orderBeans = null;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderBean.getOrderId());
            resultSet = preparedStatement.executeQuery();

            orderBeans = new ArrayList<>();
            while (resultSet.next()) {
                OrderBean temp = new OrderBean();
                temp.setOrderId(orderBean.getOrderId());
                temp.setGoodCode(resultSet.getString("code"));
                temp.setGoodTitle(resultSet.getString("title"));
                temp.setGoodPrice(resultSet.getFloat("price"));
                temp.setQuantity(resultSet.getFloat("quantity"));
                orderBeans.add(temp);
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
    public OrderBean[] getOrderList(String likeData, String orderBy, int offset, int rowCount) {

        // dirty magic, but it's ok because of strong validation on server side !
        String[] splitSql = OrderQueries.selectPaginatedOrderList.split("<&_&>");
        String sql = splitSql[0] + orderBy + splitSql[1];
        // end of dirty magic

        ArrayList<OrderBean> orderBeans = null;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,likeData);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, rowCount);
            resultSet = preparedStatement.executeQuery();

            orderBeans = new ArrayList<>();

            while (resultSet.next()) {
                OrderBean orderBean = new OrderBean();
                orderBean.setOrderId(resultSet.getInt("order_id"));
                orderBean.setStatus(resultSet.getString("status"));
                orderBean.setGoodCode(resultSet.getString("good_code"));
                orderBean.setQuantity(resultSet.getFloat("quantity"));
                orderBeans.add(orderBean);
            }

            resultSet.close();
            resultSet = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            if (resultSet.next()) this.numOfRecords = resultSet.getInt(1);

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

    public static int getLastOrderId() {
        return lastOrderId;
    }

    public int getNumOfRecords() {
        return numOfRecords;
    }
}
