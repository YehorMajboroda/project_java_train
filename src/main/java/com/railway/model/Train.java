package com.railway.model;

import java.util.Objects;

public class Train {
    private int trainId;
    private String trainNumber;
    private int maxSeats;

    public Train() {
    }

    public Train(int trainId, String trainNumber, int maxSeats) {
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.maxSeats = maxSeats;
    }

    public int getTrainId() { return trainId; }
    public void setTrainId(int trainId) { this.trainId = trainId; }

    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }

    public int getMaxSeats() { return maxSeats; }
    public void setMaxSeats(int maxSeats) { this.maxSeats = maxSeats; }

    @Override
    public String toString() {
        return trainNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return trainId == train.trainId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainId);
    }
}
