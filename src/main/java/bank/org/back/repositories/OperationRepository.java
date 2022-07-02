package bank.org.back.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import bank.org.back.entities.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long> {
	
	public List<Operation> findByBankAccountId(String accoundId);
	public Page<Operation> findByBankAccountId(String accoundId, Pageable pageable);
	

}
