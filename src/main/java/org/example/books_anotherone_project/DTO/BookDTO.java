package org.example.books_anotherone_project.DTO;

import org.example.books_anotherone_project.model.Book;

public class BookDTO {

    private String title;
    private String author;
    private String status;
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BookDTO(String title, String author, String status) {
        this.title = title;
        this.author = author;
        this.status = status;
    }

    public BookDTO() {

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static BookDTO fromEntity(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        if (book.getCategory() != null) {
            bookDTO.setCategoryName(book.getCategory().getName());
        }
        if (book.isAvailable()) {
            bookDTO.setStatus("Available");
        } else {
            bookDTO.setStatus("Not Available");
        }
        return bookDTO;
    }

}
