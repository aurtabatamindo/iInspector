package com.example.iinspector;


public class GetDataQuestion {
    private String pageTitle;




    public GetDataQuestion(String pageTitle ) {

        this.pageTitle = pageTitle;


    }

    //Add this
    public GetDataQuestion() {

    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }
}
