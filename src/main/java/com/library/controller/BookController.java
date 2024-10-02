package com.library.controller;

import com.library.model.Book;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

// Аннотация @Controller указывает, что этот класс является контроллером в MVC архитектуре.
@Controller
public class BookController {

    // Аннотация @Autowired автоматически связывает BookService с BookController.
    @Autowired
    private BookService bookService;

    // Метод для отображения главной страницы с перечнем всех книг.
    @GetMapping("/")
    public String viewHomePage(Model model) {
        // Поиск всех книг (по пустому названию), возвращает список книг.
        List<Book> books = bookService.searchByTitle("");
        // Добавляем список книг в модель, чтобы передать их на страницу.
        model.addAttribute("books", books);
        // Добавляем количество книг в модель.
        model.addAttribute("bookCount", books.size());
        // Возвращаем имя представления (страницы), которое будет отображено.
        return "index";
    }

    // Метод для отображения формы добавления новой книги.
    @GetMapping("/showNewBookForm")
    public String showNewBookForm(Model model) {
        // Создаем новый объект Book и добавляем его в модель.
        Book book = new Book();
        model.addAttribute("book", book);
        // Возвращаем имя представления для формы добавления книги.
        return "new_book";
    }

    // Метод для сохранения новой книги в базе данных.
    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute("book") Book book) {
        // Если нужно, можно установить дату выдачи книги текущей датой.
        // book.setIssueDate(LocalDate.now());
        // Сохраняем книгу через BookService.
        bookService.saveBook(book);
        // После сохранения перенаправляем пользователя на главную страницу.
        return "redirect:/";
    }

    // Метод для отображения формы редактирования книги.
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        // Получаем книгу по ее ID через BookService.
        Book book = bookService.getBookById(id).get();
        // Добавляем книгу в модель для передачи на страницу редактирования.
        model.addAttribute("book", book);
        // Возвращаем имя представления для формы редактирования книги.
        return "update_book";
    }

    // Метод для удаления книги.
    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable(value = "id") Long id) {
        // Удаляем книгу по ее ID через BookService.
        bookService.deleteBook(id);
        // После удаления перенаправляем пользователя на главную страницу.
        return "redirect:/";
    }

    // Метод для поиска книг по названию.
    @GetMapping("/search")
    public String searchBooks(@RequestParam("keyword") String keyword, Model model) {
        // Ищем книги по ключевому слову (названию) через BookService.
        List<Book> books = bookService.searchByTitle(keyword);
        // Добавляем найденные книги в модель.
        model.addAttribute("books", books);
        // Добавляем количество найденных книг в модель.
        model.addAttribute("bookCount", books.size());
        // Возвращаем имя представления для отображения результатов поиска.
        return "index";
    }

    // Метод для поиска книг по студенту.
    @GetMapping("/searchStudent")
    public String searchByStudent(@RequestParam("keyword") String keyword, Model model) {
        // Ищем книги по имени студента через BookService.
        List<Book> books = bookService.searchByStudent(keyword);
        // Добавляем найденные книги в модель.
        model.addAttribute("books", books);
        // Добавляем количество найденных книг в модель.
        model.addAttribute("bookCount", books.size());
        // Возвращаем имя представления для отображения результатов поиска.
        return "index";
    }

    // Метод для сортировки книг по дате выдачи.
    @GetMapping("/sortByIssueDate")
    public String sortByIssueDate(Model model) {
        // Получаем отсортированный список книг через BookService.
        List<Book> books = bookService.sortByIssueDate();
        // Добавляем отсортированные книги в модель.
        model.addAttribute("books", books);
        // Добавляем количество книг в модель.
        model.addAttribute("bookCount", books.size());
        // Возвращаем имя представления для отображения отсортированных книг.
        return "index";
    }

    // Метод для перехода на страницу статистики.
    @GetMapping("/statistics")
    public String getStatistics(Model model) {
        // Получаем статистику по книгам через BookService (например, количество книг на пользователя).
        Map<String, Long> statistics = bookService.getStatistics();
        // Выводим статистику в консоль (для проверки данных).
        System.out.println(statistics);
        // Добавляем статистику в модель для передачи на страницу.
        model.addAttribute("statistics", statistics);
        // Возвращаем имя представления для отображения статистики.
        return "statistics";
    }
}
