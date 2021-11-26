package com.example.iinspector.ui.main;


public class GetDataTodo {
    private String authorTitle;
    private String group;
    private String templateDesctiption;
    private String templateTitle;
    private String status;


    public GetDataTodo(String authorTitle , String group , String templateDesctiption, String templateTitle, String status) {

        this.authorTitle = authorTitle;
        this.group = group;
        this.templateDesctiption = templateDesctiption;
        this.templateTitle = templateTitle;
        this.status = status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
