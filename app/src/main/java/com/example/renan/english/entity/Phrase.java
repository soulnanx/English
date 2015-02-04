package com.example.renan.english.entity;

import android.content.Context;

import com.example.renan.english.app.App;
import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;
import java.util.Map;

@ParseClassName("Phrase")
public class Phrase extends ParseObject {

    public static final String TITLE = "title";
    public static final String NOTE = "note";

    public static void findAllByNotes(List<Note> notes, FindCallback<Phrase> callback){
        ParseQuery queryPhrase = ParseQuery.getQuery(Phrase.class);
        queryPhrase.fromLocalDatastore();
        queryPhrase.whereContainedIn("note", notes);
        queryPhrase.findInBackground(callback);
    }

    public static void findAllByNote(Note note, FindCallback<Phrase> callback) {
        ParseQuery query = ParseQuery.getQuery(Phrase.class);
        query.fromLocalDatastore();
        query.whereEqualTo(NOTE, note);
        query.findInBackground(callback);
    }

    public static List<Phrase> findAllByNote(Note note) throws ParseException {
        ParseQuery query = ParseQuery.getQuery(Phrase.class);
        query.fromLocalDatastore();
        query.whereEqualTo(NOTE, note);
        return query.find();
    }

    public void savePhrase(Context context) throws ParseException {
        Map<Note, List<Phrase>> cacheList =((App)context.getApplicationContext()).cacheList;
        cacheList.get(this.getNote()).add(this);
        cacheList.put(this.getNote(), cacheList.get(this.getNote()));
        pinInBackground();
    }

    public String getTitle(){
        return getString(TITLE);
    }
    public void setTitle(String title){
        put(TITLE, title);
    }

    public Note getNote(){
        return (Note)getParseObject(NOTE);
    }
    public void setNote(Note note){
        put(NOTE, note);
    }

}