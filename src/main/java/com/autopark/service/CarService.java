package com.autopark.service;

import com.autopark.model.Car;
import com.autopark.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    public CarRepository carRepository;

    // Получить все машины
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // Найти машину по ID
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    // Сохранить новую машину
    public void saveCar(Car car) {
        carRepository.save(car);
    }

    // Удалить машину
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    // Найти машины по марке
    public List<Car> searchByBrand(String brand) {
        return carRepository.findByBrandContaining(brand);
    }

    // Найти машины по владельцу
    public List<Car> searchByOwner(String owner) {
        return carRepository.findByOwnerContaining(owner);
    }

    // Сортировка машин по дате постановки на учет
    public List<Car> sortByRegistrationDate() {
        return carRepository.findAll(Sort.by(Sort.Direction.ASC, "registrationDate"));
    }

    // Метод для получения статистики. Возвращает карту, где ключом является дата постановки на учет,
    // а значением — количество машин, зарегистрированных на эту дату.
    public Map<String, Long> getStatistics() {
        // Группировка всех машин по дате постановки на учет и подсчет количества машин для каждой даты.
        return carRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(car -> car.getRegistrationDate().toString(), Collectors.counting()));
    }

}
