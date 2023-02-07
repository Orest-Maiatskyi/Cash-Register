package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.OrderBean;
import com.epam.cashregister.services.dao.impl.OrderDaoImpl;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import com.epam.cashregister.services.validateservice.validators.PaginationValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 * The GetPaginatedOrderListCommand class is a subclass of FrontCommand that is responsible for orders pagination.
 * Sort and paginate orders by like-data, order-by, rows-per-page
 */
public class GetPaginatedOrderListCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        String likeData = request.getParameter("likeData");
        String orderBy = request.getParameter("orderBy");

        int rowCount = -1;
        try { rowCount = Integer.parseInt(request.getParameter("rowCount")); }
        catch (Exception ignore) { }

        int offset = -1;
        try { offset = Integer.parseInt(request.getParameter("offset")); }
        catch (Exception ignore) { }

        try {
            PaginationValidator.validate(likeData, orderBy, offset, rowCount);

            OrderDaoImpl orderDao = new OrderDaoImpl();
            OrderBean[] orderBeans = orderDao.getOrderList(likeData, orderBy, offset, rowCount);

            int numOfRecords = orderDao.getNumOfRecords();

            JSONArray ordersJson = new JSONArray();
            for (OrderBean orderBean : orderBeans) {
                JSONObject tempGoodJson = new JSONObject();
                tempGoodJson.put("orderId", orderBean.getOrderId());
                tempGoodJson.put("status", orderBean.getStatus());
                tempGoodJson.put("goodCode", orderBean.getGoodCode());
                tempGoodJson.put("quantity", orderBean.getQuantity());
                ordersJson.put(tempGoodJson);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("numOfRecords", numOfRecords);
            jsonObject.put("orders", ordersJson);

            logger.info("Successful get paginated order list");
            AjaxResponseWriter.write(response, 200, Map.of("data", jsonObject.toString()));

        } catch (ValidatorException e) {
            logger.warn(e);
            AjaxResponseWriter.write(response, 400, Map.of("status", e.getMessage()));
        }
    }

}
