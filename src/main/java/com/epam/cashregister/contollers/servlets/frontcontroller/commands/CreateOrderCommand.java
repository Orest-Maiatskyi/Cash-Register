package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.services.dao.impl.OrderDaoImpl;
import com.epam.cashregister.services.utils.AjaxResponseWriter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 The CreateOrderCommand class is a subclass of FrontCommand that is responsible for creating the order and adding it to the database.
 */
public class CreateOrderCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        if (new OrderDaoImpl().createOrder()) AjaxResponseWriter.write(response, 200, Map.of("status", "A new order has been successfully created with id: " + OrderDaoImpl.getLastOrderId()));
        else AjaxResponseWriter.write(response, 400, Map.of("status", "Something went wrong ..."));

    }

}
