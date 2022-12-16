package com.digitalbook.payload.request;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6815191657002894509L;
	private Integer id;

	@NotEmpty
	private String logo;

	@NotEmpty
	private String title;

	@NotEmpty
	private String category;

	@NotNull
	private Double price;

	private Integer authorId;

	private String author;

	@NotEmpty
	private String publisher;

	private LocalDate publishedDate;

	@NotEmpty
	private String content;

	@NotNull
	private Boolean active;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public LocalDate getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", logo=" + logo + ", title=" + title + ", category=" + category + ", price=" + price
				+ ", authorId=" + authorId + ", publisher=" + publisher + ", publishedDate=" + publishedDate
				+ ", content=" + content + ", active=" + active + ", createdDate=" + createdDate + ", updatedDate="
				+ updatedDate + "]";
	}

}
