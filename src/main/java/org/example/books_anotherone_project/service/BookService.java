package org.example.books_anotherone_project.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.example.books_anotherone_project.exception.BookNotFoundException;
import org.example.books_anotherone_project.model.Book;
import org.example.books_anotherone_project.repository.BookRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Book> findByAuthor(String author, Pageable pageable) {
        return bookRepository.findByAuthor(author, pageable);
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

    public Page<Book> getAvailableBooks(Pageable pageable) {
        return bookRepository.findByAvailableTrue(pageable);
    }

    public Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(title, pageable);
    }
}
