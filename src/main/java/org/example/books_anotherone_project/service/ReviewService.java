package org.example.books_anotherone_project.service;


import lombok.extern.slf4j.Slf4j;
import org.example.books_anotherone_project.exception.BookNotFoundException;
import org.example.books_anotherone_project.model.Book;
import org.example.books_anotherone_project.model.Review;
import org.example.books_anotherone_project.repository.BookRepository;
import org.example.books_anotherone_project.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReviewService {

    private ReviewRepository reviewRepository;

    private BookRepository bookRepository;

    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    public Review addReview(Review review, Long bookId) {
        log.info("Adding review {}", review);
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException("Book not found"));
        review.setBook(book);
        return reviewRepository.save(review);
    }
}
