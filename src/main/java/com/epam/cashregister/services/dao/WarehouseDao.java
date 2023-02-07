package com.epam.cashregister.services.dao;

import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.entities.StorageBean;
import com.epam.cashregister.entities.WarehouseBean;

public interface WarehouseDao {

    boolean addGood(WarehouseBean warehouseBean);
    boolean writeOffGood(WarehouseBean warehouseBean);
    String[] getWarehouseGoodCodes();
    StorageBean[] getStoragesForGood(String goodCode);
    WarehouseBean[] getWarehouseGoods(String likeData, String orderBy, int offset, int rowCount);
    float getGoodQuantityInStock(WarehouseBean warehouseBean);
}
