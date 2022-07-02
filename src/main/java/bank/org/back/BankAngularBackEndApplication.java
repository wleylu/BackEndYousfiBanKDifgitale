package bank.org.back;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Bean;

import bank.org.back.entities.CurrentAccount;
import bank.org.back.entities.Customer;
import bank.org.back.entities.Operation;
import bank.org.back.entities.SavingAccount;
import bank.org.back.enumerator.AccountStatus;
import bank.org.back.enumerator.OperationType;
import bank.org.back.repositories.BankAccountRepository;
import bank.org.back.repositories.CustomerRepository;
import bank.org.back.repositories.OperationRepository;
import bank.org.back.services.BankAccountService;

@SpringBootApplication
public class BankAngularBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAngularBackEndApplication.class, args);
	}
	
	//@Bean
	CommandLineRunner commandLineRunner(BankAccountService bankService){
		return args ->{
			Stream.of("KONE","Wazabanga").forEach(name ->{
				Customer cust= new Customer();
				cust.setId(UUID.randomUUID().toString());
				cust.setName(name);
				cust.setEmail(name+"@gmail.com");
				//bankService.saveCustomer(cust);
			});
			
			bankService.listCustomers().forEach(cus ->{
				bankService.saveCurrentkAccount(Math.random()*50000, 900000, cus.getId());
				bankService.savesavingkAccount(Math.random()*7000, 5.5, cus.getId());
			});
			
			bankService.bankAccountList().forEach(ac ->{
//				for(int i=0;i<10;i++){
//					try {
//						bankService.credit(Math.random()*1200, ac.getId(), "Crédit du compte "+ac.getId());
//						bankService.debit(Math.random()*300, ac.getId(), "Débit du compte "+ac.getId());
//					} catch (BankAccountNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (BalanceNotSuffisantException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
			});
			
		};
	}
	
	//@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
			                BankAccountRepository bankAccountRepository,
			                OperationRepository operationRepository){
		return args ->{
			Stream.of("Mayssane","Fahimah","Yacouba").forEach(name->{
				Customer cus= new Customer();
				cus.setId(UUID.randomUUID().toString());
				cus.setName(name);
				cus.setEmail(name+"@gmail.com");
				customerRepository.save(cus);
			});
			
			customerRepository.findAll().forEach(cust->{
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setCustomer(cust);
				currentAccount.setBalance(Math.random()*5000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setOverDraft(90000);
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCurrency("XOF");
				bankAccountRepository.save(currentAccount);
				
				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setBalance(Math.random()*7000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setCurrency("XOF");
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setInterestrate(5.5);
				savingAccount.setCustomer(cust);
				bankAccountRepository.save(savingAccount);
				
				
			});
			
			bankAccountRepository.findAll().forEach(acc->{
				for(int i=0; i<10; i++){
					Operation oper = new Operation();
					oper.setAmount(Math.random()*4000);
					oper.setBankAccount(acc);
					oper.setOperationDate(new Date());
					oper.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);				
					operationRepository.save(oper);
				}
				
			});
			
		};
	}

}
