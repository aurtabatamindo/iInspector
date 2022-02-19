package com.example.iinspector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.iinspector.ui.main.DoneDetail;
import com.example.iinspector.ui.main.DoneHolder;
import com.example.iinspector.ui.main.GetDataDone;
import com.example.iinspector.ui.main.GetDataTodo;
import com.example.iinspector.ui.main.TodoHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class InspeksiHasil extends AppCompatActivity {


    String status;
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter<GetDataHasil, HasilHolder> adaptercard;
    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeksi_hasil);

//        //spiner
//        Spinner spinner = (Spinner) findViewById(R.id.sfilter);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(InspeksiHasil.this,
//                R.array.filtergroup, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);

        status = getIntent().getStringExtra("status");
        recyclerView = findViewById(R.id.recycler_ViewDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (status == null){
            query = FirebaseFirestore.getInstance()
                    .collection("inspections")
                    .orderBy("status");
        }else{
            query = FirebaseFirestore.getInstance()
                    .collection("inspections")
                    .whereEqualTo("status",status);
        }


        FirestoreRecyclerOptions<GetDataHasil> options = new FirestoreRecyclerOptions.Builder<GetDataHasil>()
                .setQuery(query, GetDataHasil.class)
                .build();

        adaptercard = new FirestoreRecyclerAdapter<GetDataHasil, HasilHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HasilHolder holder, int position, @NonNull GetDataHasil getDataHasil) {
                holder.settemplateTeam(("Pelaksana : "+getDataHasil.getTemplateTeam()));
                holder.setTgroup(getDataHasil.getTemplateGroup());
                holder.settemplateAddress("Lokasi : "+ getDataHasil.getTemplateAddress());
                holder.settemplateTitle(getDataHasil.getTemplateTitle());
                holder.setstatus("Status : "+getDataHasil.getStatus());


            }

            @NonNull
            @Override
            public HasilHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporandone, parent, false);
                return new HasilHolder(view);
            }
        };
        adaptercard.startListening();
        recyclerView.setAdapter(adaptercard);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent kembali = new Intent(InspeksiHasil.this,Side.class);
        startActivity(kembali);
        finish();
    }
}