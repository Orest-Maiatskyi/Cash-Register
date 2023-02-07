package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.OrderBean;
import com.epam.cashregister.services.dao.impl.OrderDaoImpl;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import com.epam.cashregister.services.validateservice.ValidatorBuilder;
import org.json.JSONArray;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 The GetOrderedGoodCodesCommand class is a subclass of FrontCommand that is responsible for getting codes that were ordered.
 */
public class GetOrderedGoodCodesCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {

        int orderId = -1;
        try { orderId = Integer.parseInt(request.getParameter("orderId")); }
        catch (Exception ignore) { }

        try {
            new ValidatorBuilder().validateOrderId(orderId);
            String[] codes = new OrderDaoImpl().getOrderedGoods(orderId);

            if (codes != null) {
                JSONArray jsonCodes = new JSONArray();
                for (String code : codes) jsonCodes.put(code);
                AjaxResponseWriter.write(response, 200, Map.of("codes", jsonCodes.toString()));

            } else {
                logger.warn("Failed to get ordered goods.");
                AjaxResponseWriter.write(response, 400, Map.of("status", "Something went wrong..."));
            }

        } catch (ValidatorException e) {
            logger.warn(e);
            AjaxResponseWriter.write(response, 400, Map.of("status", e.getMessage()));
        }

    }
}
