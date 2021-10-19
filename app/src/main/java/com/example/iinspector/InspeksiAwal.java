package com.example.iinspector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.iinspector.ui.gallery.GalleryFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InspeksiAwal extends AppCompatActivity {

    Button kemali,lanjutkan;
    TextView tambah1,tambah2,tambah3, foto1, foto2, foto3,atindakan1,atindakan2,atindakan3;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeksi_awal);

        kemali = findViewById(R.id.btnback);
        lanjutkan = findViewById(R.id.btnnext);
        tambah1 = findViewById(R.id.tambah1);
        tambah2 = findViewById(R.id.tambah2);
        tambah3 = findViewById(R.id.tambah3);
        foto1 = findViewById(R.id.afoto1);
        foto2 = findViewById(R.id.afoto2);
        foto3 = findViewById(R.id.afoto3);
        atindakan1 = findViewById(R.id.atindakan1);
        atindakan2 = findViewById(R.id.atindakan2);
        atindakan3 = findViewById(R.id.atindakan3);

        kemali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembali = new Intent(InspeksiAwal.this, Side.class);
                startActivity(kembali);
                onBackPressed();
                finish();
            }
        });

        lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lanjut = new Intent(InspeksiAwal.this, InspeksiKedua.class);
                startActivity(lanjut);
                onBackPressed();
            }
        });

        tambah1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahcatatan();
            }
        });
        tambah2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahcatatan();
            }
        });
        tambah3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahcatatan();
            }
        });

        foto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambilfoto();
            }
        });

        foto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambilfoto();
            }
        });
        foto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambilfoto();
            }
        });

        atindakan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tindakan();
            }
        });
        atindakan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tindakan();
            }
        });
        atindakan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tindakan();
            }
        });
    }
    void tambahcatatan() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(InspeksiAwal.this);
        alertDialog.setTitle("Tambah Catatan");

        final EditText input = new EditText(InspeksiAwal.this);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
            LayoutInflater layoutInflater = LayoutInflater.from(InspeksiAwal.this);
            View promptView = layoutInflater.inflate(R.layout.hasilfoto, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspeksiAwal.this);
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

    private void tindakan() {
        dialog = new AlertDialog.Builder(InspeksiAwal.this);
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
}