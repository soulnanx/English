package com.example.renan.english.entity;

/**
 * Created by renan on 12/15/14.
 */
public class MajorityNote {
    private long id;
    private String titleEn;
    private String titlePt;

    public MajorityNote(String titleEn, String titlePt) {
        this.titleEn = titleEn;
        this.titlePt = titlePt;
    }

    public MajorityNote() {
    }

    public MajorityNote(long id) {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitlePt() {
        return titlePt;
    }

    public void setTitlePt(String titlePt) {
        this.titlePt = titlePt;
    }
}
