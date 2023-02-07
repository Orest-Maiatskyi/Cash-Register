package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.OrderBean;
import com.epam.cashregister.services.dao.impl.OrderDaoImpl;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import com.epam.cashregister.services.validateservice.ValidatorBuilder;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 The CancelOrderedGoodCommand class is a subclass of FrontCommand that is responsible for cancelling
 an ordered good.
 */
public class CancelOrderedGoodCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        int orderId = -1;
        try { orderId = Integer.parseInt(request.getParameter("orderId")); }
        catch (Exception ignore) { }

        String goodCode = request.getParameter("goodCode");

        try {
            new ValidatorBuilder().validateOrderId(orderId).validateGoodCode(goodCode);
            OrderBean orderBean = new OrderBean();
            orderBean.setOrderId(orderId);
            orderBean.setGoodCode(goodCode);

            if (new OrderDaoImpl().cancelOrderedGood(orderBean)) {
                logger.info("Ordered good canceled successfully");
                AjaxResponseWriter.write(response, 200, Map.of("status", "Success."));
            } else {
                logger.warn("Failed to cancel ordered good");
                AjaxResponseWriter.write(response, 400, Map.of("status", "Failed to cancel ordered good."));
            }
        } catch (ValidatorException e) {
            logger.warn(e);
            AjaxResponseWriter.write(response, 400, Map.of("status", e.getMessage()));
        }

    }

}
