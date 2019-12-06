package com.atm.validator;

import com.atm.model.Account;
import com.atm.model.ErrorItem;
import com.atm.model.LoginParam;
import com.atm.service.AccountService;
import com.atm.service.ValidationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Service
public class UserValidator implements Validator {
    @Autowired
    private ValidationService validationService;

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> aClass) {
        return LoginParam.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        LoginParam login = (LoginParam) o;

        List<ErrorItem> errorItemList = validationService.login(login.getAccountNo(), login.getPin());

        if (!errorItemList.isEmpty()) {
            for (ErrorItem errorItem : errorItemList) {
                errors.rejectValue(errorItem.getFieldName(), errorItem.getErrorDesc());
            }
        }

        Account acct = accountService.login(login.getAccountNo(), login.getPin());
        if (acct == null) {
            errors.rejectValue("pin", "invalid.credential");
        }
    }
}
