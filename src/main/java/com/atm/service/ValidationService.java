package com.atm.service;

import com.atm.model.ErrorItem;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface ValidationService {
    /**
     * Validate logging account
     *
     * @param account
     * @param pin
     * @return List of error if errors occur
     */
    List<ErrorItem> login(String account, String pin);
    void transferValidation(String srcAccountNo, String dstAccountNo, Double trxAmount) throws ValidationException;
    String withdrawalValidation(String srcAccountNo, Double trxAmount);
}
