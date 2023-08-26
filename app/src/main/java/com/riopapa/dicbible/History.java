package com.riopapa.dicbible;

import static com.riopapa.dicbible.Vars.goBacks;
import static com.riopapa.dicbible.Vars.nowBible;
import static com.riopapa.dicbible.Vars.nowChapter;
import static com.riopapa.dicbible.Vars.nowDic;
import static com.riopapa.dicbible.Vars.nowHymn;
import static com.riopapa.dicbible.Vars.nowVerse;
import static com.riopapa.dicbible.Vars.topTab;

import com.riopapa.dicbible.model.GoBack;

class History {

    public void push() {

        if (goBacks.size() > 80) {
            goBacks.remove(0); goBacks.remove(0); goBacks.remove(0);
            goBacks.remove(0); goBacks.remove(0); goBacks.remove(0);
        }
        GoBack goBack = new GoBack(topTab,nowBible, nowChapter, nowVerse, nowHymn, nowDic);
        goBacks.add(goBack);
        HandlePrefs.saveArray("goBack", goBacks);
    }

    public void pop() {

        if (goBacks.size() > 1) {
            GoBack goBack = goBacks.get(goBacks.size()-1);
            topTab = goBack.topTab;
            nowBible = goBack.bible;
            nowChapter = goBack.chapter;
            nowVerse = goBack.verse;
            nowHymn = goBack.hymn;
            nowDic = goBack.dic;
            goBacks.remove(goBacks.size()-1);
            HandlePrefs.saveArray("goBack", goBacks);
        }
    }

}