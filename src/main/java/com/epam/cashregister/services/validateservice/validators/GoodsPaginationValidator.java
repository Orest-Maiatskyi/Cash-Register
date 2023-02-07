package com.epam.cashregister.services.validateservice.validators;

import com.epam.cashregister.services.exceptions.ValidatorException;

public class GoodsPaginationValidator {

    public static void validate(String likeData, String orderBy, int offset, int rowCount) throws ValidatorException {

        String exception = null;

        if (likeData == null) exception = "Invalid search value.";
        else if (orderBy == null || (
                !orderBy.equals("goods.id") && !orderBy.equals("code") &&
                !orderBy.equals("measurement") && !orderBy.equals("title") &&
                !orderBy.equals("description") && !orderBy.equals("price") &&
                !orderBy.equals("warehouse.id") && !orderBy.equals("address") &&
                !orderBy.equals("good_code") && !orderBy.equals("quantity")
        )) exception = "Invalid sort value.";
        else if (offset == -1) exception = "Invalid limiter values.";
        else if (rowCount == -1) exception = "Invalid limiter values.";

        if (exception != null) throw new ValidatorException(exception);
    }

}
