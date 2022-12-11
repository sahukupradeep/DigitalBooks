package com.digitalbook.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbook.entity.Book;
import com.digitalbook.exception.InvalidRequestException;
import com.digitalbook.exception.RequestNotFounException;
import com.digitalbook.repository.BookRepository;
import com.digitalbook.util.ConstantValueUtil;

@Service
public class BookService {

	private Logger logger = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private BookRepository bookRepository;

	public Book createBook(Book book) {

		logger.info("createBook() {}" + book);

		Optional<Book> existBook = bookRepository.findByTitleAndAuthorId(book.getTitle(), book.getAuthorId());
		if (existBook.isPresent()) {
			throw new InvalidRequestException("Error : Already exist the Book : " + book.getTitle());
		}

		book.setCreatedDate(LocalDateTime.now());
		Book result = bookRepository.save(book);

		logger.info("Book Created successfully {}" + result);

		return result;
	}

	public Book updateBook(Book book) {
		logger.info("updateBook() {}" + book);
		Optional<Book> existBook = bookRepository.findByTitleAndAuthorId(book.getTitle(), book.getAuthorId());

		if (existBook.isEmpty()) {
			throw new RequestNotFounException("Error :Requested Book not found : " + book.getTitle());
		}

		book.setId(existBook.get().getId());
		book.setCreatedDate(existBook.get().getCreatedDate());
		book.setUpdatedDate(LocalDateTime.now());
		Book result = bookRepository.save(book);

		logger.info("Book Updated successfully {}" + result);

		return result;

	}

	public Book blockBook(Integer authorId, Integer bookId, String block) {
		logger.info(" blockBook " + block);
		Optional<Book> existBook = bookRepository.findByIdAndAuthorId(bookId, authorId);
		if (existBook.isEmpty()) {
			throw new RequestNotFounException("Error :Requested Book not found : " + bookId);
		}
		Book book = existBook.get();
		book.setUpdatedDate(LocalDateTime.now());
		switch (block) {
		case ConstantValueUtil.BLOCK:
			book.setActive(false);
			break;
		case ConstantValueUtil.UN_BLOCK:
			book.setActive(true);
			break;
		default:
			throw new InvalidRequestException("Error : Invalid requested block " + block);

		}
		Book result = bookRepository.save(book);

		logger.info("Book Updated successfully {}" + result);

		return result;
	}

	public List<Book> getByRequest(String category, String title, Integer author, Double price, String publisher) {

		List<Book> books = bookRepository.findByCategoryAndTitleAndAuthorIdAndPriceAndPublisher(category, title, author,
				price, publisher);

		if (books == null || books.isEmpty()) {
			throw new RequestNotFounException("Error : Book not found");
		}
		return books;
	}

	public List<Book> getBooksByAuthor(Integer authorId) {
		List<Book> books = bookRepository.findByAuthorId(authorId);

		if (books == null || books.isEmpty()) {
			throw new RequestNotFounException("Error : Book not found");
		}
		return books;
	}

	public Book getBook(Integer authorId, Integer bookId) {
		logger.info(" getBook ");
		Optional<Book> book = bookRepository.findByIdAndAuthorId(bookId, authorId);
		if (book.isEmpty()) {
			throw new RequestNotFounException("Error :Requested Book not found : " + bookId);
		}

		return book.get();
	}

}
