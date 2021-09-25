package com.example.greenfield;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ReestablecerPasswordActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        //select id from tUser telefono = telefono
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reestablecer_password);
        //llamar otro servicio
        // update from tuser where id = idVar password:password
        // me voy al home
    }
}