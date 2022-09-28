package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.agpColorFore;
import static com.urrecliner.dicbible.Vars.bibleColorFore;
import static com.urrecliner.dicbible.Vars.cevColorFore;
import static com.urrecliner.dicbible.Vars.darkMode;
import static com.urrecliner.dicbible.Vars.dicColorFore;
import static com.urrecliner.dicbible.Vars.highLiteMenuColor;
import static com.urrecliner.dicbible.Vars.hymnColorFore;
import static com.urrecliner.dicbible.Vars.hymnColorImage;
import static com.urrecliner.dicbible.Vars.hymnColorTitle;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.normalMenuColor;
import static com.urrecliner.dicbible.Vars.numberColorFore;
import static com.urrecliner.dicbible.Vars.paraColorFore;
import static com.urrecliner.dicbible.Vars.readNowColor;
import static com.urrecliner.dicbible.Vars.referColorFore;
import static com.urrecliner.dicbible.Vars.textColorBack;
import static com.urrecliner.dicbible.Vars.vCenterAction;
import static com.urrecliner.dicbible.Vars.verseColorFore;

import android.graphics.drawable.ColorDrawable;

import androidx.core.content.ContextCompat;

public class ScreenColor {


    public static void setVars() {

        ColorDrawable cd = (ColorDrawable) vCenterAction.getBackground();
        normalMenuColor = cd.getColor();
        highLiteMenuColor = normalMenuColor ^ 0x444444;
        readNowColor = normalMenuColor ^ 0x777777;

//        bibleColorFore = sharedPref.getInt("bibleColorFore", ContextCompat.getColor(mContext, R.color.bibleColorFore));
//        verseColorFore = sharedPref.getInt("verseColorFore", ContextCompat.getColor(mContext, R.color.verseColorFore));
//        verseColorFore = sharedPref.getInt("verseColorFore", ContextCompat.getColor(mContext, R.color.verseColorFore));
//        referColorFore = sharedPref.getInt("referColorFore", ContextCompat.getColor(mContext, R.color.referColorFore));
//        numberColorFore = sharedPref.getInt("numberColorFore", ContextCompat.getColor(mContext, R.color.numberColorFore));

        bibleColorFore = ContextCompat.getColor(mContext, R.color.bibleColorFore);
        verseColorFore = ContextCompat.getColor(mContext, R.color.verseColorFore);
        paraColorFore = ContextCompat.getColor(mContext, R.color.paraColorFore);
        referColorFore = ContextCompat.getColor(mContext, R.color.referColorFore);
        numberColorFore = ContextCompat.getColor(mContext, R.color.numberColorFore);
        textColorBack  = ContextCompat.getColor(mContext, R.color.screenBodyColor);
        cevColorFore = ContextCompat.getColor(mContext, R.color.cevColorFore);
        agpColorFore = ContextCompat.getColor(mContext, R.color.agpColorFore);
        dicColorFore = ContextCompat.getColor(mContext, R.color.dictColorFore);
        hymnColorFore = ContextCompat.getColor(mContext,R.color.hymnColorFore);
        hymnColorTitle = ContextCompat.getColor(mContext,R.color.hymnColorTitle);
        hymnColorImage = ContextCompat.getColor(mContext,R.color.hymnImageBack);
        if (darkMode) {
            final int xorColor = 0x00ffffff;
            bibleColorFore ^= xorColor; verseColorFore ^= xorColor; paraColorFore ^= xorColor;
            referColorFore ^= xorColor; numberColorFore ^= xorColor; textColorBack ^= xorColor;
            dicColorFore ^= xorColor;
            hymnColorFore ^= xorColor; hymnColorTitle ^= xorColor;  // no hymnColorImage
            agpColorFore ^= xorColor; cevColorFore ^= xorColor;
        }
    }
}