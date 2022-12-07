package com.digitalbook.service;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.digitalbook.entity.Book;
import com.digitalbook.entity.BookSub;
import com.digitalbook.exception.InvalidRequestException;
import com.digitalbook.exception.RequestNotFounException;
import com.digitalbook.repository.BookRepository;
import com.digitalbook.repository.BookSubRepository;

@SpringBootTest
class BookSubServiceTest {

	@Autowired
	private BookSubService bookSubService;

	@MockBean
	private BookSubRepository bookSubRepositoryMock;

	@MockBean
	private BookRepository bookRepositoryMock;

	@Test
	void subscribeBookSubTest() {

		BookSub bookSub = new BookSub();
		bookSub.setId(1);
		bookSub.setBookId(1);
		bookSub.setReaderId(1);

		Optional<Book> optionalB = Optional.of(new Book());
		when(bookRepositoryMock.findById(bookSub.getBookId())).thenReturn(optionalB);

		Optional<BookSub> optionalBS = Optional.empty();
		when(bookSubRepositoryMock.findByBookIdAndReaderId(bookSub.getBookId(), bookSub.getReaderId()))
				.thenReturn(optionalBS);

		when(bookSubRepositoryMock.save(bookSub)).thenReturn(bookSub);

		Assertions.assertEquals(bookSub, bookSubService.subscribeBookSub(bookSub));

	}

	@Test
	void subscribeBookSubNFETest() {

		BookSub bookSub = new BookSub();
		bookSub.setId(1);
		bookSub.setBookId(1);
		bookSub.setReaderId(1);

		Optional<Book> optionalB = Optional.empty();
		when(bookRepositoryMock.findById(bookSub.getBookId())).thenReturn(optionalB);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookSubService.subscribeBookSub(bookSub);
		});

	}

	@Test
	void subscribeBookSubIRETest() {

		BookSub bookSub = new BookSub();
		bookSub.setId(1);
		bookSub.setBookId(1);
		bookSub.setReaderId(1);

		Optional<Book> optionalB = Optional.of(new Book());
		when(bookRepositoryMock.findById(bookSub.getBookId())).thenReturn(optionalB);

		Optional<BookSub> optionalBS = Optional.of(bookSub);
		when(bookSubRepositoryMock.findByBookIdAndReaderId(bookSub.getBookId(), bookSub.getReaderId()))
				.thenReturn(optionalBS);

		Assertions.assertThrows(InvalidRequestException.class, () -> {
			bookSubService.subscribeBookSub(bookSub);
		});

	}

	@Test
	void getByReadeIdTest() {

		int readerId = 1;

		BookSub bookSub = new BookSub();
		bookSub.setId(1);
		bookSub.setBookId(1);
		bookSub.setReaderId(1);

		List<BookSub> bookSubs = List.of(bookSub);
		List<Integer> listBookId = List.of(1);
		List<Book> books = List.of(new Book());

		when(bookSubRepositoryMock.findByReaderId(readerId)).thenReturn(bookSubs);

		when(bookRepositoryMock.findByIdIn(listBookId)).thenReturn(books);

		Assertions.assertEquals(books, bookSubService.getByReadeId(readerId));

	}

	@Test
	void getByReadeIdNullNFE1Test() {

		int readerId = 1;

		List<BookSub> bookSubs = null;

		when(bookSubRepositoryMock.findByReaderId(readerId)).thenReturn(bookSubs);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookSubService.getByReadeId(readerId);
		});
	}

	@Test
	void getByReadeIdEmptyNFE1Test() {

		int readerId = 1;

		BookSub bookSub = new BookSub();
		bookSub.setId(1);
		bookSub.setBookId(1);
		bookSub.setReaderId(1);

		List<BookSub> bookSubs = List.of();

		when(bookSubRepositoryMock.findByReaderId(readerId)).thenReturn(bookSubs);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookSubService.getByReadeId(readerId);
		});
	}

	@Test
	void getByReadeIdNullNFE2Test() {

		int readerId = 1;

		BookSub bookSub = new BookSub();
		bookSub.setId(1);
		bookSub.setBookId(1);
		bookSub.setReaderId(1);

		List<BookSub> bookSubs = List.of(bookSub);
		List<Integer> listBookId = List.of(1);
		List<Book> books = null;

		when(bookSubRepositoryMock.findByReaderId(readerId)).thenReturn(bookSubs);

		when(bookRepositoryMock.findByIdIn(listBookId)).thenReturn(books);
		
		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookSubService.getByReadeId(readerId);
		});
	}

	@Test
	void getByReadeIdEmptyNFE2Test() {

		int readerId = 1;

		BookSub bookSub = new BookSub();
		bookSub.setId(1);
		bookSub.setBookId(1);
		bookSub.setReaderId(1);

		List<BookSub> bookSubs = List.of(bookSub);
		List<Integer> listBookId = List.of(1);
		List<Book> books = List.of();

		when(bookSubRepositoryMock.findByReaderId(readerId)).thenReturn(bookSubs);

		when(bookRepositoryMock.findByIdIn(listBookId)).thenReturn(books);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookSubService.getByReadeId(readerId);
		});
	}

}
