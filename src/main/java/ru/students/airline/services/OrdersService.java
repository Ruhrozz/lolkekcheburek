package ru.students.airline.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.students.airline.dto.OrderDto;
import ru.students.airline.persistence.entity.Flight;
import ru.students.airline.persistence.entity.Order;
import ru.students.airline.persistence.entity.User;
import ru.students.airline.persistence.repository.FlightsRepo;
import ru.students.airline.persistence.repository.OrdersRepo;
import ru.students.airline.persistence.repository.UsersRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService {
    @Autowired // автоматически заполняет поля, чтобы в ручкую не создавать
    OrdersRepo ordersRepo;

    @Autowired
    PassengerService passengerService;

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FlightsRepo flightsRepo;

    public void createOrder(OrderDto orderDto, String flightNumber) {
        // создаем ордер беря данные с сайта, которые ввел User
        User owner = usersRepo.findByUsername(orderDto.getUsername()).get();// ищем user в БД
        Flight flight = flightsRepo.findFlightByNumber(flightNumber).get();// ищем flight в БД
        LocalDate date = LocalDate.parse(orderDto.getFlightDate()); // задаем дату
        Order order = new Order(owner, orderDto.getNeedInsurance(), orderDto.getNeedRegistration(), flight, date);
        ordersRepo.save(order);// сохранили в БД заказ
        passengerService.saveAll(orderDto.getPassenger(), order);// сохраняем всех пассажиров
    }

    public List<OrderDto> getAllByUser(String username) {
        // берем user на сайте и смотрим его заказы, которые у него есть
        User user = usersRepo.findByUsername(username).get();
        List<Order> orders = ordersRepo.findAllByUser(user);
        return orders.stream().map((order -> modelMapper.map(order, OrderDto.class))).collect(Collectors.toList());
    }

    public OrderDto getOrderById(Long id){
        // Когда хотим посмотреть подробно про какой-то заказ пользователя
        Order order = ordersRepo.findById(id).get();
        return modelMapper.map(order, OrderDto.class);
    }
}
