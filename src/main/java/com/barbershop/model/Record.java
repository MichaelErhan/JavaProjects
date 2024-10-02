package com.barbershop.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "record")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fio;

    @Column(nullable = false)
    private LocalDate recordingDate;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private String fioMaster;

    // Конструкторы
    public Record() {}

    public Record(String fio, LocalDate recordingDate, String serviceName, String fioMaster) {
        this.fio = fio;
        this.recordingDate = recordingDate;
        this.serviceName = serviceName;
        this.fioMaster = fioMaster;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio){
        this.fio = fio;
    }

    public LocalDate getRecordingDate() {
        return recordingDate;
    }

    public void setRecordingDate(LocalDate recordingDate){
        this.recordingDate = recordingDate;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName){
        this.serviceName = serviceName;
    }

    public String getFioMaster() {
        return fioMaster;
    }

    public void setFioMaster(String fioMaster){
        this.fioMaster = fioMaster;
    }
}
