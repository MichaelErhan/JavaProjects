package com.barbershop.repository;

import com.barbershop.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BarbershopRepository extends JpaRepository<Record, Long> {

    // Поиск по всем параметрам
    @Query("SELECT r FROM Record r WHERE "
            + "LOWER(r.serviceName) LIKE LOWER(CONCAT('%', :key, '%')) OR "
            + "LOWER(r.fio) LIKE LOWER(CONCAT('%', :key, '%')) OR "
            + "LOWER(r.fioMaster) LIKE LOWER(CONCAT('%', :key, '%'))")
    List<Record> searchByAllParameters(String key);

    long countByRecordingDate(LocalDate recordingDate);
}
