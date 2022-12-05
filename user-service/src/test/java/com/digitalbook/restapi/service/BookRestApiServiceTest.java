package com.digitalbook.restapi.service;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.digitalbook.payload.request.Book;
import com.digitalbook.payload.response.MessageResponse;
import com.digitalbook.util.CommonRestApiUrl;
import com.digitalbook.util.ConstantValueUtil;
import com.digitalbook.util.StringUtil;

@SpringBootTest
class BookRestApiServiceTest {

	@Autowired
	BookRestApiService bookRestApiService;

	@MockBean
	private RestTemplate restTemplateMock;

	@MockBean
	private CommonRestApiUrl commonRestApiUrlMock;

	@Test
	void createBookTest() {

		Book book = new Book();
		String url = "http://localhost:8081/api/book/save";

		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.CREATED)
				.body(new MessageResponse("Book registered successfully!"));

		when(commonRestApiUrlMock.getCreateBookUrl()).thenReturn(url);

		when(restTemplateMock.postForEntity(url, book, MessageResponse.class)).thenReturn(response);

		Assertions.assertEquals(response, bookRestApiService.createBook(book));

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

	/*@Test
	void blockBookTest() throws Exception {

		Book book = new Book();
		String block = ConstantValueUtil.BLOCK;
		String url = "http://localhost:8081/api/book/author/1/book/1?bolck" + block;

		ResponseEntity<MessageResponse> response = ResponseEntity.ok(new MessageResponse("Book blocked successfully!"));

		when(commonRestApiUrlMock.getUpdateBookUrl()).thenReturn(url);

		when(StringUtil.replaceAll(" ", "", url)).thenReturn(url);

		when(restTemplateMock.postForEntity(url, book, MessageResponse.class)).thenReturn(response);

		Assertions.assertEquals(response, bookRestApiService.blockBook(1, 1, block));

	}*/

	@Test
	void searchBookTest() throws Exception {

		String url = "http://localhost:8081/api/book/search?category=category&title=title&author=1&price=4&publisher=publisher";

		ResponseEntity<List> response = null;

		when(commonRestApiUrlMock.getSearchBookUrl()).thenReturn(url);

		when(restTemplateMock.getForEntity(url, List.class)).thenReturn(response);

		Assertions.assertEquals(response, bookRestApiService.searchBook("category", "title", 1, 4.0, "publisher"));
	}

}
