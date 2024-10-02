package com.autopark.repository;

import com.autopark.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    // Поиск машины по марке
    List<Car> findByBrandContaining(String brand);

    // Поиск машины по владельцу
    List<Car> findByOwnerContaining(String owner);
}
