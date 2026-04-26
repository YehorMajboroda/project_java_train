package com.railway.model;

import java.sql.Time;

public class RouteStop {
    private int trainId;
    private int stopOrder;
    private int stationId;
    private Time arrivalTime;
    private Time departureTime;
    private float distanceKm;

    public RouteStop() {
    }

    public RouteStop(int trainId, int stopOrder, int stationId, Time arrivalTime, Time departureTime, float distanceKm) {
        this.trainId = trainId;
        this.stopOrder = stopOrder;
        this.stationId = stationId;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.distanceKm = distanceKm;
    }

    public int getTrainId() { return trainId; }
    public void setTrainId(int trainId) { this.trainId = trainId; }

    public int getStopOrder() { return stopOrder; }
    public void setStopOrder(int stopOrder) { this.stopOrder = stopOrder; }

    public int getStationId() { return stationId; }
    public void setStationId(int stationId) { this.stationId = stationId; }

    public Time getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(Time arrivalTime) { this.arrivalTime = arrivalTime; }

    public Time getDepartureTime() { return departureTime; }
    public void setDepartureTime(Time departureTime) { this.departureTime = departureTime; }

    public float getDistanceKm() { return distanceKm; }
    public void setDistanceKm(float distanceKm) { this.distanceKm = distanceKm; }
}
