package com.example.iinspector.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iinspector.InspeksiAwal;
import com.example.iinspector.R;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import javax.mail.Quota;

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

    int Position;
    String documentId;
    Spinner spinner;


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

                rootView = inflater.inflate(R.layout.todo, container, false);

                //spiner
                spinner = (Spinner) rootView.findViewById(R.id.sfilter);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.filtergroup, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                recyclerView = rootView.findViewById(R.id.recycler_ViewTodo);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

//                Query query = rootRef.collection("templates")
//                        .whereEqualTo("uid",currentUser.getUid());

//                String fsd = "FSD";
//                String wsh = "WSH";
//                String semua = "Semua";
//                String spinergroup = spinner.getSelectedItem().toString().trim();


                    Query query = FirebaseFirestore.getInstance()
                            .collection("templates")
                            .orderBy("status");

                    FirestoreRecyclerOptions<GetDataTodo> options = new FirestoreRecyclerOptions.Builder<GetDataTodo>()
                            .setQuery(query, GetDataTodo.class)
                            .build();

                    adaptercard = new FirestoreRecyclerAdapter<GetDataTodo, TodoHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull TodoHolder holder, int position, @NonNull GetDataTodo getDataTodo) {
                            holder.setauthorTitle(("Author : "+getDataTodo.getAuthorTitle()));
                            holder.setTgroup(getDataTodo.getGroup());
                            holder.settemplateDesctiption("Desctiption : "+ getDataTodo.getTemplateDesctiption());
                            holder.settemplateTitle(getDataTodo.getTemplateTitle());
                            holder.setstatus("Status : "+getDataTodo.getStatus());

                            holder.setOnClickListener(new TodoHolder.ClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    documentId = getSnapshots().getSnapshot(position).getId();
                                    Position = position;
                                    if (getDataTodo.getStatus().equals("Sedang Dikerjakan")){
                                        Snackbar.make(rootView.findViewById(R.id.todoku),"Inspeksi Sedang dikerjakan !",Snackbar.LENGTH_LONG).show();
                                    }else {
                                        peringatan("Jika form inspeksi telah tampil anda tidak bisa kembali.");
                                    }


                                }
                            });
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

                //spiner
                spinner = (Spinner) rootView.findViewById(R.id.sfilter);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.filtergroup, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                context = getContext();
                recyclerView = rootView.findViewById(R.id.recycler_ViewDone);
                recylerViewLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(recylerViewLayoutManager);
                recyclerViewAdapter = new AdapterRecyclerViewdone(context, subjects);
                recyclerView.setAdapter(recyclerViewAdapter);
                break;
            }

        }
        return rootView;
    }

    public void peringatan (String message){

            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle("Apakah pilihan inspeksi sudah benar ?");
            alertDialogBuilder.setIcon(R.drawable.status_icon);
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

                    rootRef.collection("templates").document(documentId)
                            .update("status","Sedang Dikerjakan")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Intent intent = new Intent(getActivity(), InspeksiAwal.class);
                                    intent.putExtra("doc",documentId);
                                    startActivity(intent);

                                }
                            });


                }
            });
            alertDialogBuilder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialogBuilder.show();
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