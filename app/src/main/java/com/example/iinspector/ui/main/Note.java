package com.example.iinspector.ui.main;

public class Note {
    private String templateTitle;
    private String group;


    public Note() {
        //empty constructor needed
    }

    public Note(String templateTitle, String group) {
        this.templateTitle = templateTitle;
        this.group = group;

    }

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
