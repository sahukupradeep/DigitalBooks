package com.digitalbook.validator;

import com.digitalbook.entity.Book;
import com.digitalbook.exception.InvalidRequestException;

public class BookValidator {

	public static void validate(Book book) {

		if (book.getPrice() < 0) {
			throw new InvalidRequestException("Error : Price should not negative : " + book.getPrice());
		}
	}

}
