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
import com.digitalbook.exception.InvalidRequestException;
import com.digitalbook.exception.RequestNotFounException;
import com.digitalbook.repository.BookRepository;
import com.digitalbook.util.ConstantValueUtil;

@SpringBootTest
class BookServiceTest {

	@MockBean
	BookRepository bookRepositoryMock;

	@Autowired
	BookService bookService;

	@Test
	void createBookTest() {
		Book book = new Book();
		book.setId(1);
		book.setAuthorId(1);
		Optional<Book> optional = Optional.empty();
		when(bookRepositoryMock.findByTitleAndAuthorId("title", 1)).thenReturn(optional);

		when(bookRepositoryMock.save(book)).thenReturn(book);

		Assertions.assertEquals(book, bookService.createBook(book));

	}

	@Test
	void createBookExceptionTest() {
		Book book = new Book();
		book.setId(1);
		book.setTitle("title");
		book.setAuthorId(1);
		Optional<Book> optional = Optional.of(book);
		when(bookRepositoryMock.findByTitleAndAuthorId("title", 1)).thenReturn(optional);

		Assertions.assertThrows(InvalidRequestException.class, () -> {
			bookService.createBook(book);
		});

	}

	@Test
	void updateBookTest() {
		Book book = new Book();
		book.setId(1);
		book.setTitle("title");
		book.setAuthorId(1);
		Optional<Book> optional = Optional.of(book);
		when(bookRepositoryMock.findByTitleAndAuthorId("title", 1)).thenReturn(optional);

		when(bookRepositoryMock.save(book)).thenReturn(book);

		Assertions.assertEquals(book, bookService.updateBook(book));

	}

	@Test
	void updateBookExceptionTest() {
		Book book = new Book();
		book.setId(1);
		book.setAuthorId(1);
		Optional<Book> optional = Optional.empty();
		when(bookRepositoryMock.findByTitleAndAuthorId("title", 1)).thenReturn(optional);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookService.updateBook(book);
		});

	}

	@Test
	void blockBookYesTest() {
		String block = ConstantValueUtil.BLOCK;
		Book book = new Book();
		book.setId(1);
		book.setId(1);
		book.setAuthorId(1);
		Optional<Book> optional = Optional.of(book);
		when(bookRepositoryMock.findByIdAndAuthorId(1, 1)).thenReturn(optional);

		when(bookRepositoryMock.save(book)).thenReturn(book);

		Assertions.assertEquals(book, bookService.blockBook(1, 1, block));

	}

	@Test
	void blockBookNoTest() {
		String block = ConstantValueUtil.UN_BLOCK;
		Book book = new Book();
		book.setId(1);
		book.setId(1);
		book.setAuthorId(1);
		Optional<Book> optional = Optional.of(book);
		when(bookRepositoryMock.findByIdAndAuthorId(1, 1)).thenReturn(optional);

		when(bookRepositoryMock.save(book)).thenReturn(book);

		Assertions.assertEquals(book, bookService.blockBook(1, 1, block));

	}

	@Test
	void blockBookIRETest() {
		String block = "default";
		Book book = new Book();
		book.setId(1);
		book.setId(1);
		book.setAuthorId(1);
		Optional<Book> optional = Optional.of(book);
		when(bookRepositoryMock.findByIdAndAuthorId(1, 1)).thenReturn(optional);

		when(bookRepositoryMock.save(book)).thenReturn(book);

		Assertions.assertThrows(InvalidRequestException.class, () -> {
			bookService.blockBook(1, 1, block);
		});

	}

	@Test
	void blockBookRNFETest() {
		String block = ConstantValueUtil.UN_BLOCK;
		Book book = new Book();
		book.setId(1);
		book.setAuthorId(1);
		Optional<Book> optional = Optional.empty();
		when(bookRepositoryMock.findByIdAndAuthorId(1, 1)).thenReturn(optional);

		when(bookRepositoryMock.save(book)).thenReturn(book);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookService.blockBook(1, 1, block);
		});

	}

	@Test
	void getByRequestTest() {
		Book book = new Book();
		book.setId(1);
		book.setAuthorId(1);
		book.setCategory("Category");
		book.setTitle("Title");
		book.setPrice(4.0);
		book.setPublisher("Publisher");

		List<Book> books = List.of(book);
		when(bookRepositoryMock.findByCategoryAndTitleAndAuthorIdAndPriceAndPublisher("Category", "Title", 1, 4.0,
				"Publisher")).thenReturn(books);

		Assertions.assertEquals(books, bookService.getByRequest("Category", "Title", 1, 4.0, "Publisher"));
	}

	@Test
	void getByRequestNullTest() {

		when(bookRepositoryMock.findByCategoryAndTitleAndAuthorIdAndPriceAndPublisher("Category", "Title", 1, 4.0,
				"Publisher")).thenReturn(null);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookService.getByRequest("Category", "Title", 1, 4.0, "Publisher");
		});
	}

	@Test
	void getByRequestEmptyTest() {

		List<Book> books = List.of();
		when(bookRepositoryMock.findByCategoryAndTitleAndAuthorIdAndPriceAndPublisher("Category", "Title", 1, 4.0,
				"Publisher")).thenReturn(books);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookService.getByRequest("Category", "Title", 1, 4.0, "Publisher");
		});
	}

}
