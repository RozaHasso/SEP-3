package dao;

import Model.Patient;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPatientDAO implements PatientDAO {

    private String url = "http://localhost:5005/api/patients/";

    @Override
    public ObservableList<Patient> getPatientsByDoctor(long doctorId) throws IOException {
        ObservableList<Patient> patients;
        String line;
        StringBuilder content = new StringBuilder();
        Gson gson = new Gson();
        HttpURLConnection connection = getHttpConnection(url + "doctor/" + doctorId, "GET");
        BufferedReader in = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        while ((line = in.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }

        String json = content.toString();
        Patient[] patientArray = gson.fromJson(json, Patient[].class);
        patients = FXCollections.observableArrayList(patientArray);
        connection.disconnect();
        return patients;
    }

    @Override
    public long create(Patient patient) throws IOException {
        long id = 1;
        HttpURLConnection connection = getHttpConnection(url, "POST");
        Gson gson = new Gson();
        JSONObject jso = new JSONObject(gson.toJson(patient));
        jso.remove("id");
        OutputStream os = connection.getOutputStream();
        os.write(jso.toString().getBytes());
        os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (connection.getInputStream())));
        String output;
        while ((output = br.readLine()) != null) {
            id = Long.valueOf(output);
        }

        connection.disconnect();
        return id;
    }

    public HttpURLConnection getHttpConnection(String url, String type) {
        URL uri = null;
        HttpURLConnection connection = null;
        try {
            uri = new URL(url);
            connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod(type);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(60000);
            connection.setReadTimeout(60000);
            connection.setRequestProperty("Content-Type", "application/json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void delete(Patient patient) throws IOException {
        long id = patient.getId();
        HttpURLConnection connection = getHttpConnection(url + id, "DELETE");
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String temp = null;
        StringBuilder sb = new StringBuilder();
        while ((temp = in.readLine()) != null) {
            sb.append(temp).append(" ");
        }
        String result = sb.toString();
        System.out.println(result);
        in.close();
        connection.disconnect();
    }

    @Override
    public void update(Patient oldPatient, Patient newPatient) throws IOException {
        HttpURLConnection connection = getHttpConnection(url + oldPatient.getId(), "PUT");
        Gson gson = new Gson();
        String json = gson.toJson(newPatient);
        OutputStream os = connection.getOutputStream();
        os.write(json.getBytes());
        os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        String output;
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }

        connection.disconnect();
    }
}
