package com.customer.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.customer.api.dto.DtoCustomerIn;
import com.customer.api.dto.DtoCustomerListOut;
import com.customer.api.dto.DtoCustomerOut;
import com.customer.api.entity.Customer;
import com.customer.api.repository.RepoCustomer;
import com.customer.commons.dto.ApiResponse;
import com.customer.commons.mapper.MapperCustomer;
import com.customer.exception.ApiException;
import com.customer.exception.DBAccessException;

@Service
public class SvcCustomerImp implements SvcCustomer{
	
	@Autowired
	RepoCustomer repo;

	@Autowired
	MapperCustomer mapper;
	
	@Override
	public List<DtoCustomerListOut> findAll() {
		try {
			List<Customer> customers = repo.findAll();
			return mapper.toListOut(customers);
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public DtoCustomerOut get(Integer id) {
		try {
			validateCustomerId(id);
			
			// getCustomer
			
			return null;
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public ApiResponse create(DtoCustomerIn in) {
		try {
			Customer customer = mapper.fromDtoIn(in);
			repo.save(customer);
			return new ApiResponse("El cliente ha sido registrado");
		}catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_customer_rfc"))
				throw new ApiException(HttpStatus.CONFLICT, "El rfc del cliente ya está registrado");
			if (e.getLocalizedMessage().contains("ux_customer_mail"))
				throw new ApiException(HttpStatus.CONFLICT, "El mail del cliente ya está registrado");
			if (e.getLocalizedMessage().contains("fk_customer_region"))
				throw new ApiException(HttpStatus.NOT_FOUND, "El id de región no existe");
			throw new DBAccessException(e);
		}
	}

	@Override
	public ApiResponse update(Integer id, DtoCustomerIn in) {
		try {
			validateCustomerId(id);
			Customer customer = mapper.fromDtoIn(id, in);
			repo.save(customer);
			return new ApiResponse("El cliente ha sido actualizado");
		}catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_customer_rfc"))
				throw new ApiException(HttpStatus.CONFLICT, "El rfc del cliente ya está registrado");
			if (e.getLocalizedMessage().contains("ux_customer_mail"))
				throw new ApiException(HttpStatus.CONFLICT, "El mail del cliente ya está registrado");
			if (e.getLocalizedMessage().contains("fk_customer_region"))
				throw new ApiException(HttpStatus.NOT_FOUND, "El id de región no existe");
			throw new DBAccessException(e);
		}
	}

	@Override
	public ApiResponse enable(Integer id) {
		try {
			validateCustomerId(id);
			Customer customer = repo.findById(id).get();
			customer.setStatus(1);
			repo.save(customer);
			return new ApiResponse("El cliente ha sido activado");
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public ApiResponse disable(Integer id) {
		try {
			validateCustomerId(id);
			Customer customer = repo.findById(id).get();
			customer.setStatus(0);
			repo.save(customer);
			return new ApiResponse("El cliente ha sido desactivado");
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	private void validateCustomerId(Integer id) {
		try {
			if(repo.findById(id).isEmpty()) {
				throw new ApiException(HttpStatus.NOT_FOUND, "El id del cliente no existe");
			}
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

}
