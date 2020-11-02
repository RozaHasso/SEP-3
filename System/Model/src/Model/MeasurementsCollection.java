package Model;

import java.util.ArrayList;

public class MeasurementsCollection {

    private ArrayList<Measurement> measurements;

    public MeasurementsCollection() {
        measurements = new ArrayList<Measurement>();
    }

    public ArrayList<Measurement> getMeasurements() {
        return measurements;
    }


    public Measurement getMeasurement(int index) {
        return measurements.get(index);
    }

    public void addMeasurement(Measurement measurement) {
        measurements.add(measurement);
    }
}
