package io.github.palharesricardo.clientejavaapi.exception;

/**
 * Class that implements AccountNotFoundException in the API
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
public class AccountNotFoundException extends Exception {

	private static final long serialVersionUID = -7139304880555402679L;
	
	public AccountNotFoundException(){
		super();
	}
	
	public AccountNotFoundException(String msg){
		super(msg);
	}
	
	public AccountNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}

}
