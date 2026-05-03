package org.example.books_anotherone_project.controller;


import jakarta.validation.Valid;
import org.example.books_anotherone_project.model.Book;
import org.example.books_anotherone_project.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;


    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/author")
    public ResponseEntity<List<Book>> getBookByAuthor(@RequestParam String author) {
        List<Book> book = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable long id) {
        if (bookService.existById(id)){
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
        book.setId(0);
        Book savedBook = bookService.addBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) {
        List<Book> books = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    @PatchMapping(("/{id}/availability"))
    public ResponseEntity<Book> updateAvailability(@PathVariable long id, @RequestParam boolean available) {
        Book updatedBook = bookService.updateAvailability(id, available);
        return ResponseEntity.ok(updatedBook);
    }


}
