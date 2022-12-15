package com.digitalbook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.digitalbook.entity.Book;
import com.digitalbook.payload.response.BookResponse;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	Optional<Book> findByTitleAndAuthorId(String title, int authorId);

	Optional<Book> findByIdAndAuthorId(Integer bookId, Integer authorId);

	@Query("SELECT b.id as bookId,b.logo as logo,b.title as title,b.authorId as authorId,b.publisher as publisher,b.price as price,"
			+ "b.createdDate as createdDate,b.category as category FROM Book b WHERE b.active=true and "
			+ "(:category is null or b.category = :category) and (:title is null or b.title = :title) and "
			+ "(:authorId is null or b.authorId=:authorId) and (:price is null or b.price=:price) and "
			+ "(:publisher is null or b.publisher=:publisher)")
	List<BookResponse> findByCategoryAndTitleAndAuthorIdAndPriceAndPublisherActive(@Param("category") String category,
			@Param("title") String title, @Param("authorId") Integer author, @Param("price") Double price,
			@Param("publisher") String publisher);

	List<Book> findByIdIn(List<Integer> listBookId);

	List<Book> findByAuthorId(Integer authorId);

	@Query("SELECT b FROM Book b WHERE b.active=true")
	List<Book> findAllActive();

}
