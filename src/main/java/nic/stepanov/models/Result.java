package nic.stepanov.models;

import java.util.List;
import java.util.ArrayList;

public class Result {
    private final String testName;
    private final List<DataPoint> dataPoints;

    public Result(String testName) {
        this.testName = testName;
        this.dataPoints = new ArrayList<>();
    }

    public String getTestName() {
        return testName;
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public void addDataPoint(double x, double y) {
        dataPoints.add(new DataPoint(x, y));
    }
}