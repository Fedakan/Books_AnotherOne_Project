package org.example.books_anotherone_project.repository;

import org.example.books_anotherone_project.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
