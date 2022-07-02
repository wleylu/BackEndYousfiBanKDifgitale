package bank.org.back.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import bank.org.back.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

	List<Customer> findByNameContains(String keyword);
}
