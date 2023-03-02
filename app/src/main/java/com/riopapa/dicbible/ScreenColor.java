package com.riopapa.dicbible;

import static com.riopapa.dicbible.Vars.agpColorFore;
import static com.riopapa.dicbible.Vars.cevColorFore;
import static com.riopapa.dicbible.Vars.darkMode;
import static com.riopapa.dicbible.Vars.dicColorFore;
import static com.riopapa.dicbible.Vars.hymnColorImage;
import static com.riopapa.dicbible.Vars.mContext;
import static com.riopapa.dicbible.Vars.markColorBack;
import static com.riopapa.dicbible.Vars.menuColorBack;
import static com.riopapa.dicbible.Vars.menuColorFore;
import static com.riopapa.dicbible.Vars.menuSelectedBack;
import static com.riopapa.dicbible.Vars.numberColorFore;
import static com.riopapa.dicbible.Vars.paraColorFore;
import static com.riopapa.dicbible.Vars.referColorFore;
import static com.riopapa.dicbible.Vars.tabDrawable;
import static com.riopapa.dicbible.Vars.textColorFore;

import androidx.core.content.ContextCompat;

public class ScreenColor {

    public static void apply() {

        if (darkMode) {
            menuColorFore = ContextCompat.getColor(mContext, R.color.menuColorForeN);
            menuColorBack = ContextCompat.getColor(mContext, R.color.menuColorBackN);
            menuSelectedBack = menuColorFore ^ 0xaa9988;
            markColorBack = ContextCompat.getColor(mContext, R.color.markColorBackN);
            textColorFore = ContextCompat.getColor(mContext, R.color.scriptColorForeN);
            paraColorFore = ContextCompat.getColor(mContext, R.color.paraColorForeN);
            referColorFore = ContextCompat.getColor(mContext, R.color.referColorForeN);
            numberColorFore = ContextCompat.getColor(mContext, R.color.numberColorForeN);
            cevColorFore = ContextCompat.getColor(mContext, R.color.cevColorForeN);
            agpColorFore = ContextCompat.getColor(mContext, R.color.agpColorForeN);
            dicColorFore = ContextCompat.getColor(mContext, R.color.dictColorForeN);
            hymnColorImage = ContextCompat.getColor(mContext, R.color.hymnImageBackN);
            tabDrawable = ContextCompat.getDrawable(mContext, R.drawable.tab_border_dark);
        } else {
            menuColorFore = ContextCompat.getColor(mContext, R.color.menuColorFore);
            menuColorBack = ContextCompat.getColor(mContext, R.color.menuColorBack);
            menuSelectedBack = menuColorFore ^ 0xaa9988;
            markColorBack = ContextCompat.getColor(mContext, R.color.markColorBack);
            textColorFore = ContextCompat.getColor(mContext, R.color.scriptColorFore);
            paraColorFore = ContextCompat.getColor(mContext, R.color.paraColorFore);
            referColorFore = ContextCompat.getColor(mContext, R.color.referColorFore);
            numberColorFore = ContextCompat.getColor(mContext, R.color.numberColorFore);
            cevColorFore = ContextCompat.getColor(mContext, R.color.cevColorFore);
            agpColorFore = ContextCompat.getColor(mContext, R.color.agpColorFore);
            dicColorFore = ContextCompat.getColor(mContext, R.color.dictColorFore);
            hymnColorImage = ContextCompat.getColor(mContext, R.color.hymnImageBack);
            tabDrawable = ContextCompat.getDrawable(mContext, R.drawable.tab_border);
        }
    }
}