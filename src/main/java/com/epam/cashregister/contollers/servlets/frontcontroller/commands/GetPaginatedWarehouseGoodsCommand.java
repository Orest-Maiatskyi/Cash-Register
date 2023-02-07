package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.WarehouseBean;
import com.epam.cashregister.services.dao.impl.WarehouseDaoImpl;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import com.epam.cashregister.services.validateservice.validators.GoodsPaginationValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * The GetPaginatedWarehouseGoodsCommand class is a subclass of FrontCommand that is responsible for warehouse goods (goods that are in stock) pagination.
 * Sort and paginate warehouse goods by like-data, order-by, rows-per-page
 */
public class GetPaginatedWarehouseGoodsCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        Map<String, String[]> params = request.getParameterMap();
        String likeData = Arrays.asList(params.get("likeData")).get(0);
        String orderBy = Arrays.asList(params.get("orderBy")).get(0);

        int rowCount = -1;
        try { rowCount = Integer.parseInt(Arrays.asList(params.get("rowCount")).get(0)); }
        catch (Exception ignore) { }

        int offset = -1;
        try { offset = Integer.parseInt(Arrays.asList(params.get("offset")).get(0)); }
        catch (Exception ignore) { }

        try {
            GoodsPaginationValidator.validate(likeData, orderBy, offset, rowCount);

            WarehouseDaoImpl paginationDao = new WarehouseDaoImpl();
            WarehouseBean[] warehouseBeans = paginationDao.getWarehouseGoods(likeData, orderBy, offset, rowCount);
            int numOfRecords = paginationDao.getNoOfRecords();

            JSONArray goodsJson = new JSONArray();
            for (WarehouseBean warehouseBean : warehouseBeans) {
                JSONObject tempGoodJson = new JSONObject();
                tempGoodJson.put("id", warehouseBean.getId());
                tempGoodJson.put("address", warehouseBean.getStorageBean().getAddress());
                tempGoodJson.put("code", warehouseBean.getGoodBean().getCode());
                tempGoodJson.put("quantity", warehouseBean.getQuantity());
                goodsJson.put(tempGoodJson);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("numOfRecords", numOfRecords);
            jsonObject.put("warehouseGoods", goodsJson);

            logger.info("Successful get paginated warehouse goods list");
            AjaxResponseWriter.write(response, 200, Map.of("data", jsonObject.toString()));

        } catch (ValidatorException e) {
            logger.warn(e);
            AjaxResponseWriter.write(response, 400, Map.of("status", e.getMessage()));
        }

    }

}
