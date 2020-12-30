package com.AccountsService.Server;

public class ReceivingAccountNotFoundException extends RuntimeException{
	
	ReceivingAccountNotFoundException(int id) {
	    super("Could not find receiving account " + id);
	  }

}
