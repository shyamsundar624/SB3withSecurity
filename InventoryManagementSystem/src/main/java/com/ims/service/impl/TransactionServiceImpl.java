package com.ims.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import org.modelmapper.TypeToken;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ims.dto.CategoryDTO;
import com.ims.dto.Response;
import com.ims.dto.TransactionDTO;
import com.ims.dto.TransactionRequest;
import com.ims.entity.Product;
import com.ims.entity.Supplier;
import com.ims.entity.Transaction;
import com.ims.entity.User;
import com.ims.enums.TransactionStatus;
import com.ims.enums.TransactionType;
import com.ims.exceptions.NameValueRequiredExcepption;
import com.ims.exceptions.NotFoundExcepption;
import com.ims.repository.ProductRepository;
import com.ims.repository.SupplierRepository;
import com.ims.repository.TransactionRepository;
import com.ims.service.TransactionService;
import com.ims.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
	private final ModelMapper modelMapper;
	private final SupplierRepository supplierRepository;
	private final UserService userService;
	private final ProductRepository productRepository;

	@Override
	public Response restockInventory(TransactionRequest transactionRequest) {

		Long productId = transactionRequest.getProductId();
		Long supplierId = transactionRequest.getSupplierId();
		Integer quantity = transactionRequest.getQuantity();

		if (supplierId == null)
			throw new NameValueRequiredExcepption("Supplier Id Is Required");
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NotFoundExcepption("Product Not Found in Transaction"));

		Supplier supplier = supplierRepository.findById(supplierId)
				.orElseThrow(() -> new NotFoundExcepption("Supplier Not Found Exception in Transaction"));

		User user = userService.getCurrentLoggedInUser();

		// update the stock quantity and resave

		product.setStockQuantity(product.getStockQuantity() + quantity);
		productRepository.save(product);

		// Create a transaction

		Transaction transaction = Transaction.builder()
				.transactionType(TransactionType.PURCHASE)
				.status(TransactionStatus.COMPLETED)
				.user(user)
				
				.supplier(supplier)
				.totalProduct(quantity)
				.totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
				.description(transactionRequest.getDescription())
				.build();
		transactionRepository.save(transaction);

		return Response.builder().status(200).message("Transaction Made Successfully").build();
	}

	@Override
	public Response sell(TransactionRequest transactionRequest) {

		Long productId = transactionRequest.getProductId();
		Integer quantity = transactionRequest.getQuantity();

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NotFoundExcepption("Product Not Found in Transaction"));

		User user = userService.getCurrentLoggedInUser();

		// update the stock quantity and resave

		product.setStockQuantity(product.getStockQuantity() - quantity);
		productRepository.save(product);

		// Create a transaction

		Transaction transaction = Transaction.builder().transactionType(TransactionType.SALE)
				.status(TransactionStatus.COMPLETED).user(user).totalProduct(quantity)
				.totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
				.description(transactionRequest.getDescription()).build();
		transactionRepository.save(transaction);

		return Response.builder().status(200).message("Transaction sold Successfully").build();

	}

	@Override
	public Response returnToSupplier(TransactionRequest transactionRequest) {

		Long productId = transactionRequest.getProductId();
		Long supplierId = transactionRequest.getSupplierId();
		Integer quantity = transactionRequest.getQuantity();

		if (supplierId == null)
			throw new NameValueRequiredExcepption("Supplier Id Is Required");
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NotFoundExcepption("Product Not Found in Transaction"));

		Supplier supplier = supplierRepository.findById(supplierId)
				.orElseThrow(() -> new NotFoundExcepption("Supplier Not Found Exception in Transaction"));

		User user = userService.getCurrentLoggedInUser();

		// update the stock quantity and resave

		product.setStockQuantity(product.getStockQuantity() - quantity);
		productRepository.save(product);

		// Create a transaction

		Transaction transaction = Transaction.builder().transactionType(TransactionType.RETURN_TO_SUPPLIER)
				.status(TransactionStatus.PROCESSING).user(user).supplier(supplier).totalProduct(quantity)
				.totalPrice(BigDecimal.ZERO).description(transactionRequest.getDescription()).build();
		transactionRepository.save(transaction);

		return Response.builder().status(200).message("Transaction Returned Successfully Initialized").build();

	}

	@Override
	public Response getAllTransactions(int page, int size, String searchText) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
		Page<Transaction> transactionPage = transactionRepository.searchTransaction(searchText, pageable);

		List<TransactionDTO> transactionDTOs = modelMapper.map(transactionPage.getContent(),
				new TypeToken<List<TransactionDTO>>() {
				}.getType());

		transactionDTOs.forEach(obj->{
			obj.setUser(null);
			obj.setProduct(null);
			obj.setSupplier(null);
		});
		
		
		return Response.builder()
				.status(200)
				.message("Success")
				.transactions(transactionDTOs)
				.build();
	}

	@Override
	public Response getTransactionById(Long id) {
		
Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new NotFoundExcepption("Transaction Not Found By Id "+id));

TransactionDTO transactionDTO = modelMapper.map(transaction,TransactionDTO.class);

transactionDTO.getUser().setTransactions(null);

		return Response.builder()
				.status(200)
				.message("Success")
				.transaction(transactionDTO)
				.build();
	}

	@Override
	public Response getAllTransactionByMonthAndYear(int month, int year) {

List<Transaction> transactions = transactionRepository.findAllByMonthAndYear(year, month);
		
List<TransactionDTO> transactionDTOs = modelMapper.map(transactions,
				new TypeToken<List<CategoryDTO>>() {
				}.getType());

		transactionDTOs.forEach(obj->{
			obj.setUser(null);
			obj.setProduct(null);
			obj.setSupplier(null);
		});
		
		
		return Response.builder()
				.status(200)
				.message("Success")
				.transactions(transactionDTOs)
				.build();
	
	}

	@Override
	public Response updateTransactionStatus(Long transactionId, TransactionStatus transactionStatus) {
		Transaction existingTransaction = transactionRepository.findById(transactionId).orElseThrow(()-> new NotFoundExcepption("Transaction Not Found By Id "+transactionId));

		existingTransaction.setStatus(transactionStatus);
		existingTransaction.setUpdatedAt(LocalDateTime.now());
		
		transactionRepository.save(existingTransaction);
		
		TransactionDTO transactionDTO = modelMapper.map(existingTransaction,TransactionDTO.class);
		transactionDTO.getUser().setTransactions(null);
		
		return Response.builder()
				.status(200)
				.message("Transation Updated Successfully")
				.transaction(transactionDTO)
				.build();
	}

}
