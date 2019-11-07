package com.atm.service;

import com.atm.exception.AtmValidationException;
import com.atm.model.Account;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

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
    void login(String account, String pin) throws ValidationException;
    void transferValidation(String srcAccountNo, String dstAccountNo, Double trxAmount) throws ValidationException;
    void withdrawalValidation(String srcAccountNo, Double trxAmount);
}
