package com.example.iinspector;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.kyanogen.signatureview.SignatureView;

public class InspeksiKedua extends AppCompatActivity {
    Button kemali, selesai;
    CardView scard1, cardview1, cardView2, cardView6, cardView7, cardView8, scard2, cardView4, cardView5, rya, rno, rya2, rno2, pass, fail;
    TextView ya, no, ya2, no2, tpass, tfail, tambahcatatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeksi_kedua);

        kemali = findViewById(R.id.btnback);
        selesai = findViewById(R.id.btnnext);
        scard1 = findViewById(R.id.scard1);
        cardview1 = findViewById(R.id.cardView1);
        cardView2 = findViewById(R.id.cardView2);
        scard2 = findViewById(R.id.scard2);
        cardView4 = findViewById(R.id.cardView4);
        cardView5 = findViewById(R.id.cardView5);
        cardView6 = findViewById(R.id.cardView6);
        cardView7 = findViewById(R.id.cardView7);
        cardView8 = findViewById(R.id.cardView8);
        rya = findViewById(R.id.rya);
        rno = findViewById(R.id.rno);
        ya = findViewById(R.id.ya);
        no = findViewById(R.id.no);
        rya2 = findViewById(R.id.rya2);
        rno2 = findViewById(R.id.rno2);
        ya2 = findViewById(R.id.ya2);
        no2 = findViewById(R.id.no2);
        pass = findViewById(R.id.pass);
        fail = findViewById(R.id.fail);
        tpass = findViewById(R.id.tpass);
        tfail = findViewById(R.id.tfail);
        tambahcatatan = findViewById(R.id.tambahcatatan);

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
                } else {
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
                    cardView7.setVisibility(View.GONE);
                    cardView8.setVisibility(View.GONE);
                } else {
                    cardView4.setVisibility(View.VISIBLE);
                    cardView5.setVisibility(View.VISIBLE);
                    cardView6.setVisibility(View.VISIBLE);
                    cardView7.setVisibility(View.VISIBLE);
                    cardView8.setVisibility(View.VISIBLE);
                }
            }
        });

        rya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int abucard = getResources().getColor(R.color.abucard);
                if (rya.getCardBackgroundColor() == ColorStateList.valueOf(Color.WHITE)) {
                    rya.setCardBackgroundColor(Color.RED);
                    ya.setTextColor(Color.WHITE);
                } else {
                    rya.setCardBackgroundColor(Color.WHITE);
                    ya.setTextColor(abucard);
                }

            }
        });

        rno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int abucard = getResources().getColor(R.color.abucard);
                if (rno.getCardBackgroundColor() == ColorStateList.valueOf(Color.WHITE)) {
                    rno.setCardBackgroundColor(Color.GREEN);
                    no.setTextColor(Color.WHITE);
                } else {
                    rno.setCardBackgroundColor(Color.WHITE);
                    no.setTextColor(abucard);
                }

            }
        });

        rya2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int abucard = getResources().getColor(R.color.abucard);
                if (rya2.getCardBackgroundColor() == ColorStateList.valueOf(Color.WHITE)) {
                    rya2.setCardBackgroundColor(Color.RED);
                    ya2.setTextColor(Color.WHITE);
                } else {
                    rya2.setCardBackgroundColor(Color.WHITE);
                    ya2.setTextColor(abucard);
                }

            }
        });

        rno2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int abucard = getResources().getColor(R.color.abucard);
                if (rno2.getCardBackgroundColor() == ColorStateList.valueOf(Color.WHITE)) {
                    rno2.setCardBackgroundColor(Color.GREEN);
                    no2.setTextColor(Color.WHITE);
                } else {
                    rno2.setCardBackgroundColor(Color.WHITE);
                    no2.setTextColor(abucard);
                }

            }
        });

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int abucard = getResources().getColor(R.color.abucard);
                if (pass.getCardBackgroundColor() == ColorStateList.valueOf(Color.WHITE)) {
                    pass.setCardBackgroundColor(Color.GREEN);
                    tpass.setTextColor(Color.WHITE);
                } else {
                    pass.setCardBackgroundColor(Color.WHITE);
                    tpass.setTextColor(abucard);
                }

            }
        });

        fail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int abucard = getResources().getColor(R.color.abucard);
                if (fail.getCardBackgroundColor() == ColorStateList.valueOf(Color.WHITE)) {
                    fail.setCardBackgroundColor(Color.RED);
                    tfail.setTextColor(Color.WHITE);
                } else {
                    fail.setCardBackgroundColor(Color.WHITE);
                    tfail.setTextColor(abucard);
                }

            }
        });

        tambahcatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttd();
            }
        });
    }

    void alertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(InspeksiKedua.this);
        alertDialog.setTitle("Tambah Catatan");

        final EditText input = new EditText(InspeksiKedua.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Tambah",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        alertDialog.setNegativeButton("Batal",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    void ttd() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(InspeksiKedua.this);
        alertDialog.setTitle("Tanda Tangan");

        final SignatureView input = new SignatureView(InspeksiKedua.this,null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Selesai",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent selesai = new Intent(InspeksiKedua.this,InspeksiSelesai.class);
                        startActivity(selesai);
                        onBackPressed();
                        finish();
                    }
                });

        alertDialog.setNegativeButton("Batal",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
}