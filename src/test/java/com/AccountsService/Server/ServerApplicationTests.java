package com.AccountsService.Server;

import java.awt.PageAttributes.MediaType;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest
class ServerApplicationTests {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	AccountRepository aR;
	

//	void contextLoads() throws Exception {
//		
//		Mockito.when(aR.findAll()).thenReturn(Collections.EMPTY_LIST);
//		
//		MvcResult mvcResult = mockMvc.perform(
//				MockMvcRequestBuilders.get("/account")
//				).andReturn();
//		
//		System.out.println(mvcResult.getResponse());
//				
//		Mockito.verify(aR).findAll();
//	
	//}

}
