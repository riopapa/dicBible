package com.riopapa.dicbible.model;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecentDict {
    public String dict;
    public long when;

    public RecentDict(String dict, long when) {
        this.dict = dict;
        this.when = when;
    }

    public static ArrayList<RecentDict> read(SharedPreferences sharedPref) {
        Gson gson = new Gson();
        String json = sharedPref.getString("recentDict", "");
        if (json.isEmpty()) return new ArrayList<>();
        else {
            Type type = new TypeToken<List<RecentDict>>() {
            }.getType();
            return  gson.fromJson(json, type);
        }
    }
}