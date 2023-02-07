package com.epam.cashregister.services.validateservice;

import com.epam.cashregister.services.exceptions.ValidatorException;

public interface Validator {

    Validator validateGoodCode(final String goodCode) throws ValidatorException;
    Validator validateGoodTitle(final String goodTitle) throws ValidatorException;
    Validator validateGoodDescription(final String goodDescription) throws ValidatorException;
    Validator validateGoodMeasurement(final String goodMeasurement) throws ValidatorException;
    Validator validateGoodMeasurementId(final int goodMeasurementId) throws ValidatorException;
    Validator validateGoodPrice(final float goodPrice) throws ValidatorException;
    Validator validateGoodQuantity(final float goodQuantity) throws ValidatorException;
    Validator validateStorageId(final int storageId) throws ValidatorException;
    Validator validateStorageAddress(final String storageAddress) throws ValidatorException;
    Validator validateOrderId(final int orderId) throws ValidatorException;
    Validator validateLoginEmail(final String loginEmail) throws ValidatorException;
    Validator validateLoginPassword(final String loginPassword) throws ValidatorException;
    Validator validateUserRoleId(final int role) throws ValidatorException;
    Validator validateUserFirstLastName(final String firstLastName) throws ValidatorException;
    Validator validateTwoPasswordsEquality(final String password_1, final String password_2) throws ValidatorException;
}
