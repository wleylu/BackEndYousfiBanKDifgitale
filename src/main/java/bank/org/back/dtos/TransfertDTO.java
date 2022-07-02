package bank.org.back.dtos;

import lombok.Data;

@Data
public class TransfertDTO {
	private String accountSource;
	private String accountDestinataire;
	private double montant;
	private String description;
	

}
