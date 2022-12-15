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
import com.digitalbook.entity.BookSub;
import com.digitalbook.exception.InvalidRequestException;
import com.digitalbook.exception.RequestNotFounException;
import com.digitalbook.payload.response.BookResponse;
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

		Assertions.assertEquals(bookSub, bookSubService.subscribeBook(bookSub));

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
			bookSubService.subscribeBook(bookSub);
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
			bookSubService.subscribeBook(bookSub);
		});

	}

	@Test
	void getByReadeIdTest() {

		int readerId = 1;

		BookSub bookSub = new BookSub();
		bookSub.setId(1);
		bookSub.setBookId(1);
		bookSub.setReaderId(1);

		List<Integer> listBookId = List.of(1);
		List<Book> books = List.of(new Book());
		List<BookResponse> bookResponses = List.of(this.newBookResponse());

		when(bookSubRepositoryMock.findByReaderId(readerId)).thenReturn(bookResponses);

		when(bookRepositoryMock.findByIdIn(listBookId)).thenReturn(books);

		Assertions.assertEquals(bookResponses, bookSubService.getByReadeId(readerId));

	}

	@Test
	void getByReadeIdNullNFE1Test() {

		int readerId = 1;

		List<BookResponse> bookSubs = null;

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

		List<BookResponse> bookSubs = List.of();

		when(bookSubRepositoryMock.findByReaderId(readerId)).thenReturn(bookSubs);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookSubService.getByReadeId(readerId);
		});
	}

	@Test
	void getByReadeIdAndSubIdTest() {

		BookSub bookSub = new BookSub();
		bookSub.setId(1);
		bookSub.setBookId(1);
		bookSub.setReaderId(1);

		BookResponse bookResponse = this.newBookResponse();
		Optional<BookResponse> optional = Optional.of(bookResponse);

		when(bookSubRepositoryMock.findByIdAndReader(bookSub.getId(), bookSub.getReaderId())).thenReturn(optional);

		Assertions.assertEquals(bookResponse,
				bookSubService.getByReadeIdAndSubId(bookSub.getId(), bookSub.getReaderId()));
	}

	@Test
	void getByReadeIdAndSubIdExceptionTest() {

		BookSub bookSub = new BookSub();
		bookSub.setId(1);
		bookSub.setBookId(1);
		bookSub.setReaderId(1);

		Optional<BookResponse> optional = Optional.empty();

		when(bookSubRepositoryMock.findByIdAndReader(bookSub.getId(), bookSub.getReaderId())).thenReturn(optional);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookSubService.getByReadeIdAndSubId(bookSub.getId(), bookSub.getReaderId());
		});
	}

	@Test
	void contentByReaderAndSubIdTest() {

		BookSub bookSub = new BookSub();
		bookSub.setId(1);
		bookSub.setBookId(1);
		bookSub.setReaderId(1);

		Optional<BookSub> optional = Optional.of(bookSub);

		when(bookSubRepositoryMock.findByIdAndReaderId(bookSub.getId(), bookSub.getReaderId())).thenReturn(optional);

		Book book = new Book();
		book.setContent("content");
		Optional<Book> optional1 = Optional.of(book);
		when(bookRepositoryMock.findById(bookSub.getBookId())).thenReturn(optional1);
		Assertions.assertEquals(book.getContent(),
				bookSubService.contentByReaderAndSubId(bookSub.getId(), bookSub.getReaderId()));
	}

	@Test
	void contentByReaderAndSubIdException1Test() {

		BookSub bookSub = new BookSub();
		bookSub.setId(1);
		bookSub.setBookId(1);
		bookSub.setReaderId(1);

		Optional<BookSub> optional = Optional.of(bookSub);

		when(bookSubRepositoryMock.findByIdAndReaderId(bookSub.getId(), bookSub.getReaderId())).thenReturn(optional);

		Optional<Book> optional1 = Optional.empty();
		when(bookRepositoryMock.findById(bookSub.getBookId())).thenReturn(optional1);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookSubService.contentByReaderAndSubId(bookSub.getId(), bookSub.getReaderId());
		});
	}

	@Test
	void contentByReaderAndSubIdException2Test() {

		BookSub bookSub = new BookSub();
		bookSub.setId(1);
		bookSub.setBookId(1);
		bookSub.setReaderId(1);

		Optional<BookSub> optional = Optional.empty();

		when(bookSubRepositoryMock.findByIdAndReaderId(bookSub.getId(), bookSub.getReaderId())).thenReturn(optional);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookSubService.contentByReaderAndSubId(bookSub.getId(), bookSub.getReaderId());
		});
	}
	
	@Test
	void cancelSubByReaderAndSubIdTest() {

		BookSub bookSub = new BookSub();
		bookSub.setId(1);
		bookSub.setBookId(1);
		bookSub.setReaderId(1);

		Optional<BookSub> optional = Optional.of(bookSub);

		when(bookSubRepositoryMock.findByIdAndReaderId(bookSub.getId(), bookSub.getReaderId())).thenReturn(optional);

		Book book = new Book();
		book.setContent("content");
		Optional<Book> optional1 = Optional.of(book);
		when(bookRepositoryMock.findById(bookSub.getBookId())).thenReturn(optional1);
		
		when(bookSubRepositoryMock.save(bookSub)).thenReturn(bookSub);
		Assertions.assertEquals(book,
				bookSubService.cancelSubByReaderAndSubId(bookSub.getId(), bookSub.getReaderId()));
	}

	@Test
	void cancelSubByReaderAndSubIdException1Test() {

		BookSub bookSub = new BookSub();
		bookSub.setId(1);
		bookSub.setBookId(1);
		bookSub.setReaderId(1);

		Optional<BookSub> optional = Optional.of(bookSub);

		when(bookSubRepositoryMock.findByIdAndReaderId(bookSub.getId(), bookSub.getReaderId())).thenReturn(optional);

		Optional<Book> optional1 = Optional.empty();
		when(bookRepositoryMock.findById(bookSub.getBookId())).thenReturn(optional1);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookSubService.cancelSubByReaderAndSubId(bookSub.getId(), bookSub.getReaderId());
		});
	}

	@Test
	void cancelSubByReaderAndSubIdException2Test() {

		BookSub bookSub = new BookSub();
		bookSub.setId(1);
		bookSub.setBookId(1);
		bookSub.setReaderId(1);

		Optional<BookSub> optional = Optional.empty();

		when(bookSubRepositoryMock.findByIdAndReaderId(bookSub.getId(), bookSub.getReaderId())).thenReturn(optional);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookSubService.cancelSubByReaderAndSubId(bookSub.getId(), bookSub.getReaderId());
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

			@Override
			public Integer getBookId() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
