package com.epam.cashregister.services.dao.queries;

public class WarehouseQueries {

    public static String insertGoodToIncomeTable = "INSERT INTO income (storage_id, good_code, quantity) VALUES (?, ?, ?)";
    public static String insertGoodAdditionToWarehouseTable = "INSERT INTO warehouse (storage_id, good_code, quantity) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";
    public static String insertGoodToScrappedTable= "INSERT INTO scrapped (storage_id, good_code, quantity) VALUES (?, ?, ?)";
    public static String insertGoodSubtractionToScrappedTable = "INSERT INTO warehouse (storage_id, good_code, quantity) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = quantity - VALUES(quantity)";
    public static String selectStorageByGoodCode = "SELECT storage_id, address FROM warehouse INNER JOIN storages ON storage_id = storages.id WHERE good_code = ?";
    public static String selectWarehouseGoods = "SELECT DISTINCT good_code FROM warehouse WHERE quantity != 0";
    public static String selectPaginatedWarehouseGoods = "SELECT SQL_CALC_FOUND_ROWS warehouse.id, storages.address, good_code, quantity FROM warehouse INNER JOIN storages ON storage_id = storages.id WHERE CONCAT (IFNULL(warehouse.id, ''), IFNULL(storages.address, ''), IFNULL(good_code, ''), IFNULL(quantity, '')) LIKE ? ORDER BY <&_&> LIMIT ?, ?";
    public static String selectGoodQuantity = "SELECT quantity FROM warehouse WHERE storage_id = ? and good_code = ?";

}
