package org.example.books_anotherone_project.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.books_anotherone_project.DTO.BookCreateRequest;
import org.example.books_anotherone_project.model.Book;
import org.example.books_anotherone_project.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void shouldCreateBookSuccessfully() throws Exception {
        BookCreateRequest request = new BookCreateRequest("Thinking in Java", "Bruce Eckel");

        mockMvc.perform(post("/api/books/addBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Thinking in Java"))
                .andExpect(jsonPath("$.status").value("Available"));
    }

    @Test
    void shouldReturnNotFoundWhenBookDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/books/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()
        );
    }

    @Test
    void shouldReturnOnlyAvailableBooks() throws Exception {
        bookRepository.save(new Book(0L, "Thinking in Java", "Bruce Eckel", true));
        bookRepository.save(new Book(0L, "Lord of The Rings", "Tolkien", false));

        mockMvc.perform(get("/api/books/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Thinking in Java"));
    }

    @Test
    void shouldDeleteBookSuccessfully() throws Exception {
        Book book = new Book(0L, "Thinking in Java", "Bruce Eckel", true);
        Book savedBook = bookRepository.save(book);


        mockMvc.perform(delete("/api/books/{id}", savedBook.getId()))
                .andExpect(status().isNoContent());
        boolean exists = bookRepository.existsById(savedBook.getId());
        assertFalse(exists);
    }
}
