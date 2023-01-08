package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.TAB_HYMN;
import static com.urrecliner.dicbible.Vars.TAB_NEW;
import static com.urrecliner.dicbible.Vars.TAB_OLD;
import static com.urrecliner.dicbible.Vars.bibleTTS;
import static com.urrecliner.dicbible.Vars.fullBibleNames;
import static com.urrecliner.dicbible.Vars.hymnAccompany;
import static com.urrecliner.dicbible.Vars.hymnTitles;
import static com.urrecliner.dicbible.Vars.isReadingNow;
import static com.urrecliner.dicbible.Vars.mActivity;
import static com.urrecliner.dicbible.Vars.menuColorFore;
import static com.urrecliner.dicbible.Vars.menuSelectedBack;
import static com.urrecliner.dicbible.Vars.nowBible;
import static com.urrecliner.dicbible.Vars.nowChapter;
import static com.urrecliner.dicbible.Vars.nowHymn;
import static com.urrecliner.dicbible.Vars.text2Speech;
import static com.urrecliner.dicbible.Vars.topTab;
import static com.urrecliner.dicbible.Vars.utils;
import static com.urrecliner.dicbible.Vars.vCenterAction;

import java.util.Timer;
import java.util.TimerTask;

public class Speaking {

    void say() {
        if (isReadingNow) {
            isReadingNow = false;
            text2Speech.stopPlay();
        } else if (topTab == TAB_OLD || topTab == TAB_NEW) {
            isReadingNow = true;
            utils.showSnackBar( fullBibleNames[nowBible]+ " "+nowChapter,
                    ((bibleTTS)? "성경읽기":"성경낭독")+" 시작");
            text2Speech.playBible();
        } else if (topTab == TAB_HYMN) {
            isReadingNow = true;
            utils.showSnackBar(hymnTitles[nowHymn],
                    ((hymnAccompany)? "반주":"찬양") + " 시작");
            text2Speech.playHymn();
        }
        setCenterColor();
    }

    void setCenterColor() {
        vCenterAction.setBackgroundColor((isReadingNow)? menuSelectedBack: menuColorFore);
        new Timer().schedule(new TimerTask() {
            public void run() {
                mActivity.runOnUiThread(() -> vCenterAction.setEnabled(true));
            }
        }, 1500);
    }
}