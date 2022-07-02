package bank.org.back.services;


import java.util.List;

import bank.org.back.dtos.AccountHistoryDTO;
import bank.org.back.dtos.BankAccountDTO;
import bank.org.back.dtos.CurrentBankAccountDTO;
import bank.org.back.dtos.CustomerDTO;
import bank.org.back.dtos.OperationDTO;
import bank.org.back.dtos.SavingBankAccountDTO;
import bank.org.back.exception.BalanceNotSuffisantException;
import bank.org.back.exception.BankAccountNotFoundException;

public interface BankAccountService {
	public CustomerDTO saveCustomer(CustomerDTO customer);
	public CurrentBankAccountDTO saveCurrentkAccount(double initialeBalance, double overDraft, String customerId);
	public SavingBankAccountDTO savesavingkAccount(double initialeBalance, double interestRate, String customerId);
	public List<CustomerDTO> listCustomers();
	public BankAccountDTO getBankAccount(String bankId) throws BankAccountNotFoundException;
	public void debit(double montant,String accountId, String description) throws BankAccountNotFoundException, BalanceNotSuffisantException;
	public void credit(double montant,String accountId, String description) throws BankAccountNotFoundException;
	public void transfert(String accountSource, String accountDestination,double montant) throws BankAccountNotFoundException, BalanceNotSuffisantException;
	public List<BankAccountDTO> bankAccountList();
	public CustomerDTO getCustomer(String customerId);
	public CustomerDTO updateCustomer(CustomerDTO customer);
	public void deleteCustomer(String customerId);
	public List<OperationDTO> accountHistory(String accountId);
	public List<CustomerDTO> searchCustomers(String keyword);
	public AccountHistoryDTO getAccountHistory(String accountId, int page, int size);
	
}
