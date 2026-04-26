package com.railway.model;

import java.util.Objects;

public class Ticket {
    private int ticketId;
    private int runId;
    private int fromStationId;
    private int toStationId;
    private int seatsSold;

    public Ticket() {
    }

    public Ticket(int ticketId, int runId, int fromStationId, int toStationId, int seatsSold) {
        this.ticketId = ticketId;
        this.runId = runId;
        this.fromStationId = fromStationId;
        this.toStationId = toStationId;
        this.seatsSold = seatsSold;
    }

    public int getTicketId() { return ticketId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }

    public int getRunId() { return runId; }
    public void setRunId(int runId) { this.runId = runId; }

    public int getFromStationId() { return fromStationId; }
    public void setFromStationId(int fromStationId) { this.fromStationId = fromStationId; }

    public int getToStationId() { return toStationId; }
    public void setToStationId(int toStationId) { this.toStationId = toStationId; }

    public int getSeatsSold() { return seatsSold; }
    public void setSeatsSold(int seatsSold) { this.seatsSold = seatsSold; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return ticketId == ticket.ticketId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId);
    }
}
