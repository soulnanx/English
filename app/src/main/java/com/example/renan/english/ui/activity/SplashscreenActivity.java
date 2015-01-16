package com.example.renan.english.ui.activity;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.example.renan.english.R;
import com.example.renan.english.entity.Note;
import com.example.renan.english.util.NavigationUtil;
import com.parse.ParseAnalytics;

public class SplashscreenActivity extends Activity {

    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NavigationUtil.navigate(SplashscreenActivity.this, DrawerLayoutMain.class);
            }
        }, 2000);

    }

//    private void loadValues(){
//        Note.findAllByUser(find(),(User) ParseUser.getCurrentUser());
//    }
//
//    private FindCallback find() {
//        return new FindCallback() {
//            @Override
//            public void done(List list, ParseException e) {
//                Phrase.findAllByNote(callbackFindPhrases(), list);
//            }
//        };
//    }

//    private FindCallback<Phrase> callbackFindPhrases() {
//        return new FindCallback<Phrase>() {
//            @Override
//            public void done(List<Phrase> phrases, ParseException e) {
//
//                for (Phrase phrase :  phrases){
//                    List<Phrase> phraseList = ((App)getApplication()).mapNotes.get(phrase.getNote());
//
//                    if (phraseList == null){
//                        phraseList = new ArrayList<>();
//                    }
//
//                    phraseList.add(phrase);
//                    ((App)getApplication()).mapNotes.put(phrase.getNote(), phraseList);
//                }
//            }
//        };
//    }


}
