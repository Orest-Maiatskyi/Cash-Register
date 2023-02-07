package com.epam.cashregister.services.validateservice.validators;

import com.epam.cashregister.entities.UserBean;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.validateservice.Validator;
import com.epam.cashregister.services.validateservice.ValidatorBuilder;

public class AddUserValidator {

    public static Validator validate(final UserBean userBean) throws ValidatorException {
        Validator validator = new ValidatorBuilder();
        validator
                .validateUserRoleId(userBean.getRoleId())
                .validateUserFirstLastName(userBean.getFirstName())
                .validateUserFirstLastName(userBean.getLastName())
                .validateLoginEmail(userBean.getEmail())
                .validateLoginPassword(userBean.getPassword_1())
                .validateLoginPassword(userBean.getPassword_2())
                .validateTwoPasswordsEquality(userBean.getPassword_1(), userBean.getPassword_2());
        return validator;
    }

}
