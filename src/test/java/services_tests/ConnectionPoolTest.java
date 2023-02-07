package services_tests;

import ch.vorburger.exec.ManagedProcessException;
import com.epam.cashregister.services.connection_pool.ConnectionPool;
import com.epam.cashregister.services.connection_pool.util.MySQLConnectionUtil;
import org.junit.jupiter.api.*;
import utils.MarinaDB4jHelper;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConnectionPoolTest {

    @BeforeAll
    public static void start() throws ManagedProcessException {
        MarinaDB4jHelper.openInMemoryDB();
        MySQLConnectionUtil.setTestingMode();

        ConnectionPool.manager = null;
        ConnectionPool.pool.clear();
        ConnectionPool.Manager.expirationTime = 1000 * 2;
    }

    @Test
    @Order(1)
    public void expirationTimeOutTest_1() throws InterruptedException {
        Connection connection = ConnectionPool.borrowConnection();
        ConnectionPool.returnConnection(connection);
        assertEquals(1, ConnectionPool.pool.size());
        Thread.sleep(1000 * 3);
        assertEquals(0, ConnectionPool.pool.size());
        assertNull(ConnectionPool.manager);
    }

    @Test
    @Order(2) // This test shows, why it's so important to return connections
    public void expirationTimeOutTest_2() throws InterruptedException {
        ConnectionPool.Manager.expirationTime = 1000 * 2;
        Connection connection = ConnectionPool.borrowConnection();
        // ConnectionPool.returnConnection(connection);
        assertEquals(1, ConnectionPool.pool.size());
        Thread.sleep(1000 * 3);
        assertEquals(1, ConnectionPool.pool.size());
        assertNotNull(ConnectionPool.manager);
        ConnectionPool.returnConnection(connection);
    }

    @Test
    @Order(3)
    public void borrowConnectionTest() throws InterruptedException {
        Connection connection = ConnectionPool.borrowConnection();
        assertNotNull(connection);
        ConnectionPool.returnConnection(connection);
        Thread.sleep(1000 * 3);
    }

    @Test
    @Order(4)
    public void returnConnectionTest() {
        Connection connection_1 = ConnectionPool.borrowConnection();
        Connection connection_2 = ConnectionPool.borrowConnection();
        int count = 0;
        for (com.epam.cashregister.services.connection_pool.Connection connection : ConnectionPool.pool) if (connection.isInUse()) count += 1;
        assertEquals(2, count);
        ConnectionPool.returnConnection(connection_1);
        ConnectionPool.returnConnection(connection_2);
        count = 0;
        for (com.epam.cashregister.services.connection_pool.Connection connection : ConnectionPool.pool) if (!connection.isInUse()) count += 1;
        assertEquals(2, count);
    }

    @AfterAll
    public static void end() throws ManagedProcessException {
        MarinaDB4jHelper.closeInMemoryDB();
    }
}
