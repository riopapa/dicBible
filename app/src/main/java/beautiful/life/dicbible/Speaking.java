package beautiful.life.dicbible;

import static beautiful.life.dicbible.Vars.TAB_HYMN;
import static beautiful.life.dicbible.Vars.TAB_NEW;
import static beautiful.life.dicbible.Vars.TAB_OLD;
import static beautiful.life.dicbible.Vars.bibleTTS;
import static beautiful.life.dicbible.Vars.fullBibleNames;
import static beautiful.life.dicbible.Vars.hymnAccompany;
import static beautiful.life.dicbible.Vars.hymnTitles;
import static beautiful.life.dicbible.Vars.isReadingNow;
import static beautiful.life.dicbible.Vars.mActivity;
import static beautiful.life.dicbible.Vars.menuColorFore;
import static beautiful.life.dicbible.Vars.menuSelectedBack;
import static beautiful.life.dicbible.Vars.nowBible;
import static beautiful.life.dicbible.Vars.nowChapter;
import static beautiful.life.dicbible.Vars.nowHymn;
import static beautiful.life.dicbible.Vars.text2Speech;
import static beautiful.life.dicbible.Vars.topTab;
import static beautiful.life.dicbible.Vars.utils;
import static beautiful.life.dicbible.Vars.vCenterAction;

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