package dao;

import Model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class FakePatientDAO implements PatientDAO {

    private ObservableList<Patient> patientObservableList;
    private long currentId = 4;
    private ArrayList<Patient> patients = new ArrayList<>();

    public FakePatientDAO() {
        Patient patient1 = new Patient("1991-05-24", 'M', "John Wick", 1, "65936503");
        Patient patient2 = new Patient("1989-04-12", 'M', "Ethan Hunt", 2, "56112288");
        Patient patient3 = new Patient("1988-02-12", 'M', "James Bond", 2, "562363416");
        Patient patient4 = new Patient("1980-12-12", 'M', "Jason Bourne", 3, "145341656342");

        patient1.setId(1);
        patient2.setId(2);
        patient3.setId(3);
        patient4.setId(4);

        patients.add(patient1);
        patients.add(patient2);
        patients.add(patient3);
        patients.add(patient4);
    }

    @Override
    public ObservableList<Patient> getPatientsByDoctor(long doctorId) {
        ArrayList<Patient> patientsByDoctor = new ArrayList<>();

        for (Patient p : patients) {
            if (p.getDoctorID() == doctorId)
                patientsByDoctor.add(p);
        }

        return FXCollections.observableList(patientsByDoctor);
    }

    @Override
    public long create(Patient patient) {
        patient.setId(currentId);
        patients.add(patient);
        currentId++;
        return currentId;
    }

    @Override
    public void update(Patient oldPatient, Patient newPatient) {
        oldPatient.update(newPatient);
    }

    @Override
    public void delete(Patient patient) {
        patients.remove(patient);
    }
}
