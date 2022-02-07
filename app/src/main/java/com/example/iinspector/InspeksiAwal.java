package com.example.iinspector;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.ajts.androidmads.fontutils.FontUtils;
import com.example.iinspector.SendNotificationPack.APIService;
import com.example.iinspector.SendNotificationPack.Client;
import com.example.iinspector.SendNotificationPack.Data;
import com.example.iinspector.SendNotificationPack.MyResponse;
import com.example.iinspector.SendNotificationPack.NotificationSender;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InspeksiAwal extends AppCompatActivity {

    Button kemali, lanjutkan;
    TextView tambah1, tambah2, tambah3, foto1, foto2, foto3, atindakan1, atindakan2, atindakan3, tglview, lokasi ,alamat,titleInspegsi;

    //getlocation plus adress
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    //camera
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    //notif
    private APIService apiService;
    final private String admin1 = "4kCznhvJW5aZfz4hkBGme3ZvV1r2";
    final private String title = "!TEMUAN!";
    final private String pesan = "!RESIKO HIGHT!";


    //tindakan
    FloatingActionButton fab;
    Toolbar toolbar;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;


    //Cuaca
    public static String BaseUrl = "https://api.openweathermap.org/";
    public static String AppId = "df844c5fe47723cce56c4601631f9b64";
    public static String lat = "1.0684909356188659";
    public static String lon = "104.02529037437219";
    public static String metric = "metric";
    private TextView weatherData;
    private ResultReceiver resultReceiver;

    //SpinerTeam
    Spinner spinner;



    //cloudfirebase
    FirebaseFirestore dbs = FirebaseFirestore.getInstance();

    //String
    String alm;
    String idtemplate;
    String documentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeksi_awal);



        //putextra
        documentId = getIntent().getStringExtra("doc");

        //gps
        resultReceiver = new AddressResultReceiver(new Handler());

        //tgl & jam
        String tgl = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

        //filterTeam
        spinner = (Spinner) findViewById(R.id.filterteam);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filterteam, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
//        spinner.getBackground().setColorFilter(Color.parseColor("#C0C2D1"), PorterDuff.Mode.SRC_ATOP);

        //getlocation plus adress
        findViewById(R.id.btnlokasi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(InspeksiAwal.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION);
                } else {
                    getlokasi();
                }
            }
        });

        //notifservice
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        lanjutkan = findViewById(R.id.btnnext);
        tambah1 = findViewById(R.id.tambah1);
        tambah2 = findViewById(R.id.tambah2);
        tambah3 = findViewById(R.id.tambah3);
        foto3 = findViewById(R.id.afoto3);
        atindakan3 = findViewById(R.id.atindakan3);
        tglview = findViewById(R.id.tglView);
        lokasi = findViewById(R.id.hasilLokasi);
        alamat = findViewById(R.id.alamat);
        titleInspegsi = findViewById(R.id.titleInspeksi);

        //settgl & setjam
        tglview.setText(tgl);

        //cuaca
        weatherData = findViewById(R.id.cuaca);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "Lato-Bold.ttf");
        FontUtils fontUtils = new FontUtils();
        fontUtils.applyFontToView(weatherData, typeface);
        getCurrentData();

        //gettile
        dbs.collection("templates")
                .document(documentId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String title = (String) task.getResult().get("templateTitle");
                titleInspegsi.setText(title);
            }
        });

        lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String lok = lokasi.getText().toString().trim();
                    String spinertim = spinner.getSelectedItem().toString().trim();
                    String pilih = "Pilih Team";
                    String suhu = weatherData.getText().toString();
                    String itgl = tgl;
                    String alamt = alamat.getText().toString();

                    if (lok.isEmpty()&& spinertim.equals(pilih)){
                        Snackbar.make(findViewById(R.id.inspeksiawal),"Lokasi & Team Tidak Boleh Kosong",Snackbar.LENGTH_LONG).show();
                    }
                    else if (lok.isEmpty()) {
                              Snackbar.make(findViewById(R.id.inspeksiawal),"Lokasi Tidak Boleh Kosong",Snackbar.LENGTH_LONG).show();
                    }
                    else if (spinertim.equals(pilih)){
                             Snackbar.make(findViewById(R.id.inspeksiawal),"Harap pilih team inspeksi",Snackbar.LENGTH_LONG).show();
                    }
                    else{


                        dbs = FirebaseFirestore.getInstance();
                        dbs.collection("templates").document(documentId)
                                .update("templateLocation",lok,
                                        "templateTeam",spinertim,
                                        "templateTemperature",suhu,
                                        "templateDate",itgl,
                                        "templateAddress",alamt)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            //pushtotemplates
                                            dbs.collection("templates").document(documentId).get()
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()){
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()) {
                                                            Log.d("cekdata", document.toString());

                                                        }
                                                    }

                                                }
                                            });


                                        } else {
                                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                        dbs.collection("templates").document(documentId)
                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                String author = (String) task.getResult().get("author");
                                String authorId = (String) task.getResult().get("authorId");
                                String templateAddress = (String) task.getResult().get("templateAddress");
                                String templateDate = (String) task.getResult().get("templateDate");
                                String templateDescription = (String) task.getResult().get("templateDescription");
                                String templateGroup = (String) task.getResult().get("templateGroup");
                                String templateLocation = (String) task.getResult().get("templateLocation");
                                String templateTeam = (String) task.getResult().get("templateTeam");
                                String templateTemperature = (String) task.getResult().get("templateTemperature");
                                String templateTitle = (String) task.getResult().get("templateTitle");

                                Map<String, Object> dataTemplate = new HashMap<>();
                                        dataTemplate.put("author",author);
                                        dataTemplate.put("authorId",authorId);
                                        dataTemplate.put("templateAddress",templateAddress);
                                        dataTemplate.put("templateDate",templateDate);
                                        dataTemplate.put("templateDescription",templateDescription);
                                        dataTemplate.put("templateGroup",templateGroup);
                                        dataTemplate.put("templateLocation",templateLocation);
                                        dataTemplate.put("templateTeam",templateTeam);
                                        dataTemplate.put("templateTemperature",templateTemperature);
                                        dataTemplate.put("templateTitle",templateTitle);

                                DocumentReference ref = dbs.collection("hasiltemplatestes").document();
                                idtemplate = ref.getId();
                                dbs.collection("hasiltemplatestes")
                                        .document(idtemplate)
                                        .set(dataTemplate);

                                Log.d("idtemplate", idtemplate);
                                Intent lanjut = new Intent(InspeksiAwal.this, InspeksiKetiga.class);
                                lanjut.putExtra("doc", documentId);
                                lanjut.putExtra("idtem", idtemplate);
                                startActivity(lanjut);
                                finish();
                            }
                        });

                    }

            }
        });


        tambah3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahcatatan();
            }
        });


        foto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambilfoto();
            }
        });

        atindakan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tindakan();
            }
        });
    }


    private void getlokasi() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(InspeksiAwal.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(InspeksiAwal.this)
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0){
                            int latesLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude = locationResult.getLocations().get(latesLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(latesLocationIndex).getLongitude();

                            lokasi.setText(String.format(
                                    "(%s,%s)",
                                    latitude,
                                    longitude
                            ));
                        Location location = new Location("providerNA");
                        location.setLatitude(latitude);
                        location.setLongitude(longitude);
                        fetcAddressFromLatLong(location);
                        }
                    }
                }, Looper.getMainLooper());


    }
    private void fetcAddressFromLatLong(Location location){
        Intent intent = new Intent(this,AddressService.class);
        intent.putExtra(Constants.RECEIVER,resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA,location);
        startService(intent);
    }
    private class AddressResultReceiver extends ResultReceiver{
         AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == Constants.SUCCESS_RESULT){
                alamat.setText(resultData.getString(Constants.RESULT_DATA_KEY));

            }else{
                Toast.makeText(InspeksiAwal.this,resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }
        }
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

        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0){
            if(grantResults [0] == PackageManager.PERMISSION_GRANTED){
                getlokasi();
            }else{
                Toast.makeText(this, "GPS permission denied", Toast.LENGTH_SHORT).show();
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

                        FirebaseDatabase.getInstance().getReference().child("Tokens").child(admin1).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String usertoken = dataSnapshot.getValue(String.class);
                                Log.d("usertoken",usertoken);
                                kirimnotif(usertoken, title.toString().trim(), pesan.toString().trim());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        kirim();
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

    void getCurrentData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService service = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = service.getCurrentWeatherData(lat, lon, metric, AppId);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    String stringBuilder =
//                            "Country: " +
//                            weatherResponse.sys.country +
//                            "\n" +
                            weatherResponse.main.temp + " °C" ;
//                                    +
//                            "\n" +
//                            "Temperature(Min): " +
//                            weatherResponse.main.temp_min +
//                            "\n" +
//                            "Temperature(Max): " +
//                            weatherResponse.main.temp_max +
//                            "\n" +
//                            "Humidity: " +
//                            weatherResponse.main.humidity +
//                            "\n" +
//                            "Pressure: " +
//                            weatherResponse.main.pressure;

                    weatherData.setText(stringBuilder);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                weatherData.setText(t.getMessage());
            }
        });
    }

    public void kirimnotif(String usertoken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Snackbar.make(findViewById(R.id.inspeksiawal),"Berhasil Mengirim Tindakan",Snackbar.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }

    public  void kirim(){
        String email = "aurtafalen@batamindo.co.id";
        String subject = "TEMUAN";
        String message = "RESIKO HIGH";

        String mEmail = email.toString();
        String mSubject = subject.toString();
        String mMessage = message.toString();

        JavaMailAPI javaMailAPI = new JavaMailAPI(InspeksiAwal.this, mEmail, mSubject, mMessage);
        javaMailAPI.execute();

        Snackbar.make(findViewById(R.id.inspeksiawal),"Berhasil Mengirim Tindakan",Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            Snackbar.make(findViewById(R.id.inspeksiawal),"Inspeksi sedang berjalan anda tidak bisa kembali sesuka hati !",Snackbar.LENGTH_INDEFINITE)
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