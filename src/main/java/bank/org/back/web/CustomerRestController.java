package bank.org.back.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bank.org.back.dtos.CustomerDTO;
import bank.org.back.services.BankAccountService;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/customer")
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerRestController {
	
	private BankAccountService bankService;
	
	@GetMapping("/customers")
	public List<CustomerDTO> customers(){
		return bankService.listCustomers();
	}
	
	@GetMapping("/customers/search")
	public List<CustomerDTO> searchCutomers(@RequestParam (name="keyword",defaultValue="") String keyword){
		return bankService.searchCustomers(keyword);
	}
	
	@GetMapping("/customers/{id}")
	public CustomerDTO getCustomer(@PathVariable(name="id") String customerId){
		return bankService.getCustomer(customerId);
	}
	
	@PostMapping("/customers")
	public CustomerDTO saveCustomer(@RequestBody CustomerDTO request){
		
		return bankService.saveCustomer(request);
		
	}
	
	@PutMapping("/customers/{customerId}")
	public CustomerDTO updateCustomer(@PathVariable(name="customerId") String customerId,@RequestBody CustomerDTO custDTO){
		custDTO.setId(customerId);		
		return bankService.updateCustomer(custDTO);
		
	}
	
	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable String id){
		bankService.deleteCustomer(id);
	}

}
