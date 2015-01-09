package com.example.renan.english.entity;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Note")
public class Note extends ParseObject {

    public static final String TITLE_EN = "titleEn";
    public static final String TITLE_PT = "titlePt";
    public static final String TYPE = "type";

    public String getTitleEn(){
        return getString(TITLE_EN);
    }
    public void setTitleEn(String titleEn){
        put(TITLE_EN, titleEn);
    }

    public String getTitlePt(){
        return getString(TITLE_PT);
    }
    public void setTitlePt(String titlePt){
        put(TITLE_PT, titlePt);
    }

    public int getType(){
        return getInt(TYPE);
    }
    public void setType(int type){
        put(TYPE, type + "");
    }

}