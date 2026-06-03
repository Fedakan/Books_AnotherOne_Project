package org.example.books_anotherone_project.service;

import lombok.extern.slf4j.Slf4j;
import org.example.books_anotherone_project.DTO.BookDTO;
import org.example.books_anotherone_project.model.Book;
import org.example.books_anotherone_project.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getBookById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void deleteBook(long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        }else {
            throw new RuntimeException("Book not found");
        }
    }

    public boolean existById(long id) {
        return bookRepository.existsById(id);
    }

    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public Book updateAvailability(long id, boolean newStatus) {
        Book avaliableBook = getBookById(id);
        avaliableBook.setAvailable(newStatus);
        bookRepository.save(avaliableBook);
        return avaliableBook;
    }

    public Book addBook(Book book) {
        log.info("Adding book {}", book);
        return bookRepository.save(book);
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableTrue();
    }

    public List<Book> findByTitleContainingIgnoreCase(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
}
