package com.epam.cashregister.services.dao;

import com.epam.cashregister.entities.StorageBean;

public interface StorageDao {


    StorageBean getStorageById(int id);
    StorageBean getStorageByAddress(String address);
    StorageBean[] getAllStorages();

}
