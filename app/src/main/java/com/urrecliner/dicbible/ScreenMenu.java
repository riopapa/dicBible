package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.TAB_MODE_DICT;
import static com.urrecliner.dicbible.Vars.TAB_MODE_KEY;
import static com.urrecliner.dicbible.Vars.TAB_HYMN;
import static com.urrecliner.dicbible.Vars.TAB_NEW;
import static com.urrecliner.dicbible.Vars.TAB_OLD;
import static com.urrecliner.dicbible.Vars.agpColorFore;
import static com.urrecliner.dicbible.Vars.agpShow;
import static com.urrecliner.dicbible.Vars.blank;
import static com.urrecliner.dicbible.Vars.btmLayout;
import static com.urrecliner.dicbible.Vars.cevColorFore;
import static com.urrecliner.dicbible.Vars.cevShow;
import static com.urrecliner.dicbible.Vars.fBody;
import static com.urrecliner.dicbible.Vars.fullBibleNames;
import static com.urrecliner.dicbible.Vars.hymnTitles;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.menuColorBack;
import static com.urrecliner.dicbible.Vars.menuColorFore;
import static com.urrecliner.dicbible.Vars.nbrOfChapters;
import static com.urrecliner.dicbible.Vars.nowBible;
import static com.urrecliner.dicbible.Vars.nowChapter;
import static com.urrecliner.dicbible.Vars.nowDic;
import static com.urrecliner.dicbible.Vars.nowHymn;
import static com.urrecliner.dicbible.Vars.shortBibleNames;
import static com.urrecliner.dicbible.Vars.tabDrawable;
import static com.urrecliner.dicbible.Vars.topLayout;
import static com.urrecliner.dicbible.Vars.topTab;
import static com.urrecliner.dicbible.Vars.utils;
import static com.urrecliner.dicbible.Vars.vAgpBible;
import static com.urrecliner.dicbible.Vars.vBackAction;
import static com.urrecliner.dicbible.Vars.vCenterAction;
import static com.urrecliner.dicbible.Vars.vCevBible;
import static com.urrecliner.dicbible.Vars.vDict;
import static com.urrecliner.dicbible.Vars.vHymn;
import static com.urrecliner.dicbible.Vars.vLeftAction;
import static com.urrecliner.dicbible.Vars.vNewBible;
import static com.urrecliner.dicbible.Vars.vOldBible;
import static com.urrecliner.dicbible.Vars.vRightAction;
import static com.urrecliner.dicbible.Vars.vSearch;
import static com.urrecliner.dicbible.Vars.vSetting;
import static com.urrecliner.dicbible.Vars.vTalk;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class ScreenMenu {

    Drawable drawableSet;
    Drawable wrappedDrawable;

    public void build() {

        vOldBible.setBackgroundColor(menuColorBack);
        vNewBible.setBackgroundColor(menuColorBack);
        vHymn.setBackgroundColor(menuColorBack);
        vDict.setBackgroundColor(menuColorBack);

        switch (topTab) {
            case TAB_OLD:
                buildOld();
                break;
            case TAB_NEW:
                buildNew();
                break;
            case TAB_HYMN:
                buildHymn();
                break;
            case TAB_MODE_DICT:
                buildDict();
                break;
            case TAB_MODE_KEY:
                buildKey();
                break;
            default:
                Toast.makeText(mContext, "topTab Case Error "+topTab, Toast.LENGTH_SHORT).show();
        }
        if ((topTab == TAB_NEW || topTab == TAB_OLD) && (nowBible > 0 && nowChapter > 0))
            buildSearch( menuColorFore);
        else
            buildSearch(0);
        if ((topTab == TAB_NEW || topTab == TAB_OLD) && (nowBible > 0 && nowChapter > 0))
            buildTalk(menuColorFore);
        else if (topTab == TAB_HYMN && nowHymn > 0)
            buildTalk(menuColorFore);
        else
            buildTalk(0);
        utils.setFullScreen();
    }

    void buildButtonColor() {

        topLayout.setBackgroundColor(menuColorBack);
        btmLayout.setBackgroundColor(menuColorBack);
        fBody.setBackgroundColor(menuColorBack);

        vSetting.setBackgroundColor(menuColorBack);
        drawableSet = VectorDrawableCompat.create(mContext.getResources(), R.drawable.ic_settings, null);
        wrappedDrawable = DrawableCompat.wrap(drawableSet);
        DrawableCompat.setTint(wrappedDrawable, menuColorFore);
        vSetting.setImageDrawable(wrappedDrawable);
        vOldBible.setBackgroundColor(menuColorBack);
        vOldBible.setTextColor(menuColorFore);
        vNewBible.setBackgroundColor(menuColorBack);
        vNewBible.setTextColor(menuColorFore);
        vHymn.setBackgroundColor(menuColorBack);
        vHymn.setTextColor(menuColorFore);
        vDict.setBackgroundColor(menuColorBack);
        vDict.setTextColor(menuColorFore);

        vLeftAction.setTextColor(menuColorFore);
        vCenterAction.setTextColor(menuColorFore);
        vRightAction.setTextColor(menuColorFore);
        vBackAction.setTextColor(menuColorFore);
        vLeftAction.setBackgroundColor(menuColorBack);
        vCenterAction.setBackgroundColor(menuColorBack);
        vRightAction.setBackgroundColor(menuColorBack);
        vBackAction.setBackgroundColor(menuColorBack);
        vCenterAction.setSingleLine(true);
        vCenterAction.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        vCenterAction.setSelected(true);
    }

    private void buildBibleBottom() {
        String s;

        vLeftAction.setText(blank);
        vRightAction.setText(blank);
        vCenterAction.setText(blank);
        if (nowChapter == 0 && nowBible > 0) {
            // bible = 33, chapt = 0
            // ???, ???, ???
            vLeftAction.setText(shortBibleNames[nowBible-1]);
            vRightAction.setText((nowBible == 65) ? blank: shortBibleNames[nowBible+1]);
            vCenterAction.setText(fullBibleNames[nowBible]);
        } else if (nowBible > 0){
            // bible = 33, chapt = 3
            // ???3 ???????????? ???4
            if (nowChapter > 1) {
                s = shortBibleNames[nowBible]+(nowChapter-1);
            } else {
                if (nowBible > 1) {
                    s = shortBibleNames[nowBible-1]+nbrOfChapters[nowBible-1];
                } else {
                    s = blank;
                }
            }
            vLeftAction.setText(s);
            s = fullBibleNames[nowBible]+nowChapter;
            vCenterAction.setText(s);
//            vCenterAction.setSingleLine(true);
//            vCenterAction.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//            vCenterAction.setSelected(true);

            if (nowChapter < nbrOfChapters[nowBible]) {
                s = shortBibleNames[nowBible]+(nowChapter+1);
            } else {
                if (nowBible < 66) {
                    s = shortBibleNames[nowBible+1]+"1";
                } else {
                    s = blank;
                }
            }
            vRightAction.setText(s);
        }
    }

    private void buildAgpCev() {
        vAgpBible.setBackgroundColor(menuColorBack);
        vCevBible.setBackgroundColor(menuColorBack);
        if (nowBible > 0 && nowChapter > 0 &&
                (topTab == TAB_NEW || topTab == TAB_OLD)) {
            vAgpBible.setText((agpShow)? R.string.AGP:R.string.agp);
            vAgpBible.setTextColor(agpColorFore);
            vCevBible.setText((cevShow)? R.string.CEV:R.string.cev);
            vCevBible.setTextColor(cevColorFore);
        } else {
            vAgpBible.setText(blank);
            vCevBible.setText(blank);
        }
    }

    private void buildOld() {

        buildSearch((nowBible > 0 && nowChapter > 0)? menuColorFore:menuColorBack);
        vOldBible.setBackground(tabDrawable);
        buildAgpCev();
        buildBibleBottom();
    }

    private void buildNew() {

        vNewBible.setBackground(tabDrawable);
        buildAgpCev();
        buildBibleBottom();
    }

    void buildHymn() {
        buildSearch(menuColorBack);
        vHymn.setBackground(tabDrawable);
        vAgpBible.setText(blank);   vAgpBible.setBackgroundColor(menuColorBack);
        vCevBible.setText(blank);   vCevBible.setBackgroundColor(menuColorBack);

        if (nowHymn != 0) {     // show Hymn List
            vLeftAction.setText((nowHymn > 1) ? "" + (nowHymn - 1):blank);
            vCenterAction.setText(hymnTitles[nowHymn]);
            vRightAction.setText((nowHymn < 645) ? "" + (nowHymn + 1):blank);
        } else {
            vLeftAction.setText(blank);
            vRightAction.setText(blank);
            vCenterAction.setText(blank);
        }
        vCenterAction.setSingleLine(true);
        vCenterAction.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        vCenterAction.setSelected(true);
    }

    private void buildDict() {
        buildAgpCev();
        buildSearch(menuColorBack);
        vDict.setBackground(tabDrawable);
        vLeftAction.setText(blank);
        vCenterAction.setText(blank);
        vRightAction.setText(blank);
    }

    private void buildKey() {
        buildSearch(menuColorBack);
        vLeftAction.setText(blank);
        vCenterAction.setText(nowDic);
        vRightAction.setText(blank);
    }

    private void buildSearch(int color) {
        vSearch.setEnabled(color == menuColorFore);
        vSearch.setBackgroundColor(menuColorBack);
        drawableSet = VectorDrawableCompat.create(
                mContext.getResources(), R.drawable.ic_search, null);
        wrappedDrawable = DrawableCompat.wrap(drawableSet);
        DrawableCompat.setTint(wrappedDrawable, color);
        vSearch.setImageDrawable(wrappedDrawable);
    }

    private void buildTalk(int color) {
        vTalk.setEnabled(color == menuColorFore);
        vTalk.setBackgroundColor(menuColorBack);
        drawableSet = VectorDrawableCompat.create(
                mContext.getResources(), R.drawable.talk, null);
        wrappedDrawable = DrawableCompat.wrap(drawableSet);
        DrawableCompat.setTint(wrappedDrawable, color);
        vTalk.setImageDrawable(wrappedDrawable);
    }
}