package com.barbershop.controller;

import com.barbershop.model.Record;
import com.barbershop.service.BarbershopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

// Аннотация @Controller указывает, что этот класс является контроллером в MVC архитектуре
@Controller
public class BarbershopController {

    // Аннотация @Autowired автоматически связывает BookService с BookController
    @Autowired
    private BarbershopService barbershopService;

    // Метод для отображения главной страницы с перечнем всех записей
    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Record> records = barbershopService.getAllRecords();
        double average = barbershopService.getAverageClientsPerDay();
        model.addAttribute("averageClients", average);
        model.addAttribute("records", records);
        model.addAttribute("recordCount", records.size());
        return "index";
    }

    // Метод для отображения формы добавления новой записи
    @GetMapping("/showNewRecordForm")
    public String showNewRecordForm(Model model) {
        Record record = new Record();
        model.addAttribute("record", record);
        return "new_record";
    }

    // Метод для сохранения новой записи в базе данных
    @PostMapping("/saveRecord")
    public String saveRecord(@ModelAttribute("record") Record record) {
        barbershopService.saveRecord(record);
        return "redirect:/";
    }

    // Метод для отображения формы редактирования записи
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        Record record = barbershopService.getRecordById(id).get();
        model.addAttribute("record", record);
        return "update_record";
    }

    // Метод для удаления записи
    @GetMapping("/deleteRecord/{id}")
    public String deleteRecord(@PathVariable(value = "id") Long id) {
        barbershopService.deleteRecord(id);
        return "redirect:/";
    }

    // Метод для поиска книг по всем параметрам
    @GetMapping("/search")
    public String searchRecords(@RequestParam("keyword") String keyword, Model model) {
        List<Record> records = barbershopService.searchByAllParameters(keyword);
        double average = barbershopService.getAverageClientsPerDay();
        model.addAttribute("averageClients", average);
        model.addAttribute("records", records);
        model.addAttribute("recordCount", records.size());
        return "index";
    }

    // Метод для сортировки записей по дате записи по возрастанию
    @GetMapping("/sortByRecordingDateASC")
    public String sortByRecordingDateASC(Model model) {
        List<Record> records = barbershopService.sortByRecordingDateASC();
        double average = barbershopService.getAverageClientsPerDay();
        model.addAttribute("averageClients", average);
        model.addAttribute("records", records);
        model.addAttribute("recordCount", records.size());
        return "index";
    }

    // Метод для сортировки записей по дате записи по убыванию
    @GetMapping("/sortByRecordingDateDESC")
    public String sortByRecordingDateDESC(Model model) {
        List<Record> records = barbershopService.sortByRecordingDateDESC();
        double average = barbershopService.getAverageClientsPerDay();
        model.addAttribute("averageClients", average);
        model.addAttribute("records", records);
        model.addAttribute("recordCount", records.size());
        return "index";
    }

    // Метод для перехода на страницу статистики
    @GetMapping("/statistics")
    public String getStatistics(Model model) {
        Map<String, Long> statistics = barbershopService.getStatistics();
        model.addAttribute("statistics", statistics);
        return "statistics";
    }

    @GetMapping("/countByDate")
    public String countByDate(@RequestParam("date") String dateStr, Model model) {
        List<Record> records = barbershopService.getAllRecords();
        double average = barbershopService.getAverageClientsPerDay();
        model.addAttribute("averageClients", average);
        model.addAttribute("records", records);
        model.addAttribute("recordCount", records.size());

        LocalDate date = LocalDate.parse(dateStr); // Преобразование строки в LocalDate
        long count = barbershopService.countRecordsByDate(date);
        model.addAttribute("clientCount", count);
        model.addAttribute("selectedDate", date); // Передаем дату для отображения
        return "index"; // Возвращаем на главную страницу
    }

}
