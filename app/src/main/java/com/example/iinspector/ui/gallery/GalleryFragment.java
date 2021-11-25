package com.example.iinspector.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
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

import com.example.iinspector.R;
import com.example.iinspector.ui.main.SectionsPagerAdapter;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    private View itemView;
    private FirestoreRecyclerAdapter<GetDataJadwal, GalleryHolder> adaptercard;

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        root = inflater.inflate(R.layout.fragment_gallery, container, false);

        //tab
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getFragmentManager() );
        ViewPager viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

//        //spiner
//        Spinner spinner = (Spinner) root.findViewById(R.id.sfilter);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.filtergroup, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);


//        recyclerView = root.findViewById(R.id.recycler_Viewgallery);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
//
//        Query query = rootRef.collection("templates")
//                .whereEqualTo("uid",currentUser.getUid());
//
//        FirestoreRecyclerOptions<GetDataJadwal> options = new FirestoreRecyclerOptions.Builder<GetDataJadwal>()
//                .setQuery(query, GetDataJadwal.class)
//                .build();
//
//        adaptercard = new FirestoreRecyclerAdapter<GetDataJadwal, GalleryHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull GalleryHolder holder, int position, @NonNull GetDataJadwal getDataJadwal) {
//                holder.setTitle(getDataJadwal.getTemplateTitle());
//                holder.setTgroup(getDataJadwal.getGroup());
//
//            }
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

    private void Tab() {

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getFragmentManager() );
        ViewPager viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
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

    @Override
    public void onResume() {
        super.onResume();
        Tab();
    }
}