package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.services.dao.impl.GoodDaoImpl;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import org.json.JSONArray;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 The GetGoodCodesCommand class is a subclass of FrontCommand that is responsible for getting all good codes that exist in database.
 */
public class GetGoodCodesCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        String[] codes = new GoodDaoImpl().getAllGoodCodes();
        if (codes != null) AjaxResponseWriter.write(response, 200, Map.of("codes", new JSONArray(codes).toString()));
        else AjaxResponseWriter.write(response, 400, Map.of("status", "Unable to get good codes."));
    }

}
