package bank.org.back.entities;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor @AllArgsConstructor
@Entity
@DiscriminatorValue(value="CA")
public class CurrentAccount extends BankAccount {
	
	private double overDraft;

	public double getOverDraft() {
		return overDraft;
	}

	public void setOverDraft(double overDraft) {
		this.overDraft = overDraft;
	}
	
	

}
