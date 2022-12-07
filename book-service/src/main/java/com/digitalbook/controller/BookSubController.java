package com.digitalbook.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.entity.Book;
import com.digitalbook.entity.BookSub;
import com.digitalbook.payload.response.MessageResponse;
import com.digitalbook.service.BookSubService;

@RestController
@RequestMapping("/api/book-sub/")
public class BookSubController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookSubService bookSubService;

	@PostMapping("subscribe")
	public ResponseEntity<?> subscribeBookSub(@Valid @RequestBody BookSub bookSub) {

		logger.info(" subscribeBookSub() {}" + bookSub);
		bookSubService.subscribeBookSub(bookSub);

		return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Book subscribed successfully!"));

	}

	@GetMapping("get-all/{readerId}")
	public ResponseEntity<?> getAllByReader(@PathVariable Integer readerId) {

		logger.info(" getAllReader() {}" + readerId);
		List<Book> books = bookSubService.getByReadeId(readerId);

		return ResponseEntity.ok(books);

	}

}
