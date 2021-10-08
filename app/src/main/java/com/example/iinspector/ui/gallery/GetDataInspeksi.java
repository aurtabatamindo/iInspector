package com.example.iinspector.ui.gallery;


public class GetDataInspeksi {

    private String devisi;
    private String judul;
    private String lokasi;
    private String tanggal;
    private String team;

    public GetDataInspeksi(String devisi, String judul , String lokasi, String tanggal, String team) {
        this.devisi = devisi;
        this.judul = judul;
        this.lokasi = lokasi;
        this.tanggal = tanggal;
        this.team = team;



    }

    //Add this
    public GetDataInspeksi() {

    }

    public String getDevisi() {
        return devisi;
    }

    public void setDevisi(String devisi) {
        this.devisi = devisi;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
