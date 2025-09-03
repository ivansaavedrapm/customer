package com.customer.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.api.entity.Region;
import com.customer.api.service.SvcRegion;

@RestController
@RequestMapping("/region")
public class CtrlRegion {
	
	@Autowired
	SvcRegion svc;

	@GetMapping
	public List<Region> getRegions(){
		return svc.getRegions();
	}
	
	@PostMapping
	public String createRegion(@RequestBody Region region) {
		
		// código necesario para crear una región

		System.out.println(region.getRegion_id());
		System.out.println(region.getRegion());
		System.out.println(region.getTag());
		System.out.println(region.getStatus());
		
		return "La región se ha registrado exitosamente";
	}
	
	
	
	

}
