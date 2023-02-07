package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.services.dao.impl.GoodDaoImpl;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 The GetGoodByTitleCommand class is a subclass of FrontCommand that is responsible for getting the good by its title.
 */
public class GetGoodByTitleCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        Map<String, String[]> goodParams = request.getParameterMap();
        String title = Arrays.asList(goodParams.get("title")).get(0);
        GoodBean goodBean = new GoodDaoImpl().getGoodByTitle(title);

        if (goodBean != null) {
            JSONObject goodJson = new JSONObject();
            goodJson.put("code", goodBean.getCode());
            goodJson.put("title", goodBean.getTitle());
            goodJson.put("description", goodBean.getDescription());
            goodJson.put("measurement", goodBean.getMeasurement().getName());
            goodJson.put("price", goodBean.getPrice());

            AjaxResponseWriter.write(response, 200, Map.of("good", goodJson.toString()));
        } else AjaxResponseWriter.write(response, 400, Map.of("status", "Invalid good title."));

    }

}
