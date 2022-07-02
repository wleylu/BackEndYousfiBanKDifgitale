package bank.org.back.entities;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Customer {
	@Id
	private String id;
	private String name;
	private String email;
	@OneToMany(mappedBy="customer")
	@JsonProperty(access=Access.WRITE_ONLY)
	//@JsonIgnore
	private List<BankAccount> bakAccounts;
	

}
