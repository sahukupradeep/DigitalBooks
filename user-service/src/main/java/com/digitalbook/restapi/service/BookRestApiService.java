package com.digitalbook.restapi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.digitalbook.payload.response.AuthorRes;
import com.digitalbook.payload.response.BookResponse;
import com.digitalbook.payload.response.MessageResponse;
import com.digitalbook.payload.response.SearchBookResponse;
import com.digitalbook.repository.UserRepository;
import com.digitalbook.util.CommonRestApiUrl;
import com.digitalbook.util.CommonStringUtil;
import com.digitalbook.util.RestApiExceptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	private CommonStringUtil commonStringUtil;

	@Autowired
	private ObjectMapper objectMapper;

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

	public ResponseEntity<List<BookResponse>> searchBook(String category, String title, Integer author, Double price,
			String publisher) throws JsonMappingException, JsonProcessingException {
		logger.info(" createBook() {} ");
		ResponseEntity<List<BookResponse>> response = null;

		Map<String, String> mapParam = new LinkedHashMap<>();

		if (category != null) {
			mapParam.put("category", "" + category);
		}
		if (title != null) {
			mapParam.put("title", "" + title);
		}
		if (author != null) {
			mapParam.put("author", "" + author);
		}
		if (price != null) {
			mapParam.put("price", "" + price);
		}
		if (publisher != null) {
			mapParam.put("publisher", "" + publisher);
		}

		String urlParam = "";

		for (Entry<String, String> entry : mapParam.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (urlParam.length() > 0) {
				urlParam += "&" + key + "=" + value;
			} else {
				urlParam += "?" + key + "=" + value;
			}
		}
		ResponseEntity<String> response1 = null;
		response1 = restTemplate.getForEntity(commonRestApiUrl.getSearchBookUrl() + urlParam, String.class);
		String resMsg = response1.getBody();

		List<User> users = userRepository.findAll();

		Map<Long, List<User>> map = users.stream().collect(Collectors.groupingBy(User::getId, Collectors.toList()));

		List<BookResponse> bookResponses = objectMapper.readValue(resMsg, new TypeReference<List<BookResponse>>() {
		});
		for (BookResponse bookResponse : bookResponses) {
			List<User> listUser = map.get((long) bookResponse.getAuthorId());
			if (listUser != null && !listUser.isEmpty()) {
				bookResponse.setAuthor(listUser.get(0).getUsername());
			}
		}

		response = ResponseEntity.status(response1.getStatusCode()).body(bookResponses);

		return response;

	}

	public ResponseEntity<MessageResponse> subscribeBook(BookSub bookSub) {

		logger.info(" subscribeBook() {} " + bookSub);

		ResponseEntity<MessageResponse> response = null;

		response = restTemplate.postForEntity(commonRestApiUrl.getSubscribeBookUrl(), bookSub, MessageResponse.class);

		return response;

	}

	public ResponseEntity<List<BookResponse>> getAllReaderBook(String emailId) throws JsonMappingException, JsonProcessingException {
		logger.info(" getAllReaderBook() {} ");

		Optional<User> user = userRepository.findByEmail(emailId);

		if (user.isEmpty()) {
			throw new RequestNotFounException(" User Not Found");
		}

		ResponseEntity<List<BookResponse>> response = null;

		String url = commonStringUtil.replaceAll("readerId", "" + user.get().getId(),
				commonRestApiUrl.getGetAllReaderBookUrl());

		ResponseEntity<String> response1 = null;
		response1 = restTemplate.getForEntity(url, String.class);
		String resMsg = response1.getBody();

		List<User> users = userRepository.findAll();

		Map<Long, List<User>> map = users.stream().collect(Collectors.groupingBy(User::getId, Collectors.toList()));

		List<BookResponse> bookResponses = objectMapper.readValue(resMsg, new TypeReference<List<BookResponse>>() {
		});
		for (BookResponse bookResponse : bookResponses) {
			List<User> listUser = map.get((long) bookResponse.getAuthorId());
			if (listUser != null && !listUser.isEmpty()) {
				bookResponse.setAuthor(listUser.get(0).getUsername());
			}
		}

		response = ResponseEntity.status(response1.getStatusCode()).body(bookResponses);

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

	public ResponseEntity<List<BookResponse>> getBooksByAuthor(Integer authorId)
			throws JsonMappingException, JsonProcessingException {

		logger.info(" createBook() {} ");

		Optional<User> user = userRepository.findById((long) authorId);

		if (user.isEmpty()) {
			throw new RequestNotFounException(" Author Not Found");
		}
		ResponseEntity<List<BookResponse>> response = null;
		String url = commonStringUtil.replaceAll("authorId", "" + authorId, commonRestApiUrl.getAllBookAuthorUrl());

		ResponseEntity<String> response1 = null;
		response1 = restTemplate.getForEntity(url, String.class);
		String resMsg = response1.getBody();

		List<BookResponse> bookResponses = objectMapper.readValue(resMsg, new TypeReference<List<BookResponse>>() {
		});
		for (BookResponse bookResponse : bookResponses) {
			bookResponse.setAuthor(user.get().getUsername());
		}

		response = ResponseEntity.status(response1.getStatusCode()).body(bookResponses);

		return response;
	}

	public ResponseEntity<Book> getBooksByAuthorAndBookId(Integer authorId, Integer bookId) throws Exception {
		logger.info(" getBookByReaderAndSubId() {} ");

		Optional<User> user = userRepository.findById((long) authorId);

		if (user.isEmpty()) {
			throw new RequestNotFounException(" User Not Found");
		}

		String url = commonStringUtil.replaceAll("authorId", "" + authorId, commonRestApiUrl.getGetAuthorBookUrl());
		url = commonStringUtil.replaceAll("bookId", "" + bookId, url);

		try {
			ResponseEntity<Book> response = restTemplate.getForEntity(url, Book.class);
			return response;
		} catch (HttpClientErrorException e) {
			throw RestApiExceptionUtil.throwClientException(e);
		}
	}

	public ResponseEntity<SearchBookResponse> getAllBook() {
		logger.info(" getAllBook() {} ");

		ResponseEntity<SearchBookResponse> response = null;

		String url = commonRestApiUrl.getAllBookUrl();

		response = restTemplate.getForEntity(url, SearchBookResponse.class);

		SearchBookResponse bookResponse = response.getBody();

		Set<Integer> listAuthorId = bookResponse.getAuthorIdSet();
		List<AuthorRes> listAuthorRes = new ArrayList<>();
		for (Integer autherId : listAuthorId) {
			Optional<User> user = userRepository.findById((long) autherId);
			if (user.isPresent()) {
				AuthorRes authorRes = new AuthorRes();
				authorRes.setId(autherId);
				authorRes.setName(user.get().getUsername());
				listAuthorRes.add(authorRes);
			}

		}

		bookResponse.setListAuther(listAuthorRes);

		return response;

	}

}
