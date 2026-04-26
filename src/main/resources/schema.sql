-- ==========================================================
-- СТРУКТУРА БАЗИ ДАНИХ (Оновлена ER-діаграма)
-- ==========================================================

CREATE TABLE IF NOT EXISTS TRAINS (
    train_id INT AUTO_INCREMENT PRIMARY KEY,
    train_number VARCHAR(50) NOT NULL UNIQUE,
    max_seats INT NOT NULL
);

CREATE TABLE IF NOT EXISTS STATIONS (
    station_id INT AUTO_INCREMENT PRIMARY KEY,
    station_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS ROUTE_STOPS (
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

CREATE TABLE IF NOT EXISTS TRAIN_RUNS (
    run_id INT AUTO_INCREMENT PRIMARY KEY,
    train_id INT NOT NULL,
    run_date DATE NOT NULL,
    is_temporary BOOLEAN NOT NULL,
    FOREIGN KEY (train_id) REFERENCES TRAINS(train_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS TICKETS (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    run_id INT NOT NULL,
    from_station_id INT NOT NULL,
    to_station_id INT NOT NULL,
    seats_sold INT NOT NULL,
    FOREIGN KEY (run_id) REFERENCES TRAIN_RUNS(run_id) ON DELETE CASCADE,
    FOREIGN KEY (from_station_id) REFERENCES STATIONS(station_id) ON DELETE CASCADE,
    FOREIGN KEY (to_station_id) REFERENCES STATIONS(station_id) ON DELETE CASCADE
);
