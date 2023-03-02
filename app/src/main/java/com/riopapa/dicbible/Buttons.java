package com.riopapa.dicbible;

import static com.riopapa.dicbible.Vars.TAB_HYMN;
import static com.riopapa.dicbible.Vars.TAB_MODE_DICT;
import static com.riopapa.dicbible.Vars.TAB_MODE_KEY;
import static com.riopapa.dicbible.Vars.TAB_NEW;
import static com.riopapa.dicbible.Vars.TAB_OLD;
import static com.riopapa.dicbible.Vars.agpShow;
import static com.riopapa.dicbible.Vars.bibleMake;
import static com.riopapa.dicbible.Vars.blank;
import static com.riopapa.dicbible.Vars.btmLayout;
import static com.riopapa.dicbible.Vars.cevShow;
import static com.riopapa.dicbible.Vars.dictMake;
import static com.riopapa.dicbible.Vars.fBody;
import static com.riopapa.dicbible.Vars.goBacks;
import static com.riopapa.dicbible.Vars.history;
import static com.riopapa.dicbible.Vars.hymnMake;
import static com.riopapa.dicbible.Vars.isReadingNow;
import static com.riopapa.dicbible.Vars.mActivity;
import static com.riopapa.dicbible.Vars.mContext;
import static com.riopapa.dicbible.Vars.nowBible;
import static com.riopapa.dicbible.Vars.nowChapter;
import static com.riopapa.dicbible.Vars.nowDic;
import static com.riopapa.dicbible.Vars.nowHymn;
import static com.riopapa.dicbible.Vars.nowVerse;
import static com.riopapa.dicbible.Vars.sharedEdit;
import static com.riopapa.dicbible.Vars.text2Speech;
import static com.riopapa.dicbible.Vars.topLayout;
import static com.riopapa.dicbible.Vars.topTab;
import static com.riopapa.dicbible.Vars.utils;
import static com.riopapa.dicbible.Vars.vAgpBible;
import static com.riopapa.dicbible.Vars.vBackAction;
import static com.riopapa.dicbible.Vars.vCenterAction;
import static com.riopapa.dicbible.Vars.vCevBible;
import static com.riopapa.dicbible.Vars.vDict;
import static com.riopapa.dicbible.Vars.vHymn;
import static com.riopapa.dicbible.Vars.vLeftAction;
import static com.riopapa.dicbible.Vars.vNewBible;
import static com.riopapa.dicbible.Vars.vOldBible;
import static com.riopapa.dicbible.Vars.vRightAction;
import static com.riopapa.dicbible.Vars.vSearch;
import static com.riopapa.dicbible.Vars.vSetting;
import static com.riopapa.dicbible.Vars.vTalk;

import android.content.Intent;
import android.widget.Toast;

public class Buttons {

    public static void assign() {

        topLayout = mActivity.findViewById(R.id.lTop);
        btmLayout = mActivity.findViewById(R.id.lBtm);

        vSetting = mActivity.findViewById(R.id.setting);
        vAgpBible = mActivity.findViewById(R.id.agpBible);
        vCevBible = mActivity.findViewById(R.id.cevBible);
        vOldBible = mActivity.findViewById(R.id.oldBible);
        vNewBible = mActivity.findViewById(R.id.newBible);
        vHymn = mActivity.findViewById(R.id.hymn);
        vDict = mActivity.findViewById(R.id.dict);
        vSearch = mActivity.findViewById(R.id.search);

        vTalk = mActivity.findViewById(R.id.talk);
        vLeftAction = mActivity.findViewById(R.id.leftAction);
        vCenterAction = mActivity.findViewById(R.id.centerAction);
        vRightAction = mActivity.findViewById(R.id.rightAction);
        vBackAction = mActivity.findViewById(R.id.backAction);
        fBody = mActivity.findViewById(R.id.fBody);

        vOldBible.setOnClickListener(v -> {
            topTab = TAB_OLD;
            nowBible = 0;
            nowChapter = 0;
            nowVerse = 0;
            bibleMake.showBibleList();
        });
        vNewBible.setOnClickListener(v -> {
            topTab = TAB_NEW;
            nowBible = 0;
            nowChapter = 0;
            nowVerse = 0;
            bibleMake.showBibleList();
        });
        vHymn.setOnClickListener(v -> {
            topTab = TAB_HYMN;
            nowBible = 0;
            nowChapter = 0;
            nowVerse = 0;
            nowHymn = 0;
            hymnMake.showNumberKey();
        });
        vDict.setOnClickListener(v -> {
            topTab = TAB_MODE_DICT;
            nowBible = 0;
            nowChapter = 0;
            nowVerse = 0;
            nowHymn = 0;
            dictMake.showDictMenu();
        });

        vAgpBible.setOnClickListener(v -> {
            if (vAgpBible.getText().toString().equals(blank))
                return;
            agpShow = !agpShow;
            sharedEdit.putBoolean("agpShow", agpShow).apply();
            bibleMake.showBibleBody();
        });

        vCevBible.setOnClickListener(v -> {
            if (vCevBible.getText().toString().equals(blank))
                return;
            cevShow = !cevShow;
            sharedEdit.putBoolean("cevShow", cevShow).apply();
            bibleMake.showBibleBody();
        });

        vSearch.setOnClickListener(v -> {
            if ((topTab == TAB_OLD || topTab == TAB_NEW) && nowBible > 0 && nowChapter > 0) {
                Intent i = new Intent(mActivity, SearchActivity.class);
                mActivity.startActivity(i);
                mActivity.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });

        vSetting.setOnClickListener(v -> {
            if (isReadingNow)
                text2Speech.stopPlay();
            Intent i = new Intent(mActivity, SetActivity.class);
            mActivity.startActivity(i);
        });

        vLeftAction.setOnClickListener(v -> {
            if (isReadingNow)
                text2Speech.stopPlay();
            if (vLeftAction.getText().toString().equals(blank))
                return;
            if ((topTab == TAB_OLD || topTab == TAB_NEW))
                bibleMake.goBibleLeft();
            else if (topTab == TAB_HYMN)
                hymnMake.goHymnLeft();
        });

        vTalk.setOnClickListener(v -> {
            if (vCenterAction.getText().toString().equals(blank))
                return;
            if ((topTab == TAB_OLD || topTab == TAB_NEW) && nowChapter > 0) {
                if (isReadingNow)
                    text2Speech.stopPlay();
                else
                    bibleMake.confirmSpeak();
            }
            else if (topTab == TAB_HYMN) {
                if (isReadingNow)
                    text2Speech.stopPlay();
                else
                    hymnMake.confirmSpeak();
            }
        });

        vRightAction.setOnClickListener(v -> {
            if (isReadingNow)
                text2Speech.stopPlay();
            if (vRightAction.getText().toString().equals(blank))
                return;
            if ((topTab == TAB_OLD || topTab == TAB_NEW))
                bibleMake.goBibleRight();
            else if (topTab == TAB_HYMN)
                hymnMake.goHymnRight();
        });

        vBackAction.setOnClickListener(v -> {
            if (isReadingNow)
                text2Speech.stopPlay();
            if (goBacks.size() > 0) {
                goBackward();
            }
        });

        vBackAction.setOnLongClickListener(view -> {
            utils.exitApp();
            return false;
        });
    }

    static void goBackward() {
            history.pop();
            if (goBacks.size() > 1) {
                history.pop();
                if (topTab == TAB_NEW || topTab == TAB_OLD) {
                    if (nowBible == 0)
                        bibleMake.showBibleList();
                    else if (nowChapter > 0)
                        bibleMake.showBibleBody();
                    else
                        bibleMake.showChapterList();
                } else if (topTab == TAB_HYMN) {
                    hymnMake.showHymnBody();
                } else if (topTab == TAB_MODE_DICT) {
                    if (nowDic.equals(""))
                        dictMake.showDictMenu();
                    else
                        new DictKey().show();
                } else if (topTab == TAB_MODE_KEY) {
                    new DictKey().show();
                } else {
                    Toast.makeText(mContext, "돌아갈 곳이 제대로 없어요", Toast.LENGTH_LONG).show();
                }
            } else {
                utils.confirmExit();
            }
        }
}