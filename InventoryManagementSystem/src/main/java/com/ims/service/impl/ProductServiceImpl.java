package com.ims.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ims.dto.ProductDTO;
import com.ims.dto.Response;
import com.ims.dto.SupplierDTO;
import com.ims.entity.Category;
import com.ims.entity.Product;
import com.ims.entity.Supplier;
import com.ims.exceptions.NotFoundExcepption;
import com.ims.repository.CategoryRepository;
import com.ims.repository.ProductRepository;
import com.ims.repository.SupplierRepository;
import com.ims.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ModelMapper modelMapper;
	private final CategoryRepository categoryRepository;

	//private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/product-image";
	private static final String IMAGE_DIRECTORY = "D:/Java Practice/Yeshendra SB/IMS_Web/imw-angular/public/products/";

	@Override
	public Response saveProduct(ProductDTO productDTO, MultipartFile imagesFile) {
		Category category = categoryRepository.findById(productDTO.getCategoryId())
				.orElseThrow(() -> new NotFoundExcepption("Category Not Found For Product"));

		Product productToSave = Product.builder().name(productDTO.getName()).sku(productDTO.getSku())
				.price(productDTO.getPrice()).stockQuantity(productDTO.getStockQuantity())
				.description(productDTO.getDescription()).category(category).build();

		if (imagesFile != null) {
			String savePath = saveImage(imagesFile);
			productToSave.setImageUrl(savePath);

		}

		productRepository.save(productToSave);
		return Response.builder().status(200).message("Product Save Successfully").build();
	}

	@Override
	public Response updateProduct(ProductDTO productDTO, MultipartFile imagesFile) {
		Product existingProduct = productRepository.findById(productDTO.getId())
				.orElseThrow(() -> new NotFoundExcepption("Product Not Found"));

//Check if image is associated with the update request
		if (imagesFile != null && !imagesFile.isEmpty()) {
			String savePath = saveImage(imagesFile);
			existingProduct.setImageUrl(savePath);
		}
//check if category is to be changed for the product
		if (productDTO.getCategoryId() != null && productDTO.getCategoryId() > 0) {
			Category category = categoryRepository.findById(productDTO.getCategoryId())
					.orElseThrow(() -> new NotFoundExcepption("Category Not Found For Product"));

			existingProduct.setCategory(category);
		}
		// check and update field

		if (productDTO.getName() != null && !productDTO.getName().isEmpty()) {
			existingProduct.setName(productDTO.getName());
		}
		if (productDTO.getSku() != null && !productDTO.getSku().isEmpty()) {
			existingProduct.setSku(productDTO.getSku());
		}
		if (productDTO.getDescription() != null && !productDTO.getDescription().isEmpty()) {
			existingProduct.setDescription(productDTO.getDescription());
		}
		if (productDTO.getPrice() != null && productDTO.getPrice().compareTo(BigDecimal.ZERO) >= 0) {
			existingProduct.setPrice(productDTO.getPrice());
		}
		if (productDTO.getStockQuantity() != null && productDTO.getStockQuantity() >= 0) {
			existingProduct.setStockQuantity(productDTO.getStockQuantity());
		}
		productRepository.save(existingProduct);

		return Response.builder().status(200).message("Product Updated Successfully").build();
	}

	@Override
	public Response getAllProduct() {

		List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

		List<ProductDTO> productsDTO = modelMapper.map(products, new TypeToken<List<ProductDTO>>() {
		}.getType());

		return Response.builder().status(200).message("success").products(productsDTO).build();

	}

	@Override
	public Response getProductById(Long id) {
		Product existingProduct = productRepository.findById(id)
				.orElseThrow(() -> new NotFoundExcepption("Product Not Found"));

		ProductDTO productDTO = modelMapper.map(existingProduct, ProductDTO.class);
		return Response.builder().status(200).message("Success").product(productDTO).build();
	}

	@Override
	public Response deleteProduct(Long id) {
		productRepository.deleteById(id);

		return Response.builder().status(200).message("Product Deleted Successfully").build();
	}

	private String saveImage(MultipartFile imageFile) {
		// Validate Image
		if (!imageFile.getContentType().startsWith("image/")) {
			throw new IllegalArgumentException("Only Image Files are Allowed");
		}
		// create the directory to store image if it doesn't exist
		File directory = new File(IMAGE_DIRECTORY);
		if (!directory.exists()) {
			directory.mkdir();
			log.info("Directory was created");
		}
		// generate unique file name for the image
		String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
		// get the absolute path of the image
		String imagePath = IMAGE_DIRECTORY + uniqueFileName;

		try {
			File destinationFile = new File(imagePath);
			// we are transferring to Store image in folder
			imageFile.transferTo(destinationFile);

		} catch (Exception e) {
			throw new IllegalArgumentException("Error Occured While Saving Image " + e.getMessage());
		}
		return imagePath;

	}
}
