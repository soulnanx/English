package com.example.renan.english.util;

import android.content.Context;

import com.example.renan.english.app.App;
import com.example.renan.english.entity.Note;

/**
 * Created by renan on 1/20/15.
 */
public class NotesUtil {
    public static int qtdPhrasesInNote(Context context, Note note){
        return ((App)context.getApplicationContext()).cacheList.get(note).size();
    }
}
