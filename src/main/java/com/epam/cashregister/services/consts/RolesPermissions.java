package com.epam.cashregister.services.consts;

public class RolesPermissions {

    public static String[] commodityExpertPermissions = new String[] {
            "GetPaginatedWarehouseGoods", "GetPaginatedGoodsList", "GetMeasurements",
            "GetGoodCodes", "GetGoodByCode", "GetMeasurement",
            "GetStorages", "GetStoragesForGood"
    };

    public static String[] cashierPermissions = new String[] {
            "GetGoodCodes", "GetGoodByCode", "GetOrdersWhichAreInProcess",
            "GetGoodsByLikeTitle", "GetGoodByTitle", "GetOrderedGoodCodes",
            "GetPaginatedOrderList", "GetWarehouseGoodCodes"
    };

    public static String[] seniorCashierPermissions = new String[] {
            "GetOrdersWhichAreInProcess", "GetOrderedGoodCodes"
    };

    public static String[] adminPermissions = new String[] {

    };

}
