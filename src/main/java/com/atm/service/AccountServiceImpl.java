package com.atm.service;

import com.atm.model.Account;
import com.atm.model.Role;
import com.atm.repository.AccountRepository;
import com.atm.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Account login(String accountNo, String pin) {
        return accountRepository.findByAccountNoAndPin(accountNo,pin);
    }

    @Override
    public Account getAccountDetail(String accountNo) {
        return accountRepository.findByAccountNo(accountNo).orElseThrow(() -> new UsernameNotFoundException("Account No NOT Found " + accountNo));
    }

    @Override
    public Iterable<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public void save(Account account) {
        account.setPin(bCryptPasswordEncoder.encode(account.getPin()));
        account.setRoles(new HashSet<>(roleRepository.findAll()));
        accountRepository.save(account);
    }

    @Override
    public Account findByAccountNo(String accountNo) {
        return accountRepository.findByAccountNo(accountNo).orElseThrow(() -> new UsernameNotFoundException("Account No NOT Found " + accountNo));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountRepository.findByAccountNo(s).orElseThrow(()-> new UsernameNotFoundException(s));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : account.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new User(account.getAccountNo(), account.getPin(), grantedAuthorities);
    }
}
