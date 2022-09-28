package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.agpShow;
import static com.urrecliner.dicbible.Vars.alwaysOn;
import static com.urrecliner.dicbible.Vars.biblePitch;
import static com.urrecliner.dicbible.Vars.bibleSpeed;
import static com.urrecliner.dicbible.Vars.cevShow;
import static com.urrecliner.dicbible.Vars.darkMode;
import static com.urrecliner.dicbible.Vars.sharedEdit;
import static com.urrecliner.dicbible.Vars.hymnAccompany;
import static com.urrecliner.dicbible.Vars.hymnImageFirst;
import static com.urrecliner.dicbible.Vars.hymnShowWhat;
import static com.urrecliner.dicbible.Vars.hymnSpeed;
import static com.urrecliner.dicbible.Vars.searchDepth;
import static com.urrecliner.dicbible.Vars.sharedPref;
import static com.urrecliner.dicbible.Vars.textSizeBible66;
import static com.urrecliner.dicbible.Vars.textSizeBibleBody;
import static com.urrecliner.dicbible.Vars.textSizeBibleRefer;
import static com.urrecliner.dicbible.Vars.textSizeHymnBody;
import static com.urrecliner.dicbible.Vars.textSizeKeyWord;
import static com.urrecliner.dicbible.Vars.textSizeSpace;

import com.google.gson.Gson;

import java.util.ArrayList;

public class SharedPref {

    static void get() {
        textSizeBible66 = sharedPref.getInt("textSizeBible66", 18);
        textSizeBibleBody = sharedPref.getInt("textSizeBibleBody", 70);
        textSizeBibleRefer = sharedPref.getInt("textSizeBibleRefer", 40);
        textSizeHymnBody = sharedPref.getInt("textSizeHymnBody", 20);
        textSizeKeyWord = sharedPref.getInt("textSizeKeyWord", 70);
        textSizeSpace = sharedPref.getInt("textSizeSpace", 15);
        hymnImageFirst = sharedPref.getBoolean("hymnImageFirst", true);
        darkMode = sharedPref.getBoolean("darkMode", true);
        hymnShowWhat = sharedPref.getInt("hymnShowWhat", 0);
        alwaysOn = sharedPref.getBoolean("alwaysOn",true);
        bibleSpeed = sharedPref.getFloat("bibleSpeed", 0.8f);
        biblePitch = sharedPref.getFloat("biblePitch",1.0f);
        hymnAccompany = sharedPref.getBoolean("hymnAccompany",true);
        hymnSpeed = sharedPref.getFloat("hymnSpeed", 0.8f);
        agpShow = sharedPref.getBoolean("agpShow", true);
        cevShow = sharedPref.getBoolean("cevShow", false);
        searchDepth = sharedPref.getInt("searchDepth", 20);
    }

    static void saveArray(String key, ArrayList arrayList) {

        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        sharedEdit.putString(key, json);
        sharedEdit.apply();
    }

}