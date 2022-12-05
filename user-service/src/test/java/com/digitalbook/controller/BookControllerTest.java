package com.digitalbook.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.digitalbook.UserServiceApplication;
import com.digitalbook.payload.request.Book;
import com.digitalbook.payload.response.MessageResponse;
import com.digitalbook.restapi.service.BookRestApiService;
import com.digitalbook.util.ConstantValueUtil;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { UserServiceApplication.class })
class BookControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private BookRestApiService bookRestApiServiceMock;

	@BeforeEach
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void createBookTest() throws Exception {

		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.CREATED)
				.body(new MessageResponse("Book registered successfully!"));

		when(bookRestApiServiceMock.createBook(any(Book.class))).thenReturn(response);

		mockMvc.perform(post("/api/v1/digitalbooks/author/1/books").contentType(MediaType.APPLICATION_JSON).content(
				"{ \"logo\": \"logo\", \"title\": \"title\",\"category\":\"category\" ,\"price\":4.0 ,\"publisher\":\"publisher\",\"publishedDate\":\"2022-03-31\","
						+ "\"content\":\"content\",\"active\":true}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Book registered successfully!"));

	}

	@Test
	void updateBookTest() throws Exception {

		ResponseEntity<MessageResponse> response = ResponseEntity.status(HttpStatus.CREATED)
				.body(new MessageResponse("Book updated successfully!"));
		when(bookRestApiServiceMock.updateBook(any(Book.class))).thenReturn(response);

		mockMvc.perform(put("/api/v1/digitalbooks/author/1/books/1").contentType(MediaType.APPLICATION_JSON).content(
				"{ \"logo\": \"logo\", \"title\": \"title\",\"category\":\"category\" ,\"price\":4.0 ,\"publisher\":\"publisher\",\"publishedDate\":\"2022-03-31\","
						+ "\"content\":\"content\",\"active\":true}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Book updated successfully!"));

	}

	@Test
	void blockBookTestYes() throws Exception {
		String block = ConstantValueUtil.BLOCK;

		ResponseEntity<MessageResponse> response = ResponseEntity.ok(new MessageResponse("Book blocked successfully!"));
		when(bookRestApiServiceMock.blockBook(0, 6, block)).thenReturn(response);

		mockMvc.perform(post("/api/v1/digitalbooks/author/0/books/6?block=" + block).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Book blocked successfully!"));

	}

	@Test
	void blockBookTestNo() throws Exception {
		String block = ConstantValueUtil.UN_BLOCK;

		ResponseEntity<MessageResponse> response = ResponseEntity
				.ok(new MessageResponse("Book unblocked successfully!"));
		when(bookRestApiServiceMock.blockBook(0, 6, block)).thenReturn(response);

		mockMvc.perform(post("/api/v1/digitalbooks/author/0/books/6?block=" + block).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Book unblocked successfully!"));

	}

	@Test
	void getByRequestTest() throws Exception {
		ResponseEntity<List> response = ResponseEntity.ok(List.of());
		when(bookRestApiServiceMock.searchBook("category", "title", 1, 4.0, "publisher")).thenReturn(response);

		mockMvc.perform(
				get("/api/v1/digitalbooks/search?category=category&title=title&author=1&price=4.0&publisher=publisher"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

}
