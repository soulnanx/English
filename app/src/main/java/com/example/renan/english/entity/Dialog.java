package com.example.renan.english.entity;


import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Dialog")
public class Dialog extends ParseObject {

    public static final String TITLE = "title";
    public static final String USER = "user";

    public String getTitle(){
        return getString(TITLE);
    }
    public void setTitle(String title){
        put(TITLE, title);
    }


    public String getUser(){
        return getString(USER);
    }
    public void setUser(String title){
        put(USER, title);
    }


}