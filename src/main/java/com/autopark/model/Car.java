package com.autopark.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private LocalDate carProduction;

    @Column(nullable = false)
    private LocalDate registrationDate;

    @Column(nullable = false)
    private String owner;

    // Конструкторы
    public Car() {}

    public Car(String _brand, LocalDate _carProduction, LocalDate _registrationDate, String _owner) {
        this.brand = _brand;
        this.carProduction = _carProduction;
        this.registrationDate = _registrationDate;
        this.owner = _owner;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public LocalDate getCarProduction() {
        return carProduction;
    }

    public void setCarProduction(LocalDate carProduction) {
        this.carProduction = carProduction;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
