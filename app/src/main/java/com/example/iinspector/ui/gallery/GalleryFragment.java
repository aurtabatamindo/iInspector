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