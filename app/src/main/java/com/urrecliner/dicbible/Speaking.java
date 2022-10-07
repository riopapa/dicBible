package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.TAB_MODE_HYMN;
import static com.urrecliner.dicbible.Vars.TAB_MODE_NEW;
import static com.urrecliner.dicbible.Vars.TAB_MODE_OLD;
import static com.urrecliner.dicbible.Vars.isReadingNow;
import static com.urrecliner.dicbible.Vars.mActivity;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.menuColorFore;
import static com.urrecliner.dicbible.Vars.menuSelectedBack;
import static com.urrecliner.dicbible.Vars.text2Speech;
import static com.urrecliner.dicbible.Vars.topTab;
import static com.urrecliner.dicbible.Vars.vCenterAction;

import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Speaking {

    void say() {
        if (isReadingNow) {
            isReadingNow = false;
            text2Speech.stopRead();
            setCenterColor();
        } else if (topTab == TAB_MODE_OLD || topTab == TAB_MODE_NEW) {
            isReadingNow = true;
//            bookMarkNow = false;
            Toast.makeText(mContext,"성경읽기를 시작합니다",Toast.LENGTH_SHORT).show();
            text2Speech.readVerse();
        } else if (topTab == TAB_MODE_HYMN) {
            isReadingNow = true;
            Toast.makeText(mContext,"찬송 부르기를 시작합니다",Toast.LENGTH_SHORT).show();
            text2Speech.playHymn();
        }
        setCenterColor();
    }

    void setCenterColor() {
        vCenterAction.setEnabled(false);
        vCenterAction.setBackgroundColor((isReadingNow)? menuSelectedBack: menuColorFore);

        new Timer().schedule(new TimerTask() {
            public void run() {
                mActivity.runOnUiThread(() -> vCenterAction.setEnabled(true));
            }
        }, 1500);

    }
}