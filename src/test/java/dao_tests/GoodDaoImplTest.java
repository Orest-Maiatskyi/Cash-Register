package dao_tests;

import ch.vorburger.exec.ManagedProcessException;
import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.entities.MeasurementBean;
import com.epam.cashregister.services.connection_pool.ConnectionPool;
import com.epam.cashregister.services.connection_pool.util.MySQLConnectionUtil;
import com.epam.cashregister.services.dao.impl.GoodDaoImpl;
import com.epam.cashregister.services.utils.ResourcesUtil;
import org.junit.jupiter.api.*;
import utils.MarinaDB4jHelper;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodDaoImplTest {

    public static GoodBean testGoodBean = new GoodBean();
    static {
        MeasurementBean measurementBean = new MeasurementBean();
        measurementBean.setId(1);
        testGoodBean.setCode("00000000");
        testGoodBean.setMeasurement(measurementBean);
        testGoodBean.setTitle("TEST-TITLE");
        testGoodBean.setDescription("TEST-DESCRIPTION");
        testGoodBean.setPrice(99.99f);
    }

    @BeforeAll
    public static void start() throws ManagedProcessException, SQLException, IOException {
        MarinaDB4jHelper.openInMemoryDB();
        MySQLConnectionUtil.setTestingMode();
        MarinaDB4jHelper.SqlRunner.runScript(ConnectionPool.borrowConnection(), new FileReader(ResourcesUtil.getFileFromResources("create_cash_register_db_script.sql")));
        MarinaDB4jHelper.SqlRunner.runScript(ConnectionPool.borrowConnection(), new FileReader(ResourcesUtil.getFileFromResources("add_test_data_script.sql")));
    }

    @Test
    @Order(1)
    public void createGoodTest() {
        assertTrue(new GoodDaoImpl().createGood(testGoodBean));
    }

    @Test
    public void createGood_ThatAlreadyExisted_Test() {
        assertFalse(new GoodDaoImpl().createGood(testGoodBean));
    }

    @Test
    @Order(2)
    public void updateGoodTest() {
        testGoodBean.setCode("00000000");
        testGoodBean.setTitle("TEST-TITLE-UPDATED");
        testGoodBean.setDescription("TEST-DESCRIPTION-UPDATED");
        testGoodBean.setPrice(9999.99f);

        assertTrue(new GoodDaoImpl().updateGood(testGoodBean));
    }

    @Test
    public void updateGood_withIncorrectGoodCode_Test() {
        testGoodBean.setCode("000000000");
        testGoodBean.setTitle("TEST-TITLE-UPDATED");
        testGoodBean.setDescription("TEST-DESCRIPTION-UPDATED");
        testGoodBean.setPrice(9999.99f);

        assertFalse(new GoodDaoImpl().updateGood(testGoodBean));
    }

    @Test
    @Order(3)
    public void getGoodByCodeTest() {
        GoodBean goodBean = new GoodDaoImpl().getGoodByCode(testGoodBean.getCode());
        assertNotNull(goodBean);
        assertEquals(testGoodBean.getCode(), goodBean.getCode());
        assertEquals(testGoodBean.getMeasurement().getId(), goodBean.getMeasurement().getId());
        assertEquals(testGoodBean.getTitle(), goodBean.getTitle());
        assertEquals(testGoodBean.getDescription(), goodBean.getDescription());
        assertEquals(testGoodBean.getPrice(), goodBean.getPrice());
    }

    @Test
    @Order(4)
    public void getGoodByTitleTest() {
        GoodBean goodBean = new GoodDaoImpl().getGoodByTitle(testGoodBean.getTitle());
        assertNotNull(goodBean);
        assertEquals(testGoodBean.getCode(), goodBean.getCode());
        assertEquals(testGoodBean.getMeasurement().getId(), goodBean.getMeasurement().getId());
        assertEquals(testGoodBean.getTitle(), goodBean.getTitle());
        assertEquals(testGoodBean.getDescription(), goodBean.getDescription());
        assertEquals(testGoodBean.getPrice(), goodBean.getPrice());
    }

    @Test
    @Order(5)
    public void getGoodsByLikeTitleTest() {
        GoodBean[] goodBeans = new GoodDaoImpl().getGoodsByLikeTitle("%" + "TITLE-" + "%");
        assertEquals(3, goodBeans.length);
    }

    @Test
    @Order(6)
    public void getAllGoodsTest() {
        GoodBean[] goodBeans = new GoodDaoImpl().getAllGoods();
        assertEquals(5, goodBeans.length);
    }

    @Test
    @Order(7)
    public void getGoodMeasurementTest() {
        assertEquals(1, new GoodDaoImpl().getGoodMeasurement("00000000").getId());
    }

    @Test
    @Order(8)
    public void getGoodsTest() {
        GoodBean[] goodBeans = new GoodDaoImpl().getGoods("%TITLE-%", "title", 0, 10);
        assertEquals(4, goodBeans.length);
    }

    @Test
    @Order(9)
    public void getAllGoodCodesTest() {
        String[] codes = new GoodDaoImpl().getAllGoodCodes();
        assertEquals(5, codes.length);
    }

    @AfterAll
    public static void end() throws ManagedProcessException {
        MarinaDB4jHelper.closeInMemoryDB();
    }

}
