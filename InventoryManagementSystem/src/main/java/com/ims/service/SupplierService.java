package com.ims.service;

import com.ims.dto.CategoryDTO;
import com.ims.dto.Response;
import com.ims.dto.SupplierDTO;

public interface SupplierService {
	
	Response creaetSupplier(SupplierDTO supplierDTO);

	Response getAllSupplier();

	Response getSupplierById(Long id);

	Response updateSupplier(Long id, SupplierDTO supplierDTO);

	Response deleteSupplier(Long id);
}
