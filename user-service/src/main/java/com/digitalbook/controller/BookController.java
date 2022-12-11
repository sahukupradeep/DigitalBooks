package com.digitalbook.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.payload.request.Book;
import com.digitalbook.payload.request.BookSub;
import com.digitalbook.payload.response.MessageResponse;
import com.digitalbook.restapi.service.BookRestApiService;
import com.digitalbook.validator.BookValidator;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/digitalbooks/")
public class BookController {

	private Logger logger = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookRestApiService bookRestApiService;

	@PostMapping("author/{authorId}/books")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<MessageResponse> createBook(@Valid @RequestBody Book book, @PathVariable Integer authorId)
			throws Exception {

		logger.info(" createBook() {}" + book);
		book.setAuthorId(authorId);
		BookValidator.validate(book);
		ResponseEntity<MessageResponse> responseEntity = bookRestApiService.createBook(book);

		return responseEntity;

	}

	@PutMapping("author/{authorId}/books/{bookId}")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<MessageResponse> updateBook(@Valid @RequestBody Book book, @PathVariable Integer authorId,
			@PathVariable Integer bookId) {

		logger.info(" updateBook() {}" + book);
		book.setAuthorId(authorId);
		book.setId(bookId);
		BookValidator.validate(book);
		ResponseEntity<MessageResponse> responseEntity = bookRestApiService.updateBook(book);

		return responseEntity;

	}

	@PostMapping("author/{authorId}/books/{bookId}")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<MessageResponse> blockBook(@PathVariable Integer authorId, @PathVariable Integer bookId,
			@RequestParam String block) {

		logger.info(" blockBook() {}");
		ResponseEntity<MessageResponse> responseEntity = bookRestApiService.blockBook(authorId, bookId, block);

		return responseEntity;

	}

	@GetMapping("author/{authorId}/books")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<List> getBooksByAuthor(@PathVariable Integer authorId) {

		logger.info(" getBooksByAuthor() {}");
		ResponseEntity<List> responseEntity = bookRestApiService.getBooksByAuthor(authorId);

		return responseEntity;

	}
	@GetMapping("author/{authorId}/books/{bookId}")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<Book> getBooksByAuthorAndBookId(@PathVariable Integer authorId,@PathVariable Integer bookId) throws Exception {

		logger.info(" getBooksByAuthorAndBookId() {}");
		ResponseEntity<Book> responseEntity = bookRestApiService.getBooksByAuthorAndBookId(authorId,bookId);

		return responseEntity;

	}

	@GetMapping("search")
	// @PreAuthorize("hasRole('GUEST') or hasRole('AUTHOR') or hasRole('READER')")
	public ResponseEntity<List> searchBooks(@RequestParam String category, @RequestParam String title,
			@RequestParam Integer author, @RequestParam Double price, @RequestParam String publisher) {

		logger.info(" searchBook() {}");
		ResponseEntity<List> responseEntity = bookRestApiService.searchBook(category, title, author, price, publisher);

		return responseEntity;

	}

	@PostMapping("readers/{bookId}/subscribe")
	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<MessageResponse> subscribeBook(@Valid @RequestBody BookSub bookSub,
			@PathVariable Integer bookId) {

		logger.info(" subscribeBook() {}" + bookSub);

		ResponseEntity<MessageResponse> responseEntity = bookRestApiService.subscribeBook(bookSub);

		return responseEntity;

	}

	@GetMapping("readers/{emailId}/books")
//	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<List> getAllReaderBook(@PathVariable String emailId) {

		logger.info(" getAllReaderBook() {}");
		ResponseEntity<List> responseEntity = bookRestApiService.getAllReaderBook(emailId);

		return responseEntity;

	}

	@GetMapping("readers/{emailId}/books/{subId}")
//	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<Book> getReaderBook(@PathVariable String emailId, @PathVariable Integer subId)
			throws Exception {

		logger.info(" getReaderBook() {}");
		ResponseEntity<Book> responseEntity = bookRestApiService.getBookByReaderAndSubId(emailId, subId);

		return responseEntity;

	}

	@GetMapping("readers/{emailId}/books/{subId}/read")
//	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<String> getReaderBookRead(@PathVariable String emailId, @PathVariable Integer subId) {

		logger.info(" getReaderBook() {}");
		ResponseEntity<String> responseEntity = bookRestApiService.getContentByReaderAndSubId(emailId, subId);

		return responseEntity;

	}

	@PostMapping("readers/{emailId}/books/{subId}/cancel-subscription")
//	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<MessageResponse> cancelReaderBook(@PathVariable String emailId, @PathVariable Integer subId) {

		logger.info(" getReaderBook() {}");
		ResponseEntity<MessageResponse> responseEntity = bookRestApiService.cancelByReaderAndSubId(emailId, subId);

		return responseEntity;

	}

}
