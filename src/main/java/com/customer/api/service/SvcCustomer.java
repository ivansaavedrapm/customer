package com.customer.api.service;

import java.util.List;

import com.customer.api.dto.DtoCustomerIn;
import com.customer.api.dto.DtoCustomerListOut;
import com.customer.api.dto.DtoCustomerOut;
import com.customer.commons.dto.ApiResponse;

public interface SvcCustomer {

	public List<DtoCustomerListOut> findAll();
	public DtoCustomerOut get(Integer id);
	public ApiResponse create(DtoCustomerIn in);
	public ApiResponse update(Integer id, DtoCustomerIn in);
	public ApiResponse enable(Integer id);
	public ApiResponse disable(Integer id);
}
