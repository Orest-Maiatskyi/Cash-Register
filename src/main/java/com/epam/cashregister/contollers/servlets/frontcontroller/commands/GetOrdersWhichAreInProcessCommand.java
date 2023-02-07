package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.OrderBean;
import com.epam.cashregister.services.dao.impl.OrderDaoImpl;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import org.json.JSONArray;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 The GetOrdersWhichAreInProcessCommand class is a subclass of FrontCommand that is responsible for getting codes that are in process (have a processing status).
 */
public class GetOrdersWhichAreInProcessCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        OrderBean[] orderBeans = new OrderDaoImpl().getOrdersWhichAreInProcess();
        if (orderBeans != null) {

            JSONArray ordersJson = new JSONArray();
            for (OrderBean orderBean : orderBeans) ordersJson.put(orderBean.getOrderId());

            AjaxResponseWriter.write(response, 200, Map.of("data", ordersJson.toString()));
        } else AjaxResponseWriter.write(response, 400, Map.of("status", "Unable to get orders which are in process."));
    }

}
