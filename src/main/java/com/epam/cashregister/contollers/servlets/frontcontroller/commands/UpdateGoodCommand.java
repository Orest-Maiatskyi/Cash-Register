package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.services.dao.impl.GoodDaoImpl;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import com.epam.cashregister.services.validateservice.validators.UpdateGoodValidator;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * The UpdateGoodCommand class is a subclass of FrontCommand that is responsible for updating good info by good title.;
 * */
public class UpdateGoodCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        Map<String, String[]> goodParams = request.getParameterMap();

        String code = Arrays.asList(goodParams.get("code")).get(0);
        String title = Arrays.asList(goodParams.get("title")).get(0);
        String description = Arrays.asList(goodParams.get("description")).get(0);

        float price = -1f;
        try { price = Float.parseFloat(Arrays.asList(goodParams.get("price")).get(0)); }
        catch (Exception ignored) { }

        GoodBean goodBean = new GoodBean();
        goodBean.setCode(code);
        goodBean.setTitle(title);
        goodBean.setDescription(description);
        goodBean.setPrice(price);

        try {
            UpdateGoodValidator.validate(goodBean);

            if (new GoodDaoImpl().updateGood(goodBean)) {
                logger.info("Good updated successfully");
                AjaxResponseWriter.write(response, 200, Map.of("status", "Good updated successfully."));

            } else {
                logger.info("Failed to update good (GoodDaoImpl.updateGood returned false)");
                AjaxResponseWriter.write(response, 400, Map.of("status", "Failed to update good."));
            }
        } catch (ValidatorException e) {
            logger.warn(e);
            AjaxResponseWriter.write(response, 400, Map.of("status", e.getMessage()));
        }
    }
}
