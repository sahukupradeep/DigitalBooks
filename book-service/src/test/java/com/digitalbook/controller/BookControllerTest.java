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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.digitalbook.BookServiceApplication;
import com.digitalbook.entity.Book;
import com.digitalbook.service.BookService;
import com.digitalbook.util.ConstantValueUtil;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { BookServiceApplication.class })
class BookControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private BookService bookServiceMock;

	@BeforeEach
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void createBookTest() throws Exception {
		when(bookServiceMock.createBook(any(Book.class))).thenReturn(new Book());

		mockMvc.perform(post("/api/book/save").contentType(MediaType.APPLICATION_JSON).content(
				"{ \"logo\": \"logo\", \"title\": \"title\",\"category\":\"category\" ,\"price\":4.0 ,\"authorId\":1,\"publisher\":\"publisher\",\"publishedDate\":\"2022-03-31\","
						+ "\"content\":\"content\",\"active\":true}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Book registered successfully!"));

	}

	@Test
	void updateBookTest() throws Exception {
		when(bookServiceMock.updateBook(any(Book.class))).thenReturn(new Book());

		mockMvc.perform(put("/api/book/update").contentType(MediaType.APPLICATION_JSON).content(
				"{ \"logo\": \"logo\", \"title\": \"title\",\"category\":\"category\" ,\"price\":4.0 ,\"authorId\":1,\"publisher\":\"publisher\",\"publishedDate\":\"2022-03-31\","
						+ "\"content\":\"content\",\"active\":true}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Book updated successfully!"));

	}

	@Test
	void blockBookTestYes() throws Exception {
		String block = ConstantValueUtil.BLOCK;
		when(bookServiceMock.blockBook(0, 6, block)).thenReturn(new Book());

		mockMvc.perform(post("/api/book/author/0/book/6?block=" + block).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Book blocked successfully!"));

	}

	@Test
	void blockBookTestNo() throws Exception {
		String block = ConstantValueUtil.UN_BLOCK;
		when(bookServiceMock.blockBook(0, 6, block)).thenReturn(new Book());

		mockMvc.perform(post("/api/book/author/0/book/6?block=" + block).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Book unblocked successfully!"));

	}

	@Test
	void getByRequestTest() throws Exception {
		when(bookServiceMock.getByRequest("category", "title", 1, 4.0, "publisher")).thenReturn(List.of(new Book()));

		mockMvc.perform(get("/api/book/search?category=category&title=title&author=1&price=4.0&publisher=publisher"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

}
