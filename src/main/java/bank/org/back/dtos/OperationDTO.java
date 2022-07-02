package bank.org.back.dtos;

import java.util.Date;



import bank.org.back.enumerator.OperationType;
import lombok.Data;


@Data
public class OperationDTO {	
	private Long id;
	private Date operationDate;
	private double amount;
	private OperationType type;
	private String description;
	

}
