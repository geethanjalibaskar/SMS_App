package com.example.anjali.sms;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    EditText _txt,  _txtphone, _txtmess;
    Button _btnsend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _txt=(EditText)findViewById(R.id.txt);
        _txtphone=(EditText)findViewById(R.id.txtphone);
        _txtmess=(EditText)findViewById(R.id.txtmess);
        _btnsend=(Button)findViewById(R.id.btnsend);
        _btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Construct data

                    String apiKey = "apikey=" + _txt.getText().toString();
                    String message = "&message=" + _txtmess.getText().toString();

                    String numbers = "&numbers=" + _txtphone.getText().toString();
                    HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
                    String data = apiKey + numbers + message;
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                    conn.getOutputStream().write(data.getBytes("UTF-8"));
                    final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    final StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        //stringBuffer.append(line);
                        Toast.makeText(getApplicationContext(), "The message is "+line, Toast.LENGTH_LONG) .show();
                    }
                    rd.close();

                    //return stringBuffer.toString();
                } catch (Exception e) {
                    //System.out.println("Error SMS "+e);
                    Toast.makeText(getApplicationContext(), "The error message is "+e, Toast.LENGTH_LONG) .show();
                    //return "Error "+e;
                }
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }
}
