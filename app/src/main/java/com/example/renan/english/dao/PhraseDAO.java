package com.example.renan.english.dao;


import com.codeslap.persistence.SqlAdapter;
import com.example.renan.english.entity.Phrase;

import java.util.List;

/**
 * Created by renan on 12/16/14.
 */
public class PhraseDAO {

    public static Long save(SqlAdapter adapter, Phrase item){
        return (Long)adapter.store(item, item.getClass());
    }

    public static Phrase findById(SqlAdapter adapter, long id){
        return adapter.findFirst(new Phrase(id));
    }

    public static List<Phrase> findAll(SqlAdapter adapter, long id){
        Phrase item = new Phrase();
        item.setIdMajorityNote(id);

        return adapter.findAll(item);
    }

    public static int findQtdItem(SqlAdapter adapter, long id){
        return findAll(adapter, id).size();
    }

    public static void deleteByIdMajorityNote(SqlAdapter adapter, long id) {
        Phrase phrase = new Phrase();
        phrase.setIdMajorityNote(id);
        adapter.delete(phrase);
    }
}
