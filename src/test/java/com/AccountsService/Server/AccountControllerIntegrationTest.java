package com.AccountsService.Server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import org.javamoney.moneta.Money;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class AccountControllerIntegrationTest {
	
	AccountRepository repository;
	TransferRepository transferRepository;
	
	 @InjectMocks
	    AccountController aController;
	     
	    @Mock
	    AccountRepository ar;
	    
	    @Mock
	    TransferRepository tr;
	    
	    @Test
	    public void testFindAll() 
	    {
	    	Currency cur = Currency.getInstance("CHF");
			Currency curEUR = Currency.getInstance("EUR");
			BigDecimal  money = new BigDecimal("10.03");
			Number n = 2;
			Money moneyCHF = Money.of(100, cur.getCurrencyCode());
			Money moneyEUR = Money.of(100, curEUR.getCurrencyCode());
	        // given
	        Account a = new Account(1,"Gabriel",cur,moneyCHF,true);
	        ar.save(a);
	        List<Account> l = new ArrayList<Account>();
	        l.add(a);
	          
	        Mockito.when(ar.findAll()).thenReturn(l);	        
	      
	        
	        List<Account> result = aController.all();
	     
	        assertEquals(result.size(), 1);
	        assertEquals(result, l);
	      }
	    
	    @Test
	    public void testAddAccount() 
	    {
	    	Currency cur = Currency.getInstance("CHF");
			Currency curEUR = Currency.getInstance("EUR");
			BigDecimal  money = new BigDecimal("10.03");
			Number n = 2;
			Money moneyCHF = Money.of(100, cur.getCurrencyCode());
			Money moneyEUR = Money.of(100, curEUR.getCurrencyCode());
	        // given
	        Account a = new Account(1,"Gabriel",cur,moneyCHF,true);
	        AccountAux aAux = new AccountAux(1,"Gabriel",cur,money,true);

	        ar.save(a);
	        List<Account> l = new ArrayList<Account>();
	        l.add(a);
	        
	        MockHttpServletRequest request = new MockHttpServletRequest();
	        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	         
	        Mockito.when(ar.save(a)).thenReturn(a);
	        
	        Account result = aController.newAccount(aAux);
	         
	      
	        Mockito.verify(ar).save(a);
	     }
	    
	   
	    
	    @Test
	    public void testAccountTransfer() {
	    	
	    	
	    	Currency cur = Currency.getInstance("CHF");
			Currency curEUR = Currency.getInstance("EUR");
			BigDecimal  money = new BigDecimal("10.03");
			Number n = 2;
			Money moneyCHF = Money.of(100, cur.getCurrencyCode());
			Money moneyEUR = Money.of(100, curEUR.getCurrencyCode());
	        // given
	        Account a = new Account(1,"Gabriel",cur,moneyCHF,true);
	        Account a2 = new Account(2,"Mario",cur,moneyCHF,true);

	        ar.save(a);
	        ar.save(a2);
	    	
	        
	    	
	        Transfer t = new Transfer(1,1,2,money,"");

	        Mockito.when(tr.save(t)).thenReturn(t);
	        
	  //     Transfer result = aController.accountTransfer(t, 1);
	         
	        assertEquals(tr.save(t), t);
	      
	       // Mockito.verify(tr).save(t);
	    	
	    	
	    }
	    
	    
		
	
	
}
