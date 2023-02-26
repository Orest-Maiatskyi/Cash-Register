package com.epam.cashregister.services.dao.impl;

import com.epam.cashregister.entities.StorageBean;
import com.epam.cashregister.services.connection_pool.ConnectionPool;
import com.epam.cashregister.services.dao.StorageDao;
import com.epam.cashregister.services.dao.queries.GoodQueries;
import com.epam.cashregister.services.dao.queries.StorageQueries;

import java.sql.*;
import java.util.ArrayList;

public class StorageDaoImpl implements StorageDao {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public StorageDaoImpl() { }

    public StorageDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public StorageBean getStorageById(int id) {
        return null;
    }

    @Override
    public StorageBean getStorageByAddress(String address) {

        String sql = StorageQueries.selectStorageByAddress;
        StorageBean storageBean = null;

        try {
            connection = connection == null ? ConnectionPool.borrowConnection() : connection;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, address);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                storageBean = new StorageBean();
                storageBean.setId(resultSet.getInt("id"));
                storageBean.setAddress(address);
            }

        } catch (SQLException e) { e.printStackTrace();}
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return storageBean;
    }

    @Override
    public StorageBean[] getAllStorages() {

        String sql = StorageQueries.selectAllStorages;
        ArrayList<StorageBean> storageBeans = null;

        try {
            connection = connection == null ? ConnectionPool.borrowConnection() : connection;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            storageBeans = new ArrayList<>();
            while (resultSet.next()) {
                StorageBean tempBean = new StorageBean();
                tempBean.setId(resultSet.getInt("id"));
                tempBean.setAddress(resultSet.getString("address"));
                storageBeans.add(tempBean);
            }

        } catch (SQLException e) { e.printStackTrace(); }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) ConnectionPool.returnConnection(connection);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        return storageBeans == null ? null :storageBeans.toArray(new StorageBean[0]);
    }

    @Override
    public boolean addStorage(StorageBean storageBean) {

        String sql = StorageQueries.insertIntoStorages;
        boolean isSuccess = true;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, storageBean.getAddress());
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
    public boolean deleteStorage(StorageBean storageBean) {
        String sql = StorageQueries.deleteStorages;
        boolean isSuccess = true;

        try {
            connection = ConnectionPool.borrowConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, storageBean.getAddress());
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

}
