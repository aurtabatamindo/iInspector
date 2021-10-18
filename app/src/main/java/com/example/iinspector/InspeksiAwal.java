package com.example.iinspector;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iinspector.ui.gallery.GalleryFragment;

public class InspeksiAwal extends AppCompatActivity {

    Button kemali,lanjutkan;
    TextView tambah1,tambah2,tambah3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeksi_awal);

        kemali = findViewById(R.id.btnback);
        lanjutkan = findViewById(R.id.btnnext);
        tambah1 = findViewById(R.id.tambah1);
        tambah2 = findViewById(R.id.tambah2);
        tambah3 = findViewById(R.id.tambah3);

        kemali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembali = new Intent(InspeksiAwal.this, Side.class);
                startActivity(kembali);
                onBackPressed();
                finish();
            }
        });

        lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lanjut = new Intent(InspeksiAwal.this, InspeksiKedua.class);
                startActivity(lanjut);
                onBackPressed();
            }
        });

        tambah1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahcatatan();
            }
        });
        tambah2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahcatatan();
            }
        });
        tambah3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahcatatan();
            }
        });
    }
    void tambahcatatan() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(InspeksiAwal.this);
        alertDialog.setTitle("Tambah Catatan");

        final EditText input = new EditText(InspeksiAwal.this);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}