package com.riopapa.dicbible;

import static com.riopapa.dicbible.SetActivity.setLayoutBackGround;
import static com.riopapa.dicbible.SetActivity.setTextBackGround;
import static com.riopapa.dicbible.SetActivity.setTexts;
import static com.riopapa.dicbible.Vars.biblePitch;
import static com.riopapa.dicbible.Vars.bibleSpeed;
import static com.riopapa.dicbible.Vars.bibleTTS;
import static com.riopapa.dicbible.Vars.searchDepth;
import static com.riopapa.dicbible.Vars.setActivity;
import static com.riopapa.dicbible.Vars.sharedEdit;
import static com.riopapa.dicbible.Vars.textSizeBible66;
import static com.riopapa.dicbible.Vars.textSizeDic;
import static com.riopapa.dicbible.Vars.textSizeRefer;
import static com.riopapa.dicbible.Vars.textSizeScript;
import static com.riopapa.dicbible.Vars.textSizeSpace;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SetBible {

    public static void set() {
        TextView tVBible, dNBible, sZBible, uPBible;
        TextView tVScript, dNScript, sZScript, uPScript;
        TextView tVDic, dNDic, sZDic, uPDic;
        TextView tVRefer, dNRefer, sZRefer, uPRefer;
        TextView tVSpace, dNSpace, sZSpace, uPSpace;
        TextView tVDepth, dNDepth, sZDepth, uPDepth;
        TextView tVSpeed, dNSpeed, sZSpeed, uPSpeed;
        TextView tVPitch, dNPitch, sZPitch, uPPitch;
        String txt;

        setTextBackGround(setActivity.findViewById(R.id.tBible));

        setLayoutBackGround(setActivity.findViewById(R.id.lBibleNameSize));

        setTextBackGround(setActivity.findViewById(R.id.lBibleName));
        tVBible = setActivity.findViewById(R.id.txtBibleName);
        dNBible = setActivity.findViewById(R.id.bibleName_size_down);
        sZBible = setActivity.findViewById(R.id.bibleName_size);
        uPBible = setActivity.findViewById(R.id.bibleName_size_up);
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

        setLayoutBackGround(setActivity.findViewById(R.id.lBibleScript));
        tVScript = setActivity.findViewById(R.id.txtScript);
        dNScript = setActivity.findViewById(R.id.script_size_down);
        sZScript = setActivity.findViewById(R.id.script_size);
        uPScript = setActivity.findViewById(R.id.script_size_up);
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

        setLayoutBackGround(setActivity.findViewById(R.id.lBibileDic));
        tVDic = setActivity.findViewById(R.id.txtDic);
        dNDic = setActivity.findViewById(R.id.dic_size_down);
        sZDic = setActivity.findViewById(R.id.dic_size);
        uPDic = setActivity.findViewById(R.id.dic_size_up);
        setTexts(tVDic, dNDic, sZDic, uPDic, textSizeDic);
        txt = "" + textSizeDic;
        sZDic.setText(txt);
        dNDic.setOnClickListener(v -> {
            textSizeDic--;
            String t = "" + textSizeDic;
            sZDic.setText(t);
            setTexts(tVDic, dNDic, sZDic, uPDic, textSizeDic);
            sharedEdit.putInt("textSizeDic", textSizeDic).apply();
        });
        uPDic.setOnClickListener(v -> {
            textSizeDic++;
            String t = "" + textSizeDic;
            sZDic.setText(t);
            setTexts(tVDic, dNDic, sZDic, uPDic, textSizeDic);
            sharedEdit.putInt("textSizeDic", textSizeDic).apply();
        });

        setLayoutBackGround(setActivity.findViewById(R.id.lBibleRefer));
        tVRefer = setActivity.findViewById(R.id.txtRef);
        dNRefer = setActivity.findViewById(R.id.ref_size_down);
        sZRefer = setActivity.findViewById(R.id.ref_size);
        uPRefer = setActivity.findViewById(R.id.ref_size_up);
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

        setLayoutBackGround(setActivity.findViewById(R.id.lBibleSpace));
        tVSpace = setActivity.findViewById(R.id.txtSpace);
        dNSpace = setActivity.findViewById(R.id.space_size_down);
        sZSpace = setActivity.findViewById(R.id.space_size);
        uPSpace = setActivity.findViewById(R.id.space_size_up);
        setTexts(tVSpace, dNSpace, sZSpace, uPSpace, textSizeSpace);
        txt = "" + textSizeSpace;
        sZSpace.setText(txt);
        dNSpace.setOnClickListener(v -> {
            textSizeSpace--;
            String t = "" + textSizeSpace;
            sZSpace.setText(t);
            setTexts(tVSpace, dNSpace, sZSpace, uPSpace, textSizeSpace);
            sharedEdit.putInt("textSizeSpace", textSizeSpace).apply();
        });
        uPSpace.setOnClickListener(v -> {
            textSizeSpace++;
            String t = "" + textSizeSpace;
            sZSpace.setText(t);
            setTexts(tVSpace, dNSpace, sZSpace, uPSpace, textSizeSpace);
            sharedEdit.putInt("textSizeSpace", textSizeSpace).apply();
        });

        setTextBackGround(setActivity.findViewById(R.id.tBibleRead));

        setLayoutBackGround(setActivity.findViewById(R.id.lBibleType));
        setTextBackGround(setActivity.findViewById(R.id.tts_voice));
        setTextBackGround(setActivity.findViewById(R.id.bible_tts));
        setTextBackGround(setActivity.findViewById(R.id.bible_voice));

        RadioGroup radioGroup = setActivity.findViewById(R.id.rBibleType);
        RadioButton radioButton;
        radioButton = (bibleTTS) ? setActivity.findViewById(R.id.bible_tts):setActivity.findViewById(R.id.bible_voice);
        radioButton.setChecked(true);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.bible_tts)
                bibleTTS = true;
            else if (checkedId == R.id.bible_voice)
                bibleTTS = false;
            sharedEdit.putBoolean("bibleTTS", bibleTTS).apply();
        });

        setLayoutBackGround(setActivity.findViewById(R.id.lBibleSpeed));
        tVSpeed = setActivity.findViewById(R.id.txtSpeed);
        dNSpeed = setActivity.findViewById(R.id.speed_down);
        sZSpeed = setActivity.findViewById(R.id.speed_size);
        uPSpeed = setActivity.findViewById(R.id.speed_up);
        setTexts(tVSpeed, dNSpeed, sZSpeed, uPSpeed, bibleSpeed*20/100);
        txt = "" + bibleSpeed + "%";
        sZSpeed.setText(txt);
        dNSpeed.setOnClickListener(v -> {
            bibleSpeed -= 5;
            String t = "" + bibleSpeed + "%";
            sZSpeed.setText(t);
            setTexts(tVSpeed, dNSpeed, sZSpeed, uPSpeed, bibleSpeed*20/100);
            sharedEdit.putInt("bibleSpeed", bibleSpeed).apply();
        });
        uPSpeed.setOnClickListener(v -> {
            bibleSpeed += 5;
            String t = "" + bibleSpeed + "%";
            sZSpeed.setText(t);
            setTexts(tVSpeed, dNSpeed, sZSpeed, uPSpeed, bibleSpeed*20/100);
            sharedEdit.putInt("bibleSpeed", bibleSpeed).apply();
        });

        setLayoutBackGround(setActivity.findViewById(R.id.lBiblePitch));
        tVPitch = setActivity.findViewById(R.id.txtPitch);
        dNPitch = setActivity.findViewById(R.id.pitch_down);
        sZPitch = setActivity.findViewById(R.id.pitch_size);
        uPPitch = setActivity.findViewById(R.id.pitch_up);
        setTexts(tVPitch, dNPitch, sZPitch, uPPitch, biblePitch*20/100);
        txt = "" + biblePitch + "%";
        sZPitch.setText(txt);
        dNPitch.setOnClickListener(v -> {
            biblePitch -= 5;
            String t = "" + biblePitch + "%";
            sZPitch.setText(t);
            setTexts(tVPitch, dNPitch, sZPitch, uPPitch, biblePitch*20/100);
            sharedEdit.putInt("biblePitch", biblePitch).apply();
        });
        uPPitch.setOnClickListener(v -> {
            biblePitch += 5;
            String t = "" + biblePitch + "%";
            sZPitch.setText(t);
            setTexts(tVPitch, dNPitch, sZPitch, uPPitch, biblePitch*20/100);
            sharedEdit.putInt("biblePitch", biblePitch).apply();
        });

        setLayoutBackGround(setActivity.findViewById(R.id.lBibleDepth));
        tVDepth = setActivity.findViewById(R.id.txtDepth);
        dNDepth = setActivity.findViewById(R.id.depth_down);
        sZDepth = setActivity.findViewById(R.id.depth_size);
        uPDepth = setActivity.findViewById(R.id.depth_up);
        setTexts(tVDepth, dNDepth, sZDepth, uPDepth, textSizeScript);
        txt = "" + searchDepth;
        sZDepth.setText(txt);
        dNDepth.setOnClickListener(v -> {
            searchDepth--;
            String t = "" + searchDepth;
            sZDepth.setText(t);
            setTexts(tVDepth, dNDepth, sZDepth, uPDepth, textSizeScript);
            sharedEdit.putInt("searchDepth", searchDepth).apply();
        });
        uPDepth.setOnClickListener(v -> {
            searchDepth++;
            String t = "" + searchDepth;
            sZDepth.setText(t);
            setTexts(tVDepth, dNDepth, sZDepth, uPDepth, textSizeScript);
            sharedEdit.putInt("searchDepth", searchDepth).apply();
        });
    }

}