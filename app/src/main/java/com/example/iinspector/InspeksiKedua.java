package com.example.iinspector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ScrollView;

public class InspeksiKedua extends AppCompatActivity {
    Button kemali,lanjutkan;
    CardView scard1,cardview1,cardView2,scard2,cardView4,cardView5;
    ScrollView scrollView;
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

        scard1 = findViewById(R.id.scard1);
        cardview1 = findViewById(R.id.cardView1);
        cardView2 = findViewById(R.id.cardView2);
        scard2 = findViewById(R.id.scard2);
        cardView4 = findViewById(R.id.cardView4);
        cardView5 = findViewById(R.id.cardView5);

        scard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cardview1.getVisibility() == View.VISIBLE) {
                    cardview1.setVisibility(View.GONE);
                    cardView2.setVisibility(View.GONE);
                }else{
                    cardview1.setVisibility(View.VISIBLE);
                    cardView2.setVisibility(View.VISIBLE);
                }
            }
        });

        scard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardView4.getVisibility() == View.VISIBLE) {
                    cardView4.setVisibility(View.GONE);
                    cardView5.setVisibility(View.GONE);
                }else{
                    cardView4.setVisibility(View.VISIBLE);
                    cardView5.setVisibility(View.VISIBLE);
                }
            }
        });


    }
}