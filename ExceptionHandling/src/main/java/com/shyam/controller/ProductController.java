package com.shyam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.entity.Product;
import com.shyam.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	private ProductService service;
	
	@PostMapping("/save")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product){
		return ResponseEntity.ok(service.createProduct(product));
	}

	@GetMapping("/getproduct/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable long id){
		return ResponseEntity.ok(service.getProduct(id));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product,@PathVariable Long id){
		return ResponseEntity.ok(service.updateProduct(product,id));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
		service.deleteProduct(id);
		return ResponseEntity.ok().build();
	}
}
