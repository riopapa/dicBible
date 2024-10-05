package beautiful.life.dicbible;

import static beautiful.life.dicbible.Vars.goBacksStacks;
import static beautiful.life.dicbible.Vars.nowBible;
import static beautiful.life.dicbible.Vars.nowChapter;
import static beautiful.life.dicbible.Vars.nowDic;
import static beautiful.life.dicbible.Vars.nowHymn;
import static beautiful.life.dicbible.Vars.nowVerse;
import static beautiful.life.dicbible.Vars.topTab;

import beautiful.life.dicbible.model.GoBack;

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