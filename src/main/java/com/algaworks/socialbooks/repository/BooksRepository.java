package com.algaworks.socialbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.socialbooks.domain.Book;

public interface BooksRepository extends JpaRepository<Book, Long>{

}
