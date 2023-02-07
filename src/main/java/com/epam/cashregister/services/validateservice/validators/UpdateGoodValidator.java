package com.epam.cashregister.services.validateservice.validators;

import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.validateservice.Validator;
import com.epam.cashregister.services.validateservice.ValidatorBuilder;

public class UpdateGoodValidator {

    public static Validator validate(final GoodBean goodBean) throws ValidatorException {
        Validator validator = new ValidatorBuilder();

        validator
                .validateGoodCode(goodBean.getCode())
                .validateGoodTitle(goodBean.getTitle())
                .validateGoodDescription(goodBean.getDescription())
                .validateGoodPrice(goodBean.getPrice());

        return validator;
    }

}
