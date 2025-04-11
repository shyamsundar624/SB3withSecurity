package com.ims.service;

import com.ims.dto.CategoryDTO;
import com.ims.dto.Response;

public interface CategoryService {
	
	Response creaetCategory(CategoryDTO categoryDTO);

	Response getAllCategories();

	Response getCategoryById(Long id);

	Response updateCategory(Long id, CategoryDTO categoryDTO);

	Response deleteCategory(Long id);
}
