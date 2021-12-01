package com.example.iinspector;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.iinspector.ui.main.GetDataTodo;
import com.example.iinspector.ui.main.TodoHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.kyanogen.signatureview.SignatureView;

public class InspeksiKedua extends AppCompatActivity {

    Button  selesai;
//    CardView scard1, cardview1, cardView2, cardView6, cardView7, cardView8, scard2, cardView4, cardView5, rya, rno, rya2, rno2, pass, fail;
//    TextView ya, no, ya2, no2, tpass, tfail, tambah1, tambah2, tambah3, tambah4, tambah5, foto1, foto2, foto3, foto4, foto5,tindakan1,tindakan2,tindakan3,tindakan4,tindakan5;
    CardView scard1,scard2;
    TextView qTitle;

    //Recyclerview
    RecyclerView qrecyclerview;
    RecyclerView.Adapter qrecyclerViewAdapter;
    RecyclerView.LayoutManager qrecylerViewLayoutManager;
    FirestoreRecyclerAdapter<GetDataQuestion, QuestionHolder> qadaptercard;

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
    Task<QuerySnapshot> firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeksi_kedua);

        String documentId = getIntent().getStringExtra("doc");

        selesai = findViewById(R.id.btnnext);
        scard1 = findViewById(R.id.scard1);
        scard2 = findViewById(R.id.scard2);
//        qTitle = findViewById(R.id.qTitle);

        qrecyclerview = findViewById(R.id.qrecyclerView);
        qrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        Query query = FirebaseFirestore.getInstance()
                .collection("templates")
                .document(documentId)
                .collection("pages")
                .orderBy("contents");

//        firestore = FirebaseFirestore.getInstance()
//                    .collection("templates")
//                    .document(documentId)
//                    .collection("pages")
//                    .orderBy("pageTitle")
//                    .get();

//        qTitle.setText(firestore.toString());

        FirestoreRecyclerOptions<GetDataQuestion> options = new FirestoreRecyclerOptions.Builder<GetDataQuestion>()
                .setQuery(query, GetDataQuestion.class)
                .build();

        qadaptercard = new FirestoreRecyclerAdapter<GetDataQuestion, QuestionHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull QuestionHolder holder, int position, @NonNull GetDataQuestion getDataQuestion) {
                holder.setdescription((getDataQuestion.getDescription()));


                holder.setOnClickListener(new QuestionHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

//                        documentId = getSnapshots().getSnapshot(position).getId();
//                        Position = position;
//                        if (getDataTodo.getStatus().equals("Sedang Dikerjakan")){
//                            Snackbar.make(rootView.findViewById(R.id.todoku),"Inspeksi Sedang dikerjakan !",Snackbar.LENGTH_LONG).show();
//                        }else {
//                            peringatan("Jika form inspeksi telah tampil anda tidak bisa kembali.");
//                        }


                    }
                });
            }

            @NonNull
            @Override
            public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
                return new QuestionHolder(view);
            }
        };
        qadaptercard.startListening();
        qrecyclerview.setAdapter(qadaptercard);

        //cardview1 = findViewById(R.id.cardView1);
//        cardView2 = findViewById(R.id.cardView2);
//        cardView4 = findViewById(R.id.cardView4);
//        cardView5 = findViewById(R.id.cardView5);
//        cardView6 = findViewById(R.id.cardView6);
////        cardView7 = findViewById(R.id.cardView7);
////        cardView8 = findViewById(R.id.cardView8);
//        rya = findViewById(R.id.rya);
//        rno = findViewById(R.id.rno);
//        ya = findViewById(R.id.ya);
//        no = findViewById(R.id.no);
//        rya2 = findViewById(R.id.rya2);
//        rno2 = findViewById(R.id.rno2);
//        ya2 = findViewById(R.id.ya2);
//        no2 = findViewById(R.id.no2);
//        pass = findViewById(R.id.pass);
//        fail = findViewById(R.id.fail);
//        tpass = findViewById(R.id.tpass);
//        tfail = findViewById(R.id.tfail);
//        tambah1 = findViewById(R.id.tambah1);
//        tambah2 = findViewById(R.id.tambah2);
//        tambah3 = findViewById(R.id.tambah3);
////        tambah4 = findViewById(R.id.tambah4);
////        tambah5 = findViewById(R.id.tambah5);
//        foto1 = findViewById(R.id.foto1);
//        foto2 = findViewById(R.id.foto2);
//        foto3 = findViewById(R.id.foto3);
////        foto4 = findViewById(R.id.foto4);
////        foto5 = findViewById(R.id.foto5);
//        tindakan1 = findViewById(R.id.tindakan1);
//        tindakan2 = findViewById(R.id.tindakan2);
//        tindakan3 = findViewById(R.id.tindakan3);
//        tindakan4 = findViewById(R.id.tindakan4);
//        tindakan5 = findViewById(R.id.tindakan5);

//        kemali.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent kembali = new Intent(InspeksiKedua.this, InspeksiAwal.class);
//                startActivity(kembali);
//                onBackPressed();
//                finish();
//            }
//        });



        scard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qrecyclerview.getVisibility() == View.VISIBLE){
                    qrecyclerview.setVisibility(View.GONE);
                }else{
                    qrecyclerview.setVisibility(View.VISIBLE);
                }
//                if (cardview1.getVisibility() == View.VISIBLE) {
//                    cardview1.setVisibility(View.GONE);
//                    cardView2.setVisibility(View.GONE);
//                } else {
//                    cardview1.setVisibility(View.VISIBLE);
//                    cardView2.setVisibility(View.VISIBLE);
//                }
            }
        });

        scard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (cardView4.getVisibility() == View.VISIBLE) {
//                    cardView4.setVisibility(View.GONE);
//                    cardView5.setVisibility(View.GONE);
//                    cardView6.setVisibility(View.GONE);
////                    cardView7.setVisibility(View.GONE);
////                    cardView8.setVisibility(View.GONE);
//                } else {
//                    cardView4.setVisibility(View.VISIBLE);
//                    cardView5.setVisibility(View.VISIBLE);
//                    cardView6.setVisibility(View.VISIBLE);
////                    cardView7.setVisibility(View.VISIBLE);
////                    cardView8.setVisibility(View.VISIBLE);
//                }
            }
        });
//
//        rya.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int abucard = getResources().getColor(R.color.abucard);
//                if (rya.getCardBackgroundColor() == ColorStateList.valueOf(Color.WHITE)) {
//                    rya.setCardBackgroundColor(Color.GREEN);
//                    ya.setTextColor(Color.WHITE);
//                } else {
//                    rya.setCardBackgroundColor(Color.WHITE);
//                    ya.setTextColor(abucard);
//                }
//
//            }
//        });
//
//        rno.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int abucard = getResources().getColor(R.color.abucard);
//                if (rno.getCardBackgroundColor() == ColorStateList.valueOf(Color.WHITE)) {
//                    rno.setCardBackgroundColor(Color.RED);
//                    no.setTextColor(Color.WHITE);
//                } else {
//                    rno.setCardBackgroundColor(Color.WHITE);
//                    no.setTextColor(abucard);
//                }
//
//            }
//        });
//
//        rya2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int abucard = getResources().getColor(R.color.abucard);
//                if (rya2.getCardBackgroundColor() == ColorStateList.valueOf(Color.WHITE)) {
//                    rya2.setCardBackgroundColor(Color.GREEN);
//                    ya2.setTextColor(Color.WHITE);
//                } else {
//                    rya2.setCardBackgroundColor(Color.WHITE);
//                    ya2.setTextColor(abucard);
//                }
//
//            }
//        });
//
//        rno2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int abucard = getResources().getColor(R.color.abucard);
//                if (rno2.getCardBackgroundColor() == ColorStateList.valueOf(Color.WHITE)) {
//                    rno2.setCardBackgroundColor(Color.RED);
//                    no2.setTextColor(Color.WHITE);
//                } else {
//                    rno2.setCardBackgroundColor(Color.WHITE);
//                    no2.setTextColor(abucard);
//                }
//
//            }
//        });
//
//        pass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int abucard = getResources().getColor(R.color.abucard);
//                if (pass.getCardBackgroundColor() == ColorStateList.valueOf(Color.WHITE)) {
//                    pass.setCardBackgroundColor(Color.GREEN);
//                    tpass.setTextColor(Color.WHITE);
//                } else {
//                    pass.setCardBackgroundColor(Color.WHITE);
//                    tpass.setTextColor(abucard);
//                }
//
//            }
//        });
//
//        fail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int abucard = getResources().getColor(R.color.abucard);
//                if (fail.getCardBackgroundColor() == ColorStateList.valueOf(Color.WHITE)) {
//                    fail.setCardBackgroundColor(Color.RED);
//                    tfail.setTextColor(Color.WHITE);
//                } else {
//                    fail.setCardBackgroundColor(Color.WHITE);
//                    tfail.setTextColor(abucard);
//                }
//
//            }
//        });
//
//        tambah1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tambahcatatan();
//            }
//        });
//        tambah2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tambahcatatan();
//            }
//        });
//        tambah3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tambahcatatan();
//            }
//        });
//        tambah4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tambahcatatan();
//            }
//        });
//        tambah5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tambahcatatan();
//            }
//        });

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttd();
            }
        });

//        foto1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ambilfoto();
//            }
//        });
//
//        foto2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ambilfoto();
//            }
//        });
//        foto3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ambilfoto();
//            }
//        });
//        foto4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ambilfoto();
//            }
//        });
//        foto5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ambilfoto();
//            }
//        });
//
//        tindakan1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tindakan();
//            }
//        });
//        tindakan2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tindakan();
//            }
//        });
//        tindakan3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tindakan();
//            }
//        });
//        tindakan4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tindakan();
//            }
//        });
//        tindakan5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tindakan();
//            }
//        });
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
