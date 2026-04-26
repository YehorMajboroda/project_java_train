package com.railway.model;

import java.util.Objects;

public class Station {
    private int stationId;
    private String stationName;

    public Station() {
    }

    public Station(int stationId, String stationName) {
        this.stationId = stationId;
        this.stationName = stationName;
    }

    public int getStationId() { return stationId; }
    public void setStationId(int stationId) { this.stationId = stationId; }

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }

    @Override
    public String toString() {
        return stationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return stationId == station.stationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationId);
    }
}
