package ru.students.airline.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flight_dates")
public class FlightDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // сам подбирает ключ чтобы был уникальным
    private Integer id;

    private LocalDate date;

    private Integer reservedSeats = 0;

    @ManyToOne(fetch = FetchType.EAGER) //ManyToOne Многие даты соответствуют одному рейсу
    // FetchType.EAGER - вычисляет всегда, а не только когда обращаются
    @JoinColumn(name = "flight_number", referencedColumnName = "number") // по какому полю он опрелеяет что поля связаны
    private Flight flight;

    @OneToMany(mappedBy = "flightDate", fetch = FetchType.LAZY) // аналогично датам, на одну дату может быть много билетов
    private List<Ticket> tickets;

    public FlightDate(LocalDate date, Flight flight) {
        this.date = date;
        this.flight = flight;
    }
}
