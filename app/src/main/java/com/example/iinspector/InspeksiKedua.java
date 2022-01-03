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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    TextView qTitle,qDes;

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

    //collection
    CollectionReference pages = db.collection("templates");


    //recyclerview
    private RecyclerView recyclerView;
    private QuestionAdapter adapter;
    private ArrayList<GetDataQuestion> questionArrayList;

//    //linear
//    LinearLayoutCompat myLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeksi_kedua);

        String documentId = getIntent().getStringExtra("doc");


        scard1 = findViewById(R.id.scard1);
        scard2 = findViewById(R.id.scard2);
        qTitle = findViewById(R.id.qTitile);

//        qDes = findViewById(R.id.qDescr);


          pages.document(documentId)
                  .collection("pages")
                  .limit(2)
                  .get()
                  .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
              @Override
              public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                  for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                      String title = (String) documentSnapshot.get("pageTitle");
                      qTitle.setText(title);

                  }

              }
          });


        pages.document(documentId)
                .collection("pages")
//                .document("OJwTZnHufPTXWKomQdBb")
                .limit(2)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                            if (document != null && document.exists()) {

                                ArrayList<Map> list = (ArrayList<Map>) document.get("contents");
                                int ukuranArray = list.size();
                                for (int i = 0; i < ukuranArray; i++) {

                                    String deskripsi = list.get(i).get("description").toString();
                                    Log.d("ini des : ", deskripsi);


                                    LinearLayoutCompat myLinearLayout = findViewById(R.id.lPertanyaan);


                                    LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                                    params.setMargins(30, 20, 30, 20);

                                    LinearLayoutCompat.LayoutParams params2 = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.FILL_PARENT, LinearLayoutCompat.LayoutParams.FILL_PARENT);
                                    params2.setMargins(50, 5, 50, 5);


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

                                    // add the textview to the linearlayout
                                    myLinearLayout.addView(rowTextView);

                                    String respon = list.get(i).get("typeOfResponse").toString();
                                    Log.d("ini typeOfResponse : ", respon);

                                    if (respon.equals("{type=text, option=null}")) {
                                        myLinearLayout.addView(rowEditText);


                                    } else if (respon.equals("{type=person, option=null}")) {
                                        myLinearLayout.addView(rowEditTextP);


                                    } else if (respon.equals("{type=map, option=null}")) {
                                        myLinearLayout.addView(rowEditTextM);


                                    } else {
                                        myLinearLayout.addView(rowButton1);
                                        myLinearLayout.addView(rowButton2);

                                    }

                                    // save a reference to the textview for later
                                    myTextViews[i] = rowTextView;

                                    final Button selesai = new Button(InspeksiKedua.this);
                                    selesai.setLayoutParams(params);
                                    selesai.setText("Lanjut");
                                    selesai.setTextColor(Color.parseColor("#4CAF50"));
                                    selesai.setBackgroundResource(R.drawable.btnlanjut);

                                }
                            }
                            }
                            LinearLayoutCompat myLinearLayout = findViewById(R.id.lPertanyaan);
                            LinearLayoutCompat.LayoutParams paramselesai = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                            paramselesai.setMargins(30, 20, 30, 20);
                            final Button selesai = new Button(InspeksiKedua.this);
                            selesai.setLayoutParams(paramselesai);
                            selesai.setText("Lanjut");
                            selesai.setTextColor(Color.parseColor("#4CAF50"));
                            selesai.setBackgroundResource(R.drawable.btnlanjut);
                            myLinearLayout.addView(selesai);
                        }
                    }
                });

//        LinearLayoutCompat myLinearLayout = findViewById(R.id.lnext);
//        LinearLayoutCompat.LayoutParams paramselesai = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
//        paramselesai.setMargins(30, 20, 30, 20);
//        final Button selesai = new Button(InspeksiKedua.this);
//        selesai.setLayoutParams(paramselesai);
//        selesai.setText("Lanjut");
//        selesai.setTextColor(Color.parseColor("#4CAF50"));
//        selesai.setBackgroundResource(R.drawable.btnlanjut);
//        myLinearLayout.addView(selesai);

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
