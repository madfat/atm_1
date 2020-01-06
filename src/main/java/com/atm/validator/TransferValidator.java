package com.atm.validator;

import com.atm.model.Account;
import com.atm.model.ErrorItem;
import com.atm.model.TransferParam;
import com.atm.repository.AccountRepository;
import com.atm.service.AccountService;
import com.atm.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Service
public class TransferValidator implements Validator {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> aClass) {
        return TransferParam.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Account loginUser = authenticateUser();
        TransferParam tf = (TransferParam) o;
        List<ErrorItem> errorItems = validationService.transferValidation(loginUser.getAccountNo(),tf.getDstAccountNo(),tf.getTrxAmount());
        if (!errorItems.isEmpty()){
            for (ErrorItem errorItem : errorItems) {
                errors.rejectValue(errorItem.getFieldName(), errorItem.getErrorDesc());
            }
        }
    }

    private Account authenticateUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account userDetail = null;
        if (principal instanceof UserDetails) {
            String loginUser = ((UserDetails) principal).getUsername();
            userDetail = accountService.getAccountDetail(loginUser);
        }
        return userDetail;
    }

}
