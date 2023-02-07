package com.epam.cashregister.services.dao.impl;

import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.entities.MeasurementBean;
import com.epam.cashregister.services.connection_pool.ConnectionPool;
import com.epam.cashregister.services.dao.GoodDao;
import com.epam.cashregister.services.dao.queries.GoodQueries;

import java.sql.*;
import java.util.ArrayList;


public class GoodDaoImpl implements GoodDao {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private int noOfRecords;

    @Override
    public boolean createGood(GoodBean goodBean) {

        String sql = GoodQueries.insertGoodToGoodsTable;
        boolean isSuccess = true;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, goodBean.getCode());
            preparedStatement.setInt(2, goodBean.getMeasurement().getId());
            preparedStatement.setString(3, goodBean.getTitle());
            preparedStatement.setString(4, goodBean.getDescription());
            preparedStatement.setFloat(5, goodBean.getPrice());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            isSuccess = false;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return isSuccess;
    }

    @Override
    public boolean updateGood(GoodBean goodBean) {

        String sql = GoodQueries.updateGood;
        boolean isSuccess = true;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(4, goodBean.getCode());
            preparedStatement.setString(3, goodBean.getTitle());
            preparedStatement.setString(2, goodBean.getDescription());
            preparedStatement.setFloat(1, goodBean.getPrice());
            if (preparedStatement.executeUpdate() == 0) isSuccess = false;


        } catch (SQLException e) {
            e.printStackTrace();
            isSuccess = false;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return isSuccess;
    }

    @Override
    public GoodBean getGoodByCode(String code) {

        GoodBean goodBean = null;
        String sql = GoodQueries.selectGoodByCode;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                goodBean = new GoodBean();
                MeasurementBean measurementBean = new MeasurementBean();
                goodBean.setId(resultSet.getInt("goods.id"));
                goodBean.setCode(resultSet.getString("code"));
                measurementBean.setId(resultSet.getInt("measurements.id"));
                measurementBean.setName(resultSet.getString("measurement"));
                goodBean.setMeasurement(measurementBean);
                goodBean.setTitle(resultSet.getString("title"));
                goodBean.setDescription(resultSet.getString("description"));
                goodBean.setPrice(resultSet.getFloat("price"));
            }

        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return goodBean;
    }

    @Override
    public GoodBean getGoodByTitle(String title) {

        GoodBean goodBean = null;
        String sql = GoodQueries.selectGoodByTitle;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                goodBean = new GoodBean();
                MeasurementBean measurementBean = new MeasurementBean();
                goodBean.setId(resultSet.getInt("goods.id"));
                goodBean.setCode(resultSet.getString("code"));
                measurementBean.setId(resultSet.getInt("measurements.id"));
                measurementBean.setName(resultSet.getString("measurement"));
                goodBean.setMeasurement(measurementBean);
                goodBean.setTitle(resultSet.getString("title"));
                goodBean.setDescription(resultSet.getString("description"));
                goodBean.setPrice(resultSet.getFloat("price"));
            }

        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return goodBean;
    }

    @Override
    public GoodBean[] getGoodsByLikeTitle(String title) {

        ArrayList<GoodBean> goodBeans = null;
        String sql = GoodQueries.selectGoodsByLikeTitle;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();

            goodBeans = new ArrayList<>();
            while (resultSet.next()) {
                GoodBean tempBean = new GoodBean();
                MeasurementBean measurementBean = new MeasurementBean();
                tempBean.setCode(resultSet.getString("code"));
                measurementBean.setId(resultSet.getInt("measurement_id"));
                measurementBean.setName(resultSet.getString("measurement"));
                tempBean.setMeasurement(measurementBean);
                tempBean.setTitle(resultSet.getString("title"));
                tempBean.setDescription(resultSet.getString("description"));
                tempBean.setPrice(resultSet.getFloat("price"));
                goodBeans.add(tempBean);
            }

        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return goodBeans == null ? null : goodBeans.toArray(new GoodBean[0]);

    }

    @Override
    public GoodBean[] getAllGoods() {

        ArrayList<GoodBean> goodBeans = null;
        String sql = GoodQueries.selectAllGoods;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            goodBeans = new ArrayList<>();
            while (resultSet.next()) {
                GoodBean tempBean = new GoodBean();
                MeasurementBean measurementBean = new MeasurementBean();
                tempBean.setCode(resultSet.getString("code"));
                measurementBean.setId(resultSet.getInt("measurement_id"));
                measurementBean.setName(resultSet.getString("measurement"));
                tempBean.setMeasurement(measurementBean);
                tempBean.setTitle(resultSet.getString("title"));
                tempBean.setDescription(resultSet.getString("description"));
                tempBean.setPrice(resultSet.getFloat("price"));
                goodBeans.add(tempBean);
            }

        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return goodBeans == null ? null : goodBeans.toArray(new GoodBean[0]);
    }

    @Override
    public MeasurementBean getGoodMeasurement(String code) {

        String sql = GoodQueries.selectGoodMeasurementByCode;
        MeasurementBean measurementBean = null;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                measurementBean = new MeasurementBean();
                measurementBean.setId(resultSet.getInt("id"));
                measurementBean.setName(resultSet.getString("measurement"));
            }

        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return measurementBean;
    }

    @Override
    public MeasurementBean[] getAllGoodMeasurements() {

        String sql = GoodQueries.selectAllGoodMeasurements;
        ArrayList<MeasurementBean> measurementBeans = null;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            measurementBeans = new ArrayList<>();
            while (resultSet.next()) {
                MeasurementBean tempBean = new MeasurementBean();
                tempBean.setId(resultSet.getInt("id"));
                tempBean.setName(resultSet.getString("measurement"));
                measurementBeans.add(tempBean);
            }

        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return measurementBeans == null ? null : measurementBeans.toArray(new MeasurementBean[0]);
    }

    @Override
    public GoodBean[] getGoods(String likeData, String orderBy, int offset, int rowCount) {

        // dirty magic, but it's ok because of strong validation on server side !
        String[] splitSql = GoodQueries.selectPaginatedGoods.split("<&_&>");
        String sql = splitSql[0] + orderBy + splitSql[1];
        // end of dirty magic
        
        ArrayList<GoodBean> goods = null;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,likeData);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, rowCount);
            resultSet = preparedStatement.executeQuery();

            goods = new ArrayList<>();

            while (resultSet.next()) {
                GoodBean tempGoodBean = new GoodBean();
                MeasurementBean tempMeasurementBean = new MeasurementBean();
                tempMeasurementBean.setName(resultSet.getString("measurement"));
                tempGoodBean.setId(resultSet.getInt("goods.id"));
                tempGoodBean.setCode(resultSet.getString("code"));
                tempGoodBean.setMeasurement(tempMeasurementBean);
                tempGoodBean.setTitle(resultSet.getString("title"));
                tempGoodBean.setDescription(resultSet.getString("description"));
                tempGoodBean.setPrice(resultSet.getFloat("price"));
                goods.add(tempGoodBean);
            }

            resultSet.close();
            resultSet = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            if (resultSet.next()) this.noOfRecords = resultSet.getInt(1);

        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return goods == null ? null : goods.toArray(new GoodBean[0]);
    }

    @Override
    public String[] getAllGoodCodes() {
        String sql = GoodQueries.selectAllGoodCodes;
        ArrayList<String> codes = null;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            codes = new ArrayList<>();
            while (resultSet.next())
                codes.add(resultSet.getString("code"));

            resultSet.close();

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

    public int getNoOfRecords() { return noOfRecords; }

}
