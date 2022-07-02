package bank.org.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import bank.org.back.entities.BankAccount;


public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

}
