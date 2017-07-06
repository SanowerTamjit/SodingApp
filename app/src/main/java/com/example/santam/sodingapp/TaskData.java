package com.example.santam.sodingapp;


public class TaskData {

    private String id, name, desc, dateCrt, dateUp;

    public TaskData(String id, String name, String desc, String dateCrt, String dateUp) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.dateCrt = dateCrt;
        this.dateUp = dateUp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDateCrt() {
        return dateCrt;
    }

    public void setDateCrt(String dateCrt) {
        this.dateCrt = dateCrt;
    }

    public String getDateUp() {
        return dateUp;
    }

    public void setDateUp(String dateUp) {
        this.dateUp = dateUp;
    }






}
