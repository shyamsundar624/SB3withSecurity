package com.ims.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ims.dto.CategoryDTO;
import com.ims.dto.Response;
import com.ims.dto.SupplierDTO;
import com.ims.service.CategoryService;
import com.ims.service.SupplierService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

	private final SupplierService supplierService;

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> addSupplier(@RequestBody SupplierDTO supplierDTO) {
		return new ResponseEntity(supplierService.creaetSupplier(supplierDTO), HttpStatus.CREATED);
	}

	@GetMapping("/all")
	public ResponseEntity<Response> getAllSuppliers() {
		return ResponseEntity.ok(supplierService.getAllSupplier());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response> getCategoryById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(supplierService.getSupplierById(id));
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> updateSupplier(@PathVariable("id") Long id, @RequestBody SupplierDTO supplierDTO) {
		return ResponseEntity.ok(supplierService.updateSupplier(id, supplierDTO));
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> deleteSupplier(@PathVariable("id") Long id) {
		return ResponseEntity.ok(supplierService.deleteSupplier(id));
	}

}
