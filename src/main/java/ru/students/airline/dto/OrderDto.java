package ru.students.airline.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@RequiredArgsConstructor
public class OrderDto {
    Long id;
    FlightDto flight;
    String flightDate;
    String username;
    List<PassengerDto> passenger;
    Boolean needRegistration;
    Boolean needInsurance;

    // все функции необходимы для подсчета суммы заказа
    public long luggageCount() {
        // считатет кому нужен багаж
        return passenger.stream().filter((x) -> x.needLuggage).count();
    }

    public int insuranceCount() {
        // если нужна страховка, то равно числу пассажиров
        if (needInsurance) {
            return passenger.size();
        } else {
            return 0;
        }
    }

    public int registrationCount() {
        // если нужна авторегистрация, то равно числу пассажиров
        if (needRegistration) {
            return passenger.size();
        } else {
            return 0;
        }
    }
}
