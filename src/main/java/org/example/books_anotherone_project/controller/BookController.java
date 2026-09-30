package org.example.books_anotherone_project.controller;


import jakarta.validation.Valid;
import org.example.books_anotherone_project.DTO.BookCreateRequest;
import org.example.books_anotherone_project.DTO.BookDTO;
import org.example.books_anotherone_project.model.Book;
import org.example.books_anotherone_project.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<BookDTO>> getBookByAuthor(@RequestParam String author, Pageable pageable) {
        Page<BookDTO> dto = bookService.findByAuthor(author, pageable)
                .map(BookDTO::fromEntity);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<Page<BookDTO>> getAvailableBooks(Pageable pageable) {
        Page<BookDTO> dto = bookService.getAvailableBooks(pageable)
                .map(BookDTO::fromEntity);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/addBook")
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookCreateRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setAvailable(true);

        Book savedBook = bookService.addBook(book);

        BookDTO dto = BookDTO.fromEntity(savedBook);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookDTO>> searchBooksByTitle(@RequestParam String title, Pageable pageable) {
        Page<BookDTO> dto = bookService.findByTitleContainingIgnoreCase(title, pageable)
                .map(BookDTO::fromEntity);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping(("/{id}/availability"))
    public ResponseEntity<BookDTO> updateAvailability(@PathVariable long id, @RequestParam boolean available) {
        Book updatedBook = bookService.updateAvailability(id, available);
        BookDTO dto = BookDTO.fromEntity(updatedBook);
        return ResponseEntity.ok(dto);
    }
}
