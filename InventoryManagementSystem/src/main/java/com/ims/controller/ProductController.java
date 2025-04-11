package com.ims.controller;

import java.math.BigDecimal;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ims.dto.CategoryDTO;
import com.ims.dto.ProductDTO;
import com.ims.dto.Response;
import com.ims.service.CategoryService;
import com.ims.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> addproduct(
			@RequestParam("imageFile") MultipartFile imageFile,
			@RequestParam("name") String name,
			@RequestParam("sku") String sku,
			@RequestParam("price") BigDecimal price,
			@RequestParam("stockQuantity") Integer stockQuantity,
			@RequestParam("categoryId") Long categoryId,
			@RequestParam(value="description",required = false) String description
			
			) {
		ProductDTO productDTO=new ProductDTO();
		productDTO.setName(name);
		productDTO.setSku(sku);
		productDTO.setPrice(price);
		productDTO.setStockQuantity(stockQuantity);
		productDTO.setCategoryId(categoryId);
		productDTO.setDescription(description);
		
		return new ResponseEntity(productService.saveProduct(productDTO,imageFile), HttpStatus.CREATED);
	
	}

	

	@PutMapping("/update")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> updateProduct(
			@RequestParam( value = "imageFile", required=false) MultipartFile imageFile,
			@RequestParam( value = "id", required=false) Long id,
			@RequestParam( value = "name", required=false) String name,
			@RequestParam( value = "sku", required=false) String sku,
			@RequestParam( value = "price", required=false) BigDecimal price,
			@RequestParam( value = "stockQuantity", required=false) Integer stockQuantity,
			@RequestParam( value = "categoryId", required=false) Long categoryId,
			@RequestParam(value="description",required = false) String description
			
			) {

		ProductDTO productDTO=new ProductDTO();
		productDTO.setId(id);
		productDTO.setName(name);
		productDTO.setSku(sku);
		productDTO.setPrice(price);
		productDTO.setStockQuantity(stockQuantity);
		productDTO.setCategoryId(categoryId);
		productDTO.setDescription(description);
		
		return ResponseEntity.ok(productService.updateProduct(productDTO,imageFile));
		
	}

	@GetMapping("/all")
	public ResponseEntity<Response> getAllProducts() {
		return ResponseEntity.ok(productService.getAllProduct());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response> getProductById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(productService.getProductById(id));
	}
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> deleteProduct(@PathVariable("id") Long id) {
		return ResponseEntity.ok(productService.deleteProduct(id));
	}

}
