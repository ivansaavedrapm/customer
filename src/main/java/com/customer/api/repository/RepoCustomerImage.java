package com.customer.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer.api.entity.CustomerImage;

@Repository
public interface RepoCustomerImage extends JpaRepository<CustomerImage, Integer> {

}
