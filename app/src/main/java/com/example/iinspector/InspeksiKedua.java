package com.example.iinspector;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class InspeksiKedua extends AppCompatActivity {
    Button kemali,lanjutkan;
    CardView scard1,cardview1,cardView2,cardView6,scard2,cardView4,cardView5,rya,rno,rya2,rno2;
    TextView ya,no,ya2,no2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeksi_kedua);

        kemali = findViewById(R.id.btnback);
        lanjutkan = findViewById(R.id.btnnext);
        scard1 = findViewById(R.id.scard1);
        cardview1 = findViewById(R.id.cardView1);
        cardView2 = findViewById(R.id.cardView2);
        scard2 = findViewById(R.id.scard2);
        cardView4 = findViewById(R.id.cardView4);
        cardView5 = findViewById(R.id.cardView5);
        cardView6 = findViewById(R.id.cardView6);
        rya = findViewById(R.id.rya);
        rno = findViewById(R.id.rno);
        ya = findViewById(R.id.ya);
        no = findViewById(R.id.no);
        rya2 = findViewById(R.id.rya2);
        rno2 = findViewById(R.id.rno2);
        ya2 = findViewById(R.id.ya2);
        no2 = findViewById(R.id.no2);

        kemali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembali = new Intent(InspeksiKedua.this, InspeksiAwal.class);
                startActivity(kembali);
                onBackPressed();
                finish();
            }
        });



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
                    cardView6.setVisibility(View.GONE);
                }else{
                    cardView4.setVisibility(View.VISIBLE);
                    cardView5.setVisibility(View.VISIBLE);
                    cardView6.setVisibility(View.VISIBLE);
                }
            }
        });

        rya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int abucard = getResources().getColor(R.color.abucard);
                if (rya.getCardBackgroundColor() == ColorStateList.valueOf(Color.WHITE)){
                    rya.setCardBackgroundColor(Color.RED);
                    ya.setTextColor(Color.WHITE);
                }else {
                    rya.setCardBackgroundColor(Color.WHITE);
                    ya.setTextColor(abucard);
                }

            }
        });

        rno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int abucard = getResources().getColor(R.color.abucard);
                if (rno.getCardBackgroundColor() == ColorStateList.valueOf(Color.WHITE)){
                    rno.setCardBackgroundColor(Color.GREEN);
                    no.setTextColor(Color.WHITE);
                }else {
                    rno.setCardBackgroundColor(Color.WHITE);
                    no.setTextColor(abucard);
                }

            }
        });

        rya2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int abucard = getResources().getColor(R.color.abucard);
                if (rya2.getCardBackgroundColor() == ColorStateList.valueOf(Color.WHITE)){
                    rya2.setCardBackgroundColor(Color.RED);
                    ya2.setTextColor(Color.WHITE);
                }else {
                    rya2.setCardBackgroundColor(Color.WHITE);
                    ya2.setTextColor(abucard);
                }

            }
        });

        rno2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int abucard = getResources().getColor(R.color.abucard);
                if (rno2.getCardBackgroundColor() == ColorStateList.valueOf(Color.WHITE)){
                    rno2.setCardBackgroundColor(Color.GREEN);
                    no2.setTextColor(Color.WHITE);
                }else {
                    rno2.setCardBackgroundColor(Color.WHITE);
                    no2.setTextColor(abucard);
                }

            }
        });
    }
}