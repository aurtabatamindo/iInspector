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


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

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
    private View itemView;
    FirestoreRecyclerAdapter<GetDataTodo, TodoHolder> adaptercard;



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


    View rootView;
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container,
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
                recyclerView = rootView.findViewById(R.id.recycler_ViewTodo);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

                Query query = rootRef.collection("templates")
                        .whereEqualTo("uid",currentUser.getUid());

                FirestoreRecyclerOptions<GetDataTodo> options = new FirestoreRecyclerOptions.Builder<GetDataTodo>()
                        .setQuery(query, GetDataTodo.class)
                        .build();

                adaptercard = new FirestoreRecyclerAdapter<GetDataTodo, TodoHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull TodoHolder holder, int position, @NonNull GetDataTodo getDataTodo) {
                        holder.setTitle(getDataTodo.getTemplateTitle());
                        holder.setTgroup(getDataTodo.getGroup());

                    }

                    @NonNull
                    @Override
                    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan, parent, false);
                        return new TodoHolder(view);
                    }
                };
                adaptercard.startListening();
                recyclerView.setAdapter(adaptercard);

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
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}