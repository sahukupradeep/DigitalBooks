package com.digitalbook.restapi.service;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.digitalbook.entity.User;
import com.digitalbook.exception.RequestNotFounException;
import com.digitalbook.payload.request.Book;
import com.digitalbook.payload.request.BookSub;
import com.digitalbook.payload.response.MessageResponse;
import com.digitalbook.payload.response.SearchBookResponse;
import com.digitalbook.repository.UserRepository;
import com.digitalbook.util.CommonRestApiUrl;
import com.digitalbook.util.CommonStringUtil;
import com.digitalbook.util.ConstantValueUtil;

@SpringBootTest
class BookRestApiServiceTest {

	@Autowired
	BookRestApiService bookRestApiService;

	@MockBean
	private RestTemplate restTemplateMock;

	@MockBean
	private CommonRestApiUrl commonRestApiUrlMock;

	@MockBean
	private CommonStringUtil commonStringUtilMock;

	@MockBean
	private UserRepository userRepositoryMock;

	@MockBean
	private ResponseEntity<?> response;

	@Test
	void createBookTest() throws Exception {

		Book book = new Book();
		book.setAuthorId(1);
		String url = "http://localhost:8081/api/book/save";

		Optional<User> user = Optional.of(new User());

		when(userRepositoryMock.findById(1L)).thenReturn(user);

		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.CREATED)
				.body(new MessageResponse("Book registered successfully!"));

		when(commonRestApiUrlMock.getCreateBookUrl()).thenReturn(url);

		when(restTemplateMock.postForEntity(url, book, MessageResponse.class)).thenReturn(response);

		Assertions.assertEquals(null, bookRestApiService.createBook(book));

	}

	@Test
	void createBookExceptionTest() {

		Book book = new Book();
		book.setAuthorId(1);

		Optional<User> user = Optional.empty();

		when(userRepositoryMock.findById(1L)).thenReturn(user);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookRestApiService.createBook(book);
		});

	}

	@Test
	void updateBookTest() throws Exception {

		Book book = new Book();
		String url = "http://localhost:8081/api/book/update";

		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.CREATED)
				.body(new MessageResponse("Book updated successfully!"));
		when(commonRestApiUrlMock.getUpdateBookUrl()).thenReturn(url);

		restTemplateMock.put(url, book, MessageResponse.class);

		Assertions.assertEquals(response.getStatusCodeValue(),
				bookRestApiService.updateBook(book).getStatusCodeValue());

		when(commonRestApiUrlMock.getBlockBookUrl()).thenReturn(url);

	}

	@Test
	void blockBookTest() throws Exception {

		Book book = new Book();
		String block = ConstantValueUtil.BLOCK;
		String url = "http://localhost:8081/api/book/author/1/book/1?bolck" + block;

		ResponseEntity<MessageResponse> response = null;

		when(commonRestApiUrlMock.getUpdateBookUrl()).thenReturn(url);

		when(commonStringUtilMock.replaceAll(" ", "", url)).thenReturn(url);

		when(restTemplateMock.postForEntity(url, book, MessageResponse.class)).thenReturn(response);

		Assertions.assertEquals(response, bookRestApiService.blockBook(1, 1, block));

	}

	@Test
	void searchBookTest() throws Exception {

		String url = "http://localhost:8081/api/book/search?category=category&title=title&author=1&price=4&publisher=publisher";

		ResponseEntity<List> response = null;

		when(commonRestApiUrlMock.getSearchBookUrl()).thenReturn(url);

		when(restTemplateMock.getForEntity(url, List.class)).thenReturn(response);

		Assertions.assertThrows(NullPointerException.class, () -> {
			bookRestApiService.searchBook("category", "title", 1, 4.0, "publisher");
		});
	}

	@Test
	void searchBookV2Test() throws Exception {

		String url = "http://localhost:8081/api/book/search?category=category&title=title&author=1&price=4&publisher=publisher";

		ResponseEntity<List> response = null;

		when(commonRestApiUrlMock.getSearchBookUrl()).thenReturn(url);

		when(restTemplateMock.getForEntity(url, List.class)).thenReturn(response);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookRestApiService.searchBookV2("category", "title", "author", 4.0, "publisher");
		});
	}

	@Test
	void getBookByReaderAndSubIdTest() throws Exception {

		BookSub bookSub = new BookSub();

		User user = new User();

		Book book = new Book();

		String url = "http://localhost:8081/api/book-sub/get/readerId/subId";
		Optional<User> optional = Optional.of(user);
		when(userRepositoryMock.findByEmail("")).thenReturn(optional);

		when(commonStringUtilMock.replaceAll(" ", "", url)).thenReturn(url);

		ResponseEntity<Book> response = ResponseEntity.status(HttpStatus.OK).body(book);

		when(commonRestApiUrlMock.getGetReaderBookUrl()).thenReturn(url);

		when(restTemplateMock.getForEntity(url, Book.class)).thenReturn(response);

		Assertions.assertThrows(NullPointerException.class, () -> {
			bookRestApiService.getBookByReaderAndSubId("", 1);
		});

	}

	@Test
	void subscribeBookTest() throws Exception {

		BookSub bookSub = new BookSub();

		String url = "http://localhost:8081/api/book-sub/subscribe";

		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.CREATED)
				.body(new MessageResponse("Book subscribed successfully!"));

		when(commonRestApiUrlMock.getSubscribeBookUrl()).thenReturn(url);

		when(restTemplateMock.postForEntity(url, bookSub, MessageResponse.class)).thenReturn(response);

		Assertions.assertEquals(null, bookRestApiService.subscribeBook(bookSub));

	}

	@Test
	void getAllReaderBookTest() throws Exception {

		String url = "http://localhost:8081/api/book-sub/get-all/2";
		String email = "pradeep@gmail.com";
		Optional<User> user = Optional.of(new User());

		ResponseEntity<List> response = null;

		when(userRepositoryMock.findByEmail(email)).thenReturn(user);

		when(commonRestApiUrlMock.getGetAllReaderBookUrl()).thenReturn(url);

		when(commonStringUtilMock.replaceAll(" ", "", url)).thenReturn(url);

		when(restTemplateMock.getForEntity(url, List.class)).thenReturn(response);

		Assertions.assertThrows(NullPointerException.class, () -> {
			bookRestApiService.getAllReaderBook(email);
		});
	}

	@Test
	void getAllReaderBookExceptionTest() throws Exception {

		String email = "pradeep@gmail.com";
		Optional<User> user = Optional.empty();

		when(userRepositoryMock.findByEmail(email)).thenReturn(user);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookRestApiService.getAllReaderBook(email);
		});
	}

	@Test
	void getContentByReaderAndSubIdTest() {
		String email = "pradeep@gmail.com";
		Optional<User> user = Optional.empty();

		when(userRepositoryMock.findByEmail(email)).thenReturn(user);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookRestApiService.getContentByReaderAndSubId(email, 1);
		});
	}

	@Test
	void cancelByReaderAndSubIdTest() {
		String email = "pradeep@gmail.com";
		Optional<User> user = Optional.empty();

		when(userRepositoryMock.findByEmail(email)).thenReturn(user);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookRestApiService.cancelByReaderAndSubId(email, 1);
		});
	}

	@Test
	void getBooksByAuthorTest() {
		String email = "pradeep@gmail.com";
		Optional<User> user = Optional.empty();

		when(userRepositoryMock.findById((long) 1)).thenReturn(user);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookRestApiService.getBooksByAuthor(1);
		});
	}

	@Test
	void getBooksByAuthorAndBookIdTest() {
		String email = "pradeep@gmail.com";
		Optional<User> user = Optional.empty();

		when(userRepositoryMock.findById((long) 1)).thenReturn(user);

		Assertions.assertThrows(RequestNotFounException.class, () -> {
			bookRestApiService.getBooksByAuthorAndBookId(1, 1);
		});
	}

	@Test
	void getAllBookTest() {

		String url = "http://localhost:8081/api/book/get-all/books";

		when(commonRestApiUrlMock.getAllBookUrl()).thenReturn(url);

		when(restTemplateMock.getForEntity(url, SearchBookResponse.class)).thenReturn(null);

		Assertions.assertThrows(NullPointerException.class, () -> {
			bookRestApiService.getAllBook();
		});
	}

}
