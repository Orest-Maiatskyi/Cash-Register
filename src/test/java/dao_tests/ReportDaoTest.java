package dao_tests;

import com.epam.cashregister.services.dao.impl.ReportDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportDaoTest extends Mockito {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ReportDaoImpl reportDao = new ReportDaoImpl(connection);

    @Test
    public void getIncomeGoodsTest_withoutAnyExceptions() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        assertNotNull(reportDao.getIncomeGoods());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getIncomeGoodsTest_withPrepareStatementException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertNull(reportDao.getIncomeGoods());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getOutcomeGoodsTest_withoutAnyExceptions() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        assertNotNull(reportDao.getOutcomeGoods());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getOutcomeGoodsTest_withPrepareStatementException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertNull(reportDao.getOutcomeGoods());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getScrappedGoodsTest_withoutAnyExceptions() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        assertNotNull(reportDao.getOutcomeGoods());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getScrappedGoodsTest_withPrepareStatementException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertNull(reportDao.getScrappedGoods());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void closeDayTest_withoutAnyExceptions() throws SQLException {

        when(connection.createStatement()).thenReturn(statement);
        doNothing().when(statement).addBatch(anyString());
        when(statement.executeBatch()).thenReturn(new int[1]);

        assertTrue(reportDao.closeDay());

        verify(connection, times(1)).createStatement();
        verify(statement, times(1)).executeBatch();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
        verify(connection, times(1)).commit();
        verify(connection, times(2)).setAutoCommit(true);
        verify(connection, times(1)).setAutoCommit(false);
    }

    @Test
    public void closeDayTest_withCreateStatementException() throws SQLException {

        when(connection.createStatement()).thenThrow(new SQLException("Test exception."));

        assertFalse(reportDao.closeDay());

        verify(connection, times(1)).createStatement();
        verify(statement, times(0)).executeBatch();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
        verify(connection, times(0)).commit();
        verify(connection, times(1)).setAutoCommit(true);
        verify(connection, times(1)).setAutoCommit(false);
    }
}
