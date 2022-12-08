package com.digitalbook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbook.entity.BookSub;

@Repository
public interface BookSubRepository extends JpaRepository<BookSub, Integer> {

	Optional<BookSub> findByBookIdAndReaderId(Integer bookId, Integer readerId);

	List<BookSub> findByReaderId(Integer readerId);

	Optional<BookSub> findByIdAndReaderId(Integer subId, Integer readerId);

}
