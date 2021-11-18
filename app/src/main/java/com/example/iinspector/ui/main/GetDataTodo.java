package com.example.iinspector.ui.main;

public class GetDataTodo {
    private String group;
    private String templateTitle;


    public GetDataTodo(String group , String templateTitle) {


        this.group = group;
        this.templateTitle = templateTitle;


    }

    //Add this
    public GetDataTodo() {

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
