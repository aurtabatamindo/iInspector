package com.example.iinspector.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iinspector.InspeksiAwal;
import com.example.iinspector.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    //hardCardview
    Context context;
    RecyclerView recyclerView;

    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    String[] subjects = {
            "Inspeksi", "Inspeksi", "Inspeksi", "Inspeksi",
            "Inspeksi", "Inspeksi"
    };



    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    View rootView;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        switch (getArguments().getInt(ARG_SECTION_NUMBER))
        {
            case 1: {
                rootView = inflater.inflate(R.layout.todo, container, false);
                context = getContext();
                recyclerView = rootView.findViewById(R.id.recycler_View);
                recylerViewLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(recylerViewLayoutManager);
                recyclerViewAdapter = new AdapterRecyclerView(context, subjects);
                recyclerView.setAdapter(recyclerViewAdapter);

        //click
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                Intent intent = new Intent(getActivity(), InspeksiAwal.class);
                startActivity(intent);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {


            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

                break;
            }
            case 2: {
                rootView = inflater.inflate(R.layout.done, container, false);
                context = getContext();
                recyclerView = rootView.findViewById(R.id.recycler_Viewdone);
                recylerViewLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(recylerViewLayoutManager);
                recyclerViewAdapter = new AdapterRecyclerViewdone(context, subjects);
                recyclerView.setAdapter(recyclerViewAdapter);
                break;
            }

        }
//        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView = root.findViewById(R.id.section_label);
//                textView.setText(s);
//            }
//        });

//        //hardCardview
//        context = getContext();
//        recyclerView = root.findViewById(R.id.recycler_View);
//        recylerViewLayoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(recylerViewLayoutManager);
//        recyclerViewAdapter = new AdapterRecyclerView(context, subjects);
//        recyclerView.setAdapter(recyclerViewAdapter);
//        //click
//        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                Intent intent = new Intent(getActivity(), InspeksiAwal.class);
//                startActivity(intent);
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });
//
//        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//
//            }
//        });
        return rootView;
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();


    }
}