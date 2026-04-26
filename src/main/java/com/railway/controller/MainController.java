package com.railway.controller;

import com.railway.model.Train;
import com.railway.service.TrainService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.util.List;

public class MainController {

    @FXML private TableView<Train> trainTable;
    @FXML private TableColumn<Train, Integer> colId;
    @FXML private TableColumn<Train, String> colNumber;
    @FXML private TableColumn<Train, Integer> colMaxSeats;

    @FXML private TextField txtTrainNumber;
    @FXML private TextField txtMaxSeats;
    
    @FXML private TextField txtSearchNumber;
    @FXML private TextArea txtQueryResult;

    private TrainService trainService;
    private ObservableList<Train> trainList;

    public MainController() {
        trainService = new TrainService();
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("trainId"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("trainNumber"));
        colMaxSeats.setCellValueFactory(new PropertyValueFactory<>("maxSeats"));

        loadTrains();

        trainTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtTrainNumber.setText(newSelection.getTrainNumber());
                txtMaxSeats.setText(String.valueOf(newSelection.getMaxSeats()));
            }
        });
    }

    private void loadTrains() {
        try {
            List<Train> trains = trainService.getAllTrains();
            trainList = FXCollections.observableArrayList(trains);
            trainTable.setItems(trainList);
        } catch (SQLException e) {
            showAlert("Помилка БД", e.getMessage());
        }
    }

    @FXML
    public void handleAdd() {
        try {
            Train t = new Train();
            t.setTrainNumber(txtTrainNumber.getText());
            t.setMaxSeats(Integer.parseInt(txtMaxSeats.getText()));
            
            trainService.createTrain(t);
            loadTrains();
            clearForm();
        } catch (Exception e) {
            showAlert("Помилка", e.getMessage());
        }
    }

    @FXML
    public void handleUpdate() {
        Train selected = trainTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Попередження", "Оберіть потяг для оновлення");
            return;
        }
        try {
            selected.setTrainNumber(txtTrainNumber.getText());
            selected.setMaxSeats(Integer.parseInt(txtMaxSeats.getText()));
            
            trainService.updateTrain(selected);
            loadTrains();
        } catch (Exception e) {
            showAlert("Помилка", e.getMessage());
        }
    }

    @FXML
    public void handleDelete() {
        Train selected = trainTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                trainService.deleteTrain(selected.getTrainId());
                loadTrains();
                clearForm();
            } catch (SQLException e) {
                showAlert("Помилка", e.getMessage());
            }
        }
    }

    @FXML
    public void handleSearch() {
        try {
            String q = txtSearchNumber.getText();
            List<Train> res = trainService.searchTrains(q);
            trainList.setAll(res);
        } catch (SQLException e) {
            showAlert("Помилка БД", e.getMessage());
        }
    }

    @FXML
    public void handleQueryTopStations() {
        try {
            var stations = trainService.getTop5StationsByTrainCount();
            StringBuilder sb = new StringBuilder("Топ 5 станцій:\n");
            stations.forEach(s -> sb.append(s.getStationName()).append("\n"));
            txtQueryResult.setText(sb.toString());
        } catch (SQLException e) {
            txtQueryResult.setText("Помилка: " + e.getMessage());
        }
    }
    
    @FXML
    public void handleQueryTempTrains() {
        try {
            var trains = trainService.getTemporaryTrains();
            StringBuilder sb = new StringBuilder("Тимчасові потяги:\n");
            trains.forEach(t -> sb.append(t.getTrainNumber()).append("\n"));
            txtQueryResult.setText(sb.toString());
        } catch (SQLException e) {
            txtQueryResult.setText("Помилка: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtTrainNumber.clear();
        txtMaxSeats.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
