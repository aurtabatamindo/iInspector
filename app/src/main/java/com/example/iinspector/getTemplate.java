package com.example.iinspector;


public class getTemplate {
    private String author;
    private String authorId;
    private String createdAt;
    private String templateAddress;
    private String templateDate;
    private String templateDescription;
    private String templateGroup;
    private String templateLocation;
    private String templateTeam;
    private String templateTemperature;
    private String templateTitle;
    private String updateAt;



    public getTemplate(String author, String authorId , String createdAt, String templateAddress, String templateDate, String templateDescription, String templateGroup, String templateLocation, String templateTeam, String templateTemperature, String templateTitle, String updateAt) {
        this.author = author;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.templateAddress = templateAddress;
        this.templateDate = templateDate;
        this.templateDescription = templateDescription;
        this.templateGroup = templateGroup;
        this.templateLocation = templateLocation;
        this.templateTeam = templateTeam;
        this.templateTemperature = templateTemperature;
        this.templateTitle = templateTitle;
        this.updateAt = updateAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTemplateAddress() {
        return templateAddress;
    }

    public void setTemplateAddress(String templateAddress) {
        this.templateAddress = templateAddress;
    }

    public String getTemplateDate() {
        return templateDate;
    }

    public void setTemplateDate(String templateDate) {
        this.templateDate = templateDate;
    }

    public String getTemplateDescription() {
        return templateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    public String getTemplateGroup() {
        return templateGroup;
    }

    public void setTemplateGroup(String templateGroup) {
        this.templateGroup = templateGroup;
    }

    public String getTemplateLocation() {
        return templateLocation;
    }

    public void setTemplateLocation(String templateLocation) {
        this.templateLocation = templateLocation;
    }

    public String getTemplateTeam() {
        return templateTeam;
    }

    public void setTemplateTeam(String templateTeam) {
        this.templateTeam = templateTeam;
    }

    public String getTemplateTemperature() {
        return templateTemperature;
    }

    public void setTemplateTemperature(String templateTemperature) {
        this.templateTemperature = templateTemperature;
    }

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
