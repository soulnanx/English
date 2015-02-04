package com.example.renan.english.entity;

import android.content.Context;

import com.example.renan.english.app.App;
import com.parse.DeleteCallback;
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

    public void saveNote(Context context, SaveCallback callback) {
        ((App)context.getApplicationContext()).cacheList.put(this, new ArrayList<Phrase>());
        pinInBackground(callback);
    }

    public void deleteNote(Context context, final DeleteCallback deleteCallback) {
        ((App)context.getApplicationContext()).cacheList.remove(this);
        unpinInBackground();

        Phrase.findAllByNote(this, new FindCallback<Phrase>() {
            @Override
            public void done(List<Phrase> phrases, ParseException e) {
                ParseObject.unpinAllInBackground(phrases, deleteCallback);
            }
        });

    }

    public static void findAll(int type, FindCallback<Note> callback) {
        ParseQuery query = ParseQuery.getQuery(Note.class);
        query.fromLocalDatastore();
        query.whereEqualTo(TYPE, type);
        query.findInBackground(callback);
    }

//    public void setUser(User user){
//        put(USER, user);
//    }
//
//    public User getUser(){
//        return (User)getParseUser(USER);
//    }

    public String getTitleEn() {
        return getString(TITLE_EN);
    }

    public void setTitleEn(String titleEn) {
        put(TITLE_EN, titleEn);
    }

    public String getTitlePt() {
        return getString(TITLE_PT);
    }

    public void setTitlePt(String titlePt) {
        put(TITLE_PT, titlePt);
    }

    public int getType() {
        return getInt(TYPE);
    }

    public void setType(int type) {
        put(TYPE, type);
    }


}