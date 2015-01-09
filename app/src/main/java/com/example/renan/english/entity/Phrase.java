package com.example.renan.english.entity;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Phrase")
public class Phrase extends ParseObject {

    public static final String TITLE = "title";
    public static final String NOTE = "note";

    public String getTitle(){
        return getString(TITLE);
    }
    public void setTitle(String title){
        put(TITLE, title);
    }

    public ParseObject getNote(){
        return getParseObject(NOTE);
    }
    public void setNote(Note note){
        put(NOTE, note);
    }

}