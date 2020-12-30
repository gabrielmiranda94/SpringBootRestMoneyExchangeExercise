package com.AccountsService.Server;

public class NotFoundException extends RuntimeException{
	
	String exception;

	 public NotFoundException(int id,String exception) {
		    super(exception+ "  id : " + id);
		  }
	 
	 public NotFoundException(String exception) {
		    super(exception);
		  }

	
	 
	 
	 
}
