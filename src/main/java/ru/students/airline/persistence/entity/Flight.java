package ru.students.airline.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity // класс БД. Слой который работает с БД, он соединяет БД и Джаву
@Data // Автоматически генерирует сеттеры и геттеры
@NoArgsConstructor
@Table(name = "flights")
public class Flight {

    @Id // номер помечен так, потому что у нас нет 2 повторяющихся рейсов, номер уникален
    private String number;

    private String departureCity;

    private String arrivalCity;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    private Double cost;

    private Integer seats;

    private String weekdays;

    @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY)
    private List<FlightDate> dates;
}
// OneToMany - Одному рейсу может соответствовать много дат вылетов. Один ко многим
//mappedBy - по какому полю он определяет что эта дата соответствует этому полету.
//FetchType - значит вычисляет лениво, не при каждом обращение к БД, а когда мы это требуем