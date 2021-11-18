package com.example.iinspector.ui.gallery;




import android.text.format.DateFormat;

import com.google.firebase.Timestamp;

import java.util.Date;

public class GetDataJadwal {

    private String group;
    private String templateTitle;


    public GetDataJadwal(String group , String templateTitle) {


        this.group = group;
        this.templateTitle = templateTitle;


    }

    //Add this
    public GetDataJadwal() {

    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }
}
