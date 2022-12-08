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

		logger.info(" subscribeBookSub() ");

		Optional<Book> book = bookRepository.findById(bookSub.getBookId());
		if (book.isEmpty()) {
			logger.error("Book not found ");
			throw new RequestNotFounException("Book not found ");
		}

		Optional<BookSub> existbookSub = bookSubRepository.findByBookIdAndReaderId(bookSub.getBookId(),
				bookSub.getReaderId());
		if (existbookSub.isPresent()) {
			logger.error("Book already subscribed");
			throw new InvalidRequestException("Book already subscribed");
		}

		bookSub.setCreatedDate(LocalDate.now());

		BookSub result = bookSubRepository.save(bookSub);
		logger.info("Book Subscribe successfully");

		return result;

	}

	public List<Book> getByReadeId(Integer readerId) {

		logger.info("getByReadeId()");

		List<BookSub> bookSubs = bookSubRepository.findByReaderId(readerId);

		if (bookSubs == null || bookSubs.isEmpty()) {
			logger.error("Book not found");
			throw new RequestNotFounException("Book not found ");
		}

		List<Integer> listBookId = bookSubs.stream().map(bookSub -> bookSub.getBookId()).collect(Collectors.toList());

		List<Book> books = bookRepository.findByIdIn(listBookId);

		if (books == null || books.isEmpty()) {
			logger.error("Book not found");
			throw new RequestNotFounException("Book not found ");
		}

		return books;

	}

	public Book getByReadeIdAndSubId(Integer readerId, Integer subId) {
		logger.info("getByReadeIdAndSubId()");

		Optional<BookSub> bookSub = bookSubRepository.findByIdAndReaderId(subId, readerId);

		if (bookSub.isEmpty()) {
			logger.error("Book not found");
			throw new RequestNotFounException("Book not found ");
		}

		Optional<Book> book = bookRepository.findById(bookSub.get().getBookId());

		if (book.isEmpty()) {
			logger.error("Book not found");
			throw new RequestNotFounException("Book not found ");
		}

		return book.get();

	}

	public String contentByReaderAndSubId(Integer readerId, Integer subId) {
		logger.info("contentByReaderAndSubId()");

		Book book = this.getByReadeIdAndSubId(readerId, subId);
		String content = book.getContent();

		return content;
	}

	public Book cancelSubByReaderAndSubId(Integer readerId, Integer subId) {

		logger.info("cancelSubByReaderAndSubId() ");

		Optional<BookSub> bookSub = bookSubRepository.findByIdAndReaderId(subId, readerId);

		if (bookSub.isEmpty()) {
			logger.error("Book not found");
			throw new RequestNotFounException("Book not found ");
		}

		Optional<Book> book = bookRepository.findById(bookSub.get().getBookId());

		if (book.isEmpty()) {
			logger.error("Book not found");
			throw new RequestNotFounException("Book not found ");
		}

		bookSub.get().setActive(false);
		bookSub.get().setUpdatedDate(LocalDate.now());
		bookSubRepository.save(bookSub.get());

		logger.info("Book cancel subscription successfully!");

		return book.get();
	}

}
