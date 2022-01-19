package com.example.iinspector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.iinspector.ui.main.GetDataTodo;
import com.example.iinspector.ui.main.TodoHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Formattable;
import java.util.List;

public class InspeksiKetiga extends AppCompatActivity {

    //firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //collection templates
    CollectionReference pages = db.collection("templates");
    //Collection hasiltemplates
    CollectionReference df = db.collection("hasiltemplatestes");

    //penamaan
    TextView qtitle,berikutnya;

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

        //Page1
        showtitle();

        //Button
        buttonberiktunya();


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
                            String desc = (String) document.get("description");
                            Log.d("getdes",desc);

                            //Get type
                            String type = (String) document.get("type");
                            Log.d("gettype",type);

                            LinearLayoutCompat myLinearLayout = findViewById(R.id.lPertanyaan);

                            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                            params.setMargins(30, 20, 30, 20);

                            LinearLayoutCompat.LayoutParams params2 = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.FILL_PARENT, LinearLayoutCompat.LayoutParams.FILL_PARENT);
                            params2.setMargins(50, 5, 50, 5);

                            LinearLayoutCompat.LayoutParams params3 = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                            params3.setMargins(10, 20, 10, 20);

                            // Build Description
                            final TextView Description = new TextView(InspeksiKetiga.this);
                            Description.setBackgroundResource(R.drawable.cardpertanyaan);
                            Description.setTextSize(11);
                            Description.setPaddingRelative(50, 25, 10, 25);
                            Description.setTypeface(null, Typeface.ITALIC);
                            Description.setTextColor(Color.parseColor("#767676"));
                            Description.setLayoutParams(params);
                            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.action_icon);
                            Description.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                            Description.setText("Pertanyaan :" + "\n" + desc);

                            //Build Section
                            final TextView Section = new TextView(InspeksiKetiga.this);
                            Section.setLayoutParams(params3);
                            Section.setBackgroundResource(R.drawable.cardsection);
                            Section.setTextSize(13);
                            Section.setPaddingRelative(50, 25, 10, 25);
                            Section.setTypeface(null, Typeface.ITALIC);
                            Section.setTextColor(Color.parseColor("#767676"));
                            Section.setLayoutParams(params);
                            Drawable img1 = getApplicationContext().getResources().getDrawable(R.drawable.down_icon);
                            Section.setCompoundDrawablesWithIntrinsicBounds(null, null, img1, null);
                            Section.setText(desc);

                            // Type = Text
                            final EditText Answer = new EditText(InspeksiKetiga.this);
                            Answer.setLayoutParams(params);
                            Answer.setTextSize(11);
                            Answer.setHint("Jawab disini");

                            allAnswer.add(Answer);
                            idContent = document.getId();
                            Log.d("getidContent", idContent);


                            if (type.equals("section")){
                                myLinearLayout.addView(Section);
                                idDocSection = document.getId();
                                Log.d("idSection",idDocSection);

                                showContentSection();

                            }else {
                                myLinearLayout.addView(Description);
                                myLinearLayout.addView(Answer);

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
                            String desc = (String) document.get("description");
                            Log.d("getdes", desc);

                            //Get type
                            String type = (String) document.get("type");
                            Log.d("gettype", type);

                            LinearLayoutCompat myLinearLayout = findViewById(R.id.lPertanyaan);

                            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                            params.setMargins(30, 20, 30, 20);

                            LinearLayoutCompat.LayoutParams params2 = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.FILL_PARENT, LinearLayoutCompat.LayoutParams.FILL_PARENT);
                            params2.setMargins(50, 5, 50, 5);

                            LinearLayoutCompat.LayoutParams params3 = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                            params3.setMargins(10, 20, 10, 20);

                            // Build Description
                            final TextView DescriptionDescription = new TextView(InspeksiKetiga.this);
                            DescriptionDescription.setBackgroundResource(R.drawable.cardpertanyaan);
                            DescriptionDescription.setTextSize(11);
                            DescriptionDescription.setPaddingRelative(50, 25, 10, 25);
                            DescriptionDescription.setTypeface(null, Typeface.ITALIC);
                            DescriptionDescription.setTextColor(Color.parseColor("#767676"));
                            DescriptionDescription.setLayoutParams(params);
                            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.action_icon);
                            DescriptionDescription.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                            DescriptionDescription.setText("Pertanyaan :" + "\n" + desc);

                            // Type = Text
                            final EditText Answer = new EditText(InspeksiKetiga.this);
                            Answer.setLayoutParams(params);
                            Answer.setTextSize(11);
                            Answer.setHint("Jawab disini");

                            myLinearLayout.addView(DescriptionDescription);
                            myLinearLayout.addView(Answer);
                        }
                    }
                }
            }
        });
    }

    private void buttonberiktunya() {
        berikutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();

            }

            private void getdata() {
                pages.document(documentId)
                        .collection("pages")
                        .document(idPages)
                        .collection("contents")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.getId());
                            }
                            Log.d("testgetlist", list.toString());
                            updateData(list);
                        }
                    }

                    private void updateData(List<String> list) {

                        //get answer
                        String[] strings = new String[allAnswer.size()];
                        for (int i = 0; i < allAnswer.size(); i++) {

                            strings[i] = allAnswer.get(i).getText().toString();
                            Log.d("getAnswer", strings[i]);

                        }

                        // Iterate through the list
                        for (int k = 0; k < list.size(); k++) {

                            pages.document(documentId)
                                    .collection("pages")
                                    .document(idPages)
                                    .collection("contents")
                                    .document(list.get(k))
                                    .update("answer", strings[k]);

                        }
                    }
                });
            }
        });
    }
}