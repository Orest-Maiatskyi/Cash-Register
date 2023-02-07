package com.epam.cashregister.services.dao.queries;

public class ReportQueries {

    public static String selectIncomeGoods = "SELECT code, title, price, quantity, address, date FROM income INNER JOIN goods ON good_code = code INNER JOIN storages ON storage_id = storages.id WHERE quantity != 0";
    public static String selectOutcomeGoods = "SELECT code, title, price, quantity, address, date FROM outcome INNER JOIN goods ON good_code = code INNER JOIN storages ON storage_id = storages.id WHERE quantity != 0";
    public static String selectScrappedGoods = "SELECT code, title, price, quantity, address, date FROM scrapped INNER JOIN goods ON good_code = code INNER JOIN storages ON storage_id = storages.id WHERE quantity != 0";

}
