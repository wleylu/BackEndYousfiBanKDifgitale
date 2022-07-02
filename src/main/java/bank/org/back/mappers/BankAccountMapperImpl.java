package bank.org.back.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import bank.org.back.dtos.CurrentBankAccountDTO;
import bank.org.back.dtos.CustomerDTO;
import bank.org.back.dtos.OperationDTO;
import bank.org.back.dtos.SavingBankAccountDTO;
import bank.org.back.entities.CurrentAccount;
import bank.org.back.entities.Customer;
import bank.org.back.entities.Operation;
import bank.org.back.entities.SavingAccount;

@Service
public class BankAccountMapperImpl {
	
	public CustomerDTO fromCustomer(Customer customer){
		CustomerDTO customerDTO = new CustomerDTO();
		BeanUtils.copyProperties(customer, customerDTO);
		
		return customerDTO;
	}
	
   public Customer fromCustomer(CustomerDTO customerDTO){
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDTO, customer);
		return customer;
	}

   public SavingAccount fromSavingAccount(SavingBankAccountDTO savingBankAccountDTO){
	   SavingAccount savingAccount = new SavingAccount();
	   BeanUtils.copyProperties(savingBankAccountDTO, savingAccount);
	   savingAccount.setCustomer(fromCustomer(savingBankAccountDTO.getCustomer()));
	   
	   return savingAccount;
   }
   
   public SavingBankAccountDTO fromSavingAccount(SavingAccount savingAccount){
	   SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
	   BeanUtils.copyProperties(savingAccount, savingBankAccountDTO);
	   savingBankAccountDTO.setCustomer(fromCustomer(savingAccount.getCustomer()));
	   savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
	   return savingBankAccountDTO;
   }
   
   public CurrentAccount fromCurrentAccount(CurrentBankAccountDTO currentBankAccountDTO){
	   CurrentAccount currentAccount = new CurrentAccount();
	   BeanUtils.copyProperties(currentBankAccountDTO, currentAccount);
	   currentAccount.setCustomer(fromCustomer(currentBankAccountDTO.getCustomer()));
	   
	   return currentAccount;
   }
   
   public CurrentBankAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
	   CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
	   BeanUtils.copyProperties(currentAccount, currentBankAccountDTO);
	   currentBankAccountDTO.setCustomer(fromCustomer(currentAccount.getCustomer()));
	   currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
	   return currentBankAccountDTO;
   }
   
   public Operation fromOperation(OperationDTO operationDTO){
	   Operation operation = new Operation();
	   BeanUtils.copyProperties(operationDTO, operation);
	  
	   return operation;
   }
   
   public OperationDTO fromOperation(Operation operation){
	   OperationDTO operationDTO = new OperationDTO();
	   BeanUtils.copyProperties(operation, operationDTO);
	  
	   return operationDTO;
   }
   

}
