package com.example.iinspector.ui.slideshow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iinspector.InspeksiAwal;
import com.example.iinspector.R;
import com.example.iinspector.ui.main.AdapterRecyclerView;
import com.example.iinspector.ui.main.DoneDetail;
import com.example.iinspector.ui.main.DoneHolder;
import com.example.iinspector.ui.main.GetDataDone;
import com.example.iinspector.ui.main.GetDataTodo;
import com.example.iinspector.ui.main.TodoHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

//        //hardCardview
//    RecyclerView.Adapter recyclerViewAdapter;
//    RecyclerView.LayoutManager recylerViewLayoutManager;
//    String[] subjects = {
//            "Inspeksi", "Inspeksi", "Inspeksi", "Inspeksi",
//            "Inspeksi", "Inspeksi"
//    };
//    Context context;
//    RecyclerView recyclerView;

    Button btnlanjut;
    //hardCardview
    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    private View itemView;
    FirestoreRecyclerAdapter<GetDataTugas, TugasHolder> adaptercardTugas;
    int Position;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

//        //hardCardview
//        context = getContext();
//        recyclerView = root.findViewById(R.id.recycler_View);
//        recylerViewLayoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(recylerViewLayoutManager);
//        recyclerViewAdapter = new AdapterRecyclerViewTugas(context, subjects);
//        recyclerView.setAdapter(recyclerViewAdapter);

//        btnlanjut = root.findViewById(R.id.btnlanjut);
//        btnlanjut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), InspeksiAwal.class);
//                startActivity(intent);
//            }
//        });
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


//                context = getContext();
//                recyclerView = rootView.findViewById(R.id.recycler_ViewDone);
//                recylerViewLayoutManager = new LinearLayoutManager(context);
//                recyclerView.setLayoutManager(recylerViewLayoutManager);
//                recyclerViewAdapter = new AdapterRecyclerViewdone(context, subjects);
//                recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView = root.findViewById(R.id.recycler_ViewTugas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query = FirebaseFirestore.getInstance()
                .collection("tugasTemplate")
                .orderBy("titleTugas");

        FirestoreRecyclerOptions<GetDataTugas> options = new FirestoreRecyclerOptions.Builder<GetDataTugas>()
                .setQuery(query, GetDataTugas.class)
                .build();

        adaptercardTugas = new FirestoreRecyclerAdapter<GetDataTugas, TugasHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TugasHolder holder, int position, @NonNull GetDataTugas getDataTugas) {
                holder.setTitle((getDataTugas.getTitleTugas()));
                holder.setDesk(getDataTugas.getDeskripsi());
                holder.setTeam("Untuk : "+ getDataTugas.getTeamTugas());


                holder.setOnClickListener(new TugasHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
//                        documentClickId = getSnapshots().getSnapshot(position).getId();
//                        Log.d("getclickdoc", documentClickId);
//
//                        Intent intent = new Intent(getActivity(), DoneDetail.class);
//                        intent.putExtra("doc",documentClickId);
//                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public TugasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporatugas, parent, false);
                return new TugasHolder(view);
            }
        };
        adaptercardTugas.startListening();
        recyclerView.setAdapter(adaptercardTugas);

        return root;
    }
}