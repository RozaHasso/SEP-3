package dao;

import Model.Doctor;
import javafx.collections.ObservableList;

public interface DoctorDAO {
    ObservableList<Doctor> getAll() throws Exception;

    long create(Doctor doctor) throws Exception;

    void update(Doctor oldDoctor, Doctor newDoctor) throws Exception;

    void delete(Doctor doctor) throws Exception;
}
