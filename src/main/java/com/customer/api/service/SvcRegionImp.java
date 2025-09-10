package com.customer.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.customer.api.dto.DtoRegionIn;
import com.customer.api.entity.Region;
import com.customer.api.repository.RepoRegion;
import com.customer.commons.dto.ApiResponse;

@Service
public class SvcRegionImp implements SvcRegion{

	@Override
	public List<Region> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Region> findActive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse create(DtoRegionIn in) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse update(DtoRegionIn in, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse enable(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse disable(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
