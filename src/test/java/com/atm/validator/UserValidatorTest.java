package com.atm.validator;

import com.atm.model.ErrorItem;
import com.atm.model.LoginParam;
import com.atm.service.AccountService;
import com.atm.service.ValidationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class UserValidatorTest {
    @Mock
    private ValidationService validationService;

    @Mock
    private Errors errors;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private UserValidator userValidator;

    @Test
    public void testValidator_return(){
        LoginParam lp = new LoginParam("112233", "111111");
        List<ErrorItem> items = new ArrayList<>();
        items.add(new ErrorItem("account", "numeric only"));

        when(validationService.login(lp.getAccountNo(), lp.getPin())).thenReturn(items);
        when(accountService.login(lp.getAccountNo(), lp.getPin())).thenReturn(null);
        userValidator.validate(lp,errors);
    }
}
