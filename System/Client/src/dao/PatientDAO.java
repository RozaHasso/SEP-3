package dao;

import Model.Patient;
import javafx.collections.ObservableList;

import java.io.IOException;

public interface PatientDAO {
    ObservableList<Patient> getPatientsByDoctor(long doctorId) throws IOException;

    long create(Patient patient) throws Exception;

    void update(Patient oldPatient, Patient newPatient) throws IOException;

    void delete(Patient patient) throws IOException;
}
