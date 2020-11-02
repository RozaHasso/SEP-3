package Model;

public class Measurement {

    private String timestamp;
    private long patientId;

    public Measurement(long patientId, String timestamp) {
        this.patientId = patientId;
        this.timestamp = timestamp;
    }

    public Measurement() {

    }

    public String getTimestamp() {
        return timestamp;
    }

}
