package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.StorageBean;
import com.epam.cashregister.services.dao.impl.StorageDaoImpl;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import org.json.JSONArray;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The GetStoragesCommand class is a subclass of FrontCommand that is responsible for getting all storages.
 * */
public class GetStoragesCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        StorageBean[] storageBeans = new StorageDaoImpl().getAllStorages();

        if (storageBeans != null) {
            List<String> storages = new ArrayList<>();
            for (StorageBean storageBean : storageBeans) storages.add(storageBean.getAddress());
            AjaxResponseWriter.write(response, 200, Map.of("storages", new JSONArray(storages).toString()));
        } else AjaxResponseWriter.write(response, 400, Map.of("storages", "Unable to get available storages."));

    }

}
