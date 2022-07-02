package bank.org.back.entities;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor @AllArgsConstructor
@Entity
@DiscriminatorValue(value="SA")
public class SavingAccount extends BankAccount {
	private double interestrate;

	public double getInterestrate() {
		return interestrate;
	}

	public void setInterestrate(double interestrate) {
		this.interestrate = interestrate;
	}
	
	

}
