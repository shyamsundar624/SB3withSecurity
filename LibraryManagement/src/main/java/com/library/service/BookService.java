package com.library.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.DTO.BookDTO;
import com.library.entity.Book;
import com.library.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public Book getBookById(Long id) {
		// TODO Auto-generated method stub
		return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book Not Found With ID " + id));
	}

	public Book addBook(BookDTO bookDto) {
		Book book = new Book();
		book.setAuthor(bookDto.getAuthor());
		book.setIsbn(bookDto.getIsbn());
		book.setQuantity(bookDto.getQuantity());
		book.setTitle(bookDto.getTitle());
		book.setIsAvailable(bookDto.getIsAvailable());

		return bookRepository.save(book);
	}

	public Book updateBook(Long id, BookDTO bookDTO) {
		Book book = getBookById(id);
		
		book.setAuthor(bookDTO.getAuthor());
		book.setTitle(bookDTO.getTitle());
		book.setQuantity(bookDTO.getQuantity());
		book.setIsAvailable(bookDTO.getIsAvailable());
		book.setIsbn(bookDTO.getIsbn());
		return bookRepository.save(book);
	}

	public void deleteBookById(Long id) {

		bookRepository.deleteById(id);
	}

}
