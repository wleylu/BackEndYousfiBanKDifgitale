package bank.org.back.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bank.org.back.dtos.AccountHistoryDTO;
import bank.org.back.dtos.BankAccountDTO;
import bank.org.back.dtos.CreditDTO;
import bank.org.back.dtos.DebitDTO;
import bank.org.back.dtos.OperationDTO;
import bank.org.back.dtos.TransfertDTO;
import bank.org.back.exception.BalanceNotSuffisantException;
import bank.org.back.exception.BankAccountNotFoundException;
import bank.org.back.services.BankAccountService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/account")
@CrossOrigin("*")
public class BankAccountRestAPI {
	
	private BankAccountService bankService;
	
	@GetMapping("/accounts/{accountId}")
	public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException{
		return bankService.getBankAccount(accountId);
	}
	
	@GetMapping("/accounts")
	public List<BankAccountDTO> listAccount(){
		return bankService.bankAccountList();
	}
	
	@GetMapping("/accounts/{accountId}/operations")
	public List<OperationDTO> getHistory(@PathVariable String accountId){
		return bankService.accountHistory(accountId);
	}
	
	
	@GetMapping("/accounts/{accountId}/accoounthistory")
	public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
			@RequestParam(name="page",defaultValue="0") int page,
			@RequestParam(name="size", defaultValue="5") int size){
		
		return bankService.getAccountHistory(accountId,page,size);
	}
	
	@PostMapping("/accounts/debit")
	public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSuffisantException{
		this.bankService.debit(debitDTO.getMontant(), debitDTO.getAccountId(), debitDTO.getDescription());
		
		return debitDTO;
	}
	
	@PostMapping("/accounts/credit")
	public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException, BalanceNotSuffisantException{
		this.bankService.credit(creditDTO.getMontant(), creditDTO.getAccountId(), creditDTO.getDescription());		
		return creditDTO;
	}
	
	@PostMapping("/accounts/transfert")
	public TransfertDTO transfert(@RequestBody TransfertDTO transfertDTO) throws BankAccountNotFoundException, BalanceNotSuffisantException{
		this.bankService.transfert(transfertDTO.getAccountSource(),
				transfertDTO.getAccountDestinataire(),
				transfertDTO.getMontant());	
		
		return transfertDTO;
	}
}
