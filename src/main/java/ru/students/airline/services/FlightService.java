package ru.students.airline.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.students.airline.dto.FlightDto;
import ru.students.airline.dto.FlightRequestDto;
import ru.students.airline.persistence.entity.Flight;
import ru.students.airline.persistence.repository.FlightsRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightsRepo flightsRepo;
    private final ModelMapper mapper;

    public List<FlightDto> getAllFlights() {
        // из sql получаем значения и передаем в дто
        return flightsRepo.findAll().stream()// stream - это удобное представление данных
                .map(flight -> mapper.map(flight, FlightDto.class))// mapper.map - преобразует flight-entity в flight-dto
                .collect(Collectors.toList());
    }

    public List<Flight> saveAll(List<FlightDto> dtos) {
        // обратная фуннкция из dto передаем в sql и сохраняем
        List<Flight> flights = dtos.stream()
                .map(dto -> mapper.map(dto, Flight.class))// mapper.map - преобразует flight-dto в flight-entity
                .collect(Collectors.toList());
        flightsRepo.saveAll(flights);
        return flights;
    }

    public List<FlightDto> getSomeFlights(FlightRequestDto dto) {
        // Найти часть полеты и преобразовать в дто
        LocalDate date = LocalDate.parse(dto.getDate());// нужная дата
        String dCity = dto.getDepartureCity();// города отправления и прибытия
        String aCity = dto.getArrivalCity();
        Integer seats = dto.getNeededSeats();// нужное количество мест
        return flightsRepo.findSomeFlights(date, dCity, aCity, seats)
                .stream()
                .map(flight -> mapper.map(flight, FlightDto.class))
                .collect(Collectors.toList());
    }

}
