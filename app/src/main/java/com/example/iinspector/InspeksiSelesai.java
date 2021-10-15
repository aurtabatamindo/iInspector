package com.example.iinspector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InspeksiSelesai extends AppCompatActivity {

    Button selesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeksi_selesai);

        selesai = findViewById(R.id.btnselesai);
        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selesai = new Intent(InspeksiSelesai.this,Side.class);
                startActivity(selesai);
                onBackPressed();
                finish();
            }
        });
    }
}