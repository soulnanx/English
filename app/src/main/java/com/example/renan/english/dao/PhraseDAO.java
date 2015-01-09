package com.example.renan.english.dao;


import com.codeslap.persistence.SqlAdapter;
import com.example.renan.english.entity.PhraseOld;

import java.util.List;

/**
 * Created by renan on 12/16/14.
 */
public class PhraseDAO {

    public static Long save(SqlAdapter adapter, PhraseOld item){
        return (Long)adapter.store(item, item.getClass());
    }

    public static PhraseOld findById(SqlAdapter adapter, long id){
        return adapter.findFirst(new PhraseOld(id));
    }

    public static List<PhraseOld> findAll(SqlAdapter adapter, long id){
        PhraseOld item = new PhraseOld();
        item.setIdMajorityNote(id);

        return adapter.findAll(item);
    }

    public static int findQtdItem(SqlAdapter adapter, long id){
        return findAll(adapter, id).size();
    }

    public static void deleteByIdMajorityNote(SqlAdapter adapter, long id) {
        PhraseOld phraseOld = new PhraseOld();
        phraseOld.setIdMajorityNote(id);
        adapter.delete(phraseOld);
    }
}
