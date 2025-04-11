package com.ims.controller;

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

import com.ims.dto.CategoryDTO;
import com.ims.dto.Response;
import com.ims.dto.TransactionRequest;
import com.ims.enums.TransactionStatus;
import com.ims.service.CategoryService;
import com.ims.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

	private final TransactionService transactionService;

	@PostMapping("/purchase")
	public ResponseEntity<Response> purchaseInventory(@RequestBody @Valid TransactionRequest transactionRequest) {
		return new ResponseEntity(transactionService.restockInventory(transactionRequest), HttpStatus.CREATED);
	}

	@PostMapping("/sell")
	public ResponseEntity<Response> sell(@RequestBody @Valid TransactionRequest transactionRequest) {
		return ResponseEntity.ok(transactionService.sell(transactionRequest));
	}
	
	@PostMapping("/return")
	public ResponseEntity<Response> returnToSupplier(@RequestBody @Valid  TransactionRequest transactionRequest) {
		return ResponseEntity.ok(transactionService.returnToSupplier(transactionRequest));
	}
	
	@GetMapping("/all")
	public ResponseEntity<Response> getAllTransaction(
			@RequestParam(name = "page",defaultValue ="0") int page,
			@RequestParam(name = "size",defaultValue = "1000") int size,
			@RequestParam(name = "searchText",required = false) String searchText
			){
		return ResponseEntity.ok(transactionService.getAllTransactions(page,size,searchText));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response> getTransactionById(@PathVariable("id") Long id){
		return ResponseEntity.ok(transactionService.getTransactionById(id));
	}
	
	@GetMapping("/by-month-year")
	public ResponseEntity<Response> getAllTransaction(
			@RequestParam int month,
			@RequestParam int year
			){
		return ResponseEntity.ok(transactionService.getAllTransactionByMonthAndYear(month, year));
	}
	
	@PutMapping("/update/{transactionId}")
	public ResponseEntity<Response> updateTransaction(@PathVariable("transactionId") Long transactionId, @RequestBody TransactionStatus transactionStatus){
		return ResponseEntity.ok(transactionService.updateTransactionStatus(transactionId, transactionStatus));
	}
	
	
}
