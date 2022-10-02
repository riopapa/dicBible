package com.urrecliner.dicbible.model;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookMark {
    public int bible;
    public int chapter;
    public int verse;
    public boolean save;
    public long when;

    public BookMark(int bible, int chapter, int verse, long when, boolean save) {
        this.bible = bible;
        this.chapter = chapter;
        this.verse = verse;
        this.when = when;
        this.save = save;
    }

    public static ArrayList<BookMark> read(SharedPreferences sharedPref) {
        Gson gson = new Gson();
        String json = sharedPref.getString("bookMark", "");
        if (json.isEmpty()) {
            return new ArrayList<BookMark>();
        } else {
            Type type = new TypeToken<List<BookMark>>() {
            }.getType();
            return  gson.fromJson(json, type);
        }
    }
    public boolean isSave() { return save; }
    public void setSave(boolean save) { this.save = save; }
}