package com.ims.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ims.dto.CategoryDTO;
import com.ims.dto.Response;
import com.ims.dto.SupplierDTO;
import com.ims.dto.UserDTO;
import com.ims.entity.Category;
import com.ims.entity.Supplier;
import com.ims.entity.User;
import com.ims.exceptions.NotFoundExcepption;
import com.ims.repository.CategoryRepository;
import com.ims.repository.SupplierRepository;
import com.ims.service.CategoryService;
import com.ims.service.SupplierService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierServiceImpl implements SupplierService{

	private final SupplierRepository supplierRepository;
	private final ModelMapper modelMapper;

	@Override
	public Response creaetSupplier(SupplierDTO supplierDTO) {
		Supplier supplierTOSave = modelMapper.map(supplierDTO, Supplier.class);

		supplierRepository.save(supplierTOSave);
		return Response.builder().status(200).message("Supplier Created Successfully").build();
	}

	@Override
	public Response getAllSupplier() {

		List<Supplier> categories = supplierRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

		List<SupplierDTO> supplierDTO = modelMapper.map(categories, new TypeToken<List<SupplierDTO>>() {
		}.getType());

		return Response.builder().status(200).message("success").suppliers(supplierDTO).build();

	}

	@Override
	public Response getSupplierById(Long id) {
		Supplier category = supplierRepository.findById(id)
				.orElseThrow(() -> new NotFoundExcepption("Supplier Not Found"));

		SupplierDTO supplierDTO = modelMapper.map(category, SupplierDTO.class);

		return Response.builder().status(200).message("Success").supplier(supplierDTO).build();
	}

	@Override
	public Response updateSupplier(Long id, SupplierDTO supplierDTO) {
		Supplier existingSupplier = supplierRepository.findById(id)
				.orElseThrow(() -> new NotFoundExcepption("Supplier Not Found"));

		existingSupplier.setName(supplierDTO.getName());
		existingSupplier.setAddress(supplierDTO.getAddress());
		supplierRepository.save(existingSupplier);

		return Response.builder().status(200).message("Success").supplier(supplierDTO).build();
	}

	@Override
	public Response deleteSupplier(Long id) {
		supplierRepository.deleteById(id);

		return Response.builder()
				.status(200)
				.message("Supplier Deleted Successfully")
				.build();
	}

}
