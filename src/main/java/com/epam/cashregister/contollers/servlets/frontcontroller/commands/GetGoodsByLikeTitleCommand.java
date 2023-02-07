package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.services.dao.impl.GoodDaoImpl;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 The GetGoodsByLikeTitleCommand class is a subclass of FrontCommand that is responsible for getting all good codes and titles which are similar to given title.
 */
public class GetGoodsByLikeTitleCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        Map<String, String[]> params = request.getParameterMap();
        String title = Arrays.asList(params.get("title")).get(0);

        GoodBean[] goodBeans = new GoodDaoImpl().getGoodsByLikeTitle(title);
        if (goodBeans != null) {
            JSONArray jsonArray = new JSONArray();
            for (GoodBean goodBean : goodBeans) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", goodBean.getCode());
                jsonObject.put("title", goodBean.getTitle());
                jsonArray.put(jsonObject);
            }

            AjaxResponseWriter.write(response, 200, Map.of("data", jsonArray.toString()));
        } else AjaxResponseWriter.write(response, 400, Map.of("status", "Unable to get goods by title like."));
    }

}
