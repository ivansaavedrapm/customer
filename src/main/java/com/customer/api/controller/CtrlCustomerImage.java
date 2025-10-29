package com.customer.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.api.dto.DtoCustomerImageIn;
import com.customer.api.service.SvcCustomerImage;
import com.customer.commons.dto.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer-image")
public class CtrlCustomerImage {

	@Autowired
    SvcCustomerImage svc;
	
    @PostMapping
    public ResponseEntity<ApiResponse> createCustomerImage(@Valid @RequestBody DtoCustomerImageIn in){
    	return ResponseEntity.ok(svc.upload(in));
    }

}
