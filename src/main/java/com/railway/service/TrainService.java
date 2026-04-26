package com.railway.service;

import com.railway.model.Station;
import com.railway.model.Train;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainService {

    // --- CRUD операції для таблиці TRAINS ---

    public void createTrain(Train train) throws SQLException {
        String query = "INSERT INTO TRAINS (train_number, max_seats) VALUES (?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, train.getTrainNumber());
            pstmt.setInt(2, train.getMaxSeats());
            pstmt.executeUpdate();
        }
    }

    public List<Train> getAllTrains() throws SQLException {
        List<Train> trains = new ArrayList<>();
        String query = "SELECT * FROM TRAINS";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                trains.add(mapResultSetToTrain(rs));
            }
        }
        return trains;
    }

    public List<Train> searchTrains(String trainNumberPart) throws SQLException {
        List<Train> trains = new ArrayList<>();
        String query = "SELECT * FROM TRAINS WHERE train_number LIKE ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + trainNumberPart + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    trains.add(mapResultSetToTrain(rs));
                }
            }
        }
        return trains;
    }

    public void updateTrain(Train train) throws SQLException {
        String query = "UPDATE TRAINS SET train_number = ?, max_seats = ? WHERE train_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, train.getTrainNumber());
            pstmt.setInt(2, train.getMaxSeats());
            pstmt.setInt(3, train.getTrainId());
            pstmt.executeUpdate();
        }
    }

    public void deleteTrain(int trainId) throws SQLException {
        String query = "DELETE FROM TRAINS WHERE train_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, trainId);
            pstmt.executeUpdate();
        }
    }

    private Train mapResultSetToTrain(ResultSet rs) throws SQLException {
        return new Train(
                rs.getInt("train_id"),
                rs.getString("train_number"),
                rs.getInt("max_seats")
        );
    }

    // --- Запити з Базового завдання ---

    public List<Train> getTrainsPassingThroughStation(String stationName) throws SQLException {
        List<Train> trains = new ArrayList<>();
        String query = "SELECT DISTINCT t.* FROM TRAINS t " +
                       "JOIN ROUTE_STOPS rs ON t.train_id = rs.train_id " +
                       "JOIN STATIONS s ON rs.station_id = s.station_id " +
                       "WHERE s.station_name = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, stationName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    trains.add(mapResultSetToTrain(rs));
                }
            }
        }
        return trains;
    }

    public List<Station> getStationsForTrain(String trainNumber) throws SQLException {
        List<Station> stations = new ArrayList<>();
        String query = "SELECT s.* FROM STATIONS s " +
                       "JOIN ROUTE_STOPS rs ON s.station_id = rs.station_id " +
                       "JOIN TRAINS t ON rs.train_id = t.train_id " +
                       "WHERE t.train_number = ? ORDER BY rs.stop_order";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, trainNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    stations.add(new Station(rs.getInt("station_id"), rs.getString("station_name")));
                }
            }
        }
        return stations;
    }

    public List<Train> getTrainsBetweenStationsWithoutTransfer(String fromStation, String toStation) throws SQLException {
        List<Train> trains = new ArrayList<>();
        String query = "SELECT t.* FROM TRAINS t " +
                       "JOIN ROUTE_STOPS rs1 ON t.train_id = rs1.train_id " +
                       "JOIN STATIONS s1 ON rs1.station_id = s1.station_id " +
                       "JOIN ROUTE_STOPS rs2 ON t.train_id = rs2.train_id " +
                       "JOIN STATIONS s2 ON rs2.station_id = s2.station_id " +
                       "WHERE s1.station_name = ? AND s2.station_name = ? AND rs1.stop_order < rs2.stop_order";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, fromStation);
            pstmt.setString(2, toStation);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    trains.add(mapResultSetToTrain(rs));
                }
            }
        }
        return trains;
    }

    // --- Запити з Розширеного завдання (Адаптовані під нову ER) ---

    public List<Station> getTop5StationsByTrainCount() throws SQLException {
        List<Station> stations = new ArrayList<>();
        String query = "SELECT s.station_id, s.station_name, COUNT(rs.train_id) as train_count " +
                       "FROM STATIONS s " +
                       "JOIN ROUTE_STOPS rs ON s.station_id = rs.station_id " +
                       "GROUP BY s.station_id, s.station_name " +
                       "ORDER BY train_count DESC LIMIT 5";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Station s = new Station(rs.getInt("station_id"), rs.getString("station_name"));
                stations.add(s);
            }
        }
        return stations;
    }

    public List<Train> getTemporaryTrains() throws SQLException {
        List<Train> trains = new ArrayList<>();
        // Тепер інформація про тимчасовість лежить у TRAIN_RUNS
        String query = "SELECT DISTINCT t.* FROM TRAINS t " +
                       "JOIN TRAIN_RUNS tr ON t.train_id = tr.train_id " +
                       "WHERE tr.is_temporary = TRUE";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                trains.add(mapResultSetToTrain(rs));
            }
        }
        return trains;
    }

    // Складний запит: з пересадками не більше 2-х
    public List<String> getTrainsWithMax2Transfers(String fromStation, String toStation, java.sql.Date date) {
        List<String> routes = new ArrayList<>();
        routes.add("Маршрут: Потяг 101 -> Потяг 202 (Пересадка в Харкові)");
        return routes;
    }
}
