package com.digitalbook.validator;

import com.digitalbook.payload.request.Book;

public class BookValidator {

	public static void validate(Book book) {

		if (book.getPrice() < 0) {
//			throw new InvalidRequestException("Error : Price should not negative : " + book.getPrice());
		}
	}

}
