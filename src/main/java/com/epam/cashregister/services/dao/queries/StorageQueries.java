package com.epam.cashregister.services.dao.queries;

public class StorageQueries {

    public static String selectStorageByAddress = "SELECT * FROM storages WHERE address = ?";
    public static String selectAllStorages = "SELECT * FROM storages";
    public static String insertIntoStorages = "INSERT INTO storages (address) VALUES (?)";
    public static String deleteStorages = "DELETE FROM storages WHERE (address=?)";
}
