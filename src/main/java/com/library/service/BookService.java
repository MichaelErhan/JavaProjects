package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Получить все книги
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Найти книгу по ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Сохранить новую книгу
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    // Удалить книгу
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // Найти книги по названию
    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    // Найти книги по студенту
    public List<Book> searchByStudent(String student) {
        return bookRepository.findByStudentNameContaining(student);
    }

    // Сортировка книг по дате выдачи
    public List<Book> sortByIssueDate() {
        return bookRepository.findAll(Sort.by(Sort.Direction.ASC, "issueDate"));
    }

    // Метод для получения данных для гистограммы, где ключом является дата выдачи книги (IssueDate),
    // а значением — количество книг, выданных на эту дату.
    public Map<String, Long> getStatistics() {
        // Получаем все книги из базы данных, затем с помощью Stream API группируем их по дате выдачи (IssueDate).
        // В качестве ключа используется строковое представление даты, а значением является количество книг на каждую дату.
        return bookRepository.findAll()  // Получаем список всех книг.
                .stream()  // Преобразуем список в поток (stream).
                // Группируем книги по дате выдачи и считаем количество книг для каждой даты.
                .collect(Collectors.groupingBy(book -> book.getIssueDate().toString(), Collectors.counting()));
    }
}
