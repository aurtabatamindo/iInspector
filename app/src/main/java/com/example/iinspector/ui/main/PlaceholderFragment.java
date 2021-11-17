package com.example.iinspector.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iinspector.R;
import com.example.iinspector.ui.gallery.GalleryHolder;
import com.example.iinspector.ui.gallery.GetDataJadwal;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
    private FirestoreRecyclerAdapter<GetDataJadwal, GalleryHolder> adapter;

    //firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("templates");


    String[] subjects = {
            "Inspeksi", "Inspeksi", "Inspeksi", "Inspeksi",
            "Inspeksi", "Inspeksi"
    };

    public PlaceholderFragment() {
    }


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
//                rootView = inflater.inflate(R.layout.todo, container, false);
//                context = getContext();
//                recyclerView = rootView.findViewById(R.id.recycler_View);
//                recylerViewLayoutManager = new LinearLayoutManager(context);
//                recyclerView.setLayoutManager(recylerViewLayoutManager);
//                recyclerViewAdapter = new AdapterRecyclerView(context, subjects);
//                recyclerView.setAdapter(recyclerViewAdapter);

                rootView = inflater.inflate(R.layout.todo, container, false);

//                recyclerView = rootView.findViewById(R.id.recycler_ViewTodo);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
//
//                Query query = rootRef.collection("kegiatan")
////                .orderBy("uid");
//                        .whereEqualTo("uid",currentUser.getUid());
//
//                FirestoreRecyclerOptions<GetDataJadwal> options = new FirestoreRecyclerOptions.Builder<GetDataJadwal>()
//                        .setQuery(query, GetDataJadwal.class)
//                        .build();
//
//                adapter = new FirestoreRecyclerAdapter<GetDataJadwal, GalleryHolder>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull GalleryHolder holder, int position, @NonNull GetDataJadwal getDataJadwal) {
//                        holder.setjudulKegiatan(getDataJadwal.getJudulKegiatan());
//                        holder.setjenisTugas(getDataJadwal.getJenisTugas());
//                        holder.setwaktuMasuk(getDataJadwal.getWaktuMulai());
//                        holder.setstatus(getDataJadwal.getStatus());
//
//
//                    }
//
//                    @NonNull
//                    @Override
//                    public GalleryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
//                        return new GalleryHolder(view);
//                    }
//                };
//
//
//                recyclerView.setAdapter(adapter);
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
        return rootView;
    }




    @Override
    public void onPause() {
        super.onPause();
        adapter.startListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {

            adapter.stopListening();
        }

    }
}