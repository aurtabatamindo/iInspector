package com.example.iinspector.ui.gallery;




import android.text.format.DateFormat;

import com.google.firebase.Timestamp;

import java.util.Date;

public class GetDataJadwal {



    private String jenisTugas;
    private String judulKegiatan;
    private String tim;
    private Timestamp waktuMulai;
    private String waktuSelesai;
    private String status;

    public GetDataJadwal(String jenisTugas, String judulKegiatan, String tim, Timestamp waktuMulai, String waktuSelesai, String status) {



        this.jenisTugas = jenisTugas;
        this.judulKegiatan = judulKegiatan;
        this.tim = tim;
        this.waktuMulai = waktuMulai;
        this.waktuSelesai = waktuSelesai;
        this.status = status;

    }

    //Add this
    public GetDataJadwal() {

    }


    public String getJenisTugas() {
        return jenisTugas;
    }

    public void setJenisTugas(String jenisTugas) {
        this.jenisTugas = jenisTugas;
    }

    public String getJudulKegiatan() {
        return judulKegiatan;
    }

    public void setJudulKegiatan(String judulKegiatan) {
        this.judulKegiatan = judulKegiatan;
    }

    public String getWaktuMulai() {
        long miliseconds = waktuMulai.toDate().getTime();
        String dateString = DateFormat.format("d MMMM, yyyy HH:mm", new Date(miliseconds)).toString();
        return dateString;
    }

    public void setWaktuMulai(Timestamp waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public String getTim() {
        return tim;
    }

    public void setTim(String tim) {
        this.tim = tim;
    }

    public String getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(String waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
