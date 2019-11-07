package com.atm.repository;

import com.atm.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String> {
    Account findByAccountNo(String acctNo);
    Account findByAccountNoAndPin(String accountNo, String pin);
}
