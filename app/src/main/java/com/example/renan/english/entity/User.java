package com.example.renan.english.entity;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("User")
public class User extends ParseObject {

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String NAME = "name";

    public String getName(){
        return getString(NAME);
    }
    public void setName(String name){
        put(NAME, name);
    }


    public String getEmail(){
        return getString(EMAIL);
    }
    public void setEmail(String email){
        put(EMAIL, email);
    }


    public String getUsername(){
        return getString(USERNAME);
    }
    public void setUsername(String username){
        put(USERNAME, username);
    }


}