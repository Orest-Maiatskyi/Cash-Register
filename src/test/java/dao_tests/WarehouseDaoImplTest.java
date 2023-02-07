package dao_tests;

import ch.vorburger.exec.ManagedProcessException;
import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.entities.StorageBean;
import com.epam.cashregister.entities.WarehouseBean;
import com.epam.cashregister.services.connection_pool.ConnectionPool;
import com.epam.cashregister.services.connection_pool.util.MySQLConnectionUtil;
import com.epam.cashregister.services.dao.impl.WarehouseDaoImpl;
import com.epam.cashregister.services.utils.ResourcesUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.MarinaDB4jHelper;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WarehouseDaoImplTest {

    @BeforeAll
    public static void setup() throws ManagedProcessException, SQLException, IOException {
        MarinaDB4jHelper.openInMemoryDB();
        MySQLConnectionUtil.setTestingMode();
        MarinaDB4jHelper.SqlRunner.runScript(ConnectionPool.borrowConnection(), new FileReader(ResourcesUtil.getFileFromResources("create_cash_register_db_script.sql")));
        MarinaDB4jHelper.SqlRunner.runScript(ConnectionPool.borrowConnection(), new FileReader(ResourcesUtil.getFileFromResources("add_test_data_script.sql")));
    }

    /*
    @Test
    public void testAddGood() {
        WarehouseBean warehouseBean = new WarehouseBean();
        GoodBean goodBean = new GoodBean();
        goodBean.setCode("10000000");
        StorageBean storageBean = new StorageBean();
        storageBean.setId(1);
        warehouseBean.setGood(goodBean);
        warehouseBean.setStorageBean(storageBean);
        warehouseBean.setQuantity(10);
        WarehouseDaoImpl warehouseDao = new WarehouseDaoImpl();
        assertTrue(warehouseDao.addGood(warehouseBean));
    }
    */

    @AfterAll
    public static void end() throws ManagedProcessException {
        MarinaDB4jHelper.closeInMemoryDB();
    }

}
