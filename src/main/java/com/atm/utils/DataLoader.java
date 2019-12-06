package com.atm.utils;

import com.atm.model.Account;
import com.atm.model.Role;
import com.atm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account act1 = new Account();
        act1.setAccountNo("112233");
        act1.setName("Ricard");
        act1.setBalance(6000);
        act1.setPin("111111");
        act1.setStatus(true);

        Set<Role> roles = new HashSet<>();
        Role r = new Role();
        r.setName(Constant.ROLES.ADM);
        roles.add(r);

        act1.setRoles(roles);
        accountService.save(act1);

        Account act2 = new Account();
        act2.setAccountNo("112244");
        act2.setName("John");
        act2.setBalance(7000);
        act2.setPin("121212");
        act2.setStatus(true);

        Set<Role> roles2 = new HashSet<>();
        Role r2 = new Role();
        r2.setName(Constant.ROLES.ADM);
        roles2.add(r2);

        act2.setRoles(roles2);
        accountService.save(act2);
    }
}
