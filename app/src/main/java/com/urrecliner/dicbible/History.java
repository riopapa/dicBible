package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.goBacks;
import static com.urrecliner.dicbible.Vars.nowBible;
import static com.urrecliner.dicbible.Vars.nowChapter;
import static com.urrecliner.dicbible.Vars.nowDic;
import static com.urrecliner.dicbible.Vars.nowHymn;
import static com.urrecliner.dicbible.Vars.nowVerse;
import static com.urrecliner.dicbible.Vars.topTab;

import com.urrecliner.dicbible.model.GoBack;

class History {

    public void push() {
//        dumpGoBack("push");
        if (goBacks.size() > 40) {
            goBacks.remove(0); goBacks.remove(0); goBacks.remove(0);
            goBacks.remove(0); goBacks.remove(0); goBacks.remove(0);
        }
        GoBack goBack = new GoBack(topTab,nowBible, nowChapter, nowVerse, nowHymn, nowDic);
        goBacks.add(goBack);
    }

    public void pop() {
//        dumpGoBack("pop");
        if (goBacks.size() > 1) {
            GoBack goBack = goBacks.get(goBacks.size()-1);
            topTab = goBack.topTab;
            nowBible = goBack.bible;
            nowChapter = goBack.chapter;
            nowVerse = goBack.verse;
            nowHymn = goBack.hymn;
            nowDic = goBack.dic;
            goBacks.remove(goBacks.size()-1);
        }
    }

//    void dumpGoBack(String pp) {
//        Log.w("goBack "+goBacks.size(), pp); {
//            for (int i = 0; i < goBacks.size(); i++) {
//                GoBack gb = goBacks.get(i);
//                Log.w("back "+i, "topTab="+gb.topTab+" bib="+gb.bible
//                        +" chap="+gb.chapter+" ver="+gb.verse+" hymn="+gb.hymn
//                        +" dic="+gb.dic);
//            }
//        }
//    }
}