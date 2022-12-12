package com.digitalbook.service;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.digitalbook.payload.response.BookResponse;
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

		List<BookResponse> books = List.of(this.newBookResponse());
		when(bookRepositoryMock.findByCategoryAndTitleAndAuthorIdAndPriceAndPublisherActive("Category", "Title", 1, 4.0,
				"Publisher")).thenReturn(books);

		Assertions.assertEquals(books, bookService.getByRequest("Category", "Title", 1, 4.0, "Publisher"));
	}

	@Test
	void getByRequestNullTest() {

		when(bookRepositoryMock.findByCategoryAndTitleAndAuthorIdAndPriceAndPublisherActive("Category", "Title", 1, 4.0,
				"Publisher")).thenReturn(null);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookService.getByRequest("Category", "Title", 1, 4.0, "Publisher");
		});
	}

	@Test
	void getByRequestEmptyTest() {

		List<BookResponse> books = List.of();
		when(bookRepositoryMock.findByCategoryAndTitleAndAuthorIdAndPriceAndPublisherActive("Category", "Title", 1, 4.0,
				"Publisher")).thenReturn(books);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookService.getByRequest("Category", "Title", 1, 4.0, "Publisher");
		});
	}

	public BookResponse newBookResponse() {
		return new BookResponse() {

			@Override
			public String getLogo() {
				return null;
			}

			@Override
			public String getTitle() {
				return null;
			}

			@Override
			public Integer getAuthorId() {
				return null;
			}

			@Override
			public String getPublisher() {
				return null;
			}

			@Override
			public Double getPrice() {
				return null;
			}

			@Override
			public LocalDateTime getCreatedDate() {
				return null;
			}

			@Override
			public String getCategory() {
				return null;
			}

			@Override
			public Integer getSubId() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public LocalDate getSubDate() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
