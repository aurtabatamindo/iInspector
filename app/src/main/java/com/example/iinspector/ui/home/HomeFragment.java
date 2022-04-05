package com.example.iinspector.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.iinspector.InspeksiHasil;
import com.example.iinspector.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    // Create the object of TextView and PieChart class
    TextView tvR, tvPython, tvCPP , tgl,persenAll,persenAman,persenTidakAman;
    TextView allnaik,allturun,amanNaik,amanTurun,tidakamanNaik,tidakamanTurun;

    PieChart pieChart;
    Button bTgl;
    CardView cardsatu,carddua,cardtiga;

    Integer allInspeksi;
    Integer allAman;
    Integer allTidakAman;

    //firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //Collection hasiltemplates
    CollectionReference df = db.collection("inspections");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        tvR = root.findViewById(R.id.tvR);
        tvPython = root.findViewById(R.id.tvPython);
        tvCPP = root.findViewById(R.id.tvCPP);
        pieChart = root.findViewById(R.id.piechart);
        bTgl= root.findViewById(R.id.bTgl);
        tgl = root.findViewById(R.id.tanggal);
        cardsatu = root.findViewById(R.id.cardsatu);
        carddua = root.findViewById(R.id.carddua);
        cardtiga = root.findViewById(R.id.cardtiga);
        persenAll = root.findViewById(R.id.persenAll);
        persenAman = root.findViewById(R.id.persenAman);
        persenTidakAman = root.findViewById(R.id.persenTidakAman);

        allnaik = root.findViewById(R.id.allnaik);
        allturun = root.findViewById(R.id.allTurun);
        amanNaik = root.findViewById(R.id.amanNaik);
        amanTurun = root.findViewById(R.id.amanTurun);
        tidakamanNaik = root.findViewById(R.id.tidakamanNaik);
        tidakamanTurun = root.findViewById(R.id.tidakamanTurun);

        String month = new SimpleDateFormat("M/yyyy", Locale.getDefault()).format(new Date());
        tgl.setText(month);

        //allInspeksi
        df.whereEqualTo("templateMonth",tgl.getText())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                allInspeksi = task.getResult().size();
                tvR.setText(String.valueOf(allInspeksi));
                pieChart.addPieSlice(
                        new PieModel(
                                "R",
                                Integer.parseInt(tvR.getText().toString()),
                                Color.parseColor("#29B6F6")));

                int jAkhir = task.getResult().size();

                int bulanKurang = Integer.parseInt(month.substring(0,1)) -1;
                String kurang = bulanKurang +"/"+ month.substring(2,6);
                Log.d("limitstring",kurang);

                df.whereEqualTo("templateMonth",kurang)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int jAwal = task.getResult().size();
                        int jkurang = jAkhir - jAwal;
                        int jkurangAwal = jkurang - jAwal;
                        int jumlah = jkurangAwal * 100;

                        if (jumlah < 0) {
                            persenAll.setText(jumlah + "%" + " Turun 30 hari terakhir");
                            persenAll.setTextColor(Color.LTGRAY);
                            allnaik.setVisibility(View.INVISIBLE);
                            allturun.setVisibility(View.VISIBLE);

                        }else if (jumlah == 0){
                            persenAll.setText(jumlah + "%" +" Tidak ada data 30 hari terakhir");
                            persenAll.setTextColor(Color.LTGRAY);
                            allnaik.setVisibility(View.VISIBLE);
                            allturun.setVisibility(View.VISIBLE);
                        }else{
                            persenAll.setText(jumlah + "%" + " Naik 30 hari terakhir");
                            persenAll.setTextColor(Color.LTGRAY);
                            allnaik.setVisibility(View.VISIBLE);
                            allturun.setVisibility(View.INVISIBLE);
                        }

                        Log.d("getjumlah",String.valueOf(jumlah));

                    }
                });
            }
        });

        //inspeksiAman
        df.whereEqualTo("templateMonth",tgl.getText())
                .whereEqualTo("status","Pass")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                allAman = task.getResult().size();
                tvPython.setText(String.valueOf(allAman));

                pieChart.addPieSlice(
                        new PieModel(
                                "Python",
                                Integer.parseInt(tvPython.getText().toString()),
                                Color.parseColor("#66BB6A")));

                int jAkhir = task.getResult().size();

                int bulanKurang = Integer.parseInt(month.substring(0,1)) -1;
                String kurang = bulanKurang +"/"+ month.substring(2,6);
                Log.d("limitstring",kurang);

                df.whereEqualTo("templateMonth",kurang)
                        .whereEqualTo("status","Pass")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int jAwal = task.getResult().size();
                        int jkurang = jAkhir - jAwal;
                        int jkurangAwal = jkurang - jAwal;
                        int jumlah = jkurangAwal * 100;

                        if (jumlah < 0) {
                            persenAman.setText(jumlah + "%" + " Turun 30 hari terakhir");
                            persenAman.setTextColor(Color.LTGRAY);
                            amanNaik.setVisibility(View.INVISIBLE);
                            amanTurun.setVisibility(View.VISIBLE);

                        }else if (jumlah == 0){
                            persenAman.setText(jumlah + "%" +" Tidak ada data 30 hari terakhir");
                            persenAman.setTextColor(Color.LTGRAY);
                            amanNaik.setVisibility(View.VISIBLE);
                            amanTurun.setVisibility(View.VISIBLE);
                        }else{
                            persenAman.setText(jumlah + "%" + " Naik 30 hari terakhir");
                            persenAman.setTextColor(Color.LTGRAY);
                            amanNaik.setVisibility(View.VISIBLE);
                            amanTurun.setVisibility(View.INVISIBLE);
                        }

                    }
                });

            }
        });

        //inspeksiTidakAman
        df.whereEqualTo("templateMonth",tgl.getText())
                .whereEqualTo("status","Fail close")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                allTidakAman = task.getResult().size();
                tvCPP.setText(String.valueOf(allTidakAman));

                pieChart.addPieSlice(
                        new PieModel(
                                "C++",
                                Integer.parseInt(tvCPP.getText().toString()),
                                Color.parseColor("#EF5350")));

                int jAkhir = task.getResult().size();

                int bulanKurang = Integer.parseInt(month.substring(0,1)) -1;
                String kurang = bulanKurang +"/"+ month.substring(2,6);
                Log.d("limitstring",kurang);

                df.whereEqualTo("status","Fail close")
                        .whereEqualTo("templateMonth",kurang)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int jAwal = task.getResult().size();
                        int jkurang = jAkhir - jAwal;
                        int jkurangAwal = jkurang - jAwal;
                        int jumlah = jkurangAwal * 100;

                        if (jumlah < 0) {
                            persenTidakAman.setText(jumlah + "%" + " Turun 30 hari terakhir");
                            persenTidakAman.setTextColor(Color.LTGRAY);
                            tidakamanNaik.setVisibility(View.INVISIBLE);
                            tidakamanTurun.setVisibility(View.VISIBLE);

                        }else if (jumlah == 0){
                            persenTidakAman.setText(jumlah + "%" +" Tidak ada data 30 hari terakhir");
                            persenTidakAman.setTextColor(Color.LTGRAY);
                            tidakamanNaik.setVisibility(View.VISIBLE);
                            tidakamanTurun.setVisibility(View.VISIBLE);
                        }else{
                            persenTidakAman.setText(jumlah + "%" + " Naik 30 hari terakhir");
                            persenTidakAman.setTextColor(Color.LTGRAY);
                            tidakamanNaik.setVisibility(View.VISIBLE);
                            tidakamanTurun.setVisibility(View.INVISIBLE);
                        }

                        Log.d("getjumlah",String.valueOf(jumlah));

                    }
                });

            }
        });


        // To animate the pie chart
        pieChart.startAnimation();

        bTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TampilTanggal();
                monthYear();
            }
        });

        cardsatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsatu = new Intent(getActivity(), InspeksiHasil.class);
                detailsatu.putExtra("month",tgl.getText());
                startActivity(detailsatu);
            }
        });

        carddua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detaildua = new Intent(getActivity(), InspeksiHasil.class);
                detaildua.putExtra("month",tgl.getText());
                detaildua.putExtra("status", "Pass");
                startActivity(detaildua);
            }
        });
        cardtiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailtiga = new Intent(getActivity(), InspeksiHasil.class);
                detailtiga.putExtra("month",tgl.getText());
                detailtiga.putExtra("status", "Fail close");
                startActivity(detailtiga);
            }
        });
        return root;
    }
    public void monthYear(){
    final Calendar today = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(),
                new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        pieChart.clearChart();
                        // on date set
                        tgl.setText((selectedMonth +1)+ "/" +selectedYear);

                        String month = tgl.getText().toString();

                        //all
                        df.whereEqualTo("templateMonth",tgl.getText())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        allInspeksi = task.getResult().size();
                                        tvR.setText(String.valueOf(allInspeksi));

                                        pieChart.addPieSlice(
                                                new PieModel(
                                                        "R",
                                                        Integer.parseInt(tvR.getText().toString()),
                                                        Color.parseColor("#29B6F6")));

                                        int jAkhir = task.getResult().size();

                                        int bulanKurang = Integer.parseInt(month.substring(0,1)) -1;
                                        String kurang = bulanKurang +"/"+ month.substring(2,6);
                                        Log.d("limitstring",kurang);

                                        df.whereEqualTo("templateMonth",kurang)
                                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                int jAwal = task.getResult().size();
                                                int jkurang = jAkhir - jAwal;
                                                int jkurangAwal = jkurang - jAwal;
                                                int jumlah = jkurangAwal * 100;

                                                if (jumlah < 0) {
                                                    persenAll.setText(jumlah + "%" + " Turun 30 hari terakhir");
                                                    persenAll.setTextColor(Color.LTGRAY);
                                                    allnaik.setVisibility(View.INVISIBLE);
                                                    allturun.setVisibility(View.VISIBLE);

                                                }else if (jumlah == 0){
                                                    persenAll.setText(jumlah + "%" +" Tidak ada data 30 hari terakhir");
                                                    persenAll.setTextColor(Color.LTGRAY);
                                                    allnaik.setVisibility(View.VISIBLE);
                                                    allturun.setVisibility(View.VISIBLE);
                                                }else{
                                                    persenAll.setText(jumlah + "%" + " Naik 30 hari terakhir");
                                                    persenAll.setTextColor(Color.LTGRAY);
                                                    allnaik.setVisibility(View.VISIBLE);
                                                    allturun.setVisibility(View.INVISIBLE);
                                                }

                                            }
                                        });

                                    }
                                });
                        //inspeksiAman
                        df.whereEqualTo("status","Pass")
                                .whereEqualTo("templateMonth",tgl.getText())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        allAman = task.getResult().size();
                                        tvPython.setText(String.valueOf(allAman));


                                        pieChart.addPieSlice(
                                                new PieModel(
                                                        "Python",
                                                        Integer.parseInt(tvPython.getText().toString()),
                                                        Color.parseColor("#66BB6A")));

                                        int jAkhir = task.getResult().size();

                                        int bulanKurang = Integer.parseInt(month.substring(0,1)) -1;
                                        String kurang = bulanKurang +"/"+ month.substring(2,6);
                                        Log.d("limitstring",kurang);

                                        df.whereEqualTo("status","Pass")
                                                .whereEqualTo("templateMonth",kurang)
                                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                int jAwal = task.getResult().size();
                                                int jkurang = jAkhir - jAwal;
                                                int jkurangAwal = jkurang - jAwal;
                                                int jumlah = jkurangAwal * 100;

                                                if (jumlah < 0) {
                                                    persenAman.setText(jumlah + "%" + " Turun 30 hari terakhir");
                                                    persenAman.setTextColor(Color.LTGRAY);
                                                    amanNaik.setVisibility(View.INVISIBLE);
                                                    amanTurun.setVisibility(View.VISIBLE);

                                                }else if (jumlah == 0){
                                                    persenAman.setText(jumlah + "%" +" Tidak ada data 30 hari terakhir");
                                                    persenAman.setTextColor(Color.LTGRAY);
                                                    amanNaik.setVisibility(View.VISIBLE);
                                                    amanTurun.setVisibility(View.VISIBLE);
                                                }else{
                                                    persenAman.setText(jumlah + "%" + " Naik 30 hari terakhir");
                                                    persenAman.setTextColor(Color.LTGRAY);
                                                    amanNaik.setVisibility(View.VISIBLE);
                                                    amanTurun.setVisibility(View.INVISIBLE);
                                                }

                                            }
                                        });

                                    }
                                });

                        //inspeksiTidakAman
                        df.whereEqualTo("status","Fail close")
                                .whereEqualTo("templateMonth",tgl.getText())
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                allTidakAman = task.getResult().size();
                                tvCPP.setText(String.valueOf(allTidakAman));


                                pieChart.addPieSlice(
                                        new PieModel(
                                                "C++",
                                                Integer.parseInt(tvCPP.getText().toString()),
                                                Color.parseColor("#EF5350")));

                                int jAkhir = task.getResult().size();

                                int bulanKurang = Integer.parseInt(month.substring(0,1)) -1;
                                String kurang = bulanKurang +"/"+ month.substring(2,6);
                                Log.d("limitstring",kurang);

                                df.whereEqualTo("status","Fail close")
                                        .whereEqualTo("templateMonth",kurang)
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        int jAwal = task.getResult().size();
                                        int jkurang = jAkhir - jAwal;
                                        int jkurangAwal = jkurang - jAwal;
                                        int jumlah = jkurangAwal * 100;

                                        if (jumlah < 0) {
                                            persenTidakAman.setText(jumlah + "%" + " Turun 30 hari terakhir");
                                            persenTidakAman.setTextColor(Color.LTGRAY);
                                            tidakamanNaik.setVisibility(View.INVISIBLE);
                                            tidakamanTurun.setVisibility(View.VISIBLE);

                                        }else if (jumlah == 0){
                                            persenTidakAman.setText(jumlah + "%" +" Tidak ada data 30 hari terakhir");
                                            persenTidakAman.setTextColor(Color.LTGRAY);
                                            tidakamanNaik.setVisibility(View.VISIBLE);
                                            tidakamanTurun.setVisibility(View.VISIBLE);
                                        }else{
                                            persenTidakAman.setText(jumlah + "%" + " Naik 30 hari terakhir");
                                            persenTidakAman.setTextColor(Color.LTGRAY);
                                            tidakamanNaik.setVisibility(View.VISIBLE);
                                            tidakamanTurun.setVisibility(View.INVISIBLE);
                                        }

                                    }
                                });
                            }
                        });
                        pieChart.startAnimation();
                        }
                    }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

        builder.setActivatedMonth(today.get(Calendar.MONTH))
                .setMinYear(1990)
                .setActivatedYear(today.get(Calendar.YEAR))
                .setMaxYear(2030)
                .setTitle("Select month year")
                .build().show();
    }

    public void TampilTanggal(){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getFragmentManager(), "data");
        datePickerFragment.setOnDateClickListener(new DatePickerFragment.onDateClickListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String tahun = ""+datePicker.getYear();
                String bulan = ""+(datePicker.getMonth()+1);
                String hari = ""+datePicker.getDayOfMonth();
                String text = ""+hari+"/"+bulan+"/"+tahun;
                tgl.setText(text);
            }
        });
    }

    

}