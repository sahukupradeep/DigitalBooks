package com.digitalbook.restapi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.digitalbook.entity.User;
import com.digitalbook.exception.RequestNotFounException;
import com.digitalbook.payload.request.Book;
import com.digitalbook.payload.request.BookSub;
import com.digitalbook.payload.response.MessageResponse;
import com.digitalbook.repository.UserRepository;
import com.digitalbook.util.CommonRestApiUrl;
import com.digitalbook.util.CommonStringUtil;
import com.digitalbook.util.RestApiExceptionUtil;

@Service
public class BookRestApiService {

	private Logger logger = LoggerFactory.getLogger(BookRestApiService.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CommonRestApiUrl commonRestApiUrl;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	CommonStringUtil commonStringUtil;

	public ResponseEntity<MessageResponse> createBook(Book book) throws Exception {

		logger.info(" createBook() {} " + book);

		Optional<User> user = userRepository.findById((long) book.getAuthorId());

		if (user.isEmpty()) {
			throw new RequestNotFounException(" Author Not Found");
		}

		try {
			ResponseEntity<MessageResponse> response = restTemplate.postForEntity(commonRestApiUrl.getCreateBookUrl(),
					book, MessageResponse.class);
			return response;
		} catch (HttpClientErrorException e) {
			throw RestApiExceptionUtil.throwClientException(e);
		}

	}

	public ResponseEntity<MessageResponse> updateBook(Book book) {

		logger.info(" updateBook() {} " + book);

		restTemplate.put(commonRestApiUrl.getUpdateBookUrl(), book);

		return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Book updated successfully!"));

	}

	public ResponseEntity<MessageResponse> blockBook(Integer authorId, Integer bookId, String block) {

		logger.info(" blockBook() {} " + bookId);

		ResponseEntity<MessageResponse> response = null;

		String url = commonStringUtil.replaceAll("authorId", "" + authorId, commonRestApiUrl.getBlockBookUrl());
		url = commonStringUtil.replaceAll("bookId", "" + bookId, commonRestApiUrl.getBlockBookUrl());

		Map<String, Object> reqParam = new HashMap<>();

		response = restTemplate.postForEntity(url + "?block=" + block, reqParam, MessageResponse.class);

		return response;

	}

	public ResponseEntity<List> searchBook(String category, String title, Integer author, Double price,
			String publisher) {
		logger.info(" createBook() {} ");
		ResponseEntity<List> response = null;

		String urlParam = "?category=" + category + "&title=" + title + "&author=" + author + "&price=" + price
				+ "&publisher=" + publisher;

		response = restTemplate.getForEntity(commonRestApiUrl.getSearchBookUrl() + urlParam, List.class);

		return response;

	}

	public ResponseEntity<MessageResponse> subscribeBook(BookSub bookSub) {

		logger.info(" subscribeBook() {} " + bookSub);

		ResponseEntity<MessageResponse> response = null;

		response = restTemplate.postForEntity(commonRestApiUrl.getSubscribeBookUrl(), bookSub, MessageResponse.class);

		return response;

	}

	public ResponseEntity<List> getAllReaderBook(String emailId) {
		logger.info(" getAllReaderBook() {} ");

		Optional<User> user = userRepository.findByEmail(emailId);

		if (user.isEmpty()) {
			throw new RequestNotFounException(" User Not Found");
		}

		ResponseEntity<List> response = null;

		String url = commonStringUtil.replaceAll("readerId", "" + user.get().getId(),
				commonRestApiUrl.getGetAllReaderBookUrl());

		response = restTemplate.getForEntity(url, List.class);

		return response;

	}

	public ResponseEntity<Book> getBookByReaderAndSubId(String emailId, Integer subId) throws Exception {
		logger.info(" getBookByReaderAndSubId() {} ");

		Optional<User> user = userRepository.findByEmail(emailId);

		if (user.isEmpty()) {
			throw new RequestNotFounException(" User Not Found");
		}

		String url = commonStringUtil.replaceAll("readerId", "" + user.get().getId(),
				commonRestApiUrl.getGetReaderBookUrl());
		url = commonStringUtil.replaceAll("subId", "" + subId, url);

		try {
			ResponseEntity<Book> response = restTemplate.getForEntity(url, Book.class);
			return response;
		} catch (HttpClientErrorException e) {
			throw RestApiExceptionUtil.throwClientException(e);
		}

	}

	public ResponseEntity<String> getContentByReaderAndSubId(String emailId, Integer subId) {
		logger.info(" getContentByReaderAndSubId() {} ");

		Optional<User> user = userRepository.findByEmail(emailId);

		if (user.isEmpty()) {
			throw new RequestNotFounException(" User Not Found");
		}

		ResponseEntity<String> response = null;

		String url = commonStringUtil.replaceAll("readerId", "" + user.get().getId(),
				commonRestApiUrl.getContentReaderSubBookUrl());
		url = commonStringUtil.replaceAll("subId", "" + subId, url);

		response = restTemplate.getForEntity(url, String.class);

		return response;

	}

	public ResponseEntity<MessageResponse> cancelByReaderAndSubId(String emailId, Integer subId) {
		logger.info(" cancelByReaderAndSubId() {} ");

		Optional<User> user = userRepository.findByEmail(emailId);

		if (user.isEmpty()) {
			throw new RequestNotFounException(" User Not Found");
		}

		ResponseEntity<MessageResponse> response = null;

		String url = commonStringUtil.replaceAll("readerId", "" + user.get().getId(),
				commonRestApiUrl.getCancelSubBookUrl());
		url = commonStringUtil.replaceAll("subId", "" + subId, url);

		Map<String, Object> reqParam = new HashMap<>();

		response = restTemplate.postForEntity(url, reqParam, MessageResponse.class);

		return response;

	}

}
