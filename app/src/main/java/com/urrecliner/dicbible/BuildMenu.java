package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.TAB_MODE_DIC;
import static com.urrecliner.dicbible.Vars.TAB_MODE_HYMN;
import static com.urrecliner.dicbible.Vars.TAB_MODE_NEW;
import static com.urrecliner.dicbible.Vars.TAB_MODE_OLD;
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
import static com.urrecliner.dicbible.Vars.screenColorBack;
import static com.urrecliner.dicbible.Vars.shortBibleNames;
import static com.urrecliner.dicbible.Vars.topLayout;
import static com.urrecliner.dicbible.Vars.topTab;
import static com.urrecliner.dicbible.Vars.vAgpBible;
import static com.urrecliner.dicbible.Vars.vBackAction;
import static com.urrecliner.dicbible.Vars.vCenterAction;
import static com.urrecliner.dicbible.Vars.vCevBible;
import static com.urrecliner.dicbible.Vars.vHymn;
import static com.urrecliner.dicbible.Vars.vLeftAction;
import static com.urrecliner.dicbible.Vars.vNewBible;
import static com.urrecliner.dicbible.Vars.vOldBible;
import static com.urrecliner.dicbible.Vars.vRightAction;
import static com.urrecliner.dicbible.Vars.vSearch;
import static com.urrecliner.dicbible.Vars.vSetting;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class BuildMenu {

    Drawable d;
    Drawable wrappedDrawable;
    public void set() {

        setCommon();

        switch (topTab) {
            case TAB_MODE_OLD:
                setOLD();
                break;
            case TAB_MODE_NEW:
                setNEW();
                break;
            case TAB_MODE_HYMN:
                setHYMN();
                break;
            case TAB_MODE_DIC:
                setDIC();
                break;
            default:
                Toast.makeText(mContext, "topTab Case Error "+topTab, Toast.LENGTH_SHORT).show();
        }
    }

    private void setBibleBottom() {
        String s;
        vLeftAction.setTextColor(menuColorFore);
        vRightAction.setTextColor(menuColorFore);
        vCenterAction.setTextColor(menuColorFore);
        vLeftAction.setBackgroundColor(menuColorBack);
        vRightAction.setBackgroundColor(menuColorBack);
        vCenterAction.setBackgroundColor(menuColorBack);

        if (nowBible == 0) {        // show bible list
            vLeftAction.setText(blank);
            vRightAction.setText(blank);
            vCenterAction.setText(blank);
        } else if (nowChapter == 0) {
            // bible = 33, chapt = 0
            // 마, 막, 누
            vLeftAction.setText(shortBibleNames[nowBible-1]);
            vRightAction.setText((nowBible == 65) ? blank: shortBibleNames[nowBible+1]);
            vCenterAction.setText(fullBibleNames[nowBible]);
        } else {
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
            vCenterAction.setSingleLine(true);
            vCenterAction.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            vCenterAction.setSelected(true);

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

    private void setAgpCev() {
        if (nowBible > 0 && nowChapter > 0) {
            vAgpBible.setText(R.string.agp);
            vAgpBible.setTextColor(menuColorFore);
            vAgpBible.setBackgroundColor((agpShow)? agpColorFore : menuColorBack);
            vCevBible.setText(R.string.cev);
            vCevBible.setTextColor(menuColorFore);
            vCevBible.setBackgroundColor((cevShow)? cevColorFore : menuColorBack);
        } else {
            vAgpBible.setText(blank);
            vAgpBible.setBackgroundColor(menuColorBack);
            vCevBible.setText(blank);
            vCevBible.setBackgroundColor(menuColorBack);
        }
    }

    private void setOLD() {

        setSearch((nowBible > 0 && nowChapter > 0)? menuColorFore:menuColorBack);
        setTopMenu();
        vOldBible.setBackgroundResource(R.drawable.top_tab_border);
        setAgpCev();
        setBibleBottom();

    }

    private void setNEW() {

        setSearch((nowBible > 0 && nowChapter > 0)? menuColorFore:menuColorBack);
        setTopMenu();
        vNewBible.setBackgroundResource(R.drawable.top_tab_border);
        setAgpCev();
        setBibleBottom();
    }

    private void setTopMenu() {
        vOldBible.setBackgroundColor(menuColorBack);
        vOldBible.setTextColor(menuColorFore);
        vNewBible.setBackgroundColor(menuColorBack);
        vNewBible.setTextColor(menuColorFore);
        vHymn.setBackgroundColor(menuColorBack);
        vHymn.setTextColor(menuColorFore);
    }

    void setHYMN() {
        setSearch(menuColorBack);
        setTopMenu();
        vHymn.setBackgroundResource(R.drawable.top_tab_border);
        setAgpCev();
        vAgpBible.setText(blank);
        vCevBible.setText(blank);

        vLeftAction.setTextColor(menuColorFore);
        vRightAction.setTextColor(menuColorFore);
        vCenterAction.setTextColor(menuColorFore);
        vLeftAction.setBackgroundColor(menuColorBack);
        vRightAction.setBackgroundColor(menuColorBack);
        vCenterAction.setBackgroundColor(menuColorBack);

        if (nowHymn == 0) {     // show Hymn List
            vLeftAction.setText(blank);
            vRightAction.setText(blank);
            vCenterAction.setText(blank);
        } else {   // show Hymn
            vLeftAction.setText((nowHymn > 1) ? "" + (nowHymn - 1):blank);
            vCenterAction.setText(hymnTitles[nowHymn]);
            vRightAction.setText((nowHymn < 645) ? "" + (nowHymn + 1):blank);
        }
        vCenterAction.setSingleLine(true);
        vCenterAction.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        vCenterAction.setSelected(true);
    }

    private void setDIC() {
        setSearch(menuColorBack);
        setTopMenu();
        vLeftAction.setTextColor(menuColorFore);
        vRightAction.setTextColor(menuColorFore);
        vCenterAction.setTextColor(menuColorFore);
        vLeftAction.setBackgroundColor(menuColorBack);
        vRightAction.setBackgroundColor(menuColorBack);
        vCenterAction.setBackgroundColor(menuColorBack);
        vCenterAction.setText(nowDic);
    }

    private void setSearch(int color) {
        vSearch.setEnabled(color == menuColorFore);
        vSearch.setBackgroundColor(menuColorBack);
        d = VectorDrawableCompat.create(mContext.getResources(), R.drawable.ic_search, null);
        wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        vSearch.setImageDrawable(wrappedDrawable);
    }

    void setCommon() {
        
        topLayout.setBackgroundColor(menuColorBack);
        btmLayout.setBackgroundColor(menuColorBack);
        fBody.setBackgroundColor(screenColorBack);

        vSetting.setBackgroundColor(menuColorBack);
        d = VectorDrawableCompat.create(mContext.getResources(), R.drawable.ic_settings, null);
        wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, menuColorFore);
        vSetting.setImageDrawable(wrappedDrawable);
        vBackAction.setBackgroundColor(menuColorBack);
        vBackAction.setTextColor(menuColorFore);

    }

}