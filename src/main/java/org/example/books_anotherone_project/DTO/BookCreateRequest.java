package org.example.books_anotherone_project.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BookCreateRequest {

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 1, max = 50, message = "Cannot be less than 1 syllable and more than 50")
    String title;

    @NotBlank(message = "Cannot be empty")
    String author;

    public BookCreateRequest(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public BookCreateRequest() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
