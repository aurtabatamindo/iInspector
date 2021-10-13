package com.example.iinspector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InspeksiKedua extends AppCompatActivity {
    Button kemali,lanjutkan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeksi_kedua);

        kemali = findViewById(R.id.btnback);
        lanjutkan = findViewById(R.id.btnnext);

        kemali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembali = new Intent(InspeksiKedua.this, InspeksiAwal.class);
                startActivity(kembali);
                onBackPressed();
                finish();
            }
        });

    }
}