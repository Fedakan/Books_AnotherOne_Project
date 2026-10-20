package org.example.books_anotherone_project.model;

import jakarta.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String comment;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Review(long id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public Review() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
