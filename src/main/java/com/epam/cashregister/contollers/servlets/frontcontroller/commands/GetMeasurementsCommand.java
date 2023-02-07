package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.MeasurementBean;
import com.epam.cashregister.services.dao.impl.GoodDaoImpl;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 The GetMeasurementCommand class is a subclass of FrontCommand that is responsible for getting all good measurements.
 */
public class GetMeasurementsCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        MeasurementBean[] measurementBeans = new GoodDaoImpl().getAllGoodMeasurements();

        if (measurementBeans != null) {
            JSONArray measurementsJson = new JSONArray();

            for (MeasurementBean measurementBean : measurementBeans) {
                JSONObject measurementJson = new JSONObject();
                measurementJson.put("id", measurementBean.getId());
                measurementJson.put("name", measurementBean.getName());
                measurementsJson.put(measurementJson);
            }

            AjaxResponseWriter.write(response, 200, Map.of("measurements", measurementsJson.toString()));
        } else AjaxResponseWriter.write(response, 400, Map.of("status", "Unable to list all available measurements."));


    }

}
