package com.example.iinspector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.iinspector.ui.gallery.GalleryFragment;

public class InspeksiAwal extends AppCompatActivity {

    Button kemali,lanjutkan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeksi_awal);

        kemali = findViewById(R.id.btnback);
        lanjutkan = findViewById(R.id.btnnext);

        kemali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembali = new Intent(InspeksiAwal.this, Side.class);
                startActivity(kembali);
                onBackPressed();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}