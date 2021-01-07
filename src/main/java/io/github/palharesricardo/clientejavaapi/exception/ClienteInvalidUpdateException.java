package io.github.palharesricardo.clientejavaapi.exception;

/**
 * Class that implements TransactionInvalidUpdateException in the API.
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
public class ClienteInvalidUpdateException extends Exception{

	private static final long serialVersionUID = -6443362632495638948L;
	
	public ClienteInvalidUpdateException(){
		super();
	}
	
	public ClienteInvalidUpdateException(String msg){
		super(msg);
	}
	
	public ClienteInvalidUpdateException(String msg, Throwable cause){
		super(msg, cause);
	}

}
