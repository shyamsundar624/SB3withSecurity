package com.ims.service;

import org.springframework.web.multipart.MultipartFile;

import com.ims.dto.CategoryDTO;
import com.ims.dto.ProductDTO;
import com.ims.dto.Response;
import com.ims.dto.SupplierDTO;

public interface ProductService {
	
	Response saveProduct(ProductDTO productDTO,MultipartFile imagesFile);

	Response updateProduct( ProductDTO productDTO,MultipartFile imagesFile);
	
	Response getAllProduct();

	Response getProductById(Long id);


	Response deleteProduct(Long id);
}
