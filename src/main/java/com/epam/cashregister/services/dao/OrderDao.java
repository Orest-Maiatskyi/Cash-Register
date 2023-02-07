package com.epam.cashregister.services.dao;

import com.epam.cashregister.entities.OrderBean;

public interface OrderDao {

    boolean createOrder();
    boolean addGood(OrderBean orderBean);
    boolean updateGoodQuantity(OrderBean orderBean);
    boolean closeOrder(OrderBean[] orderBeans);
    boolean cancelOrder(int orderId);
    boolean cancelOrderedGood(OrderBean orderBean);
    OrderBean[] getSumOfOrderedGoods(String[] goodCodes);
    OrderBean[] getOrdersWhichAreInProcess();
    String getOrderedGood(OrderBean orderBean);
    String[] getOrderedGoods(int orderId);
    OrderBean[] getOrderedGoods(OrderBean orderBean);
    OrderBean[] getOrderList(String likeData, String orderBy, int offset, int rowCount);
}
