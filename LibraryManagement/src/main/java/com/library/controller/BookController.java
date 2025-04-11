package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.DTO.BookDTO;
import com.library.entity.Book;
import com.library.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	@Autowired
	private BookService bookService;

	@GetMapping("/getallbooks")
	public ResponseEntity<List<Book>> getAllBooks() {
		return ResponseEntity.ok(bookService.getAllBooks());
	}

	@GetMapping("/getbookbyid/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(bookService.getBookById(id));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addbook")
	public ResponseEntity<Book> addBook(@RequestBody BookDTO book){
		return ResponseEntity.ok(bookService.addBook(book));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updatebook/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody BookDTO book){
		return ResponseEntity.ok(bookService.updateBook(id,book));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deletebook/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id){
		bookService.deleteBookById(id);
		return ResponseEntity.ok().build();
	}
	
}
