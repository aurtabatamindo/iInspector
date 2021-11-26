package com.example.iinspector;


import com.google.firebase.Timestamp;

public class SendDataInspesiAwal {
    private String lokasi;
    private String team;
    private String suhu;
    private String tgl;



    public SendDataInspesiAwal(String lokasi, String team , String suhu, String tgl) {
        this.lokasi = lokasi;
        this.team = team;
        this.suhu = suhu;
        this.tgl = tgl;

    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getSuhu() {
        return suhu;
    }

    public void setSuhu(String suhu) {
        this.suhu = suhu;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }
}
