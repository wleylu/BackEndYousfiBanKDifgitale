package bank.org.back.dtos;

import lombok.Data;

@Data
public class CreditDTO {
	private String accountId;
	private double montant;
	private String description;
	

}
