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
import com.ims.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> addCategory(@RequestBody CategoryDTO categoryDTO) {
		return new ResponseEntity(categoryService.creaetCategory(categoryDTO), HttpStatus.CREATED);
	}

	@GetMapping("/all")
	public ResponseEntity<Response> getAllCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response> getCategoryById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(categoryService.getCategoryById(id));
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDTO categoryDTO) {
		return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> deleteCategory(@PathVariable("id") Long id) {
		return ResponseEntity.ok(categoryService.deleteCategory(id));
	}

}
