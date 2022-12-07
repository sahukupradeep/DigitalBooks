package com.digitalbook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbook.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	Optional<Book> findByTitleAndAuthorId(String title, int authorId);

	Optional<Book> findByIdAndAuthorId(Integer bookId, Integer authorId);

	List<Book> findByCategoryAndTitleAndAuthorIdAndPriceAndPublisher(String category, String title, Integer author,
			Double price, String publisher);

	List<Book> findByIdIn(List<Integer> listBookId);

}
