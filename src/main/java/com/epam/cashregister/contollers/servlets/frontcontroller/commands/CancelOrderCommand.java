package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.services.dao.impl.OrderDaoImpl;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import com.epam.cashregister.services.validateservice.ValidatorBuilder;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 * CancelOrderCommand class extends the FrontCommand class and implements the process method to cancel an order.
 * The method retrieves the orderId from the request, validates it and cancels the order if it's valid.
 * The response of the process method is either Success or Failed to cancel order.
 */
public class CancelOrderCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        int orderId = -1;
        try { orderId = Integer.parseInt(request.getParameter("orderId")); }
        catch (Exception ignore) { }

        try {
            new ValidatorBuilder().validateOrderId(orderId);

            if (new OrderDaoImpl().cancelOrder(orderId)) {
                logger.info("Order canceled successfully");
                AjaxResponseWriter.write(response, 200, Map.of("status", "Success."));
            } else {
                logger.warn("Failed to cancel order");
                AjaxResponseWriter.write(response, 400, Map.of("status", "Failed to cancel order."));
            }

        } catch (ValidatorException e) {
            logger.warn(e);
            AjaxResponseWriter.write(response, 400, Map.of("status", e.getMessage()));
        }

    }
}
