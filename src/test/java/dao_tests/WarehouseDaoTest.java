package dao_tests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.entities.StorageBean;
import com.epam.cashregister.entities.WarehouseBean;
import com.epam.cashregister.services.dao.impl.WarehouseDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@RunWith(MockitoJUnitRunner.class)
public class WarehouseDaoTest extends Mockito {


    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private WarehouseDaoImpl warehouseDao = new WarehouseDaoImpl(connection);



    @Test
    public void addGoodTest_withoutAnyExceptions() throws SQLException {
        WarehouseBean warehouseBean = new WarehouseBean();
        StorageBean storageBean = new StorageBean();
        storageBean.setId(1);
        GoodBean goodBean = new GoodBean();
        goodBean.setCode("000");
        warehouseBean.setStorageBean(storageBean);
        warehouseBean.setGood(goodBean);
        warehouseBean.setQuantity(10.0f);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        warehouseDao.addGood(warehouseBean);

        verify(connection, times(1)).setAutoCommit(false);
        verify(connection, times(1)).setAutoCommit(true);
        verify(connection, times(2)).prepareStatement(anyString());
        verify(statement, times(2)).executeUpdate();
        verify(resultSet, times(1)).close();
        verify(statement, times(2)).close();
    }

    @Test
    public void addGoodTest_withPrepareStatementException() throws SQLException {
        WarehouseBean warehouseBean = new WarehouseBean();
        StorageBean storageBean = new StorageBean();
        storageBean.setId(1);
        GoodBean goodBean = new GoodBean();
        goodBean.setCode("000");
        warehouseBean.setStorageBean(storageBean);
        warehouseBean.setGood(goodBean);
        warehouseBean.setQuantity(10.0f);

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        warehouseDao.addGood(warehouseBean);

        verify(connection, times(1)).setAutoCommit(false);
        verify(connection, times(1)).setAutoCommit(true);
        verify(connection, times(1)).prepareStatement(anyString());
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void writeOffGoodTest_withoutAnyExceptions() throws SQLException {
        WarehouseBean warehouseBean = new WarehouseBean();
        StorageBean storageBean = new StorageBean();
        storageBean.setId(1);
        GoodBean goodBean = new GoodBean();
        goodBean.setCode("000");
        warehouseBean.setStorageBean(storageBean);
        warehouseBean.setGood(goodBean);
        warehouseBean.setQuantity(10.0f);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        warehouseDao.writeOffGood(warehouseBean);

        verify(connection, times(1)).setAutoCommit(false);
        verify(connection, times(1)).setAutoCommit(true);
        verify(connection, times(2)).prepareStatement(anyString());
        verify(statement, times(2)).executeUpdate();
        verify(resultSet, times(1)).close();
        verify(statement, times(2)).close();
    }

    @Test
    public void writeOffGoodTest_withPrepareStatementException() throws SQLException {
        WarehouseBean warehouseBean = new WarehouseBean();
        StorageBean storageBean = new StorageBean();
        storageBean.setId(1);
        GoodBean goodBean = new GoodBean();
        goodBean.setCode("000");
        warehouseBean.setStorageBean(storageBean);
        warehouseBean.setGood(goodBean);
        warehouseBean.setQuantity(10.0f);

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        warehouseDao.writeOffGood(warehouseBean);

        verify(connection, times(1)).setAutoCommit(false);
        verify(connection, times(1)).setAutoCommit(true);
        verify(connection, times(1)).prepareStatement(anyString());
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getWarehouseGoodCodesTest_withoutAnyExceptions() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        assertEquals(0, warehouseDao.getWarehouseGoodCodes().length);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(statement, times(1)).close();
    }

    @Test
    public void getWarehouseGoodCodesTest_withExecuteQueryException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(new SQLException("Test exception"));

        assertNull(warehouseDao.getWarehouseGoodCodes());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(statement, times(1)).close();
    }

    @Test
    public void getStoragesForGoodTest_withoutAnyExceptions() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        assertEquals(0, warehouseDao.getStoragesForGood("code").length);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(statement, times(1)).close();
    }

    @Test
    public void getStoragesForGoodTest_withExecuteQueryException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(new SQLException("Test exception"));

        assertNull(warehouseDao.getStoragesForGood("code"));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(statement, times(1)).close();
    }

    @Test
    public void getWarehouseGoodsTest_withoutAnyExceptions() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false).thenReturn(true);
        when(resultSet.getInt(anyInt())).thenReturn(1);

        assertEquals(1, warehouseDao.getWarehouseGoods("", "", 10, 20).length);
        assertEquals(1, warehouseDao.getNoOfRecords());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(statement, times(1)).executeQuery(anyString());
        verify(statement, times(1)).close();
        verify(resultSet, times(1)).getInt(anyInt());
        verify(resultSet, times(2)).close();
    }

    @Test
    public void getWarehouseGoodsTest_withExecuteQueryException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(statement.executeQuery(anyString())).thenThrow(new SQLException("Test exception"));

        assertEquals(0, warehouseDao.getWarehouseGoods("", "", 10, 20).length);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(statement, times(1)).executeQuery(anyString());
        verify(statement, times(1)).close();
        verify(resultSet, times(2)).close();
    }

    @Test
    public void getGoodQualityInStockTest_withoutAnyExceptions() throws SQLException {

        WarehouseBean warehouseBean = new WarehouseBean();
        StorageBean storageBean = new StorageBean();
        storageBean.setId(1);
        GoodBean goodBean = new GoodBean();
        goodBean.setCode("000");
        warehouseBean.setStorageBean(storageBean);
        warehouseBean.setGood(goodBean);
        warehouseBean.setQuantity(10.0f);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        warehouseDao.getGoodQuantityInStock(warehouseBean);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getGoodQualityInStockTest_withExecuteQueryException() throws SQLException {

        WarehouseBean warehouseBean = new WarehouseBean();
        StorageBean storageBean = new StorageBean();
        storageBean.setId(1);
        GoodBean goodBean = new GoodBean();
        goodBean.setCode("000");
        warehouseBean.setStorageBean(storageBean);
        warehouseBean.setGood(goodBean);
        warehouseBean.setQuantity(10.0f);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(new SQLException("Test exception"));

        warehouseDao.getGoodQuantityInStock(warehouseBean);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }
}
