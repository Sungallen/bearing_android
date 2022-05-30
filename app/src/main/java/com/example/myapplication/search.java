package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class search extends AppCompatActivity {
    TextInputEditText diameterbox, Hbox, Lbox, Abox, Jbox;
    Button back, search;
    TextView model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        back = findViewById(R.id.button3);
        search =findViewById(R.id.button4);
        diameterbox = findViewById(R.id.editText7);
        Hbox = findViewById(R.id.editText);
        Lbox = findViewById(R.id.editText2);
        Abox = findViewById(R.id.editText4);
        Jbox = findViewById(R.id.editText5);
        model = findViewById(R.id.textView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diameter, H, L, A, J;
                diameter = String.valueOf(diameterbox.getText());
                H = String.valueOf(Hbox.getText());
                L = String.valueOf(Lbox.getText());
                A = String.valueOf(Abox.getText());
                J = String.valueOf(Jbox.getText());
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[5];
                        field[0] = "diameter";
                        field[1] = "H";
                        field[2] = "L";
                        field[3] = "A";
                        field[4] = "J";

                        String[] data = new String[5];
                        data[0] = diameter;
                        data[1] = H;
                        data[2] = L;
                        data[3] = A;
                        data[4] = J;
                        PutData putData = new PutData("http://192.168.50.180/bearing/findbearing.php?_ijt=p10t1cokor9aqp5o8tlaimd285&_ij_reload=RELOAD_ON_SAVE", "POST",field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if (result.equals("not found")) {
                                    model.setText("The bearing, you filled, doesn't exist.");
                                } else if (result.equals("db not connected")) {
                                    model.setText("Please check the connection of the database");
                                } else if (result.equals("all field needed")) {
                                    model.setText("Please fill out the form");
                                } else {
                                    model.setText(result);
                                }
                            } else model.setText("not complete");
                        } else model.setText("not start");
                    }
                });
            }
        });
    }
}