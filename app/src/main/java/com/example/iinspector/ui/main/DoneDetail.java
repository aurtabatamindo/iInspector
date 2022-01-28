package com.example.iinspector.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.iinspector.R;
import com.example.iinspector.Side;
import com.example.iinspector.ui.gallery.GalleryFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoneDetail extends AppCompatActivity {
    String documentClickId;
    //firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //collection templates
    CollectionReference pages = db.collection("templates");
    //Collection hasiltemplates
    CollectionReference df = db.collection("hasiltemplatestes");

    TextView title,description,team,waktu,temperatur,kordinat,lokasi,status,signature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_detail);

        documentClickId = getIntent().getStringExtra("doc");
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        team = findViewById(R.id.team);
        waktu = findViewById(R.id.time);
        temperatur = findViewById(R.id.temperatur);
        kordinat = findViewById(R.id.kordinat);
        lokasi = findViewById(R.id.lokasiInspeksi);
        status = findViewById(R.id.status);
        signature = findViewById(R.id.signature);

        df.document(documentClickId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                String author = (String) task.getResult().get("author");
                String authorId = (String) task.getResult().get("authorId");
                String templateAddress = (String) task.getResult().get("templateAddress");
                String templateDate = (String) task.getResult().get("templateDate");
                String templateDescription = (String) task.getResult().get("templateDescription");
                String templateGroup = (String) task.getResult().get("templateGroup");
                String templateLocation = (String) task.getResult().get("templateLocation");
                String templateTeam = (String) task.getResult().get("templateTeam");
                String templateTemperature = (String) task.getResult().get("templateTemperature");
                String templateTitle = (String) task.getResult().get("templateTitle");
                Log.d("title",templateTitle);

                title.setText("Title Inspeksi : "+"\n"+templateTitle);
                description.setText("Description : "+"\n"+templateDescription);
                team.setText("Team Inspeksi : "+"\n"+templateTeam +" -"+ templateGroup);
                waktu.setText("Waktu Inspeksi : "+"\n"+templateDate);
                temperatur.setText("Temperatur Saat Inspeksi : "+"\n"+templateTemperature);
                kordinat.setText("Kordinat Inspeksi : "+"\n"+templateLocation);
                lokasi.setText("Lokasi Inspeksi : "+"\n"+templateAddress);
                status.setText("Status Inspeksi :"+"\n");
                signature.setText("Signature :"+"\n");

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Side.class);
        startActivity(intent);
    }
}