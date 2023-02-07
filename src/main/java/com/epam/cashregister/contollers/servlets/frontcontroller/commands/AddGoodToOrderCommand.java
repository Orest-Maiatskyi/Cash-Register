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
 * AddGoodToOrderCommand is a class that extends FrontCommand and is used to add a good to an order.
 * The class takes in parameters for orderId, goodCode, and quantity and creates a new OrderBean.
 * The AddGoodToOrderValidator is used to validate the OrderBean. If the validation is successful,
 * the class will add the good to the order using the OrderDaoImpl. If the good has already been added to the order,
 * the class will log a warning and return a response with a status message indicating this.
 * If there is a validation error, the class will log a warning and return a response with the error message.
 */
public class AddGoodToOrderCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        int orderId = -1;
        try { orderId = Integer.parseInt(request.getParameter("orderId")); }
        catch (Exception ignore) { }

        String goodCode = request.getParameter("goodCode");

        float quantity = -1.0f;
        try { quantity = Float.parseFloat(request.getParameter("quantity")); }
        catch (Exception ignore) { }

        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(orderId);
        orderBean.setGoodCode(goodCode);
        orderBean.setQuantity(quantity);

        try {
            AddGoodToOrderValidator.validate(orderBean);

            if (new OrderDaoImpl().getOrderedGood(orderBean) == null) {
                if (new OrderDaoImpl().addGood(orderBean)) {
                    logger.info("Good added to order successfully");
                    AjaxResponseWriter.write(response, 200, Map.of("status", "Good added to order successfully."));
                } else {
                    logger.warn("Failed to add good to order");
                    AjaxResponseWriter.write(response, 400, Map.of("status", "Something went wrong..."));
                }
            } else {
                logger.warn("Failed to add good to order (The good code already added)");
                AjaxResponseWriter.write(response, 400, Map.of("status", "This good has already been added."));
            }
        } catch (ValidatorException e) {
            logger.warn(e);
            AjaxResponseWriter.write(response, 400, Map.of("status", e.getMessage()));
        }
    }
}
