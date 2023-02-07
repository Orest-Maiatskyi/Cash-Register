package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.OrderBean;
import com.epam.cashregister.services.dao.impl.OrderDaoImpl;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import com.epam.cashregister.services.validateservice.validators.AddGoodToOrderValidator;


import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 * The UpdateGoodQuantityCommand class is a subclass of FrontCommand that is responsible for updating good quantity in the order by good code and order id.
 * */
public class UpdateGoodQuantityCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        int orderId = -1;
        try { orderId = Integer.parseInt(request.getParameter("orderId")); }
        catch (Exception ignore) { }

        String code = request.getParameter("goodCode");

        float quantity = -1f;
        try { quantity = Float.parseFloat(request.getParameter("quantity")); }
        catch (Exception ignored) { }


        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(orderId);
        orderBean.setGoodCode(code);
        orderBean.setQuantity(quantity);

        try {
            AddGoodToOrderValidator.validate(orderBean);
            if (new OrderDaoImpl().updateGoodQuantity(orderBean)) {
                logger.info("Good quantity updated successfully");
                AjaxResponseWriter.write(response, 200, Map.of("status", "Good quantity updated successfully."));
            } else {
                logger.info("Failed to update good quantity");
                AjaxResponseWriter.write(response, 400, Map.of("status", "Something went wrong..."));
            }
        } catch (ValidatorException e) {
            logger.warn(e);
            AjaxResponseWriter.write(response, 400, Map.of("status", e.getMessage()));
        }
    }

}
