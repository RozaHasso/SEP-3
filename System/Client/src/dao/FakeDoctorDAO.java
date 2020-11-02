package dao;

import Model.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class FakeDoctorDAO implements DoctorDAO {

    private ObservableList<Doctor> doctorObservableList;
    private long currentId = 3;
    private ArrayList<Doctor> doctors = new ArrayList<>();

    public FakeDoctorDAO() {
        Doctor doctor1 = new Doctor("Dr. Manhattan", "1994-12-05", 'M', "05016881", "doctor", "5F4DCC3B5AA765D61D8327DEB882CF99");
        Doctor doctor2 = new Doctor("Dr. Jekyll", "1886-01-05", 'M', "05016881", "jekyll", "5F4DCC3B5AA765D61D8327DEB882CF99");
        Doctor doctor3 = new Doctor("Dr. Jens Jensen", "1971-01-01", 'M', "74030405", "jjs", "5F4DCC3B5AA765D61D8327DEB882CF99");

        doctor1.setId(1);
        doctor2.setId(2);
        doctor3.setId(3);

        doctors.add(doctor1);
        doctors.add(doctor2);
        doctors.add(doctor3);
    }

    @Override
    public ObservableList<Doctor> getAll() {
        doctorObservableList = FXCollections.observableArrayList(doctors);
        return doctorObservableList;
    }

    @Override
    public long create(Doctor doctor) {
        doctor.setId(currentId);
        doctors.add(doctor);
        currentId++;

        return currentId;
    }

    @Override
    public void update(Doctor oldDoctor, Doctor newDoctor) {
        oldDoctor.update(newDoctor);
    }

    @Override
    public void delete(Doctor doctor) {
        doctors.remove(doctor);
    }
}