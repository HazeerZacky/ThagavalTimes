package com.example.thagavaltimes;

import java.util.Map;

public class MainModel {

    private Map<String, String> timestamp;
    String title, category, description, purl;

    MainModel() {

    }

    //Constructor
    public MainModel(String title, String category, String description, String purl) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.purl = purl;
    }

    /*
    public void setTimestamp(Map<String, String> timeStamp){
        this.timestamp= timestamp;
    }

     */

    /*
    public Map<String, String> getTimestamp(){
        return timestamp;
    }

     */

    //Getter and Setter ---------------------------------------------------------------------------
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
