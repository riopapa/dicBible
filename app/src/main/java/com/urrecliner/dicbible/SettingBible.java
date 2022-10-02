package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.bibleColorFore;
import static com.urrecliner.dicbible.Vars.biblePitch;
import static com.urrecliner.dicbible.Vars.bibleSpeed;
import static com.urrecliner.dicbible.Vars.fileRead;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.screenColorBack;
import static com.urrecliner.dicbible.Vars.searchDepth;
import static com.urrecliner.dicbible.Vars.settingActivity;
import static com.urrecliner.dicbible.Vars.sharedEdit;
import static com.urrecliner.dicbible.Vars.textSizeBible66;
import static com.urrecliner.dicbible.Vars.textSizeDic;
import static com.urrecliner.dicbible.Vars.textSizeRefer;
import static com.urrecliner.dicbible.Vars.textSizeScript;
import static com.urrecliner.dicbible.Vars.textSizeSpace;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class SettingBible {

    static void setBackGround(View view) {
        view.setBackgroundColor(screenColorBack);
    }
    static void setTexts(TextView tV, TextView dN, TextView sZ, TextView uP, int textColor) {
        tV.setBackgroundColor(screenColorBack); tV.setTextColor(bibleColorFore); tV.setTextSize(textColor);
        dN.setBackgroundColor(screenColorBack); dN.setTextColor(bibleColorFore); dN.setTextSize(textColor);
        sZ.setBackgroundColor(screenColorBack); sZ.setTextColor(bibleColorFore); sZ.setTextSize(textColor);
        uP.setBackgroundColor(screenColorBack); uP.setTextColor(bibleColorFore); uP.setTextSize(textColor);
    }

    public static void set() {
        TextView tv;
        View vLayout;
        TextView tVBible, dNBible, sZBible, uPBible;
        TextView tVScript, dNScript, sZScript, uPScript;
        TextView tVDic, dNDic, sZDic, uPDic;
        TextView tVRefer, dNRefer, sZRefer, uPRefer;
        TextView tVSpace, dNSpace, sZSpace, uPSpace;
        TextView tVDepth, dNDepth, sZDepth, upDepth;
        String txt;

        setBackGround(settingActivity.findViewById(R.id.lBibleNameSize));

        setBackGround(settingActivity.findViewById(R.id.lBibleName));
        tVBible = settingActivity.findViewById(R.id.txtBibleName);
        dNBible = settingActivity.findViewById(R.id.bibleName_size_down);
        sZBible = settingActivity.findViewById(R.id.bibleName_size);
        uPBible = settingActivity.findViewById(R.id.bibleName_size_up);
        setTexts(tVBible, dNBible, sZBible, uPBible, textSizeBible66);
        txt = "" + textSizeBible66;
        sZBible.setText(txt);
        dNBible.setOnClickListener(v -> {
            textSizeBible66--;
            String t = "" + textSizeBible66;
            sZBible.setText(t);
            setTexts(tVBible, dNBible, sZBible, uPBible, textSizeBible66);
            sharedEdit.putInt("textSizeBible66", textSizeBible66).apply();
        });
        uPBible.setOnClickListener(v -> {
            textSizeBible66++;
            String t = "" + textSizeBible66;
            sZBible.setText(t);
            setTexts(tVBible, dNBible, sZBible, uPBible, textSizeBible66);
            sharedEdit.putInt("textSizeBible66", textSizeBible66).apply();
        });

        setBackGround(settingActivity.findViewById(R.id.lBibleScript));
        tVScript = settingActivity.findViewById(R.id.txtScript);
        dNScript = settingActivity.findViewById(R.id.script_size_down);
        sZScript = settingActivity.findViewById(R.id.script_size);
        uPScript = settingActivity.findViewById(R.id.script_size_up);
        setTexts(tVScript, dNScript, sZScript, uPScript, textSizeScript);
        txt = "" + textSizeScript;
        sZScript.setText(txt);
        dNScript.setOnClickListener(v -> {
            textSizeScript--;
            String t = "" + textSizeScript;
            sZScript.setText(t);
            setTexts(tVScript, dNScript, sZScript, uPScript, textSizeScript);
            sharedEdit.putInt("textSizeScript", textSizeScript).apply();
        });
        uPScript.setOnClickListener(v -> {
            textSizeScript++;
            String t = "" + textSizeScript;
            sZScript.setText(t);
            setTexts(tVScript, dNScript, sZScript, uPScript, textSizeScript);
            sharedEdit.putInt("textSizeScript", textSizeScript).apply();
        });

        setBackGround(settingActivity.findViewById(R.id.lBibileDic));
        tVDic = settingActivity.findViewById(R.id.txtDic);
        dNDic = settingActivity.findViewById(R.id.dic_size_down);
        sZDic = settingActivity.findViewById(R.id.dic_size);
        uPDic = settingActivity.findViewById(R.id.dic_size_up);
        setTexts(tVDic, dNDic, sZDic, uPDic, textSizeDic);
        txt = "" + textSizeDic;
        sZDic.setText(txt);
        dNDic.setOnClickListener(v -> {
            textSizeDic--;
            String t = "" + textSizeDic;
            sZDic.setText(t);
            setTexts(tVDic, dNDic, sZDic, uPDic, textSizeDic);
            sharedEdit.putInt("textSizeDic", textSizeScript).apply();
        });
        uPDic.setOnClickListener(v -> {
            textSizeDic++;
            String t = "" + textSizeDic;
            sZDic.setText(t);
            setTexts(tVDic, dNDic, sZDic, uPDic, textSizeDic);
            sharedEdit.putInt("textSizeDic", textSizeDic).apply();
        });

        setBackGround(settingActivity.findViewById(R.id.lBibleRefer));
        tVRefer = settingActivity.findViewById(R.id.txtRef);
        dNRefer = settingActivity.findViewById(R.id.ref_size_down);
        sZRefer = settingActivity.findViewById(R.id.ref_size);
        uPRefer = settingActivity.findViewById(R.id.ref_size_up);
        setTexts(tVRefer, dNRefer, sZRefer, uPRefer, textSizeRefer);
        txt = "" + textSizeRefer;
        sZRefer.setText(txt);
        dNRefer.setOnClickListener(v -> {
            textSizeRefer--;
            String t = "" + textSizeRefer;
            sZRefer.setText(t);
            setTexts(tVRefer, dNRefer, sZRefer, uPRefer, textSizeRefer);
            sharedEdit.putInt("textSizeRefer", textSizeRefer).apply();
        });
        uPRefer.setOnClickListener(v -> {
            textSizeRefer++;
            String t = "" + textSizeRefer;
            sZRefer.setText(t);
            setTexts(tVRefer, dNRefer, sZRefer, uPRefer, textSizeRefer);
            sharedEdit.putInt("textSizeRefer", textSizeRefer).apply();
        });

        setBackGround(settingActivity.findViewById(R.id.lBibleSpace));
        tVSpace = settingActivity.findViewById(R.id.txtSpace);
        dNSpace = settingActivity.findViewById(R.id.space_size_down);
        sZSpace = settingActivity.findViewById(R.id.space_size);
        uPSpace = settingActivity.findViewById(R.id.space_size_up);
        setTexts(tVSpace, dNSpace, sZSpace, uPSpace, textSizeSpace);
        txt = "" + textSizeSpace;
        sZSpace.setText(txt);
        dNSpace.setOnClickListener(v -> {
            textSizeSpace--;
            String t = "" + textSizeSpace;
            sZSpace.setText(t);
            setTexts(tVSpace, dNSpace, sZSpace, uPSpace, textSizeSpace);
            sharedEdit.putInt("textSizeSpace", textSizeScript).apply();
        });
        uPSpace.setOnClickListener(v -> {
            textSizeSpace++;
            String t = "" + textSizeSpace;
            sZSpace.setText(t);
            setTexts(tVSpace, dNSpace, sZSpace, uPSpace, textSizeSpace);
            sharedEdit.putInt("textSizeSpace", textSizeScript).apply();
        });

        setBackGround(settingActivity.findViewById(R.id.lBibleDepth));
        tVDepth = settingActivity.findViewById(R.id.txtDepth);
        dNDepth = settingActivity.findViewById(R.id.depth_down);
        sZDepth = settingActivity.findViewById(R.id.depth_size);
        upDepth = settingActivity.findViewById(R.id.depth_up);
        setTexts(tVDepth, dNDepth, sZDepth, upDepth, searchDepth);
        txt = "" + searchDepth;
        sZDepth.setText(txt);
        dNDepth.setOnClickListener(v -> {
            textSizeSpace--;
            String t = "" + searchDepth;
            sZDepth.setText(t);
            setTexts(tVDepth, dNDepth, sZDepth, upDepth, searchDepth);
            sharedEdit.putInt("searchDepth", searchDepth).apply();
        });
        upDepth.setOnClickListener(v -> {
            textSizeSpace++;
            String t = "" + searchDepth;
            sZDepth.setText(t);
            setTexts(tVDepth, dNDepth, sZDepth, upDepth, searchDepth);
            sharedEdit.putInt("searchDepth", searchDepth).apply();
        });

        tv = settingActivity.findViewById(R.id.build_time);
        tv.setTextColor(bibleColorFore);
        tv.setOnClickListener(v -> {
            TableLayout tableLayout = settingActivity.findViewById(R.id.table);
            TableRow tr;
            TableRow.LayoutParams params;
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            ArrayList<String> histories = fileRead.readRawTextFile(mContext, R.raw.history);
            for (String s: histories) {
                String [] oneLines = s.split(";");
                tr = new TableRow(mContext);
                LinearLayout oneLine = (LinearLayout) inflater.inflate(
                        R.layout.history_table, null);
                TextView tVx =  oneLine.findViewById(R.id.date);
                if (oneLines[1].equals("b"))
                    tVx.setTypeface(null, Typeface.BOLD);
                tVx.setText(oneLines[0]);
                tVx =  oneLine.findViewById(R.id.updates);
                if (oneLines[1].equals("b"))
                    tVx.setTypeface(null, Typeface.BOLD);
                tVx.setText(oneLines[2].replace("||", "\n"));
                params = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT, 1f);
                tr.addView(oneLine, params);
                tableLayout.addView(tr);
            }
            ScrollView scrollView = settingActivity.findViewById(R.id.setScrollView);
            scrollView.post(() -> scrollView.scrollTo(0, 500));
        });

        tv = settingActivity.findViewById(R.id.speed_size);
        tv.setTextColor(bibleColorFore);
        txt = "" + bibleSpeed + "%";  // 60 ~ 140
        tv.setText(txt);
        tv =  settingActivity.findViewById(R.id.speed_size_down);
        tv.setOnClickListener(v -> {
            bibleSpeed -= 5;
            TextView tVx = settingActivity.findViewById(R.id.speed_size);
            String t = "" + bibleSpeed + "%";
            tVx.setText(t);
            sharedEdit.putInt("bibleSpeed", bibleSpeed).apply();
        });
        tv =  settingActivity.findViewById(R.id.speed_size_up);
        tv.setTextColor(bibleColorFore);
        tv.setOnClickListener(v -> {
            bibleSpeed += 5;
            TextView tVx = settingActivity.findViewById(R.id.speed_size);
            String t = "" + bibleSpeed + "%";
            tVx.setText(t);
            sharedEdit.putInt("bibleSpeed", bibleSpeed).apply();
        });

        tv =  settingActivity.findViewById(R.id.pitch_size);
        tv.setTextColor(bibleColorFore);
        txt = "" + biblePitch + "%";  // 60 ~ 140
        tv.setText(txt);
        tv =  settingActivity.findViewById(R.id.pitch_size_down);
        tv.setTextColor(bibleColorFore);
        tv.setOnClickListener(v -> {
            biblePitch -= 5;
            TextView tVx = settingActivity.findViewById(R.id.pitch_size);
            String t = "" + biblePitch + "%";
            tVx.setText(t);
            sharedEdit.putInt("biblePitch", biblePitch).apply();
        });
        tv =  settingActivity.findViewById(R.id.pitch_size_up);
        tv.setTextColor(bibleColorFore);
        tv.setOnClickListener(v -> {
            biblePitch += 5;
            TextView tVx = settingActivity.findViewById(R.id.pitch_size);
            String t = "" + biblePitch + "%";
            tVx.setText(t);
            sharedEdit.putInt("biblePitch", biblePitch).apply();
        });
    }

}