package com.atm.validator;

import com.atm.model.ErrorItem;
import com.atm.model.TransferParam;
import com.atm.repository.AccountRepository;
import com.atm.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

import static com.atm.utils.Constant.loginAccount;

@Service
public class TransferValidator implements Validator {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ValidationService validationService;

    @Override
    public boolean supports(Class<?> aClass) {
        return TransferParam.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TransferParam tf = (TransferParam) o;
        List<ErrorItem> errorItems = validationService.transferValidation(loginAccount.getAccountNo(),tf.getDstAccountNo(),tf.getTrxAmount());
        if (!errorItems.isEmpty()){
            for (ErrorItem errorItem : errorItems) {
                errors.rejectValue(errorItem.getFieldName(), errorItem.getErrorDesc());
            }
        }
    }

}
