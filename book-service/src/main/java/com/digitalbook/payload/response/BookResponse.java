package com.digitalbook.payload.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.rest.core.config.Projection;

import com.digitalbook.entity.Book;
import com.digitalbook.entity.BookSub;

@Projection(types = { Book.class, BookSub.class })
public interface BookResponse {

	public String getLogo();

	public String getTitle();

	public Integer getAuthorId();

	public String getPublisher();

	public Double getPrice();

	public LocalDateTime getCreatedDate();

	public String getCategory();

	public Integer getSubId();

	LocalDate getSubDate();

}
