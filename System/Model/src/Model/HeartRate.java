package Model;

public class HeartRate extends Measurement {

    private int beatsPerMin;

    public HeartRate(long patientId, String timestamp, int beatsPerMin) {
        super(patientId, timestamp);
        this.beatsPerMin = beatsPerMin;
    }

    public int getBeatsPerMin() {
        return beatsPerMin;
    }


    public boolean isCritical() {
        return beatsPerMin < 60 || beatsPerMin > 100;
    }

    public String toString() {
        return super.getTimestamp() + "	" + beatsPerMin;
    }
}
