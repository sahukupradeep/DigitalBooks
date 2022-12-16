package com.digitalbook.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.digitalbook.UserServiceApplication;
import com.digitalbook.payload.request.LoginRequest;
import com.digitalbook.payload.request.SignupRequest;
import com.digitalbook.payload.response.JwtResponse;
import com.digitalbook.payload.response.MessageResponse;
import com.digitalbook.service.AuthService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { UserServiceApplication.class })
class AuthControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private AuthService authServiceMock;

	@BeforeEach
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void authenticateUserTest() throws Exception {
		ResponseEntity<JwtResponse> response = ResponseEntity.ok(new JwtResponse(null, null, null, null, null));

		when(authServiceMock.authenticateUser(any(LoginRequest.class))).thenReturn(response);

		mockMvc.perform(post("/api/auth/signin").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"username\": \"username\", \"password\": \"password\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void registerUserTest() throws Exception {
		ResponseEntity<MessageResponse> response = ResponseEntity.ok(new MessageResponse());

		when(authServiceMock.registerUser(any(SignupRequest.class))).thenReturn(response);

		mockMvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"username\": \"username\",\"email\": \"email@gmail.com\", \"password\": \"password\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
