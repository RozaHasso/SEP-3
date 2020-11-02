package Model;

public class BloodPressure extends Measurement {

    private int systolicPressure;
    private int diastolicPressure;

    public BloodPressure(long patientId, String timestamp, int systolicPressure, int diastolicPressure) {
        super(patientId, timestamp);
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
    }

    public int getSystolicPressure() {
        return systolicPressure;
    }

    public int getDiastolicPressure() {
        return diastolicPressure;
    }


    public boolean isCritical() {
        return systolicPressure < 90 || systolicPressure > 140 || diastolicPressure < 60 || diastolicPressure > 90;
    }

    public String toString() {
        return super.getTimestamp() + "	" + systolicPressure + "/"
                + diastolicPressure;
    }
}
