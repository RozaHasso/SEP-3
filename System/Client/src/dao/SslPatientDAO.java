package dao;

import Model.Patient;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SslPatientDAO implements PatientDAO {

    private final String uri = "https://localhost:5004/api/patients/";

    private SSLConnectionSocketFactory sslConnectionSocketFactory;

    public SslPatientDAO(SSLConnectionSocketFactory sslConnectionSocketFactory) {
        this.sslConnectionSocketFactory = sslConnectionSocketFactory;
    }

    @Override
    public ObservableList<Patient> getPatientsByDoctor(long doctorId) throws IOException {
        ObservableList<Patient> patients;
        String line;
        Gson gson = new Gson();

        try (CloseableHttpClient client = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build()) {
            HttpGet get = new HttpGet(uri + "doctor/" + doctorId);
            get.setHeader("Content-type", "application/json");

            try (CloseableHttpResponse response = client.execute(get)) {
                HttpEntity entity = response.getEntity();

                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuilder result = new StringBuilder();

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

                String json = result.toString();
                Patient[] patientArray = gson.fromJson(json, Patient[].class);
                patients = FXCollections.observableArrayList(patientArray);

                EntityUtils.consume(entity);
            }

        }

        return patients;
    }

    @Override
    public long create(Patient patient) throws Exception {
        long id;
        Gson gson = new Gson();
        JSONObject jso = new JSONObject(gson.toJson(patient));
        jso.remove("id");

        try (CloseableHttpClient client = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build()) {
            HttpPost post = new HttpPost(uri);
            post.setHeader("Content-type", "application/json");

            HttpEntity entity = new StringEntity(jso.toString());
            post.setEntity(entity);

            try (CloseableHttpResponse response = client.execute(post)) {
                HttpEntity responseEntity = response.getEntity();

                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuilder result = new StringBuilder();

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

                id = Long.valueOf(result.toString());

                EntityUtils.consume(responseEntity);
            }
        }
        return id;
    }

    @Override
    public void delete(Patient patient) throws IOException {

        try (CloseableHttpClient client = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build()) {
            HttpDelete delete = new HttpDelete(uri + patient.getId());
            delete.setHeader("Content-type", "application/json");

            client.execute(delete);
        }
    }

    @Override
    public void update(Patient oldPatient, Patient newPatient) throws IOException {

        try (CloseableHttpClient client = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build()) {
            HttpPut put = new HttpPut(uri + oldPatient.getId());
            put.setHeader("Content-type", "application/json");

            Gson gson = new Gson();
            String json = gson.toJson(newPatient);

            HttpEntity entity = new StringEntity(json);
            put.setEntity(entity);

            String line;

            try (CloseableHttpResponse response = client.execute(put)) {
                HttpEntity responseEntity = response.getEntity();

                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuilder result = new StringBuilder();

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

                EntityUtils.consume(responseEntity);
            }
        }
    }
}
