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

		Assertions.assertThrows(InvalidRequestException.class, () -> {
			BookValidator.validate(book);
		});
	}

}
