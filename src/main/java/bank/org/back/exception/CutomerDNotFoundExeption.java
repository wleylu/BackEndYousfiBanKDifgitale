package bank.org.back.exception;

@SuppressWarnings("serial")
public class CutomerDNotFoundExeption extends RuntimeException{
	
	public CutomerDNotFoundExeption(String message){
		super(message);
	}

}
