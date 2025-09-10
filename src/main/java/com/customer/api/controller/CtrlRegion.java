package com.customer.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.api.dto.DtoRegionIn;
import com.customer.api.entity.Region;
import com.customer.api.service.SvcRegion;
import com.customer.commons.dto.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/region")
public class CtrlRegion {
	
	@Autowired
	SvcRegion svc;

	@GetMapping
	public ResponseEntity<List<Region>> findAll(){
		return ResponseEntity.ok(svc.findAll());
	}
	
	@GetMapping("/active")
	public ResponseEntity<List<Region>> findActive(){
		return ResponseEntity.ok(svc.findActive());
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse> create(@Valid @RequestBody DtoRegionIn in) {
		return ResponseEntity.ok(svc.create(in));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> update(@Valid @RequestBody DtoRegionIn in, @PathVariable("id") Integer id){
		return ResponseEntity.ok(svc.update(in, id));
	}

	@PatchMapping("/{id}/enable")
	public ResponseEntity<ApiResponse> enable(@PathVariable Integer id) {
		return ResponseEntity.ok(svc.enable(id));
	}

	@PatchMapping("/{id}/disable")
	public ResponseEntity<ApiResponse> disable(@PathVariable Integer id) {
		return ResponseEntity.ok(svc.disable(id));
	}

	
	

}
