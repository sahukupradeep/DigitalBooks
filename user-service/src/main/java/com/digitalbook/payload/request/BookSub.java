package com.digitalbook.payload.request;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class BookSub implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8013980103344238308L;

	private Integer id;

	@NotNull
	private Integer bookId;

	@NotNull
	private Integer readerId;

	@NotNull
	private Boolean active;

	private LocalDate createdDate;

	private LocalDate updatedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getReaderId() {
		return readerId;
	}

	public void setReaderId(Integer readerId) {
		this.readerId = readerId;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

}
