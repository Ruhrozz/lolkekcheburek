package ru.students.airline.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.students.airline.dto.TicketDto;
import ru.students.airline.persistence.entity.Flight;
import ru.students.airline.persistence.entity.FlightDate;
import ru.students.airline.persistence.repository.FlightDatesRepo;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.DayOfWeek.*;

@RequiredArgsConstructor
@Service
public class FlightDatesService {
    private final FlightDatesRepo flightDatesRepo;

    private final static Map<String, DayOfWeek> DAYS = Map.of(
            "Пн", MONDAY,
            "Вт", TUESDAY,
            "Ср", WEDNESDAY,
            "Чт", THURSDAY,
            "Пт", FRIDAY,
            "Сб", SATURDAY,
            "Вс", SUNDAY
            // Преобразаует дни недели для БД в удобном формате
    );

    @Transactional
    public FlightDate reserveSeats(TicketDto dto) {
        // находит полеты в БД по данным с сайта, для строки Date.valueOf так потому что на сайте это просто строка
        FlightDate flightDate = flightDatesRepo
                .findFlightDate(dto.getFlightNumber(), Date.valueOf(dto.getDate()))
                .orElseThrow(EntityNotFoundException::new); // так как мы используем optional и если optional пустой, то вернет ошибку
        flightDate.setReservedSeats(flightDate.getReservedSeats() + dto.getSeats());// добавляем забронированные места с сайта
        return flightDate;
    }

    public List<FlightDate> generateFlightDates(List<Flight> flights) {
        // для создания дат, так как изначально их нет
        List<FlightDate> flightDates = new ArrayList<>();
        for (Flight flight : flights) {
            List<DayOfWeek> days =  getDaysList(flight.getWeekdays()); // содержаться дни недели полетов
            LocalDate nextDay = LocalDate.now(); //  например вот так 2022-12-28
            for (int i = 0 ; i < 50; i++) {
                if (days.contains(nextDay.getDayOfWeek()))
                    flightDates.add(new FlightDate(nextDay ,flight));
                nextDay = nextDay.plusDays(1);
            }
        }
        flightDatesRepo.saveAll(flightDates);
        return flightDates;
    }

    private List<DayOfWeek> getDaysList(String weekdays) {
        return Arrays.stream(weekdays.split(" "))
                .map(DAYS::get)
                .collect(Collectors.toList());
    }
}
