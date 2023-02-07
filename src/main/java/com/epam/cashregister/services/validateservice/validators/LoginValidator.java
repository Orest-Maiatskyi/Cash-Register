package com.epam.cashregister.services.validateservice.validators;

import com.epam.cashregister.entities.LoginBean;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.validateservice.Validator;
import com.epam.cashregister.services.validateservice.ValidatorBuilder;

public class LoginValidator {

    public static Validator validate(final LoginBean loginBean) throws ValidatorException {
        Validator validator = new ValidatorBuilder();

        validator
                .validateLoginEmail(loginBean.getEmail())
                .validateLoginPassword(loginBean.getPassword());

        return validator;
    }

}
