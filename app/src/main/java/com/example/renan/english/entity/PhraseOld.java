package com.example.renan.english.entity;

/**
 * Created by renan on 12/15/14.
 */
public class PhraseOld {
    private long id;
    private long idMajorityNote;
    private int valid;
    private String title;

    public PhraseOld(long idMajorityNote, int valid, String title) {
        this.idMajorityNote = idMajorityNote;
        this.valid = valid;
        this.title = title;
    }

    public PhraseOld() {
    }

    public PhraseOld(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdMajorityNote() {
        return idMajorityNote;
    }

    public void setIdMajorityNote(long idMajorityNote) {
        this.idMajorityNote = idMajorityNote;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
