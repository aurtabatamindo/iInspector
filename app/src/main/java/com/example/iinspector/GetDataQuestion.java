package com.example.iinspector;


public class GetDataQuestion {
    private String description;




    public GetDataQuestion(String description ) {

        this.description = description;


    }

    //Add this
    public GetDataQuestion() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
