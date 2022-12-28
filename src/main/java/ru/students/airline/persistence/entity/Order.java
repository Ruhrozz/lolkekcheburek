package ru.students.airline.persistence.entity;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@RequiredArgsConstructor
public class Order {
    // страница с заказом
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // почему long?
    @ManyToOne // Так как один пользователь может иметь много заказов
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    @ManyToOne
    @JoinColumn(name = "flight_number", referencedColumnName = "number")
    private Flight flight;
    private Boolean needRegistration;// авторегистрация на рейс
    private Boolean needInsurance;// страховка на рейс

    private LocalDate flightDate;// просто дата вылета

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY) // к одному заказу может быть привязано много пассажиров,
    // а уже заказ привязан к пользователю, с которого мы вошли в систему
    private List<Passenger> passengerList;

    public Order(User user, Boolean needInsurance, Boolean needRegistration, Flight flight, LocalDate date) {
        this.user = user;
        this.needInsurance = needInsurance;
        this.needRegistration = needRegistration;
        this.flight = flight;
        this.flightDate = date;
    }
}
