package com.example.renan.english.entity;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ParseClassName("Note")
public class Note extends ParseObject implements Serializable {

    public static final String TITLE_EN = "titleEn";
    public static final String TITLE_PT = "titlePt";
    public static final String TYPE = "type";
//    public static final String USER = "user";

    public static final int MAJORITY = 1;
    public static final int MINOR = 2;

    public void saveNote() {
        pinInBackground();
    }

    public void deleteNote() {
        unpinAllInBackground();
        unpinAllInBackground(Phrase.findAllByNote(this));
    }

    public static List<Note> findAll(int type) {
        ParseQuery query = ParseQuery.getQuery(Note.class);
        query.fromLocalDatastore();
        query.whereEqualTo(TYPE, type);
        List<Note> notes = (List<Note>) query.findInBackground().getResult();
        return notes == null ? new ArrayList<Note>() : notes;
    }

//    public void setUser(User user){
//        put(USER, user);
//    }
//
//    public User getUser(){
//        return (User)getParseUser(USER);
//    }

    public String getTitleEn(){
        return getString(TITLE_EN);
    }
    public void setTitleEn(String titleEn){
        put(TITLE_EN, titleEn);
    }

    public String getTitlePt(){
        return getString(TITLE_PT);
    }
    public void setTitlePt(String titlePt){
        put(TITLE_PT, titlePt);
    }

    public int getType(){
        return getInt(TYPE);
    }
    public void setType(int type){
        put(TYPE, type );
    }


}