package controller;

import Model.Doctor;
import Model.Patient;
import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TableView<Doctor> doctorTableView;

    @FXML
    private TableView<Patient> patientTableView;

    @FXML
    private TableColumn<?, ?> doctorID;

    @FXML
    private TableColumn<?, ?> doctorName;

    @FXML
    private TableColumn<?, ?> doctorGender;

    @FXML
    private TableColumn<?, ?> doctorDateOfBirth;

    @FXML
    private TableColumn<?, ?> doctorPhone;

    @FXML
    private TableColumn<?, ?> doctorUsername;

    @FXML
    private TableColumn<?, ?> patientID;

    @FXML
    private TableColumn<?, ?> patientName;

    @FXML
    private TableColumn<?, ?> patientGender;

    @FXML
    private TableColumn<?, ?> patientDateOfBirth;

    @FXML
    private TableColumn<?, ?> patientPhone;

    private ObservableList<Doctor> doctorList;
    private ObservableList<Patient> patientList;
    private Doctor selectedDoctor;
    private Patient selectedPatient;
    private DoctorDAO doctorDAO;
    private PatientDAO patientDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            SSLContext sslcontext;
            SSLConnectionSocketFactory SslSocketFactory;

            sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(new File("Certificates/admin.keystore"), "password".toCharArray(),
                            new TrustSelfSignedStrategy())
                    .build();

            SslSocketFactory = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1.2"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());

            doctorDAO = new SslDoctorDAO(SslSocketFactory);
            doctorList = doctorDAO.getAll();
            patientDAO = new SslPatientDAO(SslSocketFactory);

            doctorTableView.setItems(doctorList);

            doctorTableView.getSelectionModel().selectFirst();
            selectedDoctor = doctorTableView.getSelectionModel().getSelectedItem();

            doctorTableView.setItems(doctorList);

            doctorTableView.getSelectionModel().selectFirst();
            selectedDoctor = doctorTableView.getSelectionModel().getSelectedItem();

            patientList = FXCollections.observableArrayList(patientDAO.getPatientsByDoctor(selectedDoctor.getId()));
            patientTableView.setItems(patientList);

            if (patientList.size() > 0) {
                selectedPatient = patientList.get(0);
                patientTableView.getSelectionModel().selectFirst();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        doctorID.setCellValueFactory(new PropertyValueFactory<>("id"));
        doctorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        doctorPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        doctorGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        doctorDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        doctorUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

        patientID.setCellValueFactory(new PropertyValueFactory<>("id"));
        patientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        patientGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        patientDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        patientPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        doctorTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedDoctor = newValue;
            try {
                patientList = patientDAO.getPatientsByDoctor(selectedDoctor.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            patientTableView.setItems(patientList);
            patientTableView.getSelectionModel().selectFirst();
        });

        patientTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedPatient = newValue);
    }

    public void addDoctorClicked(ActionEvent actionEvent) {
        Dialog<Doctor> dialog = new Dialog<>();
        dialog.setTitle("Add new");
        dialog.setHeaderText("Doctor");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField genderField = new TextField();
        genderField.setPromptText("Gender");
        DatePicker dateOfBirthPicker = new DatePicker(LocalDate.of(1990, 1, 1));
        dateOfBirthPicker.showWeekNumbersProperty().set(false);
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone number");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");

        dialogPane.setContent(new VBox(8, nameField, genderField, dateOfBirthPicker, phoneNumberField, usernameField, passwordField));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                Doctor updatedDoctor = new Doctor(nameField.getText(), dateOfBirthPicker.getValue().toString(), genderField.getText().toUpperCase().charAt(0), phoneNumberField.getText(), usernameField.getText(), passwordField.getText());
                updatedDoctor.setId(selectedDoctor.getId());
                return updatedDoctor;
            }
            return null;
        });
        Optional<Doctor> optionalDoctor = dialog.showAndWait();
        optionalDoctor.ifPresent((Doctor doctor) -> {
            try {
                long id = doctorDAO.create(doctor);
                doctor.setId(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            doctorList.add(doctor);
        });
    }

    public void addPatientClicked(ActionEvent actionEvent) {
        Dialog<Patient> dialog = new Dialog<>();
        dialog.setTitle("Add new");
        dialog.setHeaderText("Patient");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField genderField = new TextField();
        genderField.setPromptText("Gender");
        DatePicker dateOfBirthPicker = new DatePicker(LocalDate.of(1990, 1, 1));
        dateOfBirthPicker.showWeekNumbersProperty().set(false);
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone number");

        dialogPane.setContent(new VBox(8, nameField, genderField, dateOfBirthPicker, phoneNumberField));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new Patient(dateOfBirthPicker.getValue().toString(), genderField.getText().toUpperCase().charAt(0), nameField.getText(), selectedDoctor.getId(), phoneNumberField.getText());
            }
            return null;
        });

        Optional<Patient> optionalPatient = dialog.showAndWait();
        optionalPatient.ifPresent((Patient patient) -> {
            try {
                long id = patientDAO.create(patient);
                patient.setId(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            patientList.add(patient);
        });
    }

    public void editDoctorClicked(ActionEvent actionEvent) throws IOException {
        Dialog<Doctor> dialog = new Dialog<>();
        dialog.setTitle("Edit");
        dialog.setHeaderText("Doctor");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setText(selectedDoctor.getName());
        TextField genderField = new TextField();
        genderField.setPromptText("Gender");
        genderField.setText(Character.toString(selectedDoctor.getGender()));
        DatePicker dateOfBirthPicker = new DatePicker(LocalDate.parse(selectedDoctor.getDateOfBirth()));
        dateOfBirthPicker.showWeekNumbersProperty().set(false);
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone number");
        phoneNumberField.setText(selectedDoctor.getPhoneNumber());
        TextField usernameField = new TextField(selectedDoctor.getUsername());
        usernameField.setPromptText("Username");
        TextField passwordField = new TextField();
        passwordField.setPromptText("New password");

        dialogPane.setContent(new VBox(8, nameField, genderField, dateOfBirthPicker, phoneNumberField, usernameField, passwordField));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new Doctor(nameField.getText(), dateOfBirthPicker.getValue().toString(), genderField.getText().toUpperCase().charAt(0), phoneNumberField.getText(), usernameField.getText(), passwordField.getText());
            }
            return null;
        });
        Optional<Doctor> optionalDoctor = dialog.showAndWait();

        Doctor newDoctor = optionalDoctor.orElse(null);
        if (newDoctor != null) {
            newDoctor.setId(selectedDoctor.getId());
            try {
                doctorDAO.update(selectedDoctor, newDoctor);
            } catch (Exception e) {
                e.printStackTrace();
            }
            selectedDoctor.update(newDoctor);
            doctorTableView.refresh();
        }
    }

    public void editPatientClicked(ActionEvent actionEvent) throws IOException {
        Dialog<Patient> dialog = new Dialog<>();
        dialog.setTitle("Edit");
        dialog.setHeaderText("Patient");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setText(selectedPatient.getName());
        TextField genderField = new TextField();
        genderField.setPromptText("Gender");
        genderField.setText(Character.toString(selectedPatient.getGender()));
        DatePicker dateOfBirthPicker = new DatePicker(LocalDate.parse(selectedPatient.getDateOfBirth()));
        dateOfBirthPicker.showWeekNumbersProperty().set(false);
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone number");
        phoneNumberField.setText(selectedPatient.getPhoneNumber());
        ComboBox<Doctor> doctorsComboBox = new ComboBox<>(doctorList);
        doctorsComboBox.setPromptText("Move to doctor...");

        dialogPane.setContent(new VBox(8, nameField, genderField, dateOfBirthPicker, phoneNumberField, doctorsComboBox));

        Patient oldPatient = selectedPatient;

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                long doctorId;
                if (doctorsComboBox.getValue() == null) {
                    doctorId = selectedPatient.getDoctorID();
                } else {
                    doctorId = doctorsComboBox.getValue().getId();
                    patientList.remove(selectedPatient);
                }

                Patient newPatient = new Patient(dateOfBirthPicker.getValue().toString(), genderField.getText().toUpperCase().charAt(0), nameField.getText(), doctorId, phoneNumberField.getText());
                newPatient.setId(oldPatient.getId());
                return newPatient;
            }
            return null;
        });
        Optional<Patient> optionalPatient = dialog.showAndWait();

        Patient newPatient = optionalPatient.orElse(null);
        if (newPatient != null) {
            patientDAO.update(oldPatient, newPatient);
            oldPatient.update(newPatient);
            patientTableView.refresh();
        }
    }

    public void removeDoctorClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove Doctor");
        alert.setHeaderText("Are you sure?");
        alert.setContentText(selectedDoctor.getId() + ", " + selectedDoctor.getName());

        Optional<ButtonType> result = alert.showAndWait();
        if (selectedDoctor != null && result.isPresent() && result.get() == ButtonType.OK) {
            try {
                doctorDAO.delete(selectedDoctor);
                doctorList.remove(selectedDoctor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removePatientClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove Patient");
        alert.setHeaderText("Are you sure?");
        alert.setContentText(selectedPatient.getId() + ", " + selectedPatient.getName());

        Optional<ButtonType> result = alert.showAndWait();
        if (selectedPatient != null && result.isPresent() && result.get() == ButtonType.OK) {
            try {
                patientDAO.delete(selectedPatient);
                patientList.remove(selectedPatient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}