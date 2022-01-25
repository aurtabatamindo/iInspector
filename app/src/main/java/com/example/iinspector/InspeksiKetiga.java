package com.example.iinspector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.iinspector.ui.main.GetDataTodo;
import com.example.iinspector.ui.main.TodoHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.kyanogen.signatureview.SignatureView;

import java.util.ArrayList;
import java.util.Formattable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InspeksiKetiga extends AppCompatActivity {

    //camera
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    //tindakan
    FloatingActionButton fab;
    Toolbar toolbar;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    //firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //collection templates
    CollectionReference pages = db.collection("templates");
    //Collection hasiltemplates
    CollectionReference df = db.collection("hasiltemplatestes");

    //penamaan
    TextView qtitle,berikutnya,jPage,nPage;

    //getIntentString
    String documentId;
    String idtemplate;

    //idPages
    String idPages;

    //idContent
    String idContent;

    //idDocSection
    String idDocSection;

    //answer
    List<EditText> allAnswer = new ArrayList<EditText>();

    //answerSection
    List<EditText> allAnswerSection = new ArrayList<EditText>();



    //linear
    LinearLayoutCompat myLinearLayout;

    //String
    String desc;
    String idAn;
    String idAnSection;
    String idAnSectionBox;
    String parentId;
    String parentIdBox;
    Integer sizeawal;
    String ck;
    String idOpsi;


    //Textview
    TextView Description;
    TextView DescriptionSection;
    TextView Section;


    //Params
    LinearLayoutCompat.LayoutParams params;
    LinearLayoutCompat.LayoutParams params2;
    LinearLayoutCompat.LayoutParams params3;

    DocumentSnapshot lastvisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeksi_ketiga);

        //idDocument
        documentId = getIntent().getStringExtra("doc");
        idtemplate = getIntent().getStringExtra("idtem");

        //judul
        qtitle = findViewById(R.id.qTitile);

        //button berikutnya
        berikutnya = findViewById(R.id.berikutnya);

        //Halaman
        jPage = findViewById(R.id.jPage);
        nPage = findViewById(R.id.nPage);
        halaman();

        //Page1
        showtitle();

        //Button
        buttonberiktunya();



    }

    private void halaman() {
        //getjumlahpage
        pages.document(documentId)
                .collection("pages")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        sizeawal = task.getResult().size();
                        Log.d("sizeawal : ", String.valueOf(sizeawal));
                        jPage.setText(String.valueOf(sizeawal));
                    }
                });
    }

    private void showtitle() {

        pages.document(documentId)
                .collection("pages")
                .limit(1)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //title
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    String title = (String) documentSnapshot.get("pageTitle");
                    qtitle.setText(title);
                    idPages = documentSnapshot.getId();
                    Log.d("testgettask", idPages);

                }
                lastvisible = task.getResult().getDocuments().get(task.getResult().size() - 1);
                showcontent();

            }


        });
    }

    private void showcontent() {
        pages.document(documentId)
                .collection("pages")
                .document(idPages)
                .collection("contents")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        if (document != null && document.exists()) {



                            //get Document
                            Log.d("getdoc",document.getId());

                            //Get Description
                            desc = (String) document.get("description");
                            Log.d("getdes",desc);

                            //Get type
                            String type = (String) document.get("type");
                            Log.d("gettype",type);



                            myLinearLayout = findViewById(R.id.lPertanyaan);


                            params = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                            params.setMargins(30, 20, 30, 20);

                            params2 = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.FILL_PARENT, LinearLayoutCompat.LayoutParams.FILL_PARENT);
                            params2.setMargins(50, 5, 50, 5);

                            params3 = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                            params3.setMargins(10, 20, 10, 20);

                            // Build Description
                            Description = new TextView(InspeksiKetiga.this);
                            Description.setBackgroundResource(R.drawable.cardpertanyaan);
                            Description.setTextSize(11);
                            Description.setPaddingRelative(50, 25, 10, 25);
                            Description.setTypeface(null, Typeface.ITALIC);
                            Description.setTextColor(Color.parseColor("#767676"));
                            Description.setLayoutParams(params3);
                            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.action_icon);
                            Description.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                            Description.setText("Pertanyaan :" + "\n" + desc);

                            //Build Section
                            Section = new TextView(InspeksiKetiga.this);
                            Section.setLayoutParams(params3);
                            Section.setBackgroundResource(R.drawable.cardsection);
                            Section.setTextSize(13);
                            Section.setPaddingRelative(50, 25, 10, 25);
                            Section.setTypeface(null, Typeface.ITALIC);
                            Section.setTextColor(Color.parseColor("#767676"));
                            Drawable img1 = getApplicationContext().getResources().getDrawable(R.drawable.down_icon);
                            Section.setCompoundDrawablesWithIntrinsicBounds(null, null, img1, null);
                            Section.setText(desc);

                            // Type = Text
                            final EditText Answer = new EditText(InspeksiKetiga.this);
                            Answer.setLayoutParams(params);
                            Answer.setTextSize(11);
                            Answer.setHint("Jawab disini");


                            //actionPopup
                            actionPopup();


                            allAnswer.add(Answer);
                            idContent = document.getId();
                            Log.d("getidContent", idContent);


                            if (type.equals("section")){
                                myLinearLayout.addView(Section);
                                idDocSection = document.getId();
                                Log.d("idSection",idDocSection);
                                showContentSection();
                                Log.d("Ini","Section");


                            }else if (type.equals("question")){

                                myLinearLayout.addView(Description);

//                              //get Map typeOfresonse
                                Map maptype = (Map) document.get("typeOfResponse");
                                Log.d("maptype",maptype.toString());

                                //get type in Map typeOfresponse
                                String typeResponse = String.valueOf(maptype.get("type"));
                                Log.d("getTypeResponse", typeResponse);

                                if (typeResponse.equals("checkboxes")){
                                    ArrayList opsi = (ArrayList) maptype.get("options");
                                    Log.d("iniOpsi", opsi.toString());

                                    //MapOpsi
                                    ArrayList<String> mapOpsi = new ArrayList<String>();

                                    for (int i = 0; i < opsi.size(); i++){
                                        // Type = checkboxes
                                        final CheckBox boxOpsi = new CheckBox(InspeksiKetiga.this);
                                        boxOpsi.setLayoutParams(params);
                                        boxOpsi.setTextColor(Color.parseColor("#767676"));
                                        boxOpsi.setBackgroundResource(R.drawable.btn_jawab);
                                        GradientDrawable drawable = (GradientDrawable) boxOpsi.getBackground();
                                        drawable.setColor(Color.WHITE);
                                        boxOpsi.setText(opsi.get(i).toString());

                                        boxOpsi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                if (isChecked){
                                                    idOpsi = document.getId();
                                                    mapOpsi.add(boxOpsi.getText().toString());
                                                    Log.d("opsiAns",mapOpsi.toString());
                                                    //checkboxes update
                                                    pages.document(documentId)
                                                            .collection("pages")
                                                            .document(idPages)
                                                            .collection("contents")
                                                            .document(idOpsi)
                                                            .update("answer", mapOpsi);
                                                }else{
                                                    idOpsi = document.getId();
                                                    mapOpsi.remove(boxOpsi.getText().toString());
                                                    //checkboxes update
                                                    pages.document(documentId)
                                                            .collection("pages")
                                                            .document(idPages)
                                                            .collection("contents")
                                                            .document(idOpsi)
                                                            .update("answer", mapOpsi);
                                                }
                                            }
                                        });
;
                                        myLinearLayout.addView(boxOpsi);
                                    }
                                }else{
                                    Answer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                        @Override
                                        public void onFocusChange(View v, boolean hasFocus) {

                                            if (hasFocus) {
                                                idAn = document.getId();
                                                String parentId = (String) document.get("parentId");
                                                Log.d("fokus","ya");
                                                Log.d("ida",idAn);

                                            } else {
                                                    //Text
                                                    String idaAnswer = Answer.getText().toString();
                                                    pages.document(documentId)
                                                            .collection("pages")
                                                            .document(idPages)
                                                            .collection("contents")
                                                            .document(idAn)
                                                            .update("answer", idaAnswer);


                                                    Log.d("fokus", "tidak");

                                            }
                                        }
                                    });
                                    myLinearLayout.addView(Answer);
                                }
                                Log.d("Ini","Question");
                            }

                        }
                    }

                }
            }




        });
    }

    private void showContentSection() {

        pages.document(documentId)
                .collection("pages")
                .document(idPages)
                .collection("contents")
                .document(idDocSection)
                .collection("contents")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        if (document != null && document.exists()) {



                            //get Document
                            Log.d("getdoc", document.getId());

                            //Get Description
                            String descSection = (String) document.get("description");
                            Log.d("getdes", descSection);

                            //Get type
                            String type = (String) document.get("type");
                            Log.d("gettype",type);

                            //get Map typeOfresonse
                            Map maptype = (Map) document.get("typeOfResponse");
                            Log.d("maptype",maptype.toString());

                            //get type in Map typeOfresponse
                            String typeResponse = String.valueOf(maptype.get("type"));
                            Log.d("getTypeResponse", typeResponse);


                            // Build Description
                            DescriptionSection = new TextView(InspeksiKetiga.this);
                            DescriptionSection.setBackgroundResource(R.drawable.cardpertanyaan);
                            DescriptionSection.setTextSize(11);
                            DescriptionSection.setPaddingRelative(50, 25, 10, 25);
                            DescriptionSection.setTypeface(null, Typeface.ITALIC);
                            DescriptionSection.setTextColor(Color.parseColor("#767676"));
                            DescriptionSection.setLayoutParams(params);
                            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.action_icon);
                            DescriptionSection.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                            DescriptionSection.setText("Pertanyaan :" + "\n" + descSection);

                            // Type = Text
                            final EditText AnswerSection = new EditText(InspeksiKetiga.this);
                            AnswerSection.setLayoutParams(params);
                            AnswerSection.setTextSize(11);
                            AnswerSection.setHint("Jawab disini");


                            myLinearLayout.addView(DescriptionSection);

//                            setContentView(DescriptionSection,params2);
                                if (typeResponse.equals("checkboxes")){
                                    ArrayList opsi = (ArrayList) maptype.get("options");
                                    Log.d("iniOpsi", opsi.toString());

                                    //MapOpsiSection
                                    ArrayList<String> mapOpsiSection = new ArrayList<String>();

                                    for (int i = 0; i < opsi.size(); i++){
                                        // Type = checkboxes
                                        final CheckBox boxOpsi = new CheckBox(InspeksiKetiga.this);
                                        boxOpsi.setLayoutParams(params);
                                        boxOpsi.setTextColor(Color.parseColor("#767676"));
                                        boxOpsi.setBackgroundResource(R.drawable.btn_jawab);
                                        GradientDrawable drawable = (GradientDrawable) boxOpsi.getBackground();
                                        drawable.setColor(Color.WHITE);
                                        boxOpsi.setText(opsi.get(i).toString());

                                        boxOpsi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                                                if (isChecked){
                                                    idAnSectionBox = document.getId();
                                                    parentIdBox = (String) document.get("parentId");
                                                    mapOpsiSection.add(boxOpsi.getText().toString());

                                                    Log.d("getparentIdBox",parentIdBox+" idopsi: "+idAnSectionBox +" answer: "+mapOpsiSection);
                                                    //checkboxes update
                                                    pages.document(documentId)
                                                            .collection("pages")
                                                            .document(idPages)
                                                            .collection("contents")
                                                            .document(parentIdBox)
                                                            .collection("contents")
                                                            .document(idAnSectionBox)
                                                            .update("answer", mapOpsiSection);
                                                }else{
                                                    mapOpsiSection.remove(boxOpsi.getText().toString());
                                                    idAnSectionBox = document.getId();
                                                    //checkboxes update
                                                    pages.document(documentId)
                                                            .collection("pages")
                                                            .document(idPages)
                                                            .collection("contents")
                                                            .document(parentIdBox)
                                                            .collection("contents")
                                                            .document(idAnSectionBox)
                                                            .update("answer", mapOpsiSection);
                                                }
                                            }
                                        });
                                        ;
                                        myLinearLayout.addView(boxOpsi);
                                    }
                                }else{
                                    AnswerSection.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                        @Override
                                        public void onFocusChange(View v, boolean hasFocus) {

                                            if (hasFocus) {

                                                idAnSection = document.getId();
                                                parentId = (String) document.get("parentId");
                                                Log.d("fokus Ya "+"parentId",parentId+ "  idaSection : "+idAnSection);

                                            } else {
                                                //Text
                                                String idaAnswer = AnswerSection.getText().toString();
                                                pages.document(documentId)
                                                        .collection("pages")
                                                        .document(idPages)
                                                        .collection("contents")
                                                        .document(parentId)
                                                        .collection("contents")
                                                        .document(idAnSection)
                                                        .update("answer", idaAnswer);

                                                Log.d("fokus Tidak "+"parentId",parentId+ "  idaSection : "+idAnSection);
                                            }
                                        }
                                    });
                                    myLinearLayout.addView(AnswerSection);
                                }
                            }

                            //actionPopup
                            actionPopupSection();


                        }
                    }
                }

        });

    }

    private void buttonberiktunya() {
        berikutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLinearLayout.removeAllViews();
                pages.document(documentId)
                        .collection("pages")
                        .startAfter(lastvisible)
                        .limit(1)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //title
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            String title = (String) documentSnapshot.get("pageTitle");
                            qtitle.setText(title);
                            idPages = documentSnapshot.getId();
                            Log.d("idclick", idPages+" title "+title);

                        }
                        showcontent();

                    }


                });

                int angkaawal = Integer.parseInt(nPage.getText().toString());
                int tambah = 1;
                int hasil = angkaawal + tambah;
                nPage.setText(String.valueOf(hasil));

                int jsize = Integer.parseInt(jPage.getText().toString());
                int jpage = Integer.parseInt(nPage.getText().toString());

                if (allAnswer.isEmpty()){

                    Snackbar.make(findViewById(R.id.inspeksikedua),"Pertanyaan Belum di isi semua bos !",Snackbar.LENGTH_INDEFINITE)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                }else{
                    if (jpage > jsize) {
                        ttd();
                        nPage.setText(String.valueOf(sizeawal));



                    }
                }


            }


        });
    }
    
    private void actionPopup() {
        //popup menu
        final PopupMenu popupMenu2 = new PopupMenu(InspeksiKetiga.this, Description);
        //add menu items in popup menu
        popupMenu2.getMenu().add(Menu.NONE, 0, 0, "Tambah Catatan"); //parm 2 is menu id, param 3 is position of this menu item in menu items list, param 4 is title of the menu
        popupMenu2.getMenu().add(Menu.NONE, 1, 1, "Tambah Foto");
        popupMenu2.getMenu().add(Menu.NONE, 2, 2, "Tambah Tindakan");

        //handle menu item clicks
        popupMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //get id of the clicked item
                int id = menuItem.getItemId();
                //handle clicks
                if (id == 0) {
                    tambahcatatan();
                    //Copy clicked
                    //set text
                    //selectedTv.setText("Copy clicked");
                } else if (id == 1) {
                    ambilfoto();
                    //Share clicked
                    //set text
                    // selectedTv.setText("Share clicked");
                } else if (id == 2) {
                    tindakan();
                    //Save clicked
                    //set text
                    //selectedTv.setText("Save clicked");
                }

                return false;
            }
        });
        //handle button click, show popup menu
        Description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu2.show();
            }
        });
    }

    private void actionPopupSection() {
        //popup menu
        final PopupMenu popupMenu2 = new PopupMenu(InspeksiKetiga.this, DescriptionSection);
        //add menu items in popup menu
        popupMenu2.getMenu().add(Menu.NONE, 0, 0, "Tambah Catatan"); //parm 2 is menu id, param 3 is position of this menu item in menu items list, param 4 is title of the menu
        popupMenu2.getMenu().add(Menu.NONE, 1, 1, "Tambah Foto");
        popupMenu2.getMenu().add(Menu.NONE, 2, 2, "Tambah Tindakan");

        //handle menu item clicks
        popupMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //get id of the clicked item
                int id = menuItem.getItemId();
                //handle clicks
                if (id == 0) {
                    tambahcatatan();
                    //Copy clicked
                    //set text
                    //selectedTv.setText("Copy clicked");
                } else if (id == 1) {
                    ambilfoto();
                    //Share clicked
                    //set text
                    // selectedTv.setText("Share clicked");
                } else if (id == 2) {
                    tindakan();
                    //Save clicked
                    //set text
                    //selectedTv.setText("Save clicked");
                }

                return false;
            }
        });
        //handle button click, show popup menu
        DescriptionSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu2.show();
            }
        });
    }

    private void tindakan() {
        dialog = new AlertDialog.Builder(InspeksiKetiga.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.tindakan, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Tambah Tindakan");

        dialog.setPositiveButton("Tambah",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dialog.setNegativeButton("Batal",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        dialog.show();
    }

    void tambahcatatan() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(InspeksiKetiga.this);
        alertDialog.setTitle("Tambah Catatan");

        final EditText input = new EditText(InspeksiKetiga.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Tambah",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        alertDialog.setNegativeButton("Batal",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    void ttd() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(InspeksiKetiga.this);
        alertDialog.setTitle("Tanda Tangan");

        final SignatureView input = new SignatureView(InspeksiKetiga.this, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Selesai",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent selesai = new Intent(InspeksiKetiga.this, InspeksiSelesai.class);
                        startActivity(selesai);
                        finish();

                    }
                });

        alertDialog.setNegativeButton("Batal",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    void ambilfoto() {

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            // Show pop up window
            LayoutInflater layoutInflater = LayoutInflater.from(InspeksiKetiga.this);
            View promptView = layoutInflater.inflate(R.layout.hasilfoto, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspeksiKetiga.this);
            alertDialogBuilder.setTitle("Tambah Foto");
            alertDialogBuilder.setView(promptView);
            imageView = (ImageView) promptView.findViewById(R.id.gambar1);
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);


            alertDialogBuilder.setPositiveButton("Tambah",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            alertDialogBuilder.setNegativeButton("Batal",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            alertDialogBuilder.show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            Snackbar.make(findViewById(R.id.inspeksikedua),"Inspeksi sedang berjalan anda tidak bisa kembali sesuka hati !",Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}