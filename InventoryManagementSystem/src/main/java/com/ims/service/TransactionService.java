package com.ims.service;

import com.ims.dto.CategoryDTO;
import com.ims.dto.Response;
import com.ims.dto.TransactionDTO;
import com.ims.dto.TransactionRequest;
import com.ims.enums.TransactionStatus;

public interface TransactionService {
	
	Response restockInventory(TransactionRequest transactionRequest);

	Response sell(TransactionRequest transactionRequest);
	
	Response returnToSupplier(TransactionRequest transactionRequest);
	
	Response getAllTransactions(int page,int size,String searchText);

	Response getTransactionById(Long id);
	
	Response getAllTransactionByMonthAndYear(int month,int year);

	Response updateTransactionStatus(Long transactionId, TransactionStatus transactionStatus);


}
