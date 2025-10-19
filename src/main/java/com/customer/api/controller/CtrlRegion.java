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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/region")
@Tag(name = "Region", description = "Catálogo de regiones")
public class CtrlRegion {
	
	@Autowired
	SvcRegion svc;

	@GetMapping
	@Operation(summary = "Consultar regiones", description = "Consulta todas las regiones registradas")
	public ResponseEntity<List<Region>> findAll(){
		return ResponseEntity.ok(svc.findAll());
	}
	
	@GetMapping("/active")
	@Operation(summary = "Consultar regiones activas", description = "Consulta las regiones activas registradas")
	public ResponseEntity<List<Region>> findActive(){
		return ResponseEntity.ok(svc.findActive());
	}
	
	@PostMapping
	@Operation(summary = "Registrar regiones", description = "Registra una nueva región")
	public ResponseEntity<ApiResponse> create(@Valid @RequestBody DtoRegionIn in) {
		return ResponseEntity.ok(svc.create(in));
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Actualizar regiones", description = "Actualiza una región")
	public ResponseEntity<ApiResponse> update(@Valid @RequestBody DtoRegionIn in, @PathVariable("id") Integer id){
		return ResponseEntity.ok(svc.update(in, id));
	}

	@PatchMapping("/{id}/enable")
	@Operation(summary = "Activar regiones", description = "Activa una región desactivada")
	public ResponseEntity<ApiResponse> enable(@PathVariable Integer id) {
		return ResponseEntity.ok(svc.enable(id));
	}

	@PatchMapping("/{id}/disable")
	@Operation(summary = "Desactivar regiones", description = "Desactiva una región registrada")
	public ResponseEntity<ApiResponse> disable(@PathVariable Integer id) {
		return ResponseEntity.ok(svc.disable(id));
	}

	
	

}
