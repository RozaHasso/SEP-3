package Model;

import java.util.ArrayList;

public class Doctor {

    private long id;
    private String name;
    private String dateOfBirth;
    private char gender;
    private String phoneNumber;
    private String username;
    private String password;
    private ArrayList<Patient> patients;

    public Doctor(String name, String dateOfBirth, char gender, String phoneNumber, String username, String password) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
    }

    public Doctor() {
        this.patients = new ArrayList<>();
        this.id = 1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
    }

    public ArrayList<Patient> getPatients() {
        return this.patients;
    }

    public String toString() {
        return this.getName();
    }

    public void update(Doctor doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.dateOfBirth = doctor.getDateOfBirth();
        this.gender = doctor.getGender();
        this.phoneNumber = doctor.getPhoneNumber();
        this.username = doctor.getUsername();
        this.password = doctor.getPassword();
        this.patients = doctor.getPatients();
    }
}
