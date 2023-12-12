CREATE TABLE flights
(
    number         varchar(99) PRIMARY KEY,
    departure_city varchar(99),
    arrival_city   varchar(99),
    departure_time time,
    arrival_time   time,
    cost           integer CHECK (cost > 0),
    seats          int CHECK (seats > 0),
    weekdays       varchar(99)
);


CREATE TABLE flight_dates
(
    id             SERIAL PRIMARY KEY,
    date           date,
    flight_number  varchar REFERENCES flights (number) ON DELETE CASCADE,
    reserved_seats int DEFAULT 0
);

CREATE TABLE users
(
    username varchar(25) PRIMARY KEY,
    password varchar(25) NOT NULL,
    role     varchar DEFAULT 'ROLE_USER'
);

CREATE TABLE tickets
(
    id             SERIAL PRIMARY KEY,
    flight_date_id int REFERENCES flight_dates (id) ON DELETE CASCADE,
    username       varchar(25) REFERENCES users (username) ON DELETE CASCADE,
    reserved_seats int
);

CREATE TABLE orders
(
    id                SERIAL PRIMARY KEY,
    need_insurance    boolean NOT NULL,
    need_registration boolean NOT NULL,
    username          varchar(25) REFERENCES users (username) ON DELETE CASCADE,
    flight_number     varchar REFERENCES flights (number) ON DELETE CASCADE,
    flight_date       date NOT NULL
);

CREATE TABLE passengers
(
    id           SERIAL PRIMARY KEY,
    name         varchar(25) NOT NULL,
    surname      varchar(25) NOT NULL,
    birth_date   date NOT NULL,
    passport     varchar(25) NOT NULL,
    need_luggage boolean NOT NULL,
    order_id     int REFERENCES orders (id) ON DELETE CASCADE NOT NULL
);


INSERT INTO users
VALUES ('adm', 'adm', 'ROLE_ADMIN');

INSERT INTO users
VALUES ('ivan', 'ivan');

INSERT INTO flights
VALUES ('A2332', 'Moscow', 'Novosibirsk', '12:03', '13:10', 25000, 32, 'Mon Tue Wed Thu Fri Sat Sun');

INSERT INTO flights
VALUES ('B2332', 'Kemerovo', 'Barnaul', '15:02', '12:10', 30000, 12, 'Mon Wed');

INSERT INTO flights
VALUES ('A2233', 'Chita', 'Ufa', '15:01', '12:30', 11000, 12, 'Sat Sun');

INSERT INTO flight_dates(date, flight_number)
VALUES ('2023-01-10', 'A2332');

INSERT INTO flight_dates(date, flight_number)
VALUES ('2023-01-11', 'A2332');

INSERT INTO flight_dates(date, flight_number)
VALUES ('2023-01-12', 'A2332');

INSERT INTO flight_dates(date, flight_number)
VALUES ('2023-01-13', 'A2332');

INSERT INTO flight_dates(date, flight_number)
VALUES ('2023-01-14', 'A2332');

INSERT INTO flight_dates(date, flight_number)
VALUES ('2023-01-15', 'A2332');

INSERT INTO flight_dates(date, flight_number)
VALUES ('2023-01-16', 'A2332');

INSERT INTO flight_dates(date, flight_number)
VALUES ('2023-01-17', 'A2332');

INSERT INTO flight_dates(date, flight_number)
VALUES ('2023-01-12', 'B2332');

INSERT INTO flight_dates(date, flight_number)
VALUES ('2023-01-17', 'A2233');

INSERT INTO flight_dates(date, flight_number)
VALUES ('2023-01-18', 'A2233');

SELECT *
FROM flights;

SELECT *
FROM flight_dates;
