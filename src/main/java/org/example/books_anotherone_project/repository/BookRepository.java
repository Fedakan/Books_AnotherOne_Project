package org.example.books_anotherone_project.repository;


import org.example.books_anotherone_project.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    List<Book> findAll();

    List<Book> findByAuthor(String author);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAvailableTrue();
}

