package com.barbershop.service;

import com.barbershop.model.Record;
import com.barbershop.repository.BarbershopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class BarbershopService {

    @Autowired
    private BarbershopRepository barbershopRepository;

    // Получить все записи
    public List<Record> getAllRecords() {
        return barbershopRepository.findAll();
    }

    // Найти запись по ID
    public Optional<Record> getRecordById(Long id) {
        return barbershopRepository.findById(id);
    }

    // Сохранить новую запись
    public void saveRecord(Record record) {
        barbershopRepository.save(record);
    }

    // Удалить запись
    public void deleteRecord(Long id) {
        barbershopRepository.deleteById(id);
    }

    // Найти записи по любому параметру
    public List<Record> searchByAllParameters(String key) {
        return barbershopRepository.searchByAllParameters(key);
    }

    // Сортировка записей по дате по возрастанию
    public List<Record> sortByRecordingDateASC() {
        return barbershopRepository.findAll(Sort.by(Sort.Direction.ASC, "recordingDate"));
    }

    // Сортировка записей по дате по убыванию
    public List<Record> sortByRecordingDateDESC() {
        return barbershopRepository.findAll(Sort.by(Sort.Direction.DESC, "recordingDate"));
    }

    // Данные для гистограммы
    public Map<String, Long> getStatistics() {
        return barbershopRepository.findAll()  // Получаем список всех записей
                .stream()  // Преобразуем список в поток (stream)
                // Группируем по дате записи и считаем количество записей для каждой даты
                .collect(Collectors.groupingBy(record -> record.getRecordingDate().toString(), Collectors.counting()));
    }

    // Подсчёт количества клиентов за 1 день
    public long countRecordsByDate(LocalDate date) {
        return barbershopRepository.countByRecordingDate(date);
    }


    public double getAverageClientsPerDay() {
        List<Record> records = getAllRecords();
        if (records.isEmpty()) {
            return 0.0;
        }

        // Группируем записи по дате и считаем количество клиентов за день
        Map<LocalDate, Long> counts = records.stream()
                .collect(Collectors.groupingBy(Record::getRecordingDate, Collectors.counting()));

        // Вычисляем среднее
        double average = counts.values().stream().mapToDouble(Long::doubleValue).average().orElse(0.0);

        // Округляем до двух знаков после запятой
        BigDecimal roundedAverage = BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP);

        return roundedAverage.doubleValue();
    }
}
