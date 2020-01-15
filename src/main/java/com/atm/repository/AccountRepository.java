package com.atm.repository;

import com.atm.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, String> {
    Optional<Account> findByAccountNo(String acctNo);
    Account findByAccountNoAndPin(String accountNo, String pin);
}
