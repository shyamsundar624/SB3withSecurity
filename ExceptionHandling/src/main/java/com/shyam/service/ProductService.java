package com.shyam.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shyam.entity.Product;
import com.shyam.exception.DuplicateProductException;
import com.shyam.exception.ProductNotFoundException;
import com.shyam.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository repo;

	public Product createProduct(Product product) {
		if (repo.findByName(product.getName()).isPresent()) {
			throw new DuplicateProductException("Product by the name " + product.getName() + " is already available");
		}
		return repo.save(product);
	}

	public Product getProduct(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElseThrow(()-> new ProductNotFoundException("Product for Id "+id+" is not Exists"));
	}

	public Product updateProduct(Product product, Long id) {
		Product existingProduct = getProduct(id);

		existingProduct.setName(product.getName());
		existingProduct.setPrice(product.getPrice());

		return repo.save(existingProduct);
	}

	public void deleteProduct(Long id) {

		repo.deleteById(id);
	}

}
