package com.customer.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.customer.api.dto.DtoCustomerOut;
import com.customer.api.entity.Customer;

@Repository
public interface RepoCustomer extends JpaRepository<Customer, Integer> {
	
	@Query(value = "SELECT c.customer_id, c.name, c.surname, c.rfc, c.mail, c.phone_number, c. address, r.region "
			+ "FROM customer c "
			+ "INNER JOIN region r ON r.region_id = c.region_id "
				+ "WHERE c.customer_id = :customer_id;", nativeQuery = true)
			DtoCustomerOut getCustomer(Integer customer_id);
}
