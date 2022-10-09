package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.agpColorFore;
import static com.urrecliner.dicbible.Vars.bibleColorFore;
import static com.urrecliner.dicbible.Vars.cevColorFore;
import static com.urrecliner.dicbible.Vars.darkMode;
import static com.urrecliner.dicbible.Vars.dicColorFore;
import static com.urrecliner.dicbible.Vars.hymnColorFore;
import static com.urrecliner.dicbible.Vars.hymnColorImage;
import static com.urrecliner.dicbible.Vars.hymnColorTitle;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.markColorBack;
import static com.urrecliner.dicbible.Vars.menuColorBack;
import static com.urrecliner.dicbible.Vars.menuColorFore;
import static com.urrecliner.dicbible.Vars.menuSelectedBack;
import static com.urrecliner.dicbible.Vars.numberColorFore;
import static com.urrecliner.dicbible.Vars.paraColorFore;
import static com.urrecliner.dicbible.Vars.referColorFore;
import static com.urrecliner.dicbible.Vars.screenColorBack;
import static com.urrecliner.dicbible.Vars.scriptColorFore;

import androidx.core.content.ContextCompat;

public class ScreenColor {

    public static void apply() {

        if (darkMode) {
            menuColorFore = ContextCompat.getColor(mContext, R.color.menuColorForeN);
            menuColorBack = ContextCompat.getColor(mContext, R.color.menuColorBackN);
            menuSelectedBack = menuColorFore ^ 0xaa9988;
            screenColorBack = ContextCompat.getColor(mContext, R.color.screenColorBackN);
            markColorBack = ContextCompat.getColor(mContext, R.color.markColorBackN);
            bibleColorFore = ContextCompat.getColor(mContext, R.color.bibleColorForeN);
            scriptColorFore = ContextCompat.getColor(mContext, R.color.scriptColorForeN);
            paraColorFore = ContextCompat.getColor(mContext, R.color.paraColorForeN);
            referColorFore = ContextCompat.getColor(mContext, R.color.referColorForeN);
            numberColorFore = ContextCompat.getColor(mContext, R.color.numberColorForeN);
            cevColorFore = ContextCompat.getColor(mContext, R.color.cevColorForeN);
            agpColorFore = ContextCompat.getColor(mContext, R.color.agpColorForeN);
            dicColorFore = ContextCompat.getColor(mContext, R.color.dictColorForeN);
            hymnColorFore = ContextCompat.getColor(mContext, R.color.hymnColorForeN);
            hymnColorTitle = ContextCompat.getColor(mContext, R.color.hymnColorTitleN);
            hymnColorImage = ContextCompat.getColor(mContext, R.color.hymnImageBackN);
        } else {
            menuColorFore = ContextCompat.getColor(mContext, R.color.menuColorFore);
            menuColorBack = ContextCompat.getColor(mContext, R.color.menuColorBack);
            menuSelectedBack = menuColorFore ^ 0xaa9988;
            screenColorBack = ContextCompat.getColor(mContext, R.color.screenColorBack);
            markColorBack = ContextCompat.getColor(mContext, R.color.markColorBack);
            bibleColorFore = ContextCompat.getColor(mContext, R.color.bibleColorFore);
            scriptColorFore = ContextCompat.getColor(mContext, R.color.scriptColorFore);
            paraColorFore = ContextCompat.getColor(mContext, R.color.paraColorFore);
            referColorFore = ContextCompat.getColor(mContext, R.color.referColorFore);
            numberColorFore = ContextCompat.getColor(mContext, R.color.numberColorFore);
            cevColorFore = ContextCompat.getColor(mContext, R.color.cevColorFore);
            agpColorFore = ContextCompat.getColor(mContext, R.color.agpColorFore);
            dicColorFore = ContextCompat.getColor(mContext, R.color.dictColorFore);
            hymnColorFore = ContextCompat.getColor(mContext, R.color.hymnColorFore);
            hymnColorTitle = ContextCompat.getColor(mContext, R.color.hymnColorTitle);
            hymnColorImage = ContextCompat.getColor(mContext, R.color.hymnImageBack);
        }
    }
}