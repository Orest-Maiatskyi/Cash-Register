package dao_tests;

import com.epam.cashregister.entities.OrderBean;
import com.epam.cashregister.services.dao.impl.OrderDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderDaoTest extends Mockito {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private OrderDaoImpl orderDao = new OrderDaoImpl(connection);

    @Test
    public void createOrderTest_withoutAnyExceptions() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet).thenReturn(resultSet);
        when(statement.executeUpdate()).thenReturn(1);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyString())).thenReturn(1);

        assertTrue(orderDao.createOrder());

        verify(connection, times(2)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(2)).close();
    }

    @Test
    public void createOrderTest_withPrepareStatementException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertFalse(orderDao.createOrder());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void addGoodTest_withoutAnyExceptions() throws SQLException {

        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(1);
        orderBean.setGoodCode("001");
        orderBean.setQuantity(10.0f);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        //when(statement.executeQuery()).thenReturn(resultSet);
        when(statement.executeUpdate()).thenReturn(1);

        assertTrue(orderDao.addGood(orderBean));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeUpdate();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void addGoodTest_withPrepareStatementException() throws SQLException {

        OrderBean orderBean = new OrderBean();

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertFalse(orderDao.addGood(orderBean));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeUpdate();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void updateGoodQuantityTest_withoutAnyExceptions() throws SQLException {

        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(1);
        orderBean.setGoodCode("001");
        orderBean.setQuantity(10.0f);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        //when(statement.executeQuery()).thenReturn(resultSet);
        when(statement.executeUpdate()).thenReturn(1);

        assertTrue(orderDao.updateGoodQuantity(orderBean));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeUpdate();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();

    }

    @Test
    public void updateGoodQuantityTest_withPrepareStatementException() throws SQLException {

        OrderBean orderBean = new OrderBean();

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertFalse(orderDao.updateGoodQuantity(orderBean));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeUpdate();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void closeOrderTest_withoutAnyExceptions() throws SQLException {

        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(1);
        orderBean.setGoodCode("001");
        orderBean.setQuantity(10.0f);

        OrderBean[] orderBeans = new OrderBean[1];
        orderBeans[0] = orderBean;

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        //when(statement.executeQuery()).thenReturn(resultSet);
        when(statement.executeUpdate()).thenReturn(1);

        assertTrue(orderDao.addGood(orderBean));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeUpdate();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void closeOrderTest_withPrepareStatementException() throws SQLException {

        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(1);
        orderBean.setGoodCode("001");
        orderBean.setQuantity(10.0f);

        OrderBean[] orderBeans = new OrderBean[1];
        orderBeans[0] = orderBean;

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertFalse(orderDao.addGood(orderBean));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeUpdate();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void cancelOrderTest_withoutAnyExceptions() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        assertTrue(orderDao.cancelOrder(1));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeUpdate();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void cancelOrderTest_withPrepareStatementException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertFalse(orderDao.cancelOrder(1));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeUpdate();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void cancelOrderedGoodTest_withoutAnyExceptions() throws SQLException {

        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(1);
        orderBean.setGoodCode("001");

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        assertTrue(orderDao.cancelOrderedGood(orderBean));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeUpdate();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void cancelOrderedGoodTest_withPrepareStatementException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertFalse(orderDao.cancelOrderedGood(new OrderBean()));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeUpdate();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getSumOfOrderedGoodsTest_withoutAnyExceptions() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        assertEquals(0, orderDao.getSumOfOrderedGoods(new String[1]).length);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getSumOfOrderedGoodsTest_withPrepareStatementException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertNull(orderDao.getSumOfOrderedGoods(new String[1]));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getOrdersWhichAreInProcessTest_withoutAnyExceptions() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        assertEquals(0, orderDao.getOrdersWhichAreInProcess().length);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getOrdersWhichAreInProcessTest_withPrepareStatementException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertNull(orderDao.getOrdersWhichAreInProcess());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getOrderedGoodTest_withoutAnyExceptions() throws SQLException {

        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(1);
        orderBean.setGoodCode("001");

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString(anyString())).thenReturn("001");

        assertEquals("001", orderDao.getOrderedGood(orderBean));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getOrderedGoodTest_withPrepareStatementException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertNull(orderDao.getOrdersWhichAreInProcess());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getOrderedGoodsTest_withoutAnyExceptions() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        assertEquals(0, orderDao.getOrderedGoods(1).length);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getOrderedGoodsTest_withPrepareStatementException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertNull(orderDao.getOrderedGoods(1));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getOrderedGoodsByOrderBeanTest_withoutAnyExceptions() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        assertEquals(0, orderDao.getOrderedGoods(new OrderBean()).length);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getOrderedGoodsByOrderBeanTest_withPrepareStatementException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertNull(orderDao.getOrderedGoods(new OrderBean()));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getOrderListTest_withoutAnyExceptions() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false).thenReturn(true);

        assertEquals(1, orderDao.getOrderList("", "", 10, 10).length);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(2)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getOrderListTest_withPrepareStatementException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertNull(orderDao.getOrderList("", "", 10, 10));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }
}
