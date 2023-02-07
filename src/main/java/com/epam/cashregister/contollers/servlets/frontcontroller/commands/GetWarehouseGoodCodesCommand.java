package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.services.dao.impl.WarehouseDaoImpl;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import org.json.JSONArray;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 * The GetWarehouseGoodCodesCommand class is a subclass of FrontCommand that is responsible for getting all good codes which are in stock;
 * */
public class GetWarehouseGoodCodesCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {
        String[] codes = new WarehouseDaoImpl().getWarehouseGoodCodes();
        if (codes != null) AjaxResponseWriter.write(response, 200, Map.of("codes", new JSONArray(codes).toString()));
        else AjaxResponseWriter.write(response, 400, Map.of("status", "Unable to get warehouse good codes."));
    }

}
