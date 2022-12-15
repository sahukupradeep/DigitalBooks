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

import com.digitalbook.entity.BookSub;
import com.digitalbook.payload.response.BookResponse;
import com.digitalbook.payload.response.MessageResponse;
import com.digitalbook.service.BookSubService;

@RestController
@RequestMapping("/api/book-sub/")
public class BookSubController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookSubService bookSubService;

	@PostMapping("subscribe")
	public ResponseEntity<Integer> subscribeBookSub(@Valid @RequestBody BookSub bookSub) {

		logger.info(" subscribeBookSub() {}" + bookSub);
		BookSub result = bookSubService.subscribeBook(bookSub);

		return ResponseEntity.status(HttpStatus.CREATED).body(result.getId());

	}

	@GetMapping("get-all/{readerId}")
	public ResponseEntity<?> getAllByReader(@PathVariable Integer readerId) {

		logger.info(" getAllReader() {}" + readerId);
		List<BookResponse> books = bookSubService.getByReadeId(readerId);

		return ResponseEntity.ok(books);

	}

	@GetMapping("get/{readerId}/{subId}")
	public ResponseEntity<?> getByReaderAndSubId(@PathVariable Integer readerId, @PathVariable Integer subId) {

		logger.info(" getByReaderAndSubId() {}" + readerId);
		BookResponse book = bookSubService.getByReadeIdAndSubId(readerId, subId);

		return ResponseEntity.ok(book);

	}

	@GetMapping("content/{readerId}/{subId}")
	public ResponseEntity<?> contentByReaderAndSubId(@PathVariable Integer readerId, @PathVariable Integer subId) {

		logger.info(" contentByReaderAndSubId() {}" + readerId);
		String content = bookSubService.contentByReaderAndSubId(readerId, subId);

		return ResponseEntity.ok(content);

	}

	@PostMapping("cancel-sub/{readerId}/{subId}")
	public ResponseEntity<?> cancelSubByReaderAndSubId(@PathVariable Integer readerId, @PathVariable Integer subId) {

		logger.info(" readByReaderAndSubId() {}" + readerId);

		bookSubService.cancelSubByReaderAndSubId(readerId, subId);

		return ResponseEntity.ok(new MessageResponse("Cancel subscription successfully!"));

	}

}
