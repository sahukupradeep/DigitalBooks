package com.digitalbook.validator;

import com.digitalbook.exception.InvalidRequestException;
import com.digitalbook.payload.request.Book;

public class BookValidator {

	public static void validate(Book book) {

		if (book.getPrice() < 0) {
			throw new InvalidRequestException("Error : Price should not negative : " + book.getPrice());
		}

		if (book.getTitle().trim().equals("") || book.getCategory().trim().equals("")
				|| book.getContent().trim().equals("") || book.getPublisher().trim().equals("")) {
			throw new InvalidRequestException("Error : should not allow only space charcter ");
		}
	}

}
