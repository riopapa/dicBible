package com.riopapa.dicbible.model;

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
    public long when;

    public BookMark(int bible, int chapter, int verse, long when) {
        this.bible = bible;
        this.chapter = chapter;
        this.verse = verse;
        this.when = when;
    }

    public static ArrayList<BookMark> read(SharedPreferences sharedPref) {
        Gson gson = new Gson();
        String json = sharedPref.getString("bookMark", "");
        if (json.isEmpty()) {
            ArrayList<BookMark> bm = new ArrayList<>();
            bm.add(new BookMark(20, 3, 5,
                        System.currentTimeMillis()));
            bm.add(new BookMark(19, 27, 1,
                        System.currentTimeMillis()));
            bm.add(new BookMark(43, 14, 6,
                        System.currentTimeMillis()));
            return bm;
        } else {
            Type type = new TypeToken<List<BookMark>>() {
            }.getType();
            return  gson.fromJson(json, type);
        }
    }
}