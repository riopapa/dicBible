package beautiful.life.dicbible;

import static beautiful.life.dicbible.Vars.agpShow;
import static beautiful.life.dicbible.Vars.alwaysOn;
import static beautiful.life.dicbible.Vars.biblePitch;
import static beautiful.life.dicbible.Vars.bibleSpeed;
import static beautiful.life.dicbible.Vars.bibleTTS;
import static beautiful.life.dicbible.Vars.cevShow;
import static beautiful.life.dicbible.Vars.darkMode;
import static beautiful.life.dicbible.Vars.hymnAccompany;
import static beautiful.life.dicbible.Vars.hymnShowWhat;
import static beautiful.life.dicbible.Vars.hymnSpeed;
import static beautiful.life.dicbible.Vars.searchDepth;
import static beautiful.life.dicbible.Vars.sharedEdit;
import static beautiful.life.dicbible.Vars.sharedPref;
import static beautiful.life.dicbible.Vars.BibleNameSize;
import static beautiful.life.dicbible.Vars.DictShowSize;
import static beautiful.life.dicbible.Vars.HymnSize;
import static beautiful.life.dicbible.Vars.DictSize;
import static beautiful.life.dicbible.Vars.BibleSize;
import static beautiful.life.dicbible.Vars.BibleLineSize;

import com.google.gson.Gson;

import java.util.ArrayList;

class HandlePrefs {

    void get() {

        darkMode = sharedPref.getBoolean("darkMode", true);
        alwaysOn = sharedPref.getBoolean("alwaysOn",true);

        BibleNameSize = sharedPref.getInt("BibleNameSize", 18);
        BibleSize = sharedPref.getInt("BibleSize", 22);
        DictShowSize = sharedPref.getInt("DictShowSize", 23);
        DictSize = sharedPref.getInt("DictSize", 19);
        BibleLineSize = sharedPref.getInt("BibleLineSize", 9);
        bibleSpeed = sharedPref.getInt("bibleSpeed", 90);
        biblePitch = sharedPref.getInt("biblePitch",100);
        bibleTTS = sharedPref.getBoolean("bibleTTS", true);
        searchDepth = sharedPref.getInt("searchDepth", 30);

        HymnSize = sharedPref.getInt("HymnSize", 20);
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