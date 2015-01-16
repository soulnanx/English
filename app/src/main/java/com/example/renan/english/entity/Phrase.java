package com.example.renan.english.entity;

import android.support.annotation.NonNull;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Phrase")
public class Phrase extends ParseObject {

    public static final String TITLE = "title";
    public static final String NOTE = "note";

    public static void findAllByNotes(FindCallback<Phrase> callback, List<Note> notes){
        ParseQuery queryPhrase = ParseQuery.getQuery(Phrase.class);
        queryPhrase.whereContainedIn("note", notes);
        queryPhrase.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        queryPhrase.findInBackground(callback);
    }

    public static List<Phrase> findAllByNote(Note note) {
        ParseQuery query = ParseQuery.getQuery(Phrase.class);
        query.fromLocalDatastore();
        query.whereEqualTo(NOTE, note);
        List<Phrase> phrases = (List<Phrase>) query.findInBackground().getResult();
        return phrases == null ? new ArrayList<Phrase>() : phrases;
    }

    public void savePhrase() throws ParseException {
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