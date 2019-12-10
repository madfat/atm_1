package com.atm.utils;

import com.atm.model.Account;
import com.atm.model.Role;
import com.atm.repository.RoleRepository;
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

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        double initialBalance = 10000;
        String defaultPin = "121212";
        Set<Role> roles = populateRole();

        for (int i = 0; i<200 ; i++){
            String idx = String.format("%03d", i);
            Account act1 = new Account(String.format("John %s", idx), String.format("112%s", idx), defaultPin, initialBalance, true);
            act1.setRoles(roles);
            accountService.save(act1);
        }

    }

    private Set<Role> populateRole() {
        return new HashSet<>(roleRepository.findAll());
    }
}
