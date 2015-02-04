package com.example.renan.english.entity;


import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Message")
public class Message extends ParseObject {

    public static final String NAME = "name";
    public static final String BODY = "body";
    public static final String DIALOG = "dialog";
    public static final String ORDER = "order";

    public String getName(){
        return getString(NAME);
    }
    public void setName(String name){
        put(NAME, name);
    }

    public String getBody(){
        return getString(BODY);
    }
    public void setBody(String body){
        put(BODY, body);
    }

    public ParseObject getDialog(){
        return getParseObject(DIALOG);
    }
    public void setDialog(Dialog dialog){
        put(DIALOG, dialog);
    }

    public String getOrder(){
        return getString(ORDER);
    }
    public void setOrder(String order){
        put(ORDER, order);
    }



}