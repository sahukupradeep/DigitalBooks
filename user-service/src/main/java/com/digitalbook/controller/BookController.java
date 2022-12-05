package com.digitalbook.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.payload.request.Book;
import com.digitalbook.payload.response.MessageResponse;
import com.digitalbook.restapi.service.BookRestApiService;
import com.digitalbook.validator.BookValidator;

@RestController
@RequestMapping("/api/v1/digitalbooks/")
public class BookController {

	private Logger logger = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookRestApiService bookRestApiService;

	@PostMapping("author/{authorId}/books")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<MessageResponse> createBook(@Valid @RequestBody Book book, @PathVariable Integer authorId) {

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

	@GetMapping("search")
	@PreAuthorize("hasRole('GUEST') or hasRole('AUTHOR') or hasRole('READER')")
	public ResponseEntity<List> searchBooks(@RequestParam String category, @RequestParam String title,
			@RequestParam Integer author, @RequestParam Double price, @RequestParam String publisher) {

		logger.info(" searchBook() {}");
		ResponseEntity<List> responseEntity = bookRestApiService.searchBook(category, title, author, price, publisher);

		return responseEntity;

	}

}
