package com.example.iinspector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class InspeksiHasil extends AppCompatActivity {

//    Button btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeksi_hasil);

        //spiner
        Spinner spinner = (Spinner) findViewById(R.id.sfilter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(InspeksiHasil.this,
                R.array.filtergroup, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

//        btnback = findViewById(R.id.btnback);
//
//        btnback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent kembali = new Intent(InspeksiHasil.this, Side.class);
//                startActivity(kembali);
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent kembali = new Intent(InspeksiHasil.this,Side.class);
        startActivity(kembali);
        finish();
    }
}