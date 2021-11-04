package com.example.iinspector.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    // Create the object of TextView and PieChart class
    TextView tvR, tvPython, tvCPP , tgl;
    PieChart pieChart;
    Button bTgl;
    CardView cardsatu,carddua,cardtiga;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
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

        tvR.setText(Integer.toString(40));
        tvPython.setText(Integer.toString(30));
        tvCPP.setText(Integer.toString(10));


        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "R",
                        Integer.parseInt(tvR.getText().toString()),
                        Color.parseColor("#29B6F6")));
        pieChart.addPieSlice(
                new PieModel(
                        "Python",
                        Integer.parseInt(tvPython.getText().toString()),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "C++",
                        Integer.parseInt(tvCPP.getText().toString()),
                        Color.parseColor("#EF5350")));


        // To animate the pie chart
        pieChart.startAnimation();

        bTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TampilTanggal();
            }
        });

        cardsatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsatu = new Intent(getActivity(), InspeksiHasil.class);
                startActivity(detailsatu);
            }
        });

        carddua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detaildua = new Intent(getActivity(), InspeksiHasil.class);
                startActivity(detaildua);
            }
        });
        cardtiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailtiga = new Intent(getActivity(), InspeksiHasil.class);
                startActivity(detailtiga);
            }
        });
        return root;
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