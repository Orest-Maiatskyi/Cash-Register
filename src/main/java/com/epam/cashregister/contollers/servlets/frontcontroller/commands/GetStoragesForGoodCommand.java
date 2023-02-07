package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.StorageBean;
import com.epam.cashregister.services.dao.impl.WarehouseDaoImpl;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * The GetStoragesForGoodCommand class is a subclass of FrontCommand that is responsible for getting all storages that contains current good with given code.
 * */
public class GetStoragesForGoodCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        Map<String, String[]> params = request.getParameterMap();
        String code = Arrays.asList(params.get("code")).get(0);

        StorageBean[] storages = new WarehouseDaoImpl().getStoragesForGood(code);

        JSONArray jsonArray = new JSONArray();
        for (StorageBean storageBean : storages) {
            JSONObject temp = new JSONObject();
            temp.put("id", storageBean.getId());
            temp.put("address", storageBean.getAddress());
            jsonArray.put(temp);
        }

        AjaxResponseWriter.write(response, 200, Map.of("storages", new JSONArray(storages).toString()));
    }

}
