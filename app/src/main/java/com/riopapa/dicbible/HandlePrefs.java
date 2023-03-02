package com.riopapa.dicbible;

import static com.riopapa.dicbible.Vars.agpShow;
import static com.riopapa.dicbible.Vars.alwaysOn;
import static com.riopapa.dicbible.Vars.biblePitch;
import static com.riopapa.dicbible.Vars.bibleSpeed;
import static com.riopapa.dicbible.Vars.bibleTTS;
import static com.riopapa.dicbible.Vars.cevShow;
import static com.riopapa.dicbible.Vars.darkMode;
import static com.riopapa.dicbible.Vars.hymnAccompany;
import static com.riopapa.dicbible.Vars.hymnShowWhat;
import static com.riopapa.dicbible.Vars.hymnSpeed;
import static com.riopapa.dicbible.Vars.searchDepth;
import static com.riopapa.dicbible.Vars.sharedEdit;
import static com.riopapa.dicbible.Vars.sharedPref;
import static com.riopapa.dicbible.Vars.textSizeBible66;
import static com.riopapa.dicbible.Vars.textSizeDic;
import static com.riopapa.dicbible.Vars.textSizeHymn;
import static com.riopapa.dicbible.Vars.textSizeRefer;
import static com.riopapa.dicbible.Vars.textSizeScript;
import static com.riopapa.dicbible.Vars.textSizeSpace;

import com.google.gson.Gson;

import java.util.ArrayList;

class HandlePrefs {

    void get() {

        darkMode = sharedPref.getBoolean("darkMode", true);
        alwaysOn = sharedPref.getBoolean("alwaysOn",true);

        textSizeBible66 = sharedPref.getInt("textSizeBible66", 18);
        textSizeScript = sharedPref.getInt("textSizeScript", 22);
        textSizeDic = sharedPref.getInt("textSizeDic", 23);
        textSizeRefer = sharedPref.getInt("textSizeRefer", 19);
        textSizeSpace = sharedPref.getInt("textSizeSpace", 9);
        bibleSpeed = sharedPref.getInt("bibleSpeed", 90);
        biblePitch = sharedPref.getInt("biblePitch",100);
        bibleTTS = sharedPref.getBoolean("bibleTTS", true);
        searchDepth = sharedPref.getInt("searchDepth", 30);

        textSizeHymn = sharedPref.getInt("textSizeHymn", 20);
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