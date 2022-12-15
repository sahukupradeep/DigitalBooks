package com.digitalbook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.digitalbook.entity.BookSub;
import com.digitalbook.payload.response.BookResponse;

@Repository
public interface BookSubRepository extends JpaRepository<BookSub, Integer> {

	@Query("SELECT bs from BookSub bs WHERE bs.active=true and bs.bookId=:bookId and bs.readerId=:readerId")
	Optional<BookSub> findByBookIdAndReaderId(@Param("bookId")Integer bookId,@Param("readerId") Integer readerId);

	@Query("SELECT b.logo as logo,b.title as title,b.authorId as authorId,b.publisher as publisher,b.price as price,"
			+ "b.createdDate as createdDate,b.category as category, bs.id as subId,bs.createdDate as subDate "
			+ "from BookSub bs INNER JOIN Book b ON bs.bookId=b.id WHERE b.active=true and bs.readerId=:readerId and bs.active=true")
	List<BookResponse> findByReaderId(@Param("readerId") Integer readerId);

	@Query("SELECT b.logo as logo,b.title as title,b.authorId as authorId,b.publisher as publisher,b.price as price,"
			+ "b.createdDate as createdDate,b.category as category, bs.id as subId,bs.createdDate as subDate "
			+ "from BookSub bs INNER JOIN Book b ON bs.bookId=b.id WHERE b.active=true and bs.id=:id and bs.readerId=:readerId ")
	Optional<BookResponse> findByIdAndReader(@Param("id")Integer subId,@Param("readerId") Integer readerId);
	
	Optional<BookSub> findByIdAndReaderId(Integer subId, Integer readerId);

}
