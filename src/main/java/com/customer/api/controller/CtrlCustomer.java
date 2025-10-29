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

import com.customer.api.dto.DtoCustomerIn;
import com.customer.api.dto.DtoCustomerListOut;
import com.customer.api.dto.DtoCustomerOut;
import com.customer.api.service.SvcCustomer;
import com.customer.commons.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
@Tag(name = "Customer", description = "Módulo de clientes")
public class CtrlCustomer {

	@Autowired
	SvcCustomer svc;

	@GetMapping
	@Operation(summary = "Consultar clientes", description = "Consulta los clientes registrados")
	public ResponseEntity<List<DtoCustomerListOut>> findAll() {
		return ResponseEntity.ok(svc.findAll());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Consultar cliente", description = "Consulta el detalle de un cliente con su imagen")
	public ResponseEntity<DtoCustomerOut> getCustomer(@PathVariable Integer id) {
		return ResponseEntity.ok(svc.get(id));
	}

	@PostMapping
	@Operation(summary = "Registrar cliente", description = "Registro de un cliente")
	public ResponseEntity<ApiResponse> create(@Valid @RequestBody DtoCustomerIn in){
		return ResponseEntity.ok(svc.create(in));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar cliente", description = "Actualización de un cliente")
	public ResponseEntity<ApiResponse> update(@PathVariable Integer id, @Valid @RequestBody DtoCustomerIn in){
		return ResponseEntity.ok(svc.update(id, in));
	}

	@PatchMapping("/{id}/enable")
	@Operation(summary = "Activar cliente", description = "Activación de un cliente")
	public ResponseEntity<ApiResponse> enable(@PathVariable Integer id) {
		return ResponseEntity.ok(svc.enable(id));
	}

	@PatchMapping("/{id}/disable")
	@Operation(summary = "Desactivar cliente", description = "Desactivación de un cliente")
	public ResponseEntity<ApiResponse> disable(@PathVariable Integer id) {
		return ResponseEntity.ok(svc.disable(id));
	}
}
