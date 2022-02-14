package com.example.iinspector.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    FirestoreRecyclerAdapter<GetDataDone, DoneHolder> adaptercardDone;
    int Position;
    String documentId;
    String documentClickId;
    Spinner spinner;
    Query queryTodo;
    Query queryDone;

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

                String fsd = "FSD";
                String wsh = "WSH";
                String semua = "Semua";


                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String spinergroup = spinner.getSelectedItem().toString().trim();
                        Log.d("cekquery",spinergroup);

                        if (spinergroup.equals(semua)){
                            queryTodo = FirebaseFirestore.getInstance()
                                    .collection("templates")
                                    .orderBy("templateTitle");

                        }else if (spinergroup.equals(wsh)){
                            queryTodo = FirebaseFirestore.getInstance()
                                    .collection("templates")
                                    .whereEqualTo("templateGroup",wsh);

                        }else if (spinergroup.equals(fsd)){
                            queryTodo = FirebaseFirestore.getInstance()
                                    .collection("templates")
                                    .whereEqualTo("templateGroup",fsd);

                        }

                        recyclerView = rootView.findViewById(R.id.recycler_ViewTodo);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


                        FirestoreRecyclerOptions<GetDataTodo> options = new FirestoreRecyclerOptions.Builder<GetDataTodo>()
                                .setQuery(queryTodo, GetDataTodo.class)
                                .build();

                        adaptercard = new FirestoreRecyclerAdapter<GetDataTodo, TodoHolder>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull TodoHolder holder, int position, @NonNull GetDataTodo getDataTodo) {
                                holder.setauthorTitle(("Author : "+getDataTodo.getAuthor()));
                                holder.setTgroup(getDataTodo.getTemplateGroup());
                                holder.settemplateDesctiption("Desctiption : "+ getDataTodo.getTemplateDescription());
                                holder.settemplateTitle(getDataTodo.getTemplateTitle());
                                holder.setstatus("Status : "+getDataTodo.getStatus());

                                holder.setOnClickListener(new TodoHolder.ClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                        documentId = getSnapshots().getSnapshot(position).getId();
                                        Position = position;
//                                    if (getDataTodo.getStatus().equals("Sedang Dikerjakan")){
//                                        Snackbar.make(rootView.findViewById(R.id.todoku),"Inspeksi Sedang dikerjakan !",Snackbar.LENGTH_LONG).show();
//                                    }else {
                                        peringatan("Jika form inspeksi telah tampil anda tidak bisa kembali.");
//                                    }


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

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });





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

                String fsd = "FSD";
                String wsh = "WSH";
                String semua = "Semua";

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String spinergroup = spinner.getSelectedItem().toString().trim();
                        Log.d("cekquery",spinergroup);

                        if (spinergroup.equals(semua)){
                            queryDone = FirebaseFirestore.getInstance()
                                    .collection("hasiltemplatestes")
                                    .orderBy("templateTitle");

                        }else if (spinergroup.equals(wsh)){
                            queryDone = FirebaseFirestore.getInstance()
                                    .collection("hasiltemplatestes")
                                    .whereEqualTo("templateGroup",wsh);

                        }else if (spinergroup.equals(fsd)){
                            queryDone = FirebaseFirestore.getInstance()
                                    .collection("hasiltemplatestes")
                                    .whereEqualTo("templateGroup",fsd);

                        }

                        recyclerView = rootView.findViewById(R.id.recycler_ViewDone);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        

                        FirestoreRecyclerOptions<GetDataDone> options = new FirestoreRecyclerOptions.Builder<GetDataDone>()
                                .setQuery(queryDone, GetDataDone.class)
                                .build();

                        adaptercardDone = new FirestoreRecyclerAdapter<GetDataDone, DoneHolder>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull DoneHolder holder, int position, @NonNull GetDataDone getDataDone) {
                                holder.settemplateTeam(("Pelaksana : "+getDataDone.getTemplateTeam()));
                                holder.setTgroup(getDataDone.getTemplateGroup());
                                holder.settemplateAddress("Lokasi : "+ getDataDone.getTemplateAddress());
                                holder.settemplateTitle(getDataDone.getTemplateTitle());
                                holder.setstatus("Status : "+getDataDone.getStatus());

                                holder.setOnClickListener(new DoneHolder.ClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        documentClickId = getSnapshots().getSnapshot(position).getId();
                                        Log.d("getclickdoc", documentClickId);

                                        Intent intent = new Intent(getActivity(),DoneDetail.class);
                                        intent.putExtra("doc",documentClickId);
                                        startActivity(intent);
                                    }
                                });

                            }

                            @NonNull
                            @Override
                            public DoneHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporandone, parent, false);
                                return new DoneHolder(view);
                            }
                        };
                        adaptercardDone.startListening();
                        recyclerView.setAdapter(adaptercardDone);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


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

//                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//                    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
//
//                    rootRef.collection("templates").document(documentId)
//                            .update("status","Sedang Dikerjakan")
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {

                                    Intent intent = new Intent(getActivity(), InspeksiAwal.class);
                                    intent.putExtra("doc",documentId);
                                    startActivity(intent);

//                                }
//                            });


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