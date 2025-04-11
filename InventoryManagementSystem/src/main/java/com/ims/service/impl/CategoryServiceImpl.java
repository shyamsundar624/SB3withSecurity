package com.ims.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ims.dto.CategoryDTO;
import com.ims.dto.Response;
import com.ims.dto.UserDTO;
import com.ims.entity.Category;
import com.ims.entity.User;
import com.ims.exceptions.NotFoundExcepption;
import com.ims.repository.CategoryRepository;
import com.ims.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final ModelMapper modelMapper;

	@Override
	public Response creaetCategory(CategoryDTO categoryDTO) {
		Category categoryTOSave = modelMapper.map(categoryDTO, Category.class);

		categoryRepository.save(categoryTOSave);
		return Response.builder().status(200).message("Category Created Successfully").build();
	}

	@Override
	public Response getAllCategories() {

		List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

		List<CategoryDTO> categoryDTO = modelMapper.map(categories, new TypeToken<List<CategoryDTO>>() {
		}.getType());

		return Response.builder().status(200).message("success").categories(categoryDTO).build();

	}

	@Override
	public Response getCategoryById(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundExcepption("Category Not Found"));

		CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);

		return Response.builder().status(200).message("Success").category(categoryDTO).build();
	}

	@Override
	public Response updateCategory(Long id, CategoryDTO categoryDTO) {
		Category existingCategory = categoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundExcepption("Category Not Found"));

		existingCategory.setName(categoryDTO.getName());
		categoryRepository.save(existingCategory);

		return Response.builder().status(200).message("Success").category(categoryDTO).build();
	}

	@Override
	public Response deleteCategory(Long id) {
		categoryRepository.deleteById(id);

		return Response.builder()
				.status(200)
				.message("Category Deleted Successfully")
				.build();
	}

}
