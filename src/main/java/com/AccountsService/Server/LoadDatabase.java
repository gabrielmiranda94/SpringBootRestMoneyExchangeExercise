package com.AccountsService.Server;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.javamoney.moneta.Money;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LoadDatabase {
	
			private static final org.slf4j.Logger log =  LoggerFactory.getLogger(LoadDatabase.class);
			Currency cur = Currency.getInstance("CHF");
			Currency curEUR = Currency.getInstance("EUR");
			BigDecimal  money = new BigDecimal("10.03");
			Number n = 2;
			Money moneyCHF = Money.of(100, cur.getCurrencyCode());
			Money moneyEUR = Money.of(100, curEUR.getCurrencyCode());
	  @Bean
	  CommandLineRunner initDatabase(AccountRepository repository,TransferRepository transferRepository) {
	    return args -> {
	    	
	      log.info("Preloading " + repository.save(new Account(1,"Gabriel",cur,moneyCHF,true)));
	      log.info("Preloading " + repository.save(new Account(2,"Pedro",curEUR,moneyEUR,true)));
	      log.info("Preloading " + repository.save(new Account(3,"Eduardo",cur,moneyCHF,false)));
	      log.info("Preloading " + repository.save(new Account(4,"Joao",curEUR,moneyEUR,false)));

	    };
	  }
	  
	  @Bean
	  CommandLineRunner initDatabase2(TransferRepository transferRepository) {
			//atomic int
			
	    return args -> {
	      
	      log.info("Preloading " + transferRepository.save(new Transfer(5,1,2,money,"")));
	    };
	  }
}
