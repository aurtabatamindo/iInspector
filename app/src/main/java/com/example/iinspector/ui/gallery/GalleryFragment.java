package com.example.iinspector.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.iinspector.InspeksiAwal;
import com.example.iinspector.R;
import com.example.iinspector.ui.main.SectionsPagerAdapter;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    Context context;
    RecyclerView recyclerView;
    int Position;
    String documentId;
    private FirestoreRecyclerAdapter<GetDataInspeksi, GalleryHolder> adaptercard;

    private View itemView;

//    //hardCardview
//    RecyclerView.Adapter recyclerViewAdapter;
//    RecyclerView.LayoutManager recylerViewLayoutManager;
//    String[] subjects = {
//            "Inspeksi", "Inspeksi", "Inspeksi", "Inspeksi",
//            "Inspeksi", "Inspeksi"
//    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        //tab
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getFragmentManager() );
        ViewPager viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        //spiner
        Spinner spinner = (Spinner) root.findViewById(R.id.sfilter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.filtergroup, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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
//                Intent intent = new Intent(getActivity(),InspeksiAwal.class);
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
//        //cardviewFirebase
//        recyclerView = root.findViewById(R.id.recycler_View);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
//
//        Query query = rootRef.collection("todo")
////                .orderBy("uid");
////                .whereEqualTo("uid",currentUser.getUid());
//                .whereEqualTo("devisi","wsh");
//
//        FirestoreRecyclerOptions<GetDataInspeksi> options = new FirestoreRecyclerOptions.Builder<GetDataInspeksi>()
//                .setQuery(query, GetDataInspeksi.class)
//                .build();
//
//        adaptercard = new FirestoreRecyclerAdapter<GetDataInspeksi, GalleryHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull GalleryHolder holder, int position, @NonNull GetDataInspeksi getDataInspeksi) {
//                holder.setjudul(getDataInspeksi.getJudul());
//                holder.setteam(getDataInspeksi.getTeam());
//                holder.setdevisi(getDataInspeksi.getDevisi());
//                holder.setlokasi(getDataInspeksi.getLokasi());
//                holder.settanggal(getDataInspeksi.getTanggal());
//
////                if (getDataJadwal.getWaktuSelesai() == null){
////                    holder.visible();
////                }else {
////                    holder.visibleoff();
////                }
//
//
////                holder.setOnClickListener(new GalleryHolder.ClickListener() {
////                    @Override
////                    public void onItemClick(View view, int position) {
////                        documentId = getSnapshots().getSnapshot(position).getId();
////                        Position = position;
//////                        showMessageBox("Apakah benar selesai ? ");
////
////
//////                    Toast.makeText(getApplicationContext(),"id : " + documentId ,Toast.LENGTH_LONG).show();
////
////                    }
////                });
//            }
//
//
//            @NonNull
//            @Override
//            public GalleryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan, parent, false);
//                return new GalleryHolder(view);
//            }
//        };
//
//
//        recyclerView.setAdapter(adaptercard);

        return root;
    }

    @Override
    public void onStart() {
//        adaptercard.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
//        if (adaptercard != null) {
//
//            adaptercard.stopListening();
//        }
        super.onStop();
    }
}