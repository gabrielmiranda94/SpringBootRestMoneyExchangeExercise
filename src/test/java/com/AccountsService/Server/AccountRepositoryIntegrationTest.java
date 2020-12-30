package com.AccountsService.Server;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Currency;
import java.util.Optional;

import org.javamoney.moneta.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryIntegrationTest {

	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;


    @Test
    public void whenFindByName_thenReturnAccount() {
        // given
		Currency cur = Currency.getInstance("CHF");
		Money moneyCHF = Money.of(100, cur.getCurrencyCode());
        Account account = new Account(1,"Gabriel",cur,moneyCHF,true);
        accountRepository.save(account);
   

        // when
        Optional<Account> found = accountRepository.findById(account.getId());

        // then
        
        assertEquals(account, found.get());
    }


}
