package org.example.books_anotherone_project.repository;


import org.example.books_anotherone_project.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    List<Book> findAll();

    Page<Book> findByAuthor(String author, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> findByAvailableTrue(Pageable pageable);
}

