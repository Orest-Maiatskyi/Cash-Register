package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.entities.StorageBean;
import com.epam.cashregister.entities.WarehouseBean;
import com.epam.cashregister.services.dao.impl.WarehouseDaoImpl;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import com.epam.cashregister.services.validateservice.ValidatorBuilder;
import com.epam.cashregister.services.validateservice.validators.WriteOffGoodValidator;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * The WriteOffTheGoodsCommand class is a subclass of FrontCommand that is responsible for writing off the goods from warehouse by storage id, good code and quantity.
 * */
public class WriteOffTheGoodsCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {

        Map<String, String[]> goodParams = request.getParameterMap();
        String goodCode = Arrays.asList(goodParams.get("code")).get(0);

        int storageId = -1;
        try { storageId = Integer.parseInt(Arrays.asList(goodParams.get("storageId")).get(0)); }
        catch (Exception ignore) { }

        float quantity = -1f;
        try { quantity = Float.parseFloat(Arrays.asList(goodParams.get("quantity")).get(0)); }
        catch (Exception ignore) { }

        GoodBean goodBean = new GoodBean();
        StorageBean storageBean = new StorageBean();
        WarehouseBean warehouseBean = new WarehouseBean();

        goodBean.setCode(goodCode);
        storageBean.setId(storageId);
        warehouseBean.setGood(goodBean);
        warehouseBean.setStorageBean(storageBean);
        warehouseBean.setQuantity(quantity);

        try {
            WriteOffGoodValidator.validate(warehouseBean);
            float currentQuantity = new WarehouseDaoImpl().getGoodQuantityInStock(warehouseBean);
            new ValidatorBuilder().validateGoodQuantity(currentQuantity);

            if (currentQuantity >= quantity) {

                if (new WarehouseDaoImpl().writeOffGood(warehouseBean)) {
                    logger.info("Good wrote off successfully");
                    AjaxResponseWriter.write(response, 200, Map.of("status", "Good wrote off successfully."));

                } else {
                    logger.info("Failed to write off the good (bad transaction)");
                    AjaxResponseWriter.write(response, 400, Map.of("status", "Bad transaction."));
                }

            } else {
                logger.info("Failed to write off the good (Product quantity exceeded)");
                AjaxResponseWriter.write(response, 400, Map.of("status", "You can not write off more goods than are in stock. Current stock quantity: " + currentQuantity));
            }

        } catch (ValidatorException e) {
            logger.warn(e);
            AjaxResponseWriter.write(response, 400, Map.of("status", e.getMessage()));
        }
    }
}
