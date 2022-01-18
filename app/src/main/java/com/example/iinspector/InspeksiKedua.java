package com.example.iinspector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kyanogen.signatureview.SignatureView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InspeksiKedua extends AppCompatActivity {

    Button  selesai;
//    CardView scard1, cardview1, cardView2, cardView6, cardView7, cardView8, scard2, cardView4, cardView5, rya, rno, rya2, rno2, pass, fail;
//    TextView ya, no, ya2, no2, tpass, tfail, tambah1, tambah2, tambah3, tambah4, tambah5, foto1, foto2, foto3, foto4, foto5,tindakan1,tindakan2,tindakan3,tindakan4,tindakan5;
    CardView scard1,scard2 ,cardview1, cardView2;
    TextView qTitle,qDes,berikutnya,jPage,nPage;
    DocumentSnapshot lastvisible;
//    //Recyclerview
//    RecyclerView qrecyclerview;
//    RecyclerView.Adapter qrecyclerViewAdapter;
//    RecyclerView.LayoutManager qrecylerViewLayoutManager;
//    FirestoreRecyclerAdapter<GetDataQuestion, QuestionHolder> qadaptercard;


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

    //recyclerview
    private RecyclerView recyclerView;
    private QuestionAdapter adapter;
    private ArrayList<GetDataQuestion> questionArrayList;

//    //linear
//    LinearLayoutCompat myLinearLayout;

    int sizeawal;
    int sizeakhir;

    //list
    List<EditText> allEds = new ArrayList<EditText>();

    //string
    String idDocUpdate;

    //realtime
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reff = database.getReference();
    DatabaseReference dref = database.getReference().child("template");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeksi_kedua);

        String documentId = getIntent().getStringExtra("doc");
        String idtemplate = getIntent().getStringExtra("idtem");

        scard1 = findViewById(R.id.scard1);
        scard2 = findViewById(R.id.scard2);
        qTitle = findViewById(R.id.qTitile);
        berikutnya = findViewById(R.id.berikutnya);
        jPage = findViewById(R.id.jPage);
        nPage = findViewById(R.id.nPage);

//        qDes = findViewById(R.id.qDescr);


        //getjumlahpage
         pages.document(documentId).collection("pages").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        sizeawal = task.getResult().size();
                        Log.d("sizeawal : ", String.valueOf(sizeawal));
                        jPage.setText(String.valueOf(sizeawal));
                    }
                });



            //pagepertama
          pages.document(documentId)
                  .collection("pages")
                  .limit(1)
                  .get()
                  .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {

                  for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                      String title = (String) documentSnapshot.get("pageTitle");
                      qTitle.setText(title);
                      Log.d("testgettask",task.toString());
                  }



                  if (task.isSuccessful()) {
                      // looping templates
                      for (QueryDocumentSnapshot document : task.getResult()) {

                          if (document != null && document.exists()) {


                              ArrayList<Map> list = (ArrayList<Map>) document.get("contents");

                              //cobapush
                              DocumentReference ref = db.collection("hasiltemplatestes")
                                      .document(idtemplate)
                                      .collection("pages")
                                      .document();

                              idDocUpdate = ref.getId();
                              Log.d("iddocupdate",idDocUpdate);

                              Map<String, Object> map = new HashMap<>();
                              map.put("contents", list);
                              Log.d("hasilmap",map.toString());



                              db.collection("hasiltemplatestes").document(idtemplate)
                                              .collection("pages")
                                              .document(idDocUpdate)
                                              .set(map);

//                              //realtimefirebase
//                              DatabaseReference dRef = reff.child("template");
//                              dRef.setValue(map);

                              //updaterealtime
                              final Map<String, Object> answer = new HashMap<>();
                              answer.put("isFailed", "null");
                              answer.put("text", "null");

//                              reff.child("template/contents/0/answer").updateChildren(answer);
//                              dref.addListenerForSingleValueEvent(new ValueEventListener() {
//                                  @Override
//                                  public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                      Log.d("getrealtime",snapshot.toString());
//
//                                      db.collection("hasiltemplatestes").document(idtemplate)
//                                              .collection("pages")
//                                              .document(idDocUpdate)
//                                              .set(snapshot.getValue());
//                                  }
//
//                                  @Override
//                                  public void onCancelled(@NonNull DatabaseError error) {
//
//                                  }
//                              });


//                              db.collection("hasiltemplatestes").document(idtemplate)
//                                      .collection("pages").document(idDocUpdate)
//                                      .update("contents",FieldValue.arrayUnion("isFailed"));

//                              //gethasiltemplate
//                              df.document(idtemplate)
//                                       .collection("pages")
//                                       .document(idDocUpdate)
////                                       .whereArrayContains("contentId","45b83309-1acb-478e-ac59-6c5478e7323a")
//                                       .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                  @Override
//                                  public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                      if (task.isSuccessful()) {
//
//                                          DocumentSnapshot document = task.getResult();
//                                          if (task != null && document.exists()) {
//                                              ArrayList<Map> list2 = (ArrayList<Map>) document.get("contents");
//                                              Map<String, Object> map2 = new HashMap<>();
//                                              map2.put("contents", list2);
//                                              Log.d("hasilmap2",map2.toString());
//
//
//                                          }
//                                      }
//                                  }
//                              });

                              int ukuranArray = list.size();
                              for (int i = 0; i < ukuranArray; i++) {

                                  String deskripsi = list.get(i).get("description").toString();

                                  LinearLayoutCompat myLinearLayout = findViewById(R.id.lPertanyaan);


                                  LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                                  params.setMargins(30, 20, 30, 20);

                                  LinearLayoutCompat.LayoutParams params2 = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.FILL_PARENT, LinearLayoutCompat.LayoutParams.FILL_PARENT);
                                  params2.setMargins(50, 5, 50, 5);

                                  LinearLayoutCompat.LayoutParams params3 = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                                  params3.setMargins(10, 20, 10, 20);

                                  final TextView rowTextView = new TextView(InspeksiKedua.this);
                                  rowTextView.setBackgroundResource(R.drawable.cardpertanyaan);
                                  rowTextView.setTextSize(11);
                                  rowTextView.setPaddingRelative(50, 25, 10, 25);
                                  rowTextView.setTypeface(null, Typeface.ITALIC);
                                  rowTextView.setTextColor(Color.parseColor("#767676"));
                                  rowTextView.setLayoutParams(params);
                                  Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.action_icon);
                                  rowTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);

                                  //popup menu
                                  final PopupMenu popupMenu = new PopupMenu(InspeksiKedua.this, rowTextView);
                                  //add menu items in popup menu
                                  popupMenu.getMenu().add(Menu.NONE, 0, 0, "Tambah Catatan"); //parm 2 is menu id, param 3 is position of this menu item in menu items list, param 4 is title of the menu
                                  popupMenu.getMenu().add(Menu.NONE, 1, 1, "Tambah Foto");
                                  popupMenu.getMenu().add(Menu.NONE, 2, 2, "Tambah Tindakan");

                                  //handle menu item clicks
                                  popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                      @Override
                                      public boolean onMenuItemClick(MenuItem menuItem) {
                                          //get id of the clicked item
                                          int id = menuItem.getItemId();
                                          //handle clicks
                                          if (id == 0) {
                                              tambahcatatan();
                                              //Copy clicked
                                              //set text
//                                        selectedTv.setText("Copy clicked");
                                          } else if (id == 1) {
                                              ambilfoto();
                                              //Share clicked
                                              //set text
//                                        selectedTv.setText("Share clicked");
                                          } else if (id == 2) {
                                              tindakan();
                                              //Save clicked
                                              //set text
//                                        selectedTv.setText("Save clicked");
                                          }

                                          return false;
                                      }
                                  });

                                  //handle button click, show popup menu
                                  rowTextView.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          popupMenu.show();
                                      }
                                  });
                                  //type = section
                                  final TextView rowTextviewS = new TextView(InspeksiKedua.this);
                                  rowTextviewS.setLayoutParams(params3);
                                  rowTextviewS.setBackgroundResource(R.drawable.cardsection);
                                  rowTextviewS.setTextSize(12);
                                  rowTextviewS.setPaddingRelative(50, 25, 10, 25);
                                  rowTextviewS.setTypeface(null, Typeface.ITALIC);
                                  rowTextviewS.setTextColor(Color.parseColor("#767676"));
                                  rowTextviewS.setLayoutParams(params);
                                  Drawable img1 = getApplicationContext().getResources().getDrawable(R.drawable.down_icon);
                                  rowTextviewS.setCompoundDrawablesWithIntrinsicBounds(null, null, img1, null);

                                  // Type = Text
                                  final EditText rowEditText = new EditText(InspeksiKedua.this);
                                  rowEditText.setLayoutParams(params);
                                  rowEditText.setTextSize(11);
                                  rowEditText.setHint("Jawab disini");



                                  //Type = Person
                                  final EditText rowEditTextP = new EditText(InspeksiKedua.this);
                                  rowEditTextP.setLayoutParams(params);
                                  rowEditTextP.setTextSize(11);
                                  rowEditTextP.setHint("Jawab disini");
                                  rowEditTextP.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                                  //Type = Map
                                  final EditText rowEditTextM = new EditText(InspeksiKedua.this);
                                  rowEditTextM.setLayoutParams(params);
                                  rowEditTextM.setTextSize(11);
                                  rowEditTextM.setHint("Jawab disini");
                                  rowEditTextM.setInputType(InputType.TYPE_CLASS_NUMBER);

                                  //type multiple-choices
                                  final Button rowButton1 = new Button(InspeksiKedua.this);
                                  final Button rowButton2 = new Button(InspeksiKedua.this);

                                  rowButton1.setLayoutParams(params2);
                                  rowButton1.setText("Ya");
                                  rowButton1.setTextColor(Color.parseColor("#767676"));
                                  rowButton1.setBackgroundResource(R.drawable.btn_jawab);


                                  rowButton1.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          GradientDrawable drawable = (GradientDrawable) v.getBackground();

                                          if (rowButton2.getVisibility() == View.VISIBLE) {
                                              drawable.setColor(Color.GREEN);
                                              rowButton1.setTextColor(Color.WHITE);
                                              rowButton2.setVisibility(View.GONE);

                                          } else {
                                              drawable.setColor(Color.LTGRAY);
                                              rowButton1.setTextColor(Color.GRAY);
                                              rowButton2.setVisibility(View.VISIBLE);
                                          }
                                      }
                                  });

                                  rowButton2.setLayoutParams(params2);
                                  rowButton2.setText("Tidak");
                                  rowButton2.setTextColor(Color.parseColor("#767676"));
                                  rowButton2.setBackgroundResource(R.drawable.btn_jawab);

                                  rowButton2.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          GradientDrawable drawable = (GradientDrawable) v.getBackground();

                                          if (rowButton1.getVisibility() == View.VISIBLE) {
                                              drawable.setColor(Color.RED);
                                              rowButton2.setTextColor(Color.WHITE);
                                              rowButton1.setVisibility(View.GONE);

                                          } else {
                                              drawable.setColor(Color.LTGRAY);
                                              rowButton2.setTextColor(Color.GRAY);
                                              rowButton1.setVisibility(View.VISIBLE);
                                          }
                                      }
                                  });


                                  final TextView[] myTextViews = new TextView[ukuranArray]; // create an empty array;
                                  final EditText[] myEditTextViews = new EditText[ukuranArray]; // create an empty array;

                                  String type = list.get(i).get("type").toString();
                                  Log.d("initype : ", type);

                                  //get objek respon
                                  Map respon = (Map) list.get(i).get("typeOfResponse");
                                  //get tipe reson
                                  String tipeRespon = String.valueOf(respon.get("type"));
                                  Log.d("tiperes : ", tipeRespon);

//                                  int ukuranArray = list.size();
//                                  for (int i = 0; i < ukuranArray; i++) {
                                  // set some properties of rowTextView or something
                                  rowTextView.setText("Pertanyaan :" + "\n" + deskripsi);
                                  rowTextviewS.setText(deskripsi);

                                  //sectionpagepertama
                                  if (type.equals("section")) {
                                      myLinearLayout.addView(rowTextviewS);

                                      ArrayList<Map> content = (ArrayList<Map>) list.get(i).get("contents");
                                      Log.d("inicontent : ", content.toString());
                                      int jsize = content.size();

                                      for (int a = 0; a < jsize; a++) {
                                          String isi = content.get(a).get(("description")).toString();
                                          Log.d("inides : ", isi);

                                          //get objek respon
                                          Map responS = (Map) content.get(a).get("typeOfResponse");
                                          //get tipe reson
                                          String tipeResponSe = String.valueOf(responS.get("type"));
                                          Log.d("tiperese : ", tipeResponSe);

                                          //type = section
                                          final TextView rowTextviewSe = new TextView(InspeksiKedua.this);
                                          rowTextviewSe.setLayoutParams(params3);
                                          rowTextviewSe.setBackgroundResource(R.drawable.cardpertanyaan);
                                          rowTextviewSe.setTextSize(11);
                                          rowTextviewSe.setPaddingRelative(50, 25, 10, 25);
                                          rowTextviewSe.setTypeface(null, Typeface.ITALIC);
                                          rowTextviewSe.setTextColor(Color.parseColor("#767676"));
                                          rowTextviewSe.setLayoutParams(params);
                                          Drawable img2 = getApplicationContext().getResources().getDrawable(R.drawable.action_icon);
                                          rowTextviewSe.setCompoundDrawablesWithIntrinsicBounds(null, null, img2, null);

                                          // Type = Text
                                          final EditText rowEditTextSe = new EditText(InspeksiKedua.this);
                                          rowEditTextSe.setLayoutParams(params);
                                          rowEditTextSe.setTextSize(11);
                                          rowEditTextSe.setHint("Jawab disini");

                                          //Type = Person
                                          final EditText rowEditTextPSe = new EditText(InspeksiKedua.this);
                                          rowEditTextPSe.setLayoutParams(params);
                                          rowEditTextPSe.setTextSize(11);
                                          rowEditTextPSe.setHint("Jawab disini");
                                          rowEditTextPSe.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                                          //Type = Map
                                          final EditText rowEditTextMSe = new EditText(InspeksiKedua.this);
                                          rowEditTextMSe.setLayoutParams(params);
                                          rowEditTextMSe.setTextSize(11);
                                          rowEditTextMSe.setHint("Jawab disini");
                                          rowEditTextMSe.setInputType(InputType.TYPE_CLASS_NUMBER);

                                          //type multiple-choices
                                          final Button rowButton1Se = new Button(InspeksiKedua.this);
                                          final Button rowButton2Se = new Button(InspeksiKedua.this);

                                          rowButton1Se.setLayoutParams(params2);
                                          rowButton1Se.setText("Ya");
                                          rowButton1Se.setTextColor(Color.parseColor("#767676"));
                                          rowButton1Se.setBackgroundResource(R.drawable.btn_jawab);


                                          rowButton1Se.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  GradientDrawable drawable = (GradientDrawable) v.getBackground();

                                                  if (rowButton2Se.getVisibility() == View.VISIBLE) {
                                                      drawable.setColor(Color.GREEN);
                                                      rowButton1Se.setTextColor(Color.WHITE);
                                                      rowButton2Se.setVisibility(View.GONE);

                                                  } else {
                                                      drawable.setColor(Color.LTGRAY);
                                                      rowButton1Se.setTextColor(Color.GRAY);
                                                      rowButton2Se.setVisibility(View.VISIBLE);
                                                  }
                                              }
                                          });

                                          rowButton2Se.setLayoutParams(params2);
                                          rowButton2Se.setText("Tidak");
                                          rowButton2Se.setTextColor(Color.parseColor("#767676"));
                                          rowButton2Se.setBackgroundResource(R.drawable.btn_jawab);

                                          rowButton2Se.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  GradientDrawable drawable = (GradientDrawable) v.getBackground();

                                                  if (rowButton1Se.getVisibility() == View.VISIBLE) {
                                                      drawable.setColor(Color.RED);
                                                      rowButton2Se.setTextColor(Color.WHITE);
                                                      rowButton1Se.setVisibility(View.GONE);

                                                  } else {
                                                      drawable.setColor(Color.LTGRAY);
                                                      rowButton2Se.setTextColor(Color.GRAY);
                                                      rowButton1Se.setVisibility(View.VISIBLE);
                                                  }
                                              }
                                          });

                                          //popup menu
                                          final PopupMenu popupMenu2 = new PopupMenu(InspeksiKedua.this, rowTextviewSe);
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
                                          rowTextviewSe.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  popupMenu2.show();
                                              }
                                          });

                                          rowTextviewSe.setText("Pertanyaan :" + "\n" + isi);
                                          Log.d("pertanyaannya : ", String.valueOf(isi));
                                          myLinearLayout.addView(rowTextviewSe);

                                          if (tipeResponSe.equals("text")) {
                                              myLinearLayout.addView(rowEditTextSe);
                                              allEds.add(rowEditTextSe);

                                          } else if (tipeResponSe.equals("respon")) {
                                              myLinearLayout.addView(rowEditTextPSe);


                                          } else if (tipeResponSe.equals("map")) {
                                              myLinearLayout.addView(rowEditTextMSe);


                                          }  else if (tipeResponSe.equals("multiple-choices")){

                                              ArrayList opsi1 = (ArrayList) responS.get("option");
                                              Log.d("iniopsiSection", opsi1.toString());

                                              for (int b = 0; b < opsi1.size(); b++){
                                                  //type multiple-choices

                                                  final Button rowButton6 = new Button(InspeksiKedua.this);
                                                  rowButton6.setLayoutParams(params);
                                                  rowButton6.setText(opsi1.get(b).toString());
                                                  rowButton6.setTextColor(Color.parseColor("#767676"));
                                                  rowButton6.setBackgroundResource(R.drawable.btn_jawab);
                                                  GradientDrawable drawable = (GradientDrawable) rowButton6.getBackground();
                                                  drawable.setColor(Color.LTGRAY);


                                                  rowButton6.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          GradientDrawable drawable = (GradientDrawable) v.getBackground();
                                                          if (drawable.getColor() == ColorStateList.valueOf(Color.LTGRAY)){
                                                              drawable.setColor(Color.GREEN);
                                                          }else {
                                                              drawable.setColor(Color.LTGRAY);
                                                          }
                                                      }
                                                  });
                                                  myLinearLayout.addView(rowButton6);
                                              }

                                          }

                                      }

                                  }else {
                                      // add the textview to the linearlayout
                                      myLinearLayout.addView(rowTextView);
                                  }

                                  if (tipeRespon.equals("text")) {
                                      myLinearLayout.addView(rowEditText);
                                      allEds.add(rowEditText);
                                      


                                  } else if (tipeRespon.equals("person")) {
                                      myLinearLayout.addView(rowEditTextP);


                                  } else if (tipeRespon.equals("map")) {
                                      myLinearLayout.addView(rowEditTextM);


                                  } else if (tipeRespon.equals("multiple-choices")){

                                      ArrayList opsi = (ArrayList) respon.get("option");
                                      Log.d("iniopsi", opsi.toString());
                                      for (int a = 0; a < opsi.size(); a++){
                                          //type multiple-choices

                                          final Button rowButton3 = new Button(InspeksiKedua.this);
                                          rowButton3.setLayoutParams(params);
                                          rowButton3.setText(opsi.get(a).toString());
                                          rowButton3.setTextColor(Color.parseColor("#767676"));
                                          rowButton3.setBackgroundResource(R.drawable.btn_jawab);
                                          GradientDrawable drawable = (GradientDrawable) rowButton3.getBackground();
                                          drawable.setColor(Color.LTGRAY);


                                          rowButton3.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  GradientDrawable drawable = (GradientDrawable) v.getBackground();
                                                  if (drawable.getColor() == ColorStateList.valueOf(Color.LTGRAY)){
                                                      drawable.setColor(Color.GREEN);
                                                  }else {
                                                      drawable.setColor(Color.LTGRAY);
                                                  }
                                              }
                                          });
                                          myLinearLayout.addView(rowButton3);
                                      }
//                                      myLinearLayout.addView(rowButton1);
//                                      myLinearLayout.addView(rowButton2);

                                  }

                                  // save a reference to the textview for later
//                                  myTextViews[i] = rowTextView;
//                                  myEditTextViews[i] = rowEditText;
//                                  Log.d("rowtext",myEditTextViews[i].toString());



                              }
                          }
                      }

                  }
                  //pagenext

                  // Get the last visible document
                  lastvisible = task.getResult().getDocuments().get(task.getResult().size() -1);
                  LinearLayoutCompat myLinearLayout = findViewById(R.id.lPertanyaan);

                  berikutnya.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {

                          int angkaawal = Integer.parseInt(nPage.getText().toString());
                          int tambah = 1;
                          int hasil = angkaawal + tambah;
                          nPage.setText(String.valueOf(hasil));

                          int jsize = Integer.parseInt(jPage.getText().toString());
                          int jpage = Integer.parseInt(nPage.getText().toString());
                          if (jpage > jsize){
                              ttd();
                              nPage.setText(String.valueOf(sizeawal));
                          }else {
                              //getvalue
                              Map<String, Object> map2 = new HashMap<>();
                              final ArrayList[] test1 = new ArrayList[1];
                              ArrayList<Object> ob = new ArrayList<Object>();
//                              List map2 = new A
                              //gethasiltemplate
                              df.document(idtemplate)
                                       .collection("pages")
                                       .document(idDocUpdate)
//                                       .whereArrayContains("contentId","45b83309-1acb-478e-ac59-6c5478e7323a")
                                       .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                  @Override
                                  public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                      if (task.isSuccessful()) {

                                          DocumentSnapshot document = task.getResult();
                                          if (task != null && document.exists()) {
                                              ArrayList list2 = (ArrayList) document.get("contents");

                                              map2.put("contents", list2);
                                              Log.d("hasilmap22",map2.toString());
                                              Log.d("hasilmap22","" + map2.get("contents"));

                                              list2.set(1, "newString");
                                              for (int i = 0; i < list2.size(); i++) {
                                                  Log.d("pertanyaan index ke " + i, "" + list2.get(i));
                                              }
//
                                          }
                                      }
                                  }
                              });
                              String[] strings = new String[allEds.size()];
                              ArrayList contentMap = (ArrayList) map2.get("contents");
                              for(int i=0; i < allEds.size(); i++){
                                  strings[i] = allEds.get(i).getText().toString();
                                  Log.d("please",strings[i].toString());
                                  Log.d("mapsize", "" + map2);
//                                  map2.get("contents")
//                                  test1[0][0].add((Integer) test1[0][0].get(i), strings[i]);

                                  db.collection("hasiltemplatestes").document(idtemplate)
                                          .collection("pages").document(idDocUpdate)
//                                          .update("contents", test1[0][0]);
                                          .update("answer.isFailed", null,
                                                  "answer.text", FieldValue.arrayUnion(strings[i]));
                              }

//                              db.collection("hasiltemplatestes").document(idtemplate)
//                                      .collection("pages").document(idDocUpdate)
//                                          .update("contents", map2);
////                                      .update("answer.isFailed", null,
////                                              "answer.text", FieldValue.arrayUnion(strings[i]));

                              myLinearLayout.removeAllViews();
                              pages.document(documentId)
                                      .collection("pages")
                                      .startAfter(lastvisible)
                                      .limit(1)
                                      .get()
                                      .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                          @Override
                                          public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                              for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                  String title = (String) documentSnapshot.get("pageTitle");
                                                  qTitle.setText(title);

                                              }
                                          if (task.isSuccessful()) {

                                              for (QueryDocumentSnapshot document : task.getResult()) {

                                                  if (document != null && document.exists()) {


                                                      ArrayList<Map> list = (ArrayList<Map>) document.get("contents");
                                                      //cobapush

                                                      Map<String, Object> map = new HashMap<>();
                                                      map.put("contents", list);
                                                      db.collection("hasiltemplatestes").document(idtemplate)
                                                              .collection("pages").document().set(map);
                                                      

                                                      int ukuranArray = list.size();
                                                      for (int i = 0; i < ukuranArray; i++) {

                                                          String deskripsi = list.get(i).get("description").toString();

                                                          LinearLayoutCompat myLinearLayout = findViewById(R.id.lPertanyaan);


                                                          LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                                                          params.setMargins(30, 20, 30, 20);

                                                          LinearLayoutCompat.LayoutParams params2 = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.FILL_PARENT, LinearLayoutCompat.LayoutParams.FILL_PARENT);
                                                          params2.setMargins(50, 5, 50, 5);

                                                          LinearLayoutCompat.LayoutParams params3 = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                                                          params3.setMargins(10, 20, 10, 20);

                                                          final TextView rowTextView = new TextView(InspeksiKedua.this);
                                                          rowTextView.setBackgroundResource(R.drawable.cardpertanyaan);
                                                          rowTextView.setTextSize(11);
                                                          rowTextView.setPaddingRelative(50, 25, 10, 25);
                                                          rowTextView.setTypeface(null, Typeface.ITALIC);
                                                          rowTextView.setTextColor(Color.parseColor("#767676"));
                                                          rowTextView.setLayoutParams(params);
                                                          Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.action_icon);
                                                          rowTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);

                                                          //popup menu
                                                          final PopupMenu popupMenu = new PopupMenu(InspeksiKedua.this, rowTextView);
                                                          //add menu items in popup menu
                                                          popupMenu.getMenu().add(Menu.NONE, 0, 0, "Tambah Catatan"); //parm 2 is menu id, param 3 is position of this menu item in menu items list, param 4 is title of the menu
                                                          popupMenu.getMenu().add(Menu.NONE, 1, 1, "Tambah Foto");
                                                          popupMenu.getMenu().add(Menu.NONE, 2, 2, "Tambah Tindakan");

                                                          //handle menu item clicks
                                                          popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                              @Override
                                                              public boolean onMenuItemClick(MenuItem menuItem) {
                                                                  //get id of the clicked item
                                                                  int id = menuItem.getItemId();
                                                                  //handle clicks
                                                                  if (id == 0) {
                                                                      tambahcatatan();
                                                                      //Copy clicked
                                                                      //set text
//                                        selectedTv.setText("Copy clicked");
                                                                  } else if (id == 1) {
                                                                      ambilfoto();
                                                                      //Share clicked
                                                                      //set text
//                                        selectedTv.setText("Share clicked");
                                                                  } else if (id == 2) {
                                                                      tindakan();
                                                                      //Save clicked
                                                                      //set text
//                                        selectedTv.setText("Save clicked");
                                                                  }

                                                                  return false;
                                                              }
                                                          });

                                                          //handle button click, show popup menu
                                                          rowTextView.setOnClickListener(new View.OnClickListener() {
                                                              @Override
                                                              public void onClick(View view) {
                                                                  popupMenu.show();
                                                              }
                                                          });
                                                          //type = section
                                                          final TextView rowTextviewS = new TextView(InspeksiKedua.this);
                                                          rowTextviewS.setLayoutParams(params3);
                                                          rowTextviewS.setBackgroundResource(R.drawable.cardsection);
                                                          rowTextviewS.setTextSize(13);
                                                          rowTextviewS.setPaddingRelative(50, 25, 10, 25);
                                                          rowTextviewS.setTypeface(null, Typeface.ITALIC);
                                                          rowTextviewS.setTextColor(Color.parseColor("#767676"));
                                                          rowTextviewS.setLayoutParams(params);
                                                          Drawable img1 = getApplicationContext().getResources().getDrawable(R.drawable.down_icon);
                                                          rowTextviewS.setCompoundDrawablesWithIntrinsicBounds(null, null, img1, null);

                                                          // Type = Text
                                                          final EditText rowEditText = new EditText(InspeksiKedua.this);
                                                          rowEditText.setLayoutParams(params);
                                                          rowEditText.setTextSize(11);
                                                          rowEditText.setHint("Jawab disini");

                                                          //Type = Person
                                                          final EditText rowEditTextP = new EditText(InspeksiKedua.this);
                                                          rowEditTextP.setLayoutParams(params);
                                                          rowEditTextP.setTextSize(11);
                                                          rowEditTextP.setHint("Jawab disini");
                                                          rowEditTextP.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                                                          //Type = Map
                                                          final EditText rowEditTextM = new EditText(InspeksiKedua.this);
                                                          rowEditTextM.setLayoutParams(params);
                                                          rowEditTextM.setTextSize(11);
                                                          rowEditTextM.setHint("Jawab disini");
                                                          rowEditTextM.setInputType(InputType.TYPE_CLASS_NUMBER);

                                                          //type multiple-choices
                                                          final Button rowButton1 = new Button(InspeksiKedua.this);
                                                          final Button rowButton2 = new Button(InspeksiKedua.this);

                                                          rowButton1.setLayoutParams(params2);
                                                          rowButton1.setText("Ya");
                                                          rowButton1.setTextColor(Color.parseColor("#767676"));
                                                          rowButton1.setBackgroundResource(R.drawable.btn_jawab);


                                                          rowButton1.setOnClickListener(new View.OnClickListener() {
                                                              @Override
                                                              public void onClick(View v) {
                                                                  GradientDrawable drawable = (GradientDrawable) v.getBackground();

                                                                  if (rowButton2.getVisibility() == View.VISIBLE) {
                                                                      drawable.setColor(Color.GREEN);
                                                                      rowButton1.setTextColor(Color.WHITE);
                                                                      rowButton2.setVisibility(View.GONE);

                                                                  } else {
                                                                      drawable.setColor(Color.LTGRAY);
                                                                      rowButton1.setTextColor(Color.GRAY);
                                                                      rowButton2.setVisibility(View.VISIBLE);
                                                                  }
                                                              }
                                                          });

                                                          rowButton2.setLayoutParams(params2);
                                                          rowButton2.setText("Tidak");
                                                          rowButton2.setTextColor(Color.parseColor("#767676"));
                                                          rowButton2.setBackgroundResource(R.drawable.btn_jawab);

                                                          rowButton2.setOnClickListener(new View.OnClickListener() {
                                                              @Override
                                                              public void onClick(View v) {
                                                                  GradientDrawable drawable = (GradientDrawable) v.getBackground();

                                                                  if (rowButton1.getVisibility() == View.VISIBLE) {
                                                                      drawable.setColor(Color.RED);
                                                                      rowButton2.setTextColor(Color.WHITE);
                                                                      rowButton1.setVisibility(View.GONE);

                                                                  } else {
                                                                      drawable.setColor(Color.LTGRAY);
                                                                      rowButton2.setTextColor(Color.GRAY);
                                                                      rowButton1.setVisibility(View.VISIBLE);
                                                                  }
                                                              }
                                                          });


                                                          final TextView[] myTextViews = new TextView[ukuranArray]; // create an empty array;

                                                          // set some properties of rowTextView or something
                                                          rowTextView.setText("Pertanyaan :" + "\n" + deskripsi);
                                                          rowTextviewS.setText(deskripsi);

                                                          String type = list.get(i).get("type").toString();
                                                          Log.d("initype : ", type);

                                                          //get objek respon
                                                          Map respon = (Map) list.get(i).get("typeOfResponse");
                                                          //get tipe reson
                                                          String tipeRespon = String.valueOf(respon.get("type"));
                                                          Log.d("tiperes : ", tipeRespon);


                                                            //sectionnext
                                                            if (type.equals("section")) {
                                                                myLinearLayout.addView(rowTextviewS);

                                                                ArrayList<Map> content = (ArrayList<Map>) list.get(i).get("contents");
                                                                Log.d("inicontent : ", content.toString());
                                                                int jsize = content.size();

                                                                for (int a = 0; a < jsize; a++) {
                                                                    String isi = content.get(a).get(("description")).toString();
                                                                    Log.d("inides : ", isi);

                                                                    //get objek respon
                                                                    Map responS = (Map) content.get(a).get("typeOfResponse");
                                                                    //get tipe reson
                                                                    String tipeResponSe = String.valueOf(responS.get("type"));
                                                                    Log.d("tiperese : ", tipeResponSe);

                                                                    //type = section
                                                                    final TextView rowTextviewSe = new TextView(InspeksiKedua.this);
                                                                    rowTextviewSe.setLayoutParams(params3);
                                                                    rowTextviewSe.setBackgroundResource(R.drawable.cardpertanyaan);
                                                                    rowTextviewSe.setTextSize(11);
                                                                    rowTextviewSe.setPaddingRelative(50, 25, 10, 25);
                                                                    rowTextviewSe.setTypeface(null, Typeface.ITALIC);
                                                                    rowTextviewSe.setTextColor(Color.parseColor("#767676"));
                                                                    rowTextviewSe.setLayoutParams(params);
                                                                    Drawable img2 = getApplicationContext().getResources().getDrawable(R.drawable.action_icon);
                                                                    rowTextviewSe.setCompoundDrawablesWithIntrinsicBounds(null, null, img2, null);

                                                                    // Type = Text
                                                                    final EditText rowEditTextSe = new EditText(InspeksiKedua.this);
                                                                    rowEditTextSe.setLayoutParams(params);
                                                                    rowEditTextSe.setTextSize(11);
                                                                    rowEditTextSe.setHint("Jawab disini");

                                                                    //Type = Person
                                                                    final EditText rowEditTextPSe = new EditText(InspeksiKedua.this);
                                                                    rowEditTextPSe.setLayoutParams(params);
                                                                    rowEditTextPSe.setTextSize(11);
                                                                    rowEditTextPSe.setHint("Jawab disini");
                                                                    rowEditTextPSe.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                                                                    //Type = Map
                                                                    final EditText rowEditTextMSe = new EditText(InspeksiKedua.this);
                                                                    rowEditTextMSe.setLayoutParams(params);
                                                                    rowEditTextMSe.setTextSize(11);
                                                                    rowEditTextMSe.setHint("Jawab disini");
                                                                    rowEditTextMSe.setInputType(InputType.TYPE_CLASS_NUMBER);

                                                                    //type multiple-choices
                                                                    final Button rowButton1Se = new Button(InspeksiKedua.this);
                                                                    final Button rowButton2Se = new Button(InspeksiKedua.this);

                                                                    rowButton1Se.setLayoutParams(params2);
                                                                    rowButton1Se.setText("Ya");
                                                                    rowButton1Se.setTextColor(Color.parseColor("#767676"));
                                                                    rowButton1Se.setBackgroundResource(R.drawable.btn_jawab);


                                                                    rowButton1Se.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            GradientDrawable drawable = (GradientDrawable) v.getBackground();

                                                                            if (rowButton2Se.getVisibility() == View.VISIBLE) {
                                                                                drawable.setColor(Color.GREEN);
                                                                                rowButton1Se.setTextColor(Color.WHITE);
                                                                                rowButton2Se.setVisibility(View.GONE);

                                                                            } else {
                                                                                drawable.setColor(Color.LTGRAY);
                                                                                rowButton1Se.setTextColor(Color.GRAY);
                                                                                rowButton2Se.setVisibility(View.VISIBLE);
                                                                            }
                                                                        }
                                                                    });

                                                                    rowButton2Se.setLayoutParams(params2);
                                                                    rowButton2Se.setText("Tidak");
                                                                    rowButton2Se.setTextColor(Color.parseColor("#767676"));
                                                                    rowButton2Se.setBackgroundResource(R.drawable.btn_jawab);

                                                                    rowButton2Se.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            GradientDrawable drawable = (GradientDrawable) v.getBackground();

                                                                            if (rowButton1Se.getVisibility() == View.VISIBLE) {
                                                                                drawable.setColor(Color.RED);
                                                                                rowButton2Se.setTextColor(Color.WHITE);
                                                                                rowButton1Se.setVisibility(View.GONE);

                                                                            } else {
                                                                                drawable.setColor(Color.LTGRAY);
                                                                                rowButton2Se.setTextColor(Color.GRAY);
                                                                                rowButton1Se.setVisibility(View.VISIBLE);
                                                                            }
                                                                        }
                                                                    });

                                                                    //popup menu
                                                                    final PopupMenu popupMenu2 = new PopupMenu(InspeksiKedua.this, rowTextviewSe);
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
                                                                    rowTextviewSe.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View view) {
                                                                            popupMenu2.show();
                                                                        }
                                                                    });

                                                                    rowTextviewSe.setText("Pertanyaan :" + "\n" + isi);
                                                                    Log.d("pertanyaannya : ", String.valueOf(isi));
                                                                    myLinearLayout.addView(rowTextviewSe);

                                                                    if (tipeResponSe.equals("text")) {
                                                                        myLinearLayout.addView(rowEditTextSe);


                                                                    } else if (tipeResponSe.equals("respon")) {
                                                                        myLinearLayout.addView(rowEditTextPSe);


                                                                    } else if (tipeResponSe.equals("map")) {
                                                                        myLinearLayout.addView(rowEditTextMSe);


                                                                    }  else if (tipeResponSe.equals("multiple-choices")) {

                                                                        ArrayList opsi1 = (ArrayList) responS.get("option");
                                                                        Log.d("iniopsiSection", opsi1.toString());

                                                                        for (int b = 0; b < opsi1.size(); b++) {
                                                                            //type multiple-choices

                                                                            final Button rowButton4 = new Button(InspeksiKedua.this);
                                                                            rowButton4.setLayoutParams(params);
                                                                            rowButton4.setText(opsi1.get(b).toString());
                                                                            rowButton4.setTextColor(Color.parseColor("#767676"));
                                                                            rowButton4.setBackgroundResource(R.drawable.btn_jawab);
                                                                            GradientDrawable drawable = (GradientDrawable) rowButton4.getBackground();
                                                                            drawable.setColor(Color.LTGRAY);


                                                                            rowButton4.setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    GradientDrawable drawable = (GradientDrawable) v.getBackground();
                                                                                    if (drawable.getColor() == ColorStateList.valueOf(Color.LTGRAY)) {
                                                                                        drawable.setColor(Color.GREEN);
                                                                                    } else {
                                                                                        drawable.setColor(Color.LTGRAY);
                                                                                    }
                                                                                }
                                                                            });
                                                                            myLinearLayout.addView(rowButton4);

                                                                        }
                                                                    }

                                                                }

                                                            }else {
                                                                // add the textview to the linearlayout
                                                                myLinearLayout.addView(rowTextView);
                                                            }

                                                          if (tipeRespon.equals("text")) {
                                                              myLinearLayout.addView(rowEditText);


                                                          } else if (tipeRespon.equals("person")) {
                                                              myLinearLayout.addView(rowEditTextP);


                                                          } else if (tipeRespon.equals("map")) {
                                                              myLinearLayout.addView(rowEditTextM);


                                                          } else if (tipeRespon.equals("multiple-choices")){
                                                              ArrayList opsi = (ArrayList) respon.get("option");
                                                              Log.d("iniopsi", opsi.toString());

                                                              for (int c = 0; c < opsi.size(); c++){
                                                                  //type multiple-choices

                                                                  final Button rowButton5 = new Button(InspeksiKedua.this);
                                                                  rowButton5.setLayoutParams(params);
                                                                  rowButton5.setText(opsi.get(c).toString());
                                                                  rowButton5.setTextColor(Color.parseColor("#767676"));
                                                                  rowButton5.setBackgroundResource(R.drawable.btn_jawab);
                                                                  GradientDrawable drawable = (GradientDrawable) rowButton5.getBackground();
                                                                  drawable.setColor(Color.LTGRAY);


                                                                  rowButton5.setOnClickListener(new View.OnClickListener() {
                                                                      @Override
                                                                      public void onClick(View v) {
                                                                          GradientDrawable drawable = (GradientDrawable) v.getBackground();
                                                                          if (drawable.getColor() == ColorStateList.valueOf(Color.LTGRAY)){
                                                                              drawable.setColor(Color.GREEN);
                                                                          }else {
                                                                              drawable.setColor(Color.LTGRAY);
                                                                          }
                                                                      }
                                                                  });
                                                                  myLinearLayout.addView(rowButton5);
                                                              }

                                                          }

                                                          // save a reference to the textview for later
                                                          myTextViews[i] = rowTextView;


                                                      }
                                                  }else{
                                                      ttd();
                                                  }
                                              }
                                          }

                                              lastvisible = task.getResult().getDocuments().get(task.getResult().size() - 1);
                                              Log.d("LAST : ", lastvisible.toString());

                                          }

                                      });
                          }
                      }
                  });

              }
          });



        cardview1 = findViewById(R.id.cardView1);
        cardView2 = findViewById(R.id.cardView2);

    }

    private void tindakan() {
        dialog = new AlertDialog.Builder(InspeksiKedua.this);
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(InspeksiKedua.this);
        alertDialog.setTitle("Tambah Catatan");

        final EditText input = new EditText(InspeksiKedua.this);
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(InspeksiKedua.this);
        alertDialog.setTitle("Tanda Tangan");

        final SignatureView input = new SignatureView(InspeksiKedua.this, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Selesai",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent selesai = new Intent(InspeksiKedua.this, InspeksiSelesai.class);
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
            LayoutInflater layoutInflater = LayoutInflater.from(InspeksiKedua.this);
            View promptView = layoutInflater.inflate(R.layout.hasilfoto, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspeksiKedua.this);
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
