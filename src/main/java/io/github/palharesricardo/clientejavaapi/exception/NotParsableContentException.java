package io.github.palharesricardo.clientejavaapi.exception;

/**
 * Class that implements NotParsableContentException in the API
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
public class NotParsableContentException extends Exception{

	private static final long serialVersionUID = 6208890125157318839L;
	
	public NotParsableContentException(String msg){
		super(msg);
	}
	
	public NotParsableContentException(String msg, Throwable cause){
		super(msg, cause);
	}

}
