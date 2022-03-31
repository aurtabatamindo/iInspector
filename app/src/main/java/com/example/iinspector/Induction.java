package com.example.iinspector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Induction extends AppCompatActivity {
    TextInputEditText nama,perusahaan,status;
    Button selesai;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_induction);

        //loading
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Memproses Data...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

        nama = (TextInputEditText) findViewById (R.id.nama);
        perusahaan = (TextInputEditText) findViewById (R.id.company);
        status = (TextInputEditText) findViewById (R.id.statusInduction);
        selesai = (Button) findViewById (R.id.selesaiInduction);

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getNama = nama.getText().toString();
                String getPerusahaan = perusahaan.getText().toString();
                String getStatus = status.getText().toString();

                if (getNama.isEmpty() && getPerusahaan.isEmpty() && getStatus.isEmpty()){
                    nama.setError("Tidak boleh kosong !");
                    perusahaan.setError("Tidak boleh kosong !");
                    status.setError("Tidak Boleh Kosong !");
                    Toast.makeText(Induction.this, "Data Tidak lengkap !", Toast.LENGTH_SHORT).show();

                } else if (getNama.isEmpty()){
                    nama.setError("Tidak boleh kosong !");
                    Toast.makeText(Induction.this, "Data Tidak lengkap !", Toast.LENGTH_SHORT).show();

                }else if (getPerusahaan.isEmpty()){
                    perusahaan.setError("Tidak boleh kosong !");
                    Toast.makeText(Induction.this, "Data Tidak lengkap !", Toast.LENGTH_SHORT).show();

                }else if (getStatus.isEmpty()){
                    status.setError("Tidak Boleh Kosong !");
                    Toast.makeText(Induction.this, "Data Tidak lengkap !", Toast.LENGTH_SHORT).show();

                } else {
                    progress.show();

                    Map<String, Object> isiInduction = new HashMap<>();
                    isiInduction.put("nama",getNama);
                    isiInduction.put("prusahaan",getPerusahaan);
                    isiInduction.put("status",getStatus);
                    isiInduction.put("timeDate", FieldValue.serverTimestamp());

                    //firestore
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    //collection templates
                    CollectionReference push = db.collection("induction");

                    push.document()
                            .set(isiInduction).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progress.dismiss();
                            Toast.makeText(Induction.this, "Data Berhasil Ditambah !", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Induction.this,Side.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                }
            }
        });
    }
}