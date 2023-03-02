package com.riopapa.dicbible.model;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GoBack {
    public int topTab;
    public int bible;
    public int chapter;
    public int verse;
    public int hymn;
    public String dic;

    public GoBack(int topTab, int bible, int chapter, int verse, int hymn, String dic) {
        this.topTab = topTab; this.bible = bible; this.chapter = chapter; this.verse = verse; this.hymn = hymn; this.dic = dic;
    }

    public static ArrayList<GoBack> read(SharedPreferences sharedPref) {
        Gson gson = new Gson();
        String json = sharedPref.getString("goBack", "");
        if (json.isEmpty()) {
            ArrayList<GoBack> bm = new ArrayList<>();
            bm.add(new GoBack(2,43, 14, 6, 0, ""));
            bm.add(new GoBack(1,20, 3, 5, 0, ""));
            bm.add(new GoBack(1,19, 27, 1, 0, ""));
            bm.add(new GoBack(2,43, 14, 6, 0, ""));
            return bm;
        } else {
            Type type = new TypeToken<List<GoBack>>() {
            }.getType();
            return gson.fromJson(json, type);
        }
    }

}