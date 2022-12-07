package com.digitalbook.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.digitalbook.entity.BookSub;
import com.digitalbook.service.BookSubService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { BookServiceApplication.class })
class BookSubControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private BookSubService bookSubServiceMock;

	@BeforeEach
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void subscribeBookSubTest() throws Exception {

		when(bookSubServiceMock.subscribeBookSub(any(BookSub.class))).thenReturn(new BookSub());

		mockMvc.perform(post("/api/book-sub/subscribe").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"bookId\": 1, \"readerId\": 1,\"active\":true}").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Book subscribed successfully!"));

	}
	
	@Test
	void getAllByReaderTest() throws Exception {
		
		int redearId=1;
		when(bookSubServiceMock.getByReadeId(redearId)).thenReturn(List.of(new Book()));

		mockMvc.perform(get("/api/book-sub/get-all/"+redearId))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

}
