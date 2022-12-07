package com.digitalbook.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbook.entity.Book;
import com.digitalbook.entity.BookSub;
import com.digitalbook.exception.InvalidRequestException;
import com.digitalbook.exception.RequestNotFounException;
import com.digitalbook.repository.BookRepository;
import com.digitalbook.repository.BookSubRepository;

@Service
public class BookSubService {

	private Logger logger = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private BookSubRepository bookSubRepository;

	@Autowired
	private BookRepository bookRepository;

	public BookSub subscribeBookSub(BookSub bookSub) {

		logger.info(" subscribeBookSub() {} " + bookSub);

		Optional<Book> book = bookRepository.findById(bookSub.getBookId());
		if (book.isEmpty()) {
			throw new RequestNotFounException("Book not found ");
		}

		Optional<BookSub> existbookSub = bookSubRepository.findByBookIdAndReaderId(bookSub.getBookId(),
				bookSub.getReaderId());
		if (existbookSub.isPresent()) {
			throw new InvalidRequestException("Book already subscribed");
		}

		bookSub.setCreatedDate(LocalDate.now());

		BookSub result = bookSubRepository.save(bookSub);

		return result;

	}

	public List<Book> getByReadeId(Integer readerId) {

		List<BookSub> bookSubs = bookSubRepository.findByReaderId(readerId);

		if (bookSubs == null || bookSubs.isEmpty()) {
			throw new RequestNotFounException("Book not found ");
		}
		
		List<Integer> listBookId=bookSubs.stream().map(bookSub ->bookSub.getBookId()).collect(Collectors.toList());

		List<Book> books = bookRepository.findByIdIn(listBookId);

		if (books == null || books.isEmpty()) {
			throw new RequestNotFounException("Book not found ");
		}

		return books;

	}
}
