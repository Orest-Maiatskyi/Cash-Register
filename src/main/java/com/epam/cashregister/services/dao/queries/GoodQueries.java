package com.epam.cashregister.services.dao.queries;

public class GoodQueries {

    public static String insertGoodToGoodsTable = "INSERT INTO goods (code, measurement_id, title, description, price) VALUES (?, ?, ?, ?, ?)";
    public static String updateGood = "UPDATE goods SET price = ?, description = ?, title = ? WHERE code = ?";
    public static String selectGoodByCode = "SELECT * FROM goods INNER JOIN measurements ON measurement_id = measurements.id WHERE code = ?";
    public static String selectGoodByTitle = "SELECT * FROM goods INNER JOIN measurements ON measurement_id = measurements.id WHERE title = ?";
    public static String selectGoodsByLikeTitle = "SELECT * FROM warehouse INNER JOIN goods ON good_code = goods.code INNER JOIN measurements ON measurement_id = measurements.id WHERE CONCAT (IFNULL(goods.id, ''), IFNULL(code, ''), IFNULL(measurement, ''), IFNULL(title, ''), IFNULL(description, ''), IFNULL(price, '')) LIKE ?";
    public static String selectAllGoods = "SELECT * FROM goods INNER JOIN measurements ON measurement_id = measurements.id";
    public static String selectPaginatedGoods = "SELECT SQL_CALC_FOUND_ROWS * FROM goods INNER JOIN measurements ON measurement_id = measurements.id WHERE CONCAT (IFNULL(goods.id, ''), IFNULL(code, ''), IFNULL(measurement, ''), IFNULL(title, ''), IFNULL(description, ''), IFNULL(price, '')) LIKE ? ORDER BY <&_&> LIMIT ?, ?";
    public static String selectAllGoodCodes = "SELECT code FROM goods";
    public static String selectGoodMeasurementByCode = "SELECT measurements.id, measurements.measurement FROM goods INNER JOIN measurements ON measurement_id = measurements.id WHERE code = ?";
    public static String selectAllGoodMeasurements = "SELECT * FROM measurements";

}
