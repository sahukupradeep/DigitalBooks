package com.digitalbook.restapi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.digitalbook.payload.request.Book;
import com.digitalbook.payload.response.MessageResponse;
import com.digitalbook.util.CommonRestApiUrl;
import com.digitalbook.util.StringUtil;

@Service
public class BookRestApiService {

	private Logger logger = LoggerFactory.getLogger(BookRestApiService.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CommonRestApiUrl commonRestApiUrl;

	public ResponseEntity<MessageResponse> createBook(Book book) {

		logger.info(" createBook() {} " + book);
		ResponseEntity<MessageResponse> response = null;

		response = restTemplate.postForEntity(commonRestApiUrl.getCreateBookUrl(), book, MessageResponse.class);

		return response;

	}

	public ResponseEntity<MessageResponse> updateBook(Book book) {

		logger.info(" updateBook() {} " + book);

		restTemplate.put(commonRestApiUrl.getUpdateBookUrl(), book);

		return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Book updated successfully!"));

	}

	public ResponseEntity<MessageResponse> blockBook(Integer authorId, Integer bookId, String block) {

		logger.info(" blockBook() {} " + bookId);
		ResponseEntity<MessageResponse> response = null;

		String url = StringUtil.replaceAll("authorId", "" + authorId,commonRestApiUrl.getBlockBookUrl());
		url = StringUtil.replaceAll("bookId", "" + bookId,commonRestApiUrl.getBlockBookUrl());//url.replaceAll("bookId", "" + bookId);

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

}
