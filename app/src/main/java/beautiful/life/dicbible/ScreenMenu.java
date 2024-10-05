package beautiful.life.dicbible;

import static beautiful.life.dicbible.Vars.TAB_HYMN;
import static beautiful.life.dicbible.Vars.TAB_DICT;
import static beautiful.life.dicbible.Vars.SHOW_DICT;
import static beautiful.life.dicbible.Vars.TAB_NEW;
import static beautiful.life.dicbible.Vars.TAB_OLD;
import static beautiful.life.dicbible.Vars.agpColorFore;
import static beautiful.life.dicbible.Vars.agpDrawable;
import static beautiful.life.dicbible.Vars.agpShow;
import static beautiful.life.dicbible.Vars.blank;
import static beautiful.life.dicbible.Vars.btmLayout;
import static beautiful.life.dicbible.Vars.cevColorFore;
import static beautiful.life.dicbible.Vars.cevShow;
import static beautiful.life.dicbible.Vars.fBody;
import static beautiful.life.dicbible.Vars.fullBibleNames;
import static beautiful.life.dicbible.Vars.hymnTitles;
import static beautiful.life.dicbible.Vars.mContext;
import static beautiful.life.dicbible.Vars.menuColorBack;
import static beautiful.life.dicbible.Vars.menuColorFore;
import static beautiful.life.dicbible.Vars.nbrOfChapters;
import static beautiful.life.dicbible.Vars.nowBible;
import static beautiful.life.dicbible.Vars.nowChapter;
import static beautiful.life.dicbible.Vars.nowDic;
import static beautiful.life.dicbible.Vars.nowHymn;
import static beautiful.life.dicbible.Vars.shortBibleNames;
import static beautiful.life.dicbible.Vars.tabDrawable;
import static beautiful.life.dicbible.Vars.topLayout;
import static beautiful.life.dicbible.Vars.topTab;
import static beautiful.life.dicbible.Vars.utils;
import static beautiful.life.dicbible.Vars.vAgpBible;
import static beautiful.life.dicbible.Vars.vBackAction;
import static beautiful.life.dicbible.Vars.vCenterAction;
import static beautiful.life.dicbible.Vars.vCevBible;
import static beautiful.life.dicbible.Vars.vDict;
import static beautiful.life.dicbible.Vars.vHymn;
import static beautiful.life.dicbible.Vars.vLeftAction;
import static beautiful.life.dicbible.Vars.vNewBible;
import static beautiful.life.dicbible.Vars.vOldBible;
import static beautiful.life.dicbible.Vars.vRightAction;
import static beautiful.life.dicbible.Vars.vSearch;
import static beautiful.life.dicbible.Vars.vSetting;
import static beautiful.life.dicbible.Vars.vTalk;

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
        vAgpBible.setBackgroundColor(menuColorBack);
        vCevBible.setBackgroundColor(menuColorBack);

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
            case TAB_DICT:
                buildDict();
                break;
            case SHOW_DICT:
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
        btmLayout.setBackground(tabDrawable);
        btmLayout.setBackgroundColor(menuColorBack);
        fBody.setBackgroundColor(menuColorBack);

        vSetting.setBackgroundColor(menuColorBack);
        drawableSet = VectorDrawableCompat.create(mContext.getResources(), R.drawable.ic_settings, null);
        assert drawableSet != null;
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
            // 마, 막, 누
            vLeftAction.setText(shortBibleNames[nowBible-1]);
            vRightAction.setText((nowBible == 65) ? blank: shortBibleNames[nowBible+1]);
            vCenterAction.setText(fullBibleNames[nowBible]);
        } else if (nowBible > 0){
            // bible = 33, chapt = 3
            // 마3 마태복음 마4
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
        if (nowBible > 0 && nowChapter > 0 &&
                (topTab == TAB_NEW || topTab == TAB_OLD)) {
            vAgpBible.setText((agpShow)? R.string.AGP:R.string.agp);
            vAgpBible.setTextColor(agpColorFore);
            if (agpShow)
                vAgpBible.setBackground(agpDrawable);
            vCevBible.setText((cevShow)? R.string.CEV:R.string.cev);
            vCevBible.setTextColor(cevColorFore);
            if (cevShow)
                vCevBible.setBackground(agpDrawable);
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
            vLeftAction.setText((nowHymn > 1) ? String.valueOf(nowHymn - 1) :blank);
            vCenterAction.setText(hymnTitles[nowHymn]);
            vRightAction.setText((nowHymn < 645) ? String.valueOf(nowHymn + 1) :blank);
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
        buildAgpCev();
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
        assert drawableSet != null;
        wrappedDrawable = DrawableCompat.wrap(drawableSet);
        DrawableCompat.setTint(wrappedDrawable, color);
        vSearch.setImageDrawable(wrappedDrawable);
    }

    private void buildTalk(int color) {
        vTalk.setEnabled(color == menuColorFore);
        vTalk.setBackgroundColor(menuColorBack);
        drawableSet = VectorDrawableCompat.create(
                mContext.getResources(), R.drawable.talk, null);
        assert drawableSet != null;
        wrappedDrawable = DrawableCompat.wrap(drawableSet);
        DrawableCompat.setTint(wrappedDrawable, color);
        vTalk.setImageDrawable(wrappedDrawable);
    }
}