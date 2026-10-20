package org.example.books_anotherone_project.service;


import org.example.books_anotherone_project.exception.BookNotFoundException;
import org.example.books_anotherone_project.model.Book;
import org.example.books_anotherone_project.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldReturnBookWhenBookExists() {
        Book book = new Book(1L, "Thinking in Java", "Bruce Eckel", true);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        Assertions.assertEquals("Thinking in Java", result.getTitle());
        Assertions.assertEquals("Bruce Eckel", result.getAuthor());
    }

    @Test
    void shouldThrowExceptionWhenBookDoesNotExist() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(BookNotFoundException.class, () -> {bookService.getBookById(1L);});
    }

    @Test
    void shouldDeleteBookSuccessfully() {
        Mockito.when(bookRepository.existsById(1L)).thenReturn(true);

        bookService.deleteBook(1L);

        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonexistentBook() {
        Mockito.when(bookRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
    }

    @Test
    void shouldAddBookSuccessfully() {
        Book inputBook = new Book(0L, "Thinking in Java", "Bruce Eckel", true);
        Book savedBook = new Book(1L, "Thinking in Java", "Bruce Eckel", true);
        Mockito.when(bookRepository.save(inputBook)).thenReturn(savedBook);

        Book result = bookService.addBook(inputBook);

        Assertions.assertEquals("Thinking in Java", result.getTitle());
        Assertions.assertEquals(1L, result.getId());
    }

    @Test
    void shouldGetAvailableBooksSuccessfully() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = List.of(new Book(1L, "Thinking in Java", "Bruce Eckel", true));

        Page<Book> bookPage = new PageImpl<>(books);

        Mockito.when(bookRepository.findByAvailableTrue(pageable)).thenReturn(bookPage);

        Page<Book> result = bookService.getAvailableBooks(pageable);

        Assertions.assertEquals(1, result.getContent().size());
    }

    @Test
    void shouldUpdateAvailabilitySuccessfully() {
        Book book = new Book(1L, "Thinking in Java", "Bruce Eckel", false);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.updateAvailability(book.getId(), true);

        Assertions.assertTrue(book.isAvailable());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingAvailabilityForNonExistentBook() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(BookNotFoundException.class, () -> {bookService.updateAvailability(1L, false);});
    }

    @Test
    void shouldFindBookByAuthorSuccessfully() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = List.of(
                new Book(1L, "Thinking in Java", "Bruce Eckel", true),
                new Book(2L, "Thinking in Java", "Bruce Eckel", true)
        );

        Page<Book> bookPage = new PageImpl<>(books);

        Mockito.when(bookRepository.findByAuthor("Bruce Eckel", pageable)).thenReturn(bookPage);

        Page<Book> result = bookService.findByAuthor("Bruce Eckel", pageable);

        Assertions.assertEquals(2, result.getContent().size());
    }

    @Test
    void shouldFindBookByTitleSuccessfully() {
        Pageable pageable = PageRequest.of(0, 10);

        List<Book> books = List.of(
                new Book(1L, "Java", "Some nerd", true),
                new Book(2L, "Java 2", "Some nerd", true)
        );

        Page<Book> bookPage = new PageImpl<>(books);

        Mockito.when(bookRepository.findByTitleContainingIgnoreCase("Java", pageable)).thenReturn(bookPage);

        Page<Book> result = bookService.findByTitleContainingIgnoreCase("Java", pageable);

        Assertions.assertEquals(2, result.getContent().size());
    }
}
