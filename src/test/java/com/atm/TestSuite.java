package com.atm;

import com.atm.repository.AccountRepositoryTest;
import com.atm.repository.TransactionRepositoryTest;
import com.atm.service.AccountServiceTest;
import com.atm.service.TransactionServiceTest;
import com.atm.service.ValidationServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                AccountServiceTest.class,
                TransactionServiceTest.class,
                ValidationServiceTest.class,
                AccountRepositoryTest.class,
                TransactionRepositoryTest.class
        }
)
public class TestSuite {
}
