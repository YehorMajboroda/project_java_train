-- ==========================================================
-- СТРУКТУРА БАЗИ ДАНИХ (Apache Derby)
-- Примітка: Derby не підтримує конструкцію "IF NOT EXISTS", 
-- тому якщо таблиці вже існують, ці команди видадуть помилку.
-- ==========================================================

CREATE TABLE TRAINS (
    train_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    train_number VARCHAR(50) NOT NULL UNIQUE,
    max_seats INT NOT NULL
);

CREATE TABLE STATIONS (
    station_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    station_name VARCHAR(100) NOT NULL
);

CREATE TABLE ROUTE_STOPS (
    train_id INT NOT NULL,
    stop_order INT NOT NULL,
    station_id INT NOT NULL,
    arrival_time TIME,
    departure_time TIME,
    distance_km REAL,
    PRIMARY KEY (train_id, stop_order),
    FOREIGN KEY (train_id) REFERENCES TRAINS(train_id) ON DELETE CASCADE,
    FOREIGN KEY (station_id) REFERENCES STATIONS(station_id) ON DELETE CASCADE
);

CREATE TABLE TRAIN_RUNS (
    run_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    train_id INT NOT NULL,
    run_date DATE NOT NULL,
    is_temporary BOOLEAN NOT NULL,
    FOREIGN KEY (train_id) REFERENCES TRAINS(train_id) ON DELETE CASCADE
);

CREATE TABLE TICKETS (
    ticket_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    run_id INT NOT NULL,
    from_station_id INT NOT NULL,
    to_station_id INT NOT NULL,
    seats_sold INT NOT NULL,
    FOREIGN KEY (run_id) REFERENCES TRAIN_RUNS(run_id) ON DELETE CASCADE,
    FOREIGN KEY (from_station_id) REFERENCES STATIONS(station_id) ON DELETE CASCADE,
    FOREIGN KEY (to_station_id) REFERENCES STATIONS(station_id) ON DELETE CASCADE
);
