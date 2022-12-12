package com.digitalbook.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

	public List<BookResponse> getByReadeId(Integer readerId) {

		logger.info("getByReadeId()");

		List<BookResponse> bookSubs = bookSubRepository.findByReaderId(readerId);

		/*Map<Integer, List<BookSub>> mapBookSub = bookSubs.stream()
				.collect(Collectors.groupingBy(BookSub::getBookId, Collectors.toList()));

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

		List<BookResponse> bookResponses = new ArrayList<>();
		
		books.forEach(book->{
			//BookResponse bookResponse=new BookResponse();
			//BeanUtils.copyProperties(book,bookResponse);
			List<BookSub> bookSubSet =mapBookSub.get(book.getId());
			if(bookSubSet!=null && bookSubSet.size()>0) {
				//bookResponse.setSubId(bookSubSet.get(0).getId());
				//bookResponse.setActive(null)
			}
			//bookResponses.add(bookResponse);
		});
		*/
		return bookSubs;

	}

	public BookResponse getByReadeIdAndSubId(Integer readerId, Integer subId) {
		logger.info("getByReadeIdAndSubId()");

		Optional<BookResponse> bookSub = bookSubRepository.findByIdAndReader(subId, readerId);

		if (bookSub.isEmpty()) {
			logger.error("Book not found");
			throw new RequestNotFounException("Book not found ");
		}

		/*Optional<Book> book = bookRepository.findById(bookSub.get().getBookId());

		if (book.isEmpty()) {
			logger.error("Book not found");
			throw new RequestNotFounException("Book not found ");
		}*/

		return bookSub.get();

	}

	public String contentByReaderAndSubId(Integer readerId, Integer subId) {
		logger.info("contentByReaderAndSubId()");
		
		Optional<BookSub> bookSub = bookSubRepository.findByIdAndReaderId(subId, readerId);

		Optional<Book> book = bookRepository.findById(bookSub.get().getBookId());
		String content = book.get().getContent();

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
