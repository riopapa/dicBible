package com.riopapa.dicbible;

import static com.riopapa.dicbible.Vars.goBacksStacks;
import static com.riopapa.dicbible.Vars.nowBible;
import static com.riopapa.dicbible.Vars.nowChapter;
import static com.riopapa.dicbible.Vars.nowDic;
import static com.riopapa.dicbible.Vars.nowHymn;
import static com.riopapa.dicbible.Vars.nowVerse;
import static com.riopapa.dicbible.Vars.topTab;

import com.riopapa.dicbible.model.GoBack;

class GoBackStacks {

    public void push() {

        if (goBacksStacks.size() > 80) {
            goBacksStacks.remove(0); goBacksStacks.remove(0); goBacksStacks.remove(0);
            goBacksStacks.remove(0); goBacksStacks.remove(0); goBacksStacks.remove(0);
        }
        GoBack goBack = new GoBack(topTab,nowBible, nowChapter, nowVerse, nowHymn, nowDic);
        goBacksStacks.add(goBack);
        HandlePrefs.saveArray("goBack", goBacksStacks);
    }

    public void pop() {

        if (goBacksStacks.size() > 1) {
            GoBack goBack = goBacksStacks.get(goBacksStacks.size()-1);
            topTab = goBack.topTab;
            nowBible = goBack.bible;
            nowChapter = goBack.chapter;
            nowVerse = goBack.verse;
            nowHymn = goBack.hymn;
            nowDic = goBack.dic;
            goBacksStacks.remove(goBacksStacks.size()-1);
            HandlePrefs.saveArray("goBack", goBacksStacks);
        }
    }

}