package com.digitalbook.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbook.payload.request.Book;
import com.digitalbook.payload.response.MessageResponse;
import com.digitalbook.restapi.service.BookRestApiService;

@Service
public class BookService {

	@Autowired
	private BookRestApiService bookRestApiService;

	/*public MessageResponse createBook(Book book) {
		return bookRestApiService;
		
	}*/

}
