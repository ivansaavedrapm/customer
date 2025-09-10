package com.customer.api.service;

import java.util.List;

import com.customer.api.dto.DtoRegionIn;
import com.customer.api.entity.Region;
import com.customer.commons.dto.ApiResponse;

public interface SvcRegion {

	public List<Region> findAll();
	public List<Region> findActive();
	public ApiResponse create(DtoRegionIn in);
	public ApiResponse update(DtoRegionIn in, Integer id);
	public ApiResponse enable(Integer id);
	public ApiResponse disable(Integer id);
}


