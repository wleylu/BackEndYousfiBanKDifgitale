package bank.org.back.dtos;

import lombok.Data;

@Data
public class DebitDTO {
	private String accountId;
	private double montant;
	private String description;
	

}
