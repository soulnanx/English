package com.example.renan.english.util;

import com.example.renan.english.entity.Note;
import com.example.renan.english.entity.Phrase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by renan on 1/19/15.
 */
public class FilterNotesUtil {

    public static List<Note> filterByType(Map<Note, List<Phrase>> cacheNotes, int type){
        List<Note> notes = new ArrayList<>();
        for (Note note : cacheNotes.keySet()){
            if (note.getType() == type){
                notes.add(note);
            }
        }
        return notes;
    }
}