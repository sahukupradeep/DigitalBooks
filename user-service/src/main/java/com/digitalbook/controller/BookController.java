package com.digitalbook.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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
import com.digitalbook.payload.response.BookResponse;
import com.digitalbook.payload.response.MessageResponse;
import com.digitalbook.payload.response.SearchBookResponse;
import com.digitalbook.restapi.service.BookRestApiService;
import com.digitalbook.validator.BookValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

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
	public ResponseEntity<List<BookResponse>> getBooksByAuthor(@PathVariable Integer authorId) throws JsonMappingException, JsonProcessingException {

		logger.info(" getBooksByAuthor() {}");
		ResponseEntity<List<BookResponse>> responseEntity = bookRestApiService.getBooksByAuthor(authorId);

		return responseEntity;

	}

	@GetMapping("author/{authorId}/books/{bookId}")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<Book> getBooksByAuthorAndBookId(@PathVariable Integer authorId, @PathVariable Integer bookId)
			throws Exception {

		logger.info(" getBooksByAuthorAndBookId() {}");
		ResponseEntity<Book> responseEntity = bookRestApiService.getBooksByAuthorAndBookId(authorId, bookId);

		return responseEntity;

	}

	@GetMapping("search")
	// @PreAuthorize("hasRole('GUEST') or hasRole('AUTHOR') or hasRole('READER')")
	public ResponseEntity<List<BookResponse>> searchBooks(@RequestParam(required = false) String category,
			@RequestParam(required = false) @Nullable String title, @RequestParam(required = false) Integer author,
			@RequestParam(required = false) Double price, @RequestParam(required = false) String publisher)
			throws JsonMappingException, JsonProcessingException {

		logger.info(" searchBook() {} ");
		ResponseEntity<List<BookResponse>> responseEntity = bookRestApiService.searchBook(category, title, author,
				price, publisher);

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
	public ResponseEntity<List<BookResponse>> getAllReaderBook(@PathVariable String emailId) throws JsonMappingException, JsonProcessingException {

		logger.info(" getAllReaderBook() {}");
		ResponseEntity<List<BookResponse>> responseEntity = bookRestApiService.getAllReaderBook(emailId);

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

		logger.info(" getReaderBookRead() {}");
		ResponseEntity<String> responseEntity = bookRestApiService.getContentByReaderAndSubId(emailId, subId);

		// System.out.println(responseEntity.getBody()+" !!!!!!!!!!!!!!!! ");

		return responseEntity;

	}

	@PostMapping("readers/{emailId}/books/{subId}/cancel-subscription")
	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<MessageResponse> cancelReaderBook(@PathVariable String emailId, @PathVariable Integer subId) {

		logger.info(" getReaderBook() {}");
		ResponseEntity<MessageResponse> responseEntity = bookRestApiService.cancelByReaderAndSubId(emailId, subId);

		return responseEntity;

	}

	@GetMapping("get-all/books")
//	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<SearchBookResponse> getAllBook() throws Exception {

		logger.info(" getReaderBook() {}");
		ResponseEntity<SearchBookResponse> responseEntity = bookRestApiService.getAllBook();

		return responseEntity;

	}

}
