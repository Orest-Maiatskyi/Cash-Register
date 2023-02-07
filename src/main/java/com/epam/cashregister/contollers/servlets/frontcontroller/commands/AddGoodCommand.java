package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.entities.StorageBean;
import com.epam.cashregister.entities.WarehouseBean;
import com.epam.cashregister.services.dao.impl.StorageDaoImpl;
import com.epam.cashregister.services.dao.impl.WarehouseDaoImpl;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import com.epam.cashregister.services.validateservice.validators.AddGoodValidator;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 * The AddGoodCommand class extends FrontCommand and is used to add a good to a storage.
 */
public class AddGoodCommand extends FrontCommand {

    /**
     * This method processes the request to add a good to a storage.
     * Then, it creates GoodBean, StorageBean, and WarehouseBean objects, sets the relevant information and validates the data using AddGoodValidator.
     * If the validation is successful, it adds the good to the storage using WarehouseDaoImpl.addGood() method and sends a response back to the client.
     * In case of a validation error, it sends a response with the error message.
     *
     * @throws ServletException if the request could not be handled
     * @throws IOException if an input or output error is detected when the servlet handles the request
     */
    @Override
    public void process() throws ServletException, IOException {

        String code = request.getParameter("code");
        String storage = request.getParameter("storage");

        float quantity = -1f;
        try {
            quantity = Float.parseFloat(request.getParameter("quantity"));
        }
        catch (Exception ignored) { }

        GoodBean goodBean = new GoodBean();
        StorageBean storageBean = new StorageBean();
        WarehouseBean warehouseBean = new WarehouseBean();

        goodBean.setCode(code);

        int storageId = -1;
        try { storageId = new StorageDaoImpl().getStorageByAddress(storage).getId(); }
        catch (Exception ignore) { }
        storageBean.setId(storageId);

        warehouseBean.setGood(goodBean);
        warehouseBean.setStorageBean(storageBean);
        warehouseBean.setQuantity(quantity);

        try {
            AddGoodValidator.validate(warehouseBean);
            if (new WarehouseDaoImpl().addGood(warehouseBean)) {
                logger.info("Good added successfully");
                AjaxResponseWriter.write(response, 200, Map.of("status", "Good added successfully."));
            } else {
                logger.info("Failed to add good (bad transaction)");
                AjaxResponseWriter.write(response, 400, Map.of("status", "Bad transaction."));
            }
        } catch (ValidatorException e) {
            logger.warn(e);
            AjaxResponseWriter.write(response, 400, Map.of("status", e.getMessage()));
        }

    }
}
