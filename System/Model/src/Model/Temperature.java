package Model;

public class Temperature extends Measurement {
    private double temperature;

    public Temperature(long patientId, String timestamp, double temperature) {
        super(patientId, timestamp);
        this.temperature = temperature;
    }


    public double getTemperature() {
        return temperature;
    }

    public boolean isCritical() {
        return temperature < 36.5 || temperature > 37.5;
    }

    public String toString() {
        return super.getTimestamp() + "	" + temperature;
    }
}
