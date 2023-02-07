package com.epam.cashregister.services.dao.queries;

public class OrderQueries {

    public static String insertNewOrder = "INSERT INTO orders () VALUES()";
    public static String selectLastOrderId = "SELECT LAST_INSERT_ID() as lastId";
    public static String selectOrdersByStatus = "SELECT id FROM orders WHERE status_id = ?";
    public static String insertGoodIntoOrdered = "INSERT INTO ordered (order_id, good_code, quantity) VALUES(?, ?, ?)";
    public static String updateOrderedGoodQuantity = "UPDATE ordered SET quantity = ? WHERE order_id = ? and good_code = ?";
    public static String selectOrderedGoodCode = "SELECT good_code FROM ordered WHERE order_id = ? and good_code = ?";
    public static String selectOrderedGoodCodes = "SELECT good_code FROM ordered WHERE order_id = ?";
    public static String selectOrderedGoods = "SELECT code, title, price, quantity FROM ordered INNER JOIN goods ON good_code = code WHERE order_id = ?";
    public static String updateOrderStatus = "UPDATE orders SET status_id = ? WHERE id = ?";
    public static String deleteOrderedGood = "DELETE FROM ordered WHERE order_id = ? and good_code = ?";
    public static String selectPaginatedOrderList = "SELECT SQL_CALC_FOUND_ROWS orders.id as order_id, status, good_code, quantity FROM orders LEFT JOIN ordered ON orders.id = ordered.order_id LEFT JOIN statuses ON orders.status_id = statuses.id WHERE CONCAT (IFNULL(orders.id, ''), IFNULL(statuses.status, ''), IFNULL(ordered.quantity, ''), IFNULL(ordered.good_code, '')) LIKE ? ORDER BY <&_&> LIMIT ?, ?";

    public static String selectSumOfWarehouseGoodQuantity = "SELECT good_code, SUM(quantity) as sum_quantity FROM warehouse WHERE good_code in <&_&> GROUP BY good_code";

    public static String insertOrderedGoodsToOutcome =
            "INSERT INTO outcome (storage_id, good_code, quantity) " +
            "select warehouse.storage_id, warehouse.good_code, " +
            "case " +
            "when sum(quantity) over(partition by good_code order by id) - quantity >= ? " +
            "then 0 " +
            "when sum(quantity) over(partition by good_code order by id) - quantity <= ? and greatest(sum(quantity) over(partition by good_code order by id) - ?, 0) - quantity <= 0 " +
            "then  quantity - greatest(sum(quantity) over(partition by good_code order by id) - ?, 0) " +
            "end as outcome " +
            "from warehouse WHERE good_code = ? ";

    public static String updateWarehouseOrderedGoodQuantity =
            "update warehouse s " +
            "inner join ( " +
            "select " +
            "s.*, " +
            "sum(quantity) over(partition by good_code order by id) sum_quantity " +
            "from warehouse s WHERE good_code = ? " +
            ") n " +
            "on n.good_code = s.good_code " +
            "and n.id = s.id " +
            "and n.sum_quantity - s.quantity < ? " +
            "set s.quantity = greatest(n.sum_quantity - ?, 0) ";
}
