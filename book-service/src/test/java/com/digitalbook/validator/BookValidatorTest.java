package com.digitalbook.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.digitalbook.entity.Book;
import com.digitalbook.exception.InvalidRequestException;

@SpringBootTest
class BookValidatorTest {

	@Test
	void validatePositivePriceTest() {
		Book book = new Book();
		book.setPrice(4.0);

		BookValidator.validate(book);

	}

	@Test
	void validateNagativePriceTest() {

		Book book = new Book();
		book.setPrice(-4.0);
		book.setTitle("title");
		book.setCategory("Category");
		book.setPublisher("Publisher");
		book.setContent("Content");

		Assertions.assertThrows(InvalidRequestException.class, () -> {
			BookValidator.validate(book);
		});
	}

	@Test
	void validateExceptionTest() {

		Book book = new Book();
		book.setPrice(5.0);
		book.setTitle("");
		book.setCategory("");
		book.setPublisher("");
		book.setContent("");

		Assertions.assertThrows(InvalidRequestException.class, () -> {
			BookValidator.validate(book);
		});
	}

}
