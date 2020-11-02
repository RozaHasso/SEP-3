package dao;

import Model.Doctor;
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

public class HttpDoctorDAO implements DoctorDAO {

    private String url = "http://localhost:5005/api/doctors/";

    public HttpDoctorDAO() {
    }

    @Override
    public ObservableList<Doctor> getAll() throws IOException {
        ObservableList<Doctor> doctors;
        String line;
        StringBuilder content = new StringBuilder();
        Gson gson = new Gson();
        HttpURLConnection connection = getHttpConnection(url, "GET");
        BufferedReader in = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        while ((line = in.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }

        String json = content.toString();
        Doctor[] doctorArray = gson.fromJson(json, Doctor[].class);
        doctors = FXCollections.observableArrayList(doctorArray);
        connection.disconnect();
        return doctors;
    }

    @Override
    public long create(Doctor doctor) throws IOException {
        long id = 1;
        HttpURLConnection connection = getHttpConnection(url, "POST");
        Gson gson = new Gson();
        JSONObject jso = new JSONObject(gson.toJson(doctor));
        jso.remove("id");
        OutputStream os = connection.getOutputStream();
        os.write(jso.toString().getBytes());
        os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        String output;
        while ((output = br.readLine()) != null) {
            id = Long.valueOf(output);
        }

        connection.disconnect();
        return id;
    }

    @Override
    public void update(Doctor oldDoctor, Doctor newDoctor) throws IOException {
        HttpURLConnection connection = getHttpConnection(url + oldDoctor.getId(), "PUT");
        Gson gson = new Gson();
        String json = gson.toJson(newDoctor);
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

    @Override
    public void delete(Doctor doctor) throws IOException {
        long id = doctor.getId();
        HttpURLConnection connection = getHttpConnection(url + id, "DELETE");
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String temp;
        StringBuilder sb = new StringBuilder();
        while ((temp = in.readLine()) != null) {
            sb.append(temp).append(" ");
        }
        String result = sb.toString();
        System.out.println(result);
        in.close();
        connection.disconnect();
    }

    public HttpURLConnection getHttpConnection(String url, String type) {
        URL uri;
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

}