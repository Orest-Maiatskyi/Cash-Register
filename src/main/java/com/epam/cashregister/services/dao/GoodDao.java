package com.epam.cashregister.services.dao;

import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.entities.MeasurementBean;

public interface GoodDao {

    boolean createGood(GoodBean goodBean);
    boolean updateGood(GoodBean goodBean);
    GoodBean getGoodByCode(String code);
    GoodBean getGoodByTitle(String title);
    GoodBean[] getGoodsByLikeTitle(String title);
    GoodBean[] getAllGoods();
    GoodBean[] getGoods(String likeData, String orderBy, int offset, int rowCount);
    String[] getAllGoodCodes();
    MeasurementBean getGoodMeasurement(String code);
    MeasurementBean[] getAllGoodMeasurements();
}
