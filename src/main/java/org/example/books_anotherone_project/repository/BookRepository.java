package org.example.books_anotherone_project.repository;


import org.example.books_anotherone_project.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BookRepository {

    private final List<Book> books = new ArrayList<>();

    public Book saveBook(Book book) {
        if (book.getId() == 0){
            long nextId = books.stream()
                    .mapToLong(Book::getId)
                    .max()
                    .orElse(0L) + 1;
            book.setId(nextId);
            books.add(book);
        }else {
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getId() == book.getId()){
                    books.set(i, book);
                    return book;
                }

            }
        }
        return book;
    }

    public List<Book> findAll() {
        return books;
    }

    public Optional<Book> findById(long id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }

    public void delete(long id) {
        books.removeIf(book -> book.getId() == id);
    }

    public List<Book> findByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .toList();
    }

    public List<Book> findByTitleContaining(String title) {
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .sorted(Comparator.comparing(Book::getTitle))
                .toList();
    }


}

