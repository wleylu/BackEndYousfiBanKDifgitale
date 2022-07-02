package bank.org.back.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import bank.org.back.enumerator.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE",discriminatorType=DiscriminatorType.STRING, length=2)
public abstract class BankAccount {
	@Id
	private String id;
	private Date createdAt;
	private double balance;
	@Enumerated(EnumType.STRING)
	private AccountStatus status;
	private String currency;
	@ManyToOne
	private Customer customer;
	@OneToMany(mappedBy="bankAccount")
	private List<Operation> operations;

}
