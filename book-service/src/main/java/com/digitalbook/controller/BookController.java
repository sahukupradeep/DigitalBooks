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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.entity.Book;
import com.digitalbook.payload.response.MessageResponse;
import com.digitalbook.service.BookService;
import com.digitalbook.util.ConstantValueUtil;
import com.digitalbook.validator.BookValidator;

@RestController
@RequestMapping("/api/book/")
public class BookController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookService bookService;

	@PostMapping("save")
	public ResponseEntity<?> createBook(@Valid @RequestBody Book book) {

		logger.info(" createBook() {}" + book);
		BookValidator.validate(book);
		bookService.createBook(book);

		return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Book registered successfully!"));

	}

	@PutMapping("update")
	public ResponseEntity<?> updateBook(@Valid @RequestBody Book book) {

		logger.info(" updateBook() {}" + book);
		BookValidator.validate(book);
		bookService.updateBook(book);

		return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Book updated successfully!"));

	}

	@PostMapping("/author/{authorId}/book/{bookId}")
	public ResponseEntity<?> blockBook(@PathVariable Integer authorId, @PathVariable Integer bookId,
			@RequestParam String block) {

		logger.info(" blockBook() Author " + authorId + " Book " + bookId);
		bookService.blockBook(authorId, bookId, block);

		return ResponseEntity.ok(new MessageResponse(
				"Book " + (ConstantValueUtil.BLOCK.equals(block) ? "blocked" : "unblocked") + " successfully!"));

	}

	@GetMapping("/author/{authorId}/book/{bookId}")
	public ResponseEntity<Book> getBook(@PathVariable Integer authorId, @PathVariable Integer bookId) {

		logger.info(" getBook() Author " + authorId + " Book " + bookId);
		Book book = bookService.getBook(authorId, bookId);

		return ResponseEntity.ok(book);

	}

	@GetMapping("search")
	public ResponseEntity<?> getByRequest(@RequestParam String category, @RequestParam String title,
			@RequestParam Integer author, @RequestParam Double price, @RequestParam String publisher) {

		logger.info(" getByRequest() category " + category + " title " + title + " price " + price + " publisher "
				+ publisher);

		List<Book> books = bookService.getByRequest(category, title, author, price, publisher);

		return ResponseEntity.ok(books);

	}

	@GetMapping("get-all/{authorId}")
	public ResponseEntity<?> getBooksByAuthor(@PathVariable Integer authorId) {

		logger.info(" getBooksByAuthor() ");

		List<Book> books = bookService.getBooksByAuthor(authorId);

		return ResponseEntity.ok(books);

	}

}
