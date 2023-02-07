package com.epam.cashregister.services.validateservice.validators;

import com.epam.cashregister.entities.OrderBean;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.validateservice.Validator;
import com.epam.cashregister.services.validateservice.ValidatorBuilder;

public class AddGoodToOrderValidator {

    public static Validator validate(final OrderBean orderBean) throws ValidatorException {
        Validator validator = new ValidatorBuilder();
        validator
                .validateOrderId(orderBean.getOrderId())
                .validateGoodCode(orderBean.getGoodCode())
                .validateGoodQuantity(orderBean.getQuantity());
        return validator;
    }

}
