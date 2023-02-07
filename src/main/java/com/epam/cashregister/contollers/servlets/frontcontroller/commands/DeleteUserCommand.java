package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.UserBean;
import com.epam.cashregister.services.crypto.PBKDF2;
import com.epam.cashregister.services.crypto.Util;
import com.epam.cashregister.services.dao.impl.UserDaoImpl;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import com.epam.cashregister.services.utils.PropertiesManager;
import com.epam.cashregister.services.validateservice.Validator;
import com.epam.cashregister.services.validateservice.ValidatorBuilder;
import com.epam.cashregister.services.validateservice.validators.AddUserValidator;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

public class DeleteUserCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        String email = request.getParameter("email");

        UserBean userBean = new UserBean();
        userBean.setEmail(email);

        try {
            new ValidatorBuilder().validateLoginEmail(email);

            UserBean[] userBeans = new UserDaoImpl().getAllUsers();
            if (userBeans != null) {

                boolean isAdmin = false;
                boolean isEmailMatch = false;
                for (UserBean tempBean : userBeans)
                    if (tempBean.getEmail().equals(userBean.getEmail())) {
                        isEmailMatch = true;
                        if (tempBean.getRoleId() == 4) isAdmin = true;
                        break;
                    }

                if (isEmailMatch) {

                    if (!isAdmin) {

                        if (new UserDaoImpl().removeUser(userBean)) {
                            logger.info("User removed successfully");
                            AjaxResponseWriter.write(response, 200, Map.of("status", "User removed successfully."));
                        } else {
                            logger.warn("Failed to delete user (UserDaoImpl().removeUser() returned false)");
                            AjaxResponseWriter.write(response, 400, Map.of("status", "Something went wrong..."));
                        }

                    } else {
                        logger.warn("Failed to delete user (attempt to delete user with admin role)");
                        AjaxResponseWriter.write(response, 400, Map.of("status", "You cannot delete user with admin role."));
                    }

                } else {
                    logger.warn("Failed to delete user (User with this e-mail does not exist)");
                    AjaxResponseWriter.write(response, 400, Map.of("status", "User with this e-mail does not exist."));
                }
            } else {
                logger.warn("Failed to get all users");
                AjaxResponseWriter.write(response, 400, Map.of("status", "Something went wrong..."));
            }
        } catch (ValidatorException e) {
            logger.warn(e);
            AjaxResponseWriter.write(response, 400, Map.of("status", e.getMessage()));
        }

    }
}
