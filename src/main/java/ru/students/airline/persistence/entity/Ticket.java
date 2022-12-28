package ru.students.airline.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    // создается в тот момент, когда нажимаем кнопку купить

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "reserved_seats")
    private Integer seats;

    @ManyToOne // много билетов на одну дату
    @JoinColumn(name = "flight_date_id", referencedColumnName = "id")
    private FlightDate flightDate;

    @ManyToOne // много билетов на одного пользователя
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    public Ticket(Integer seats, FlightDate flightDate, User user) {
        this.seats = seats;
        this.flightDate = flightDate;
        this.user = user;
    }
}
