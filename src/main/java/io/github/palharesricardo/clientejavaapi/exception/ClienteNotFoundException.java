package io.github.palharesricardo.clientejavaapi.exception;

/**
 * Class that implements TransactionNotFoundException in the API
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
public class ClienteNotFoundException extends Exception {

	private static final long serialVersionUID = -2586209354700102349L;
	
	public ClienteNotFoundException(){
		super();
	}
	
	public ClienteNotFoundException(String msg){
		super(msg);
	}
	
	public ClienteNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}
	
}
