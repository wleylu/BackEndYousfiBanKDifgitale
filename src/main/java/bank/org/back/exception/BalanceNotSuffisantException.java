package bank.org.back.exception;

@SuppressWarnings("serial")
public class BalanceNotSuffisantException extends Exception {
	
	public BalanceNotSuffisantException(String message){
		super(message);
	}

}
