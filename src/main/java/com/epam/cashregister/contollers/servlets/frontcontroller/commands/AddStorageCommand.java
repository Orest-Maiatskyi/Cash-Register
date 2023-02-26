package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.StorageBean;
import com.epam.cashregister.services.dao.impl.StorageDaoImpl;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import com.epam.cashregister.services.validateservice.ValidatorBuilder;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

public class AddStorageCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {
        String address = request.getParameter("address");

        try {
            new ValidatorBuilder().validateStorageAddress(address);
            StorageBean storageBean = new StorageBean();
            storageBean.setAddress(address);
            if (new StorageDaoImpl().addStorage(storageBean)) {
                logger.info("Storage added successfully");
                AjaxResponseWriter.write(response, 200, Map.of("status", "Storage added successfully."));
            } else {
                logger.warn("Failed to add new storage (Address already exist)");
                AjaxResponseWriter.write(response, 400, Map.of("status", "Address already exist."));
            }
        } catch (ValidatorException e) {
            logger.warn(e);
            AjaxResponseWriter.write(response, 400, Map.of("status", e.getMessage()));
        }
    }

}
