package com.customer.api.service;

import com.customer.api.dto.DtoCustomerImageIn;
import com.customer.commons.dto.ApiResponse;

public interface SvcCustomerImage {

	public ApiResponse upload(DtoCustomerImageIn in);
}
