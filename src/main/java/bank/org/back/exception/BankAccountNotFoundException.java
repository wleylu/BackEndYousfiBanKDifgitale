package bank.org.back.exception;

@SuppressWarnings("serial")
public class BankAccountNotFoundException extends Exception {
	
	public BankAccountNotFoundException(String message){
		super(message);
	}

}
