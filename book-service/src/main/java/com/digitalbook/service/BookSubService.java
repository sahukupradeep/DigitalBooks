package com.digitalbook.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbook.entity.Book;
import com.digitalbook.entity.BookSub;
import com.digitalbook.exception.InvalidRequestException;
import com.digitalbook.exception.RequestNotFounException;
import com.digitalbook.payload.response.BookResponse;
import com.digitalbook.repository.BookRepository;
import com.digitalbook.repository.BookSubRepository;

@Service
public class BookSubService {

	private Logger logger = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private BookSubRepository bookSubRepository;

	@Autowired
	private BookRepository bookRepository;

	public BookSub subscribeBook(BookSub bookSub) {

		logger.info(" subscribeBook() ");

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
		logger.info("Book Subscribe successfully");

		return result;

	}

	public List<BookResponse> getByReadeId(Integer readerId) {

		logger.info("getByReadeId()");

		List<BookResponse> bookSubs = bookSubRepository.findByReaderId(readerId);

		if (bookSubs == null || bookSubs.isEmpty()) {
			throw new RequestNotFounException("Book not found ");
		}

		return bookSubs;

	}

	public BookResponse getByReadeIdAndSubId(Integer readerId, Integer subId) {
		logger.info("getByReadeIdAndSubId()");

		Optional<BookResponse> bookSub = bookSubRepository.findByIdAndReader(subId, readerId);

		if (bookSub.isEmpty()) {
			throw new RequestNotFounException("Book not found ");
		}

		return bookSub.get();

	}

	public String contentByReaderAndSubId(Integer readerId, Integer subId) {
		logger.info("contentByReaderAndSubId()");

		Optional<BookSub> bookSub = bookSubRepository.findByIdAndReaderId(subId, readerId);
		if (bookSub.isEmpty()) {
			throw new RequestNotFounException("Content not found ");
		}

		Optional<Book> book = bookRepository.findById(bookSub.get().getBookId());
		if (book.isEmpty()) {
			throw new RequestNotFounException("Content not found ");
		}
		String content = book.get().getContent();

		return content;
	}

	public Book cancelSubByReaderAndSubId(Integer readerId, Integer subId) {

		logger.info("cancelSubByReaderAndSubId() ");

		Optional<BookSub> bookSub = bookSubRepository.findByIdAndReaderId(subId, readerId);

		if (bookSub.isEmpty()) {
			throw new RequestNotFounException("Book not found ");
		}

		Optional<Book> book = bookRepository.findById(bookSub.get().getBookId());

		if (book.isEmpty()) {
			throw new RequestNotFounException("Book not found ");
		}

		bookSub.get().setActive(false);
		bookSub.get().setUpdatedDate(LocalDate.now());
		bookSubRepository.save(bookSub.get());

		logger.info("Book cancel subscription successfully!");

		return book.get();
	}

}
