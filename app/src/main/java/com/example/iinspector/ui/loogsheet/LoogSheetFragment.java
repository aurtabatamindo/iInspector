package com.example.iinspector.ui.loogsheet;

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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.iinspector.Induction;
import com.example.iinspector.InspeksiHasil;
import com.example.iinspector.ListInduction;
import com.example.iinspector.ListToolbox;
import com.example.iinspector.R;
import com.example.iinspector.Toolbox;
import com.example.iinspector.Training;
import com.example.iinspector.ui.home.DatePickerFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LoogSheetFragment extends Fragment {

    private LoogSheetViewModel loogSheetViewModel;
    CardView induction,toolbox,training;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loogSheetViewModel = new ViewModelProvider(this).get(LoogSheetViewModel.class);
        View root = inflater.inflate(R.layout.fragment_loogsheet, container, false);

        induction = root.findViewById(R.id.induction);
        toolbox = root.findViewById(R.id.toolbox);
        training = root.findViewById(R.id.training);

        induction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListInduction.class);
                startActivity(intent);
            }
        });

        toolbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListToolbox.class);
                startActivity(intent);
            }
        });

        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Training.class);
                startActivity(intent);
            }
        });

        return root;
    }

}