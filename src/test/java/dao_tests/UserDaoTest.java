package dao_tests;

import com.epam.cashregister.entities.UserBean;
import com.epam.cashregister.services.dao.UserDao;
import com.epam.cashregister.services.dao.impl.UserDaoImpl;
import com.epam.cashregister.services.dao.impl.WarehouseDaoImpl;
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
public class UserDaoTest extends Mockito {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private UserDaoImpl userDao = new UserDaoImpl(connection);

    @Test
    public void addUserTest_withoutAnyExceptions() throws SQLException {

        UserBean userBean = new UserBean();
        userBean.setRoleId(1);
        userBean.setFirstName("First");
        userBean.setLastName("Last");
        userBean.setEmail("email@gmail.com");
        userBean.setAvatar("base64-img");
        userBean.setPasswordHash("password-hash");

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        assertTrue(userDao.addUser(userBean));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeUpdate();
        verify(statement, times(1)).close();
    }

    @Test
    public void addUserTest_withPrepareStatementException() throws SQLException {

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertFalse(userDao.addUser(new UserBean()));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeUpdate();
        verify(statement, times(1)).close();
    }

    @Test
    public void removeUserTest_withoutAnyExceptions() throws SQLException {

        UserBean userBean = new UserBean();
        userBean.setEmail("email@gmail.com");

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        assertTrue(userDao.removeUser(userBean));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeUpdate();
        verify(statement, times(1)).close();
    }

    @Test
    public void removeUserTest_withPrepareStatementException() throws SQLException {

        UserBean userBean = new UserBean();

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertFalse(userDao.removeUser(userBean));

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeUpdate();
        verify(statement, times(1)).close();
    }

    @Test
    public void getAllUsersTest_withoutAnyExceptions() throws SQLException {

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        assertEquals(1, userDao.getAllUsers().length);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(statement, times(1)).close();
    }

    @Test
    public void getAllUsersTest_withPrepareStatementException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception."));

        assertNull(userDao.getAllUsers());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(0)).executeQuery();
        verify(statement, times(1)).close();
    }
}
