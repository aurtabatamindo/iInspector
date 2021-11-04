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
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.ajts.androidmads.fontutils.FontUtils;
import com.example.iinspector.SendNotificationPack.APIService;
import com.example.iinspector.SendNotificationPack.Client;
import com.example.iinspector.SendNotificationPack.Data;
import com.example.iinspector.SendNotificationPack.MyResponse;
import com.example.iinspector.SendNotificationPack.NotificationSender;
import com.example.iinspector.ui.gallery.GalleryFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InspeksiAwal extends AppCompatActivity {

    Button kemali,lanjutkan;
    TextView tambah1,tambah2,tambah3, foto1, foto2, foto3,atindakan1,atindakan2,atindakan3;

    //camera
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    //notif
    private APIService apiService;
    final private String admin1 = "wtBD3Nb2S6UO1O5tx1RQLYV7lcn1";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeksi_awal);

        //notifservice
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        lanjutkan = findViewById(R.id.btnnext);
        tambah1 = findViewById(R.id.tambah1);
        tambah2 = findViewById(R.id.tambah2);
        tambah3 = findViewById(R.id.tambah3);

        foto3 = findViewById(R.id.afoto3);

        atindakan3 = findViewById(R.id.atindakan3);


        //cuaca
        weatherData = findViewById(R.id.cuaca);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "Lato-Bold.ttf");
        FontUtils fontUtils = new FontUtils();
        fontUtils.applyFontToView(weatherData, typeface);
        getCurrentData();



        lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lanjut = new Intent(InspeksiAwal.this, InspeksiKedua.class);
                startActivity(lanjut);
                finish();
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

                        FirebaseDatabase.getInstance().getReference().child("Tokens").child(admin1).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String usertoken = dataSnapshot.getValue(String.class);
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