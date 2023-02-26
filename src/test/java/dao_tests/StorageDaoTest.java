package dao_tests;

import com.epam.cashregister.entities.UserBean;
import com.epam.cashregister.services.dao.impl.StorageDaoImpl;
import com.epam.cashregister.services.dao.impl.UserDaoImpl;
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
public class StorageDaoTest extends Mockito {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private StorageDaoImpl storageDao = new StorageDaoImpl(connection);

    @Test
    public void getStorageByAddressTest_withoutAnyExceptions() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyString())).thenReturn(1);

        assertNotNull(storageDao.getStorageByAddress("address"));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getStorageByAddressTest_withPrepareStatementException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertNull(storageDao.getStorageByAddress("address"));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getAllStoragesTest_withoutAnyExceptions() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        assertNotNull(storageDao.getAllStorages());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

    @Test
    public void getAllStoragesTest_withPrepareStatementException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertNull(storageDao.getAllStorages());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeQuery();
        verify(resultSet, times(1)).close();
        verify(statement, times(1)).close();
    }

}
