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

	@Autowired
	RepoRegion repo;
	
	@Override
	public ResponseEntity<List<Region>> getRegions() {
		return new ResponseEntity<>(repo.getRegions(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ApiResponse> createRegion(DtoRegionIn in) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ApiResponse> updateRegion(DtoRegionIn in, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ApiResponse> enableRegion(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ApiResponse> disableRegion(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
