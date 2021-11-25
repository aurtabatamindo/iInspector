package com.example.iinspector.ui.main;


public class GetDataTodo {
    private String authorTitle;
    private String group;
    private String templateDesctiption;
    private String templateTitle;



    public GetDataTodo(String authorTitle , String group , String templateDesctiption, String templateTitle) {

        this.authorTitle = authorTitle;
        this.group = group;
        this.templateDesctiption = templateDesctiption;
        this.templateTitle = templateTitle;


    }

    //Add this
    public GetDataTodo() {

    }

    public String getAuthorTitle() {
        return authorTitle;
    }

    public void setAuthorTitle(String authorTitle) {
        this.authorTitle = authorTitle;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTemplateDesctiption() {
        return templateDesctiption;
    }

    public void setTemplateDesctiption(String templateDesctiption) {
        this.templateDesctiption = templateDesctiption;
    }

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }
}
