package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.agpShow;
import static com.urrecliner.dicbible.Vars.alwaysOn;
import static com.urrecliner.dicbible.Vars.biblePitch;
import static com.urrecliner.dicbible.Vars.bibleSpeed;
import static com.urrecliner.dicbible.Vars.cevShow;
import static com.urrecliner.dicbible.Vars.darkMode;
import static com.urrecliner.dicbible.Vars.hymnAccompany;
import static com.urrecliner.dicbible.Vars.hymnImageFirst;
import static com.urrecliner.dicbible.Vars.hymnShowWhat;
import static com.urrecliner.dicbible.Vars.hymnSpeed;
import static com.urrecliner.dicbible.Vars.searchDepth;
import static com.urrecliner.dicbible.Vars.sharedEdit;
import static com.urrecliner.dicbible.Vars.sharedPref;
import static com.urrecliner.dicbible.Vars.textSizeBible66;
import static com.urrecliner.dicbible.Vars.textSizeDic;
import static com.urrecliner.dicbible.Vars.textSizeHymn;
import static com.urrecliner.dicbible.Vars.textSizeRefer;
import static com.urrecliner.dicbible.Vars.textSizeScript;
import static com.urrecliner.dicbible.Vars.textSizeSpace;

import com.google.gson.Gson;

import java.util.ArrayList;

public class HandlePrefs {

    static void get() {

        darkMode = sharedPref.getBoolean("darkMode", true);
        alwaysOn = sharedPref.getBoolean("alwaysOn",true);

        textSizeBible66 = sharedPref.getInt("textSizeBible66", 18);
        textSizeScript = sharedPref.getInt("textSizeScript", 22);
        textSizeDic = sharedPref.getInt("textSizeDic", 23);
        textSizeRefer = sharedPref.getInt("textSizeRefer", 19);
        textSizeSpace = sharedPref.getInt("textSizeSpace", 9);
        bibleSpeed = sharedPref.getInt("bibleSpeed", 90);
        biblePitch = sharedPref.getInt("biblePitch",100);
        searchDepth = sharedPref.getInt("searchDepth", 20);

        textSizeHymn = sharedPref.getInt("textSizeHymn", 20);
        hymnImageFirst = sharedPref.getBoolean("hymnImageFirst", true);
        hymnShowWhat = sharedPref.getInt("hymnShowWhat", 0);
        hymnAccompany = sharedPref.getBoolean("hymnAccompany",true);
        hymnSpeed = sharedPref.getInt("hymnSpeed", 90);

        agpShow = sharedPref.getBoolean("agpShow", true);
        cevShow = sharedPref.getBoolean("cevShow", false);
    }

    static void saveArray(String key, ArrayList arrayList) {

        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        sharedEdit.putString(key, json);
        sharedEdit.apply();
    }

}