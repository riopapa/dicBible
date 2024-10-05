package beautiful.life.dicbible;

import static beautiful.life.dicbible.Vars.agpColorFore;
import static beautiful.life.dicbible.Vars.agpDrawable;
import static beautiful.life.dicbible.Vars.cevColorFore;
import static beautiful.life.dicbible.Vars.darkMode;
import static beautiful.life.dicbible.Vars.dicColorFore;
import static beautiful.life.dicbible.Vars.hymnColorImage;
import static beautiful.life.dicbible.Vars.mContext;
import static beautiful.life.dicbible.Vars.markColorBack;
import static beautiful.life.dicbible.Vars.menuColorBack;
import static beautiful.life.dicbible.Vars.menuColorFore;
import static beautiful.life.dicbible.Vars.menuSelectedBack;
import static beautiful.life.dicbible.Vars.numberColorFore;
import static beautiful.life.dicbible.Vars.paraColorFore;
import static beautiful.life.dicbible.Vars.referColorFore;
import static beautiful.life.dicbible.Vars.tabDrawable;
import static beautiful.life.dicbible.Vars.textColorFore;

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
            agpDrawable = ContextCompat.getDrawable(mContext, R.drawable.agp_border_dark);
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
            agpDrawable = ContextCompat.getDrawable(mContext, R.drawable.agp_border);
        }
    }
}