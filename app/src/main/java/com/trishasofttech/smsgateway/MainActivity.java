package com.trishasofttech.smsgateway;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
private Button btn_send;
private EditText et_msg, et_mobile;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_send = findViewById(R.id.btn_send);
        et_mobile = findViewById(R.id.et_mobile);
        et_msg = findViewById(R.id.et_msg);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();
            }
        });


    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    StrictMode.setThreadPolicy(policy);

    }

    private String sendSMS() {
        try {
            // Construct data
            String apiKey = "apikey=" + "EWSnOhRUiiw-7rnnRabShUYhgFg7CCXuFNAECT9N4C";
            String message = "&message=" + et_msg.getText().toString();
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + et_mobile.getText().toString();

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
                Toast.makeText(this, line.toString(), Toast.LENGTH_LONG).show();
            }
            rd.close();

            /*policy = new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build();
            StrictMode.setThreadPolicy(policy);*/

            return stringBuffer.toString();

        } catch (Exception e) {
            Toast.makeText(this, "Error SMS "+e, Toast.LENGTH_LONG).show();
            return "Error "+e;
        }



    }
}