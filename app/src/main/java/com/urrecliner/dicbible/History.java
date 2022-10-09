package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.goBacks;
import static com.urrecliner.dicbible.Vars.nowBible;
import static com.urrecliner.dicbible.Vars.nowChapter;
import static com.urrecliner.dicbible.Vars.nowDic;
import static com.urrecliner.dicbible.Vars.nowHymn;
import static com.urrecliner.dicbible.Vars.nowVerse;
import static com.urrecliner.dicbible.Vars.topTab;

import android.util.Log;

import com.urrecliner.dicbible.model.GoBack;

class History {

    public void push() {

        if (goBacks.size() > 40) {
            goBacks.remove(0); goBacks.remove(0); goBacks.remove(0);
            goBacks.remove(0); goBacks.remove(0); goBacks.remove(0);
        }
        GoBack goBack = new GoBack(topTab,nowBible, nowChapter, nowVerse, nowHymn, nowDic);
        goBacks.add(goBack);
//        dump("after push");
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
        }
//        dump("after pop");
    }

    private void dump(String s) {
        Log.w(s,"size="+goBacks.size());
        for (int i = 0; i < goBacks.size(); i++) {
            GoBack gb = goBacks.get(i);
            Log.w(s + i, "tab="+gb.topTab+" bib="+gb.bible+" ch="+gb.chapter
                    +" ver="+gb.verse+" h="+gb.hymn+" d="+gb.dic);
        }
        Log.w(s+"now", "tab="+topTab+" bib="+nowBible+" ch="+nowChapter
                +" ver="+nowVerse+" h="+nowHymn+" d="+nowDic);
    }
}