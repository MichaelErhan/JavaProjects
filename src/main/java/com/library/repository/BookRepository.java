package com.library.repository;

import com.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Поиск книг по названию
    List<Book> findByTitleContaining(String title);

    // Поиск книг по студенту
    List<Book> findByStudentNameContaining(String studentName);
}
