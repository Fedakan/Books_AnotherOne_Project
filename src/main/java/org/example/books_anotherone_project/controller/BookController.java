package org.example.books_anotherone_project.controller;


import jakarta.validation.Valid;
import org.example.books_anotherone_project.DTO.BookCreateRequest;
import org.example.books_anotherone_project.DTO.BookDTO;
import org.example.books_anotherone_project.model.Book;
import org.example.books_anotherone_project.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;


    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable long id) {
        Book book = bookService.getBookById(id);
        BookDTO dto = BookDTO.fromEntity(book);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/author")
    public ResponseEntity<List<BookDTO>> getBookByAuthor(@RequestParam String author) {
        List<BookDTO> dto = bookService.getBooksByAuthor(author)
                .stream()
                .map(BookDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<BookDTO>> getAvailableBooks() {
        List<BookDTO> dto = bookService.getAvailableBooks()
                .stream()
                .map(BookDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/addBook")
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookCreateRequest request) {
        Book book = new Book();
        book.setId(0);
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setAvailable(true);

        Book savedBook = bookService.addBook(book);

        BookDTO dto = BookDTO.fromEntity(savedBook);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooksByTitle(@RequestParam String title) {
        List<BookDTO> dto = bookService.searchBooksByTitle(title)
                .stream()
                .map(BookDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dto);
    }

    @PatchMapping(("/{id}/availability"))
    public ResponseEntity<BookDTO> updateAvailability(@PathVariable long id, @RequestParam boolean available) {
        Book updatedBook = bookService.updateAvailability(id, available);
        BookDTO dto = BookDTO.fromEntity(updatedBook);
        return ResponseEntity.ok(dto);
    }
}
