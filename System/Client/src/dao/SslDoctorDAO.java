package dao;

import Model.Doctor;
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
import java.io.InputStreamReader;

public class SslDoctorDAO implements DoctorDAO {

    private final String uri = "https://localhost:5004/api/doctors/";

    private SSLConnectionSocketFactory sslConnectionSocketFactory;

    public SslDoctorDAO(SSLConnectionSocketFactory sslConnectionSocketFactory) {
        this.sslConnectionSocketFactory = sslConnectionSocketFactory;
    }

    public ObservableList<Doctor> getAll() throws Exception {

        ObservableList<Doctor> doctors;
        String line;
        StringBuilder content = new StringBuilder();
        Gson gson = new Gson();

        try (CloseableHttpClient client = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build()) {
            HttpGet get = new HttpGet(uri);
            get.setHeader("Content-type", "application/json");

            line = "";

            try (CloseableHttpResponse response = client.execute(get)) {
                HttpEntity entity = response.getEntity();

                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuilder result = new StringBuilder();

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

                String json = result.toString();
                Doctor[] doctorArray = gson.fromJson(json, Doctor[].class);
                doctors = FXCollections.observableArrayList(doctorArray);


                EntityUtils.consume(entity);
            }
        }
        return doctors;
    }

    @Override
    public long create(Doctor doctor) throws Exception {
        long id;
        Gson gson = new Gson();
        JSONObject jso = new JSONObject(gson.toJson(doctor));
        jso.remove("id");

        try (CloseableHttpClient client = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build()) {
            HttpPost post = new HttpPost(uri);
            post.setHeader("Content-type", "application/json");

            HttpEntity entity = new StringEntity(jso.toString());
            post.setEntity(entity);


            String line = "";

            try (CloseableHttpResponse response = client.execute(post)) {
                HttpEntity responseEntity = response.getEntity();

                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();

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
    public void update(Doctor oldDoctor, Doctor doctor) throws Exception {

        try (CloseableHttpClient client = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build()) {
            HttpPut put = new HttpPut(uri + doctor.getId());
            put.setHeader("Content-type", "application/json");

            Gson gson = new Gson();
            String json = gson.toJson(doctor);

            HttpEntity entity = new StringEntity(json);
            put.setEntity(entity);

            String line = "";

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

    @Override
    public void delete(Doctor doctor) throws Exception {

        try (CloseableHttpClient client = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build()) {
            HttpDelete delete = new HttpDelete(uri + doctor.getId());
            delete.setHeader("Content-type", "application/json");

            client.execute(delete);
        }
    }

}