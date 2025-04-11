package com.library.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.library.entity.Book;
import com.library.entity.IssueRecord;
import com.library.entity.User;
import com.library.repository.BookRepository;
import com.library.repository.IssueRecordRepository;
import com.library.repository.UserRepository;

@Service
public class IssueRecordService {

	@Autowired
	private IssueRecordRepository issueRecordRepository;
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	public IssueRecord issueTheBook(Long bookId) {

		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new RuntimeException("Book Not Found With Id " + bookId));

		if (book.getQuantity() <= 0 || !book.getIsAvailable()) {
			throw new RuntimeException("Book is not available");
		}
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByusername(username)
				.orElseThrow(() -> new RuntimeException("User Not Found With " + username));

		IssueRecord issueRecord = new IssueRecord();
		issueRecord.setIssueDate(LocalDate.now());
		issueRecord.setDueDate(LocalDate.now().plusDays(14));
		issueRecord.setIsReturn(false);
		issueRecord.setUser(user);
		issueRecord.setBook(book);

		book.setQuantity(book.getQuantity() - 1);
		if (book.getQuantity() == 0) {
			book.setIsAvailable(false);
		}

		bookRepository.save(book);
		issueRecordRepository.save(issueRecord);
		return issueRecord;
	}

	public IssueRecord returnTheBook(Long id) {
		IssueRecord issueRecord = issueRecordRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Issue is not Found With Id " + id));

		if (issueRecord.getIsReturn()) {
			throw new RuntimeException("Book is already Returned");
		}
		Book book = issueRecord.getBook();
		book.setQuantity(book.getQuantity()+1);
		book.setIsAvailable(true);
		
		issueRecord.setReturnDate(LocalDate.now());
		issueRecord.setIsReturn(true);
		
		bookRepository.save(book);
		
		issueRecordRepository.save(issueRecord);
		
		return issueRecord;
	}
}
