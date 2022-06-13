package com.example.iinspector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.iinspector.ui.slideshow.GetDataTugas;
import com.example.iinspector.ui.slideshow.TugasHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.eazegraph.lib.models.PieModel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ListInduction extends AppCompatActivity {
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter<GetDataInduction, ListInductionHolder> adapterCardInduction;
    String documentClickId;
    FloatingActionButton tambahInduction;
    TextView tgl;
    Button datePicker;
    Date dateformat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_induction);
        final Calendar myCalendar= Calendar.getInstance();

        tgl = findViewById(R.id.tanggal);
        String dateNow = new SimpleDateFormat("M/yyyy", Locale.getDefault()).format(new Date());
        tgl.setText(dateNow);
        datePicker = findViewById(R.id.bTgl);

//        String tglnow = tgl.getText().toString();
//        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
//        try {
//            dateformat = (Date)formatter.parse(tglnow);
//            Log.d("dateformat",String.valueOf(dateformat.getTime()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//
//        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH,month);
//                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
//                updateLabel();
//            }
//            private void updateLabel() {
//                String myFormat="MMMM d, yyyy";
//                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
//                tgl.setText(dateFormat.format(myCalendar.getTime()));
//            }
//
//        };
//
//        datePicker = findViewById(R.id.bTgl);
//        datePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(ListInduction.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthYear();
            }
        });

        tambahInduction = findViewById(R.id.tambahInduction);
        tambahInduction.setBackgroundColor(Color.WHITE);
        tambahInduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tambah = new Intent(ListInduction.this, Induction.class);
                startActivity(tambah);
            }
        });

        recyclerView = findViewById(R.id.listInduction);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = FirebaseFirestore.getInstance()
                .collection("induction")
//                .orderBy("timeDate",Query.Direction.DESCENDING)
                .whereEqualTo("filterBulan",tgl.getText());

        FirestoreRecyclerOptions<GetDataInduction> options = new FirestoreRecyclerOptions.Builder<GetDataInduction>()
                .setQuery(query, GetDataInduction.class)
                .build();

        adapterCardInduction = new FirestoreRecyclerAdapter<GetDataInduction, ListInductionHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ListInductionHolder holder, int position, @NonNull GetDataInduction getDataInduction) {
                holder.setNama((getDataInduction.getNama()));
                holder.setPrusahaan(getDataInduction.getPrusahaan());
                holder.setJumlahPeserta("Jumlah Peserta  : "+ getDataInduction.getJumlahPeserta()+" Orang");
                holder.setStatus(getDataInduction.getStatus());
                holder.setTimedate(getDataInduction.getTimeDate());

                holder.setOnClickListener(new ListInductionHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        documentClickId = getSnapshots().getSnapshot(position).getId();
                        Log.d("getclickdoc", documentClickId);

//                        Intent keIsiTugas = new Intent(ListInduction.this, Induction.class);
//                        keIsiTugas.putExtra("clickedId",documentClickId);
//                        startActivity(keIsiTugas);


                    }
                });

            }

            @NonNull
            @Override
            public ListInductionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_induction, parent, false);
                return new ListInductionHolder(view);
            }
        };
        adapterCardInduction.startListening();
        recyclerView.setAdapter(adapterCardInduction);

    }

    public void monthYear(){
        final Calendar today = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(ListInduction.this,
                new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {

                        // on date set
                        tgl.setText((selectedMonth +1)+ "/" +selectedYear);


                        Query query = FirebaseFirestore.getInstance()
                                .collection("induction")
                                .orderBy("timeDate",Query.Direction.DESCENDING)
                                .whereEqualTo("filterBulan",tgl.getText());

                        FirestoreRecyclerOptions<GetDataInduction> options = new FirestoreRecyclerOptions.Builder<GetDataInduction>()
                                .setQuery(query, GetDataInduction.class)
                                .build();

                        adapterCardInduction = new FirestoreRecyclerAdapter<GetDataInduction, ListInductionHolder>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull ListInductionHolder holder, int position, @NonNull GetDataInduction getDataInduction) {
                                holder.setNama((getDataInduction.getNama()));
                                holder.setPrusahaan(getDataInduction.getPrusahaan());
                                holder.setJumlahPeserta("Jumlah Peserta  : "+ getDataInduction.getJumlahPeserta()+" Orang");
                                holder.setStatus(getDataInduction.getStatus());
                                holder.setTimedate(getDataInduction.getTimeDate());

                                holder.setOnClickListener(new ListInductionHolder.ClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        documentClickId = getSnapshots().getSnapshot(position).getId();
                                        Log.d("getclickdoc", documentClickId);

//                        Intent keIsiTugas = new Intent(ListInduction.this, Induction.class);
//                        keIsiTugas.putExtra("clickedId",documentClickId);
//                        startActivity(keIsiTugas);


                                    }
                                });

                            }

                            @NonNull
                            @Override
                            public ListInductionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_induction, parent, false);
                                return new ListInductionHolder(view);
                            }
                        };
                        adapterCardInduction.startListening();
                        recyclerView.setAdapter(adapterCardInduction);

                    }
                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

        builder.setActivatedMonth(today.get(Calendar.MONTH))
                .setMinYear(1990)
                .setActivatedYear(today.get(Calendar.YEAR))
                .setMaxYear(2030)
                .setTitle("Select month year")
                .build().show();
    }
}