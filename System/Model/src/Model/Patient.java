package Model;

import java.util.ArrayList;

public class Patient {

    private long id;
    private long doctorID;
    private String dateOfBirth;
    private char gender;
    private String name;
    private String phoneNumber;
    private ArrayList<MeasurementsCollection> measurements;

    public Patient(String dateOfBirth, char gender, String name, long doctorID, String phoneNumber) {
        measurements = new ArrayList<>();
        this.id = id;
        this.doctorID = doctorID;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Patient() {
        this.id = 1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public char getChar() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(long doctorID) {
        this.doctorID = doctorID;
    }

    public String toString() {
        return name;
    }

    public void update(Patient patient) {
        this.id = patient.getId();
        this.doctorID = patient.getDoctorID();
        this.dateOfBirth = patient.getDateOfBirth();
        this.gender = patient.getGender();
        this.name = patient.getName();
        this.phoneNumber = patient.getPhoneNumber();
    }

}
