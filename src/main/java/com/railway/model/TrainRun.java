package com.railway.model;

import java.sql.Date;
import java.util.Objects;

public class TrainRun {
    private int runId;
    private int trainId;
    private Date runDate;
    private boolean isTemporary;

    public TrainRun() {
    }

    public TrainRun(int runId, int trainId, Date runDate, boolean isTemporary) {
        this.runId = runId;
        this.trainId = trainId;
        this.runDate = runDate;
        this.isTemporary = isTemporary;
    }

    public int getRunId() { return runId; }
    public void setRunId(int runId) { this.runId = runId; }

    public int getTrainId() { return trainId; }
    public void setTrainId(int trainId) { this.trainId = trainId; }

    public Date getRunDate() { return runDate; }
    public void setRunDate(Date runDate) { this.runDate = runDate; }

    public boolean isTemporary() { return isTemporary; }
    public void setTemporary(boolean temporary) { isTemporary = temporary; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainRun trainRun = (TrainRun) o;
        return runId == trainRun.runId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(runId);
    }
}
