package com.epam.cashregister.services.dao.impl;

import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.entities.StorageBean;
import com.epam.cashregister.entities.WarehouseBean;
import com.epam.cashregister.services.connection_pool.ConnectionPool;
import com.epam.cashregister.services.dao.WarehouseDao;
import com.epam.cashregister.services.dao.queries.WarehouseQueries;

import java.sql.*;
import java.util.ArrayList;

public class WarehouseDaoImpl implements WarehouseDao {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private int noOfRecords;

    @Override
    public boolean addGood(WarehouseBean warehouseBean) {

        String sql_1 = WarehouseQueries.insertGoodToIncomeTable;
        String sql_2 = WarehouseQueries.insertGoodAdditionToWarehouseTable;
        boolean success = true;

        try {
            connection = ConnectionPool.borrowConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql_1);
            preparedStatement.setInt(1, warehouseBean.getStorageBean().getId());
            preparedStatement.setString(2, warehouseBean.getGoodBean().getCode());
            preparedStatement.setFloat(3, warehouseBean.getQuantity());

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                preparedStatement.close();
                preparedStatement = connection.prepareStatement(sql_2);
                preparedStatement.setInt(1, warehouseBean.getStorageBean().getId());
                preparedStatement.setString(2, warehouseBean.getGoodBean().getCode());
                preparedStatement.setFloat(3, warehouseBean.getQuantity());
                rowAffected = preparedStatement.executeUpdate();
                if (rowAffected == 0) success = false;
            } else success = false;

            if (success) connection.commit();
            else connection.rollback();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
                ConnectionPool.returnConnection(connection);
            } catch (SQLException ignore) { }
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
    public boolean writeOffGood(WarehouseBean warehouseBean) {

        String sql_1 = WarehouseQueries.insertGoodToScrappedTable;
        String sql_2 = WarehouseQueries.insertGoodSubtractionToScrappedTable;
        boolean success = true;

        try {
            connection = ConnectionPool.borrowConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql_1);
            preparedStatement.setInt(1, warehouseBean.getStorageBean().getId());
            preparedStatement.setString(2, warehouseBean.getGoodBean().getCode());
            preparedStatement.setFloat(3, warehouseBean.getQuantity());

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                preparedStatement.close();
                preparedStatement = connection.prepareStatement(sql_2);
                preparedStatement.setInt(1, warehouseBean.getStorageBean().getId());
                preparedStatement.setString(2, warehouseBean.getGoodBean().getCode());
                preparedStatement.setFloat(3, warehouseBean.getQuantity());
                rowAffected = preparedStatement.executeUpdate();
                if (rowAffected == 0) success = false;
            } else success = false;

            if (success) connection.commit();
            else connection.rollback();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
                ConnectionPool.returnConnection(connection);
            } catch (SQLException ignore) { }
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
    public String[] getWarehouseGoodCodes() {

        String sql = WarehouseQueries.selectWarehouseGoods;
        ArrayList<String> codes = null;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
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

        return codes == null ? null : codes.toArray(new String[]{});

    }

    @Override
    public StorageBean[] getStoragesForGood(String goodCode) {

        String sql = WarehouseQueries.selectStorageByGoodCode;
        ArrayList<StorageBean> storages = null;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, goodCode);
            resultSet = preparedStatement.executeQuery();

            storages = new ArrayList<>();
            while (resultSet.next()) {
                StorageBean temp = new StorageBean();
                temp.setId(resultSet.getInt("storage_id"));
                temp.setAddress(resultSet.getString("address"));
                storages.add(temp);
            }

        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return storages == null ? null : storages.toArray(new StorageBean[0]);
    }

    @Override
    public WarehouseBean[] getWarehouseGoods(String likeData, String orderBy, int offset, int rowCount) {
        // dirty magic, but it's ok because of strong validation on server side !
        String[] splitSql = WarehouseQueries.selectPaginatedWarehouseGoods.split("<&_&>");
        String sql = splitSql[0] + orderBy + splitSql[1];
        // end of dirty magic

        ArrayList<WarehouseBean> warehouseBeans = null;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,likeData);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, rowCount);
            resultSet = preparedStatement.executeQuery();

            warehouseBeans = new ArrayList<>();

            while (resultSet.next()) {
                WarehouseBean warehouseBean = new WarehouseBean();
                StorageBean storageBean = new StorageBean();
                GoodBean goodBean = new GoodBean();

                warehouseBean.setId(resultSet.getInt("warehouse.id"));
                goodBean.setCode(resultSet.getString("good_code"));
                warehouseBean.setGood(goodBean);
                storageBean.setAddress(resultSet.getString("address"));
                warehouseBean.setStorageBean(storageBean);
                warehouseBean.setQuantity(resultSet.getFloat("quantity"));
                warehouseBeans.add(warehouseBean);
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

        return warehouseBeans == null ? null : warehouseBeans.toArray(new WarehouseBean[0]);
    }

    @Override
    public float getGoodQuantityInStock(WarehouseBean warehouseBean) {

        String sql = WarehouseQueries.selectGoodQuantity;
        float quantity = -1f;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, warehouseBean.getStorageBean().getId());
            preparedStatement.setString(2, warehouseBean.getGoodBean().getCode());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) { quantity = resultSet.getFloat("quantity"); }

        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return quantity;
    }

    public int getNoOfRecords() { return noOfRecords; }

}
