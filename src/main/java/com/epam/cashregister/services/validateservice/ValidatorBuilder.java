package com.epam.cashregister.services.validateservice;

import com.epam.cashregister.services.exceptions.ValidatorException;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class ValidatorBuilder implements Validator {

    public ValidatorBuilder() { super(); }

    @Override
    public Validator validateGoodCode(String goodCode) throws ValidatorException {
        String exception = null;

        if (goodCode == null) exception = "Invalid good code.";
        else if (goodCode.length() != 8) exception = "Invalid good code format (must be 8 numbers).";
        else if (!goodCode.matches("[0-9]+")) exception = "Invalid good code format (must contains only numbers).";

        if (exception != null) throw new ValidatorException(exception);
        return this;
    }

    @Override
    public Validator validateGoodTitle(String goodTitle) throws ValidatorException {
        String exception = null;

        if (goodTitle == null) exception = "Invalid good title.";
        else if (goodTitle.replaceAll(" ", "").length() < 3) exception = "Invalid good title format (must be at least 3 characters).";
        else if (goodTitle.replace("  ", "").length() != goodTitle.length()) exception = "Invalid good title format (Can't use more than one space in a row).";

        if (exception != null) throw new ValidatorException(exception);
        return this;
    }

    @Override
    public Validator validateGoodDescription(String goodDescription) throws ValidatorException {
        String exception = null;

        if (goodDescription == null) exception = "Invalid good description.";
        else if (goodDescription.replaceAll(" ", "").length() < 3) exception = "Invalid good description format (must be at least 3 characters).";
        else if (goodDescription.replace("  ", "").length() != goodDescription.length()) exception = "Invalid description title format (Can't use more than one space in a row).";

        if (exception != null) throw new ValidatorException(exception);
        return this;
    }

    @Override
    public Validator validateGoodMeasurement(String goodMeasurement) throws ValidatorException {
        String exception = null;

        if (goodMeasurement == null) exception = "Invalid good measurement.";
        else if (!goodMeasurement.equals("PC") && !goodMeasurement.equals("KG") && !goodMeasurement.equals("L")) exception = "Invalid good measurement.";

        if (exception != null) throw new ValidatorException(exception);
        return this;
    }

    @Override
    public Validator validateGoodMeasurementId(int goodMeasurementId) throws ValidatorException {
        String exception = null;

        if (goodMeasurementId == -1) exception = "Invalid good measurement id.";

        if (exception != null) throw new ValidatorException(exception);
        return this;
    }

    @Override
    public Validator validateGoodPrice(float goodPrice) throws ValidatorException {
        String exception = null;

        if (goodPrice < 0f) exception = "Invalid good price.";

        if (exception != null) throw new ValidatorException(exception);
        return this;
    }

    @Override
    public Validator validateGoodQuantity(float goodQuantity) throws ValidatorException {
        String exception = null;

        if (goodQuantity < 0f) exception = "Invalid good quantity.";

        if (exception != null) throw new ValidatorException(exception);
        return this;
    }


    @Override
    public Validator validateStorageId(int storageId) throws ValidatorException {
        String exception = null;

        if (storageId < 0) exception = "Invalid storage id.";

        if (exception != null) throw new ValidatorException(exception);
        return this;
    }

    @Override
    public Validator validateStorageAddress(String storageAddress) throws ValidatorException {
        String exception = null;

        if (storageAddress == null) exception = "Invalid storage address.";
        else if (storageAddress.replaceAll(" ", "").length() == 0) exception = "Invalid storage address format (the address mustn't be empty).";

        if (exception != null) throw new ValidatorException(exception);
        return this;
    }

    @Override
    public Validator validateOrderId(int orderId) throws ValidatorException {
        String exception = null;

        if (orderId < 0) exception = "Invalid order id.";

        if (exception != null) throw new ValidatorException(exception);
        return this;
    }

    @Override
    public Validator validateLoginEmail(String loginEmail) throws ValidatorException {
        String exception = null;

        if (loginEmail == null) exception = "Invalid login email.";
        else if (loginEmail.replaceAll(" ", "").length() == 0) exception = "Invalid login email format (the email mustn't be empty).";
        else if (!Pattern.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", loginEmail)) exception = "Incorrect email address.";

        if (exception != null) throw new ValidatorException(exception);
        return this;
    }

    @Override
    public Validator validateLoginPassword(String loginPassword) throws ValidatorException {
        String exception = null;

        if (loginPassword == null) exception = "Invalid login password.";
        else if (loginPassword.contains(" ")) exception = "Login password mustn't contains spaces.";
        else if (loginPassword.replaceAll(" ", "").length() == 0) exception = "Invalid login password format (the password mustn't be empty).";

        if (exception != null) throw new ValidatorException(exception);
        return this;
    }

    @Override
    public Validator validateUserRoleId(int role) throws ValidatorException {
        String exception = null;

        if (IntStream.of(1, 2, 3).noneMatch(x -> x == role)) exception = "Incorrect role id";

        if (exception != null) throw new ValidatorException(exception);
        return this;
    }

    @Override
    public Validator validateUserFirstLastName(String firstLastName) throws ValidatorException {
        String exception = null;

        if (firstLastName == null) exception = "Invalid name.";
        else if (firstLastName.trim().length() != firstLastName.length()) exception = "Incorrect spaces.";
        else if (firstLastName.replaceAll(" ", "").length() < 3) exception = "To short name. Requires at least 3 characters.";
        else if (!Pattern.matches("^[a-zA-Z+а-яА-Я]*$", firstLastName)) exception = "Name must contains only letters";

        if (exception != null) throw new ValidatorException(exception);
        return this;
    }

    @Override
    public Validator validateTwoPasswordsEquality(String password_1, String password_2) throws ValidatorException {
        String exception = null;

        if (!password_1.equals(password_2)) exception = "Password 1 and 2 are not equals.";

        if (exception != null) throw new ValidatorException(exception);
        return this;
    }
}
