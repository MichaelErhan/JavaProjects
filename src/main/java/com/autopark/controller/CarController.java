package com.autopark.controller;

import com.autopark.model.Car;
import com.autopark.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// Аннотация @Controller указывает, что этот класс является контроллером в MVC архитектуре.
@Controller
public class CarController {

    // Аннотация @Autowired автоматически внедряет зависимость CarService в контроллер.
    @Autowired
    public CarService carService;

    // Метод для отображения главной страницы с перечнем всех машин.
    @GetMapping("/")
    public String viewHomePage(Model model) {
        // Поиск всех машин (по пустой строке), возвращает список машин.
        List<Car> cars = carService.searchByBrand("");
        // Добавляем список машин в модель, чтобы передать их на страницу.
        model.addAttribute("cars", cars);
        // Добавляем количество машин в модель.
        model.addAttribute("carCount", cars.size());
        // Возвращаем имя представления (страницы), которое будет отображено.
        return "index";
    }

    // Метод для отображения формы добавления новой машины.
    @GetMapping("/showNewCarForm")
    public String showNewCarForm(Model model) {
        // Создаем новый объект Car и добавляем его в модель.
        Car car = new Car();
        model.addAttribute("car", car);
        // Возвращаем имя представления для формы добавления машины.
        return "new_car";
    }

    // Метод для сохранения новой машины в базе данных.
    @PostMapping("/saveCar")
    public String saveCar(@ModelAttribute("car") Car car) {
        // Сохраняем машину через CarService.
        carService.saveCar(car);
        // После сохранения перенаправляем пользователя на главную страницу.
        return "redirect:/";
    }

    // Метод для отображения формы редактирования машины.
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        // Получаем машину по ее ID через CarService.
        Car car = carService.getCarById(id).get();
        // Добавляем машину в модель для передачи на страницу редактирования.
        model.addAttribute("car", car);
        // Возвращаем имя представления для формы редактирования машины.
        return "update_car";
    }

    // Метод для удаления машины.
    @GetMapping("/deleteCar/{id}")
    public String deleteCar(@PathVariable(value = "id") Long id) {
        // Удаляем машину по ее ID через CarService.
        carService.deleteCar(id);
        // После удаления перенаправляем пользователя на главную страницу.
        return "redirect:/";
    }

    // Метод для поиска машин по марке.
    @GetMapping("/search")
    public String searchCars(@RequestParam("keyword") String keyword, Model model) {
        // Ищем машины по ключевому слову (марке) через CarService.
        List<Car> cars = carService.searchByBrand(keyword);
        // Добавляем найденные машины в модель.
        model.addAttribute("cars", cars);
        // Добавляем количество найденных машин в модель.
        model.addAttribute("carCount", cars.size());
        // Возвращаем имя представления для отображения результатов поиска.
        return "index";
    }

    // Метод для поиска машин по владельцу.
    @GetMapping("/searchOwner")
    public String searchByOwner(@RequestParam("keyword") String keyword, Model model) {
        // Ищем машины по имени владельца через CarService.
        List<Car> cars = carService.searchByOwner(keyword);
        // Добавляем найденные машины в модель.
        model.addAttribute("cars", cars);
        // Добавляем количество найденных машин в модель.
        model.addAttribute("carCount", cars.size());
        // Возвращаем имя представления для отображения результатов поиска.
        return "index";
    }

    // Метод для сортировки машин по дате постановки на учет.
    @GetMapping("/sortByRegistrationDate")
    public String sortByRegistrationDate(Model model) {
        // Получаем отсортированный список машин через CarService.
        List<Car> cars = carService.sortByRegistrationDate();
        // Добавляем отсортированные машины в модель.
        model.addAttribute("cars", cars);
        // Добавляем количество машин в модель.
        model.addAttribute("carCount", cars.size());
        // Возвращаем имя представления для отображения отсортированных машин.
        return "index";
    }

    // Метод для перехода на страницу статистики.
    @GetMapping("/statistics")
    public String getStatistics(Model model) {
        // Получаем статистику по машинам через CarService.
        Map<String, Long> statistics = carService.getStatistics();
        // Выводим статистику в консоль (для проверки данных).
        System.out.println(statistics);
        // Добавляем статистику в модель для передачи на страницу.
        model.addAttribute("statistics", statistics);
        // Возвращаем имя представления для отображения статистики.
        return "statistics";
    }
}
