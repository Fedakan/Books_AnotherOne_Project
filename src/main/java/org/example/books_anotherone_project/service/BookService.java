package org.example.books_anotherone_project.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.example.books_anotherone_project.exception.BookNotFoundException;
import org.example.books_anotherone_project.model.Book;
import org.example.books_anotherone_project.repository.BookRepository;

import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getBookById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    public void deleteBook(long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        }else {
            throw new BookNotFoundException("Book not found");
        }
    }

    public boolean existById(long id) {
        return bookRepository.existsById(id);
    }

    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Transactional
    public Book updateAvailability(long id, boolean newStatus) {
        Book avaliableBook = getBookById(id);
        avaliableBook.setAvailable(newStatus);
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
