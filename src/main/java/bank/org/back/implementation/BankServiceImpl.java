package bank.org.back.implementation;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bank.org.back.dtos.AccountHistoryDTO;
import bank.org.back.dtos.BankAccountDTO;
import bank.org.back.dtos.CurrentBankAccountDTO;
import bank.org.back.dtos.CustomerDTO;
import bank.org.back.dtos.OperationDTO;
import bank.org.back.dtos.SavingBankAccountDTO;
import bank.org.back.entities.BankAccount;
import bank.org.back.entities.CurrentAccount;
import bank.org.back.entities.Customer;
import bank.org.back.entities.Operation;
import bank.org.back.entities.SavingAccount;
import bank.org.back.enumerator.OperationType;
import bank.org.back.exception.BalanceNotSuffisantException;
import bank.org.back.exception.BankAccountNotFoundException;
import bank.org.back.exception.CutomerDNotFoundExeption;
import bank.org.back.mappers.BankAccountMapperImpl;
import bank.org.back.repositories.BankAccountRepository;
import bank.org.back.repositories.CustomerRepository;
import bank.org.back.repositories.OperationRepository;
import bank.org.back.services.BankAccountService;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@SuppressWarnings("unused")
@Slf4j
public class BankServiceImpl implements BankAccountService{
	
	private BankAccountRepository bankAccountRepository;
	private CustomerRepository customerRepository;
	private OperationRepository operationRepository;
	private BankAccountMapperImpl dtoMapper;
	//Logger log=LoggerFactory.getLogger(this.getClass().getName());

	
	
	
	public BankServiceImpl(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository,
			OperationRepository operationRepository,BankAccountMapperImpl dtoMapper) {
		
		this.bankAccountRepository = bankAccountRepository;
		this.customerRepository = customerRepository;
		this.operationRepository = operationRepository;
		this.dtoMapper = dtoMapper;
	}
		

	@Override
	public CustomerDTO saveCustomer(CustomerDTO customer) {
		log.info("saving customer....");		
		Customer saveCustomer = dtoMapper.fromCustomer(customer);
		saveCustomer.setId(UUID.randomUUID().toString());
		Customer custSave= customerRepository.save(saveCustomer);
		
		return dtoMapper.fromCustomer(custSave);
	}

	
	@Override
	public CustomerDTO updateCustomer(CustomerDTO customer) {
		log.info("Update customer....");		
		Customer saveCustomer = dtoMapper.fromCustomer(customer);
		Customer custSave= customerRepository.save(saveCustomer);
		
		return dtoMapper.fromCustomer(custSave);
	}
	
	@Override
	public void deleteCustomer(String customerId){
		customerRepository.deleteById(customerId);
	}
	

	@Override
	public List<CustomerDTO> listCustomers() {
		List<Customer> customers = customerRepository.findAll();
		List<CustomerDTO> customersDTO = customers.stream()
				.map(customer->dtoMapper.fromCustomer(customer))
				.collect(Collectors.toList());
		
		return customersDTO;
	}

	@Override
	public BankAccountDTO getBankAccount(String bankId) throws BankAccountNotFoundException {
		BankAccount bankAccount = bankAccountRepository.findById(bankId)
				.orElseThrow(()->new BankAccountNotFoundException("Bank Account not find"));
		
		if (bankAccount instanceof SavingAccount){
			SavingAccount savingAccount = (SavingAccount) bankAccount;
			return dtoMapper.fromSavingAccount(savingAccount);
		}
		else 
		{
			CurrentAccount currentAccount = (CurrentAccount) bankAccount;
			return dtoMapper.fromCurrentAccount(currentAccount);
		}
		
	}

	@Override
	public void debit(double montant, String accountId, String description) throws BankAccountNotFoundException, BalanceNotSuffisantException {
		BankAccount account = bankAccountRepository.findById(accountId)
				.orElseThrow(()->new BankAccountNotFoundException("Bank Account not find"));
		if (account.getBalance()< montant )
			throw new BalanceNotSuffisantException("Solde Insuffisant");
		
		Operation operation= new Operation();
		operation.setType(OperationType.DEBIT);
		operation.setAmount(montant);
		operation.setDescription(description);
		operation.setOperationDate(new Date());
		operation.setBankAccount(account);
		operationRepository.save(operation);
		account.setBalance(account.getBalance()-montant);
		bankAccountRepository.save(account);
	}

	@Override
	public void credit(double montant, String accountId, String description) throws BankAccountNotFoundException{
		BankAccount account = bankAccountRepository.findById(accountId)
				.orElseThrow(()->new BankAccountNotFoundException("Bank Account not find"));
				
		Operation operation= new Operation();
		operation.setType(OperationType.CREDIT);
		operation.setAmount(montant);
		operation.setDescription(description);
		operation.setOperationDate(new Date());
		operation.setBankAccount(account);
		operationRepository.save(operation);
		account.setBalance(account.getBalance()+montant);
		bankAccountRepository.save(account);
		
	}

	@Override
	public void transfert(String accountSource, String accountDestination, double montant) throws BankAccountNotFoundException, BalanceNotSuffisantException {
		debit(montant, accountSource, "Transfert de fonds "+accountDestination);
		credit(montant, accountDestination, "Reception de fonds "+accountSource);
	}


	@Override
	public CurrentBankAccountDTO saveCurrentkAccount(double initialeBalance, double overDraft, String customerId) {
		
		Customer customer = customerRepository.findById(customerId).orElse(null);
		if (customer ==null)
			throw new CutomerDNotFoundExeption("Customer not found");
		
		CurrentAccount bankAccount = new CurrentAccount();
		bankAccount.setId(UUID.randomUUID().toString());
		bankAccount.setBalance(initialeBalance);
		bankAccount.setCreatedAt(new Date());
		bankAccount.setCustomer(customer);
		bankAccount.setOverDraft(overDraft);
		
		CurrentAccount currentAccount = bankAccountRepository.save(bankAccount);
		
		return dtoMapper.fromCurrentAccount(currentAccount);
	}


	@Override
	public SavingBankAccountDTO savesavingkAccount(double initialeBalance, double interestRate, String customerId) {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		if (customer ==null)
			throw new CutomerDNotFoundExeption("Customer not found");
		
		SavingAccount bankAccount = new SavingAccount();
		bankAccount.setId(UUID.randomUUID().toString());
		bankAccount.setBalance(initialeBalance);
		bankAccount.setCreatedAt(new Date());
		bankAccount.setCustomer(customer);
		bankAccount.setInterestrate(interestRate);
		
		SavingAccount currentAccount = bankAccountRepository.save(bankAccount);
		
		return dtoMapper.fromSavingAccount(currentAccount);
	}


	@Override
	public List<BankAccountDTO> bankAccountList() {
		List<BankAccount> bankAccounts = bankAccountRepository.findAll();
		
		List<BankAccountDTO> banks=bankAccounts.stream()
				.map(bank ->{
					if(bank instanceof SavingAccount){
						SavingAccount savingAccount = (SavingAccount) bank;
						return dtoMapper.fromSavingAccount(savingAccount);
					}
					else{
						CurrentAccount currentAccount = (CurrentAccount) bank;	
						return dtoMapper.fromCurrentAccount(currentAccount);
					}
				}).collect(Collectors.toList());
			
		return banks;
		
	}
	

	@Override
	public CustomerDTO getCustomer(String customerId){
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(()-> new CutomerDNotFoundExeption("Customer not found"));
		
		return dtoMapper.fromCustomer(customer);
		
	}
	
	@Override
	public List<OperationDTO> accountHistory(String accountId){
		List<Operation> operations = operationRepository.findByBankAccountId(accountId);
		List<OperationDTO> operationDTO= operations.stream()
				.map(opers-> dtoMapper.fromOperation(opers)).collect(Collectors.toList());
		return operationDTO;
	}
	
	
 
	public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) {
		BankAccount account= bankAccountRepository.findById(accountId).orElse(null);
		Page<Operation> opers=operationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));
		AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
		List<OperationDTO> oper= opers.getContent().stream()
				.map(op->dtoMapper.fromOperation(op)).collect(Collectors.toList());		
		accountHistoryDTO.setOperationDTOs(oper);
		accountHistoryDTO.setAccountId(account.getId());
		accountHistoryDTO.setBalance(account.getBalance());
		accountHistoryDTO.setCurrentPage(page);
		accountHistoryDTO.setSize(size);
		accountHistoryDTO.setTotalPages(opers.getTotalPages());
		
		return accountHistoryDTO;
	}


	@Override
	public List<CustomerDTO> searchCustomers(String keyword) {
		List<CustomerDTO> customers=customerRepository.findByNameContains(keyword).stream()
				.map(cust->{
					return dtoMapper.fromCustomer(cust);
				}).collect(Collectors.toList());
		return customers;
	}
	
	


	

}
