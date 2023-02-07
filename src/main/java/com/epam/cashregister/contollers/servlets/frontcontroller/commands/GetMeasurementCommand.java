package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.services.dao.impl.GoodDaoImpl;
import com.epam.cashregister.services.utils.AjaxResponseWriter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 The GetMeasurementCommand class is a subclass of FrontCommand that is responsible for getting good measurements by good code.
 */
public class GetMeasurementCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        Map<String, String[]> goodParams = request.getParameterMap();
        String code = Arrays.asList(goodParams.get("code")).get(0);
        GoodBean goodBean = new GoodDaoImpl().getGoodByCode(code);

        String respData;
        if (goodBean != null) respData = goodBean.getMeasurement().getName();
        else respData = "N/A";

        AjaxResponseWriter.write(response, 200, Map.of("measurement", respData));

    }

}
