package com.digitalbook.payload.response;

import java.util.Set;

public class SearchBookResponse {

	private Set<String> titleSet;

	private Set<String> categorySet;

	private Set<Integer> authorIdSet;

	private Set<Double> priceSet;

	private Set<String> publisherSet;

	public Set<String> getTitleSet() {
		return titleSet;
	}

	public void setTitleSet(Set<String> titleSet) {
		this.titleSet = titleSet;
	}

	public Set<String> getCategorySet() {
		return categorySet;
	}

	public void setCategorySet(Set<String> categorySet) {
		this.categorySet = categorySet;
	}

	public Set<Integer> getAuthorIdSet() {
		return authorIdSet;
	}

	public void setAuthorIdSet(Set<Integer> authorIdSet) {
		this.authorIdSet = authorIdSet;
	}

	public Set<Double> getPriceSet() {
		return priceSet;
	}

	public void setPriceSet(Set<Double> priceSet) {
		this.priceSet = priceSet;
	}

	public Set<String> getPublisherSet() {
		return publisherSet;
	}

	public void setPublisherSet(Set<String> publisherSet) {
		this.publisherSet = publisherSet;
	}

}
