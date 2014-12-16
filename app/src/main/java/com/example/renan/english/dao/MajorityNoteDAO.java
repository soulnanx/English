package com.example.renan.english.dao;


import com.codeslap.persistence.SqlAdapter;
import com.example.renan.english.entity.MajorityNote;

import java.util.List;

/**
 * Created by renan on 12/16/14.
 */
public class MajorityNoteDAO {

    public static MajorityNote save(SqlAdapter adapter, MajorityNote majorityNote){
        return (MajorityNote)adapter.store(majorityNote, majorityNote.getClass());
    }

    public static MajorityNote findById(SqlAdapter adapter, long id){
        return adapter.findFirst(new MajorityNote(id));
    }

    public static List<MajorityNote> findAll(SqlAdapter adapter){
        return adapter.findAll(MajorityNote.class);
    }
}
