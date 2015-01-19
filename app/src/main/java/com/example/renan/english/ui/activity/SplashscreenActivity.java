package com.example.renan.english.ui.activity;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.example.renan.english.R;
import com.example.renan.english.app.App;
import com.example.renan.english.entity.Note;
import com.example.renan.english.entity.Phrase;
import com.example.renan.english.util.NavigationUtil;
import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashscreenActivity extends Activity {

    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 2000);

        updateCacheList();

    }

    public void updateCacheList() {

        ParseQuery<Note> query = ParseQuery.getQuery(Note.class);
        query.fromLocalDatastore();

        query.findInBackground(new FindCallback<Note>() {
            @Override
            public void done(final List<Note> notes, ParseException e) {
                for (Note note : notes){
                    ((App)getApplication()).cacheList.put(note, new ArrayList<Phrase>());
                }

                Phrase.findAllByNotes(notes, new FindCallback<Phrase>() {
                    @Override
                    public void done(List<Phrase> phrases, ParseException e) {
                        for (Phrase phrase : phrases) {
                            List<Phrase> phraseList = ((App)getApplication()).cacheList.get(phrase.getNote());

                            if (phraseList == null) {
                                phraseList = new ArrayList<>();
                            }

                            phraseList.add(phrase);
                            ((App)getApplication()).cacheList.put(phrase.getNote(), phraseList);
                        }
                        NavigationUtil.navigate(SplashscreenActivity.this, DrawerLayoutMain.class);
                        finish();

                    }
                });
            }
        });
    }

}
