package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.entities.MeasurementBean;
import com.epam.cashregister.services.dao.impl.GoodDaoImpl;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import com.epam.cashregister.services.validateservice.validators.CreateGoodValidator;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 The CreateGoodCommand class is a subclass of FrontCommand that is responsible for creating the good and adding it to the database.
 */
public class CreateGoodCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        String code = request.getParameter("code");

        int measurementId = -1;
        try { measurementId = Integer.parseInt(request.getParameter("measurementId")); }
        catch (Exception ignored) { }

        String title = request.getParameter("title");
        String description = request.getParameter("description");

        float price = -1f;
        try { price = Float.parseFloat(request.getParameter("price")); }
        catch (Exception ignored) { }

        GoodBean goodBean = new GoodBean();
        MeasurementBean measurementBean = new MeasurementBean();
        measurementBean.setId(measurementId);

        goodBean.setCode(code);
        goodBean.setMeasurement(measurementBean);
        goodBean.setTitle(title);
        goodBean.setDescription(description);
        goodBean.setPrice(price);

        try {
            CreateGoodValidator.validate(goodBean);
            if (new GoodDaoImpl().createGood(goodBean)) {
                logger.info("Successful good creation");
                AjaxResponseWriter.write(response, 200, Map.of("status", "Successful good creation."));
            } else {
                logger.info("Unsuccessful good creation (good code is already exist)");
                AjaxResponseWriter.write(response, 400, Map.of("status", "Unable to create good. The good code " + code + " already existed."));
            }
        } catch (ValidatorException e) {
            logger.warn(e);
            AjaxResponseWriter.write(response, 400, Map.of("status", e.getMessage()));
        }

    }

}
