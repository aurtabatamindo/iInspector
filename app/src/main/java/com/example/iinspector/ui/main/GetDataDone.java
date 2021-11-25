package com.example.iinspector.ui.main;

import android.text.format.DateFormat;

import com.google.firebase.Timestamp;

import java.util.Date;

public class GetDataDone {
    private Timestamp date;
    private String group;
    private String location;
    private String team;
    private String templateTitle;



    public GetDataDone(Timestamp date , String group , String location, String team , String templateTitle) {

        this.date = date;
        this.group = group;
        this.team = team;
        this.templateTitle = templateTitle;


    }

    //Add this
    public GetDataDone() {

    }

    public String getDate() {
        long miliseconds = date.toDate().getTime();
        String dateString = DateFormat.format("d MMM, yyyy", new Date(miliseconds)).toString();
        return dateString;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }
}
