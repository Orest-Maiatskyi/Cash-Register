package com.epam.cashregister.services.dao;

import com.epam.cashregister.entities.OrderBean;

public interface ReportDao {

    OrderBean[] getIncomeGoods();
    OrderBean[] getOutcomeGoods();
    OrderBean[] getScrappedGoods();

    boolean closeDay();

}
