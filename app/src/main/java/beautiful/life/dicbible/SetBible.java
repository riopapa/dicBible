package beautiful.life.dicbible;

import static beautiful.life.dicbible.SetActivity.setLayoutBackGround;
import static beautiful.life.dicbible.SetActivity.setTextBackGround;
import static beautiful.life.dicbible.SetActivity.setTexts;
import static beautiful.life.dicbible.Vars.biblePitch;
import static beautiful.life.dicbible.Vars.bibleSpeed;
import static beautiful.life.dicbible.Vars.bibleTTS;
import static beautiful.life.dicbible.Vars.searchDepth;
import static beautiful.life.dicbible.Vars.setActivity;
import static beautiful.life.dicbible.Vars.sharedEdit;
import static beautiful.life.dicbible.Vars.BibleNameSize;
import static beautiful.life.dicbible.Vars.DictShowSize;
import static beautiful.life.dicbible.Vars.DictSize;
import static beautiful.life.dicbible.Vars.BibleSize;
import static beautiful.life.dicbible.Vars.BibleLineSize;

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
        setTexts(tVBible, dNBible, sZBible, uPBible, BibleNameSize);
        txt = "" + BibleNameSize;
        sZBible.setText(txt);
        dNBible.setOnClickListener(v -> {
            BibleNameSize--;
            String t = "" + BibleNameSize;
            sZBible.setText(t);
            setTexts(tVBible, dNBible, sZBible, uPBible, BibleNameSize);
            sharedEdit.putInt("BibleNameSize", BibleNameSize).apply();
        });
        uPBible.setOnClickListener(v -> {
            BibleNameSize++;
            String t = "" + BibleNameSize;
            sZBible.setText(t);
            setTexts(tVBible, dNBible, sZBible, uPBible, BibleNameSize);
            sharedEdit.putInt("BibleNameSize", BibleNameSize).apply();
        });

        setLayoutBackGround(setActivity.findViewById(R.id.lBibleScript));
        tVScript = setActivity.findViewById(R.id.txtScript);
        dNScript = setActivity.findViewById(R.id.script_size_down);
        sZScript = setActivity.findViewById(R.id.script_size);
        uPScript = setActivity.findViewById(R.id.script_size_up);
        setTexts(tVScript, dNScript, sZScript, uPScript, BibleSize);
        txt = "" + BibleSize;
        sZScript.setText(txt);
        dNScript.setOnClickListener(v -> {
            BibleSize--;
            String t = "" + BibleSize;
            sZScript.setText(t);
            setTexts(tVScript, dNScript, sZScript, uPScript, BibleSize);
            sharedEdit.putInt("BibleSize", BibleSize).apply();
        });
        uPScript.setOnClickListener(v -> {
            BibleSize++;
            String t = "" + BibleSize;
            sZScript.setText(t);
            setTexts(tVScript, dNScript, sZScript, uPScript, BibleSize);
            sharedEdit.putInt("BibleSize", BibleSize).apply();
        });

        setLayoutBackGround(setActivity.findViewById(R.id.lBibileDic));
        tVDic = setActivity.findViewById(R.id.txtDic);
        dNDic = setActivity.findViewById(R.id.dic_size_down);
        sZDic = setActivity.findViewById(R.id.dic_size);
        uPDic = setActivity.findViewById(R.id.dic_size_up);
        setTexts(tVDic, dNDic, sZDic, uPDic, DictShowSize);
        txt = "" + DictShowSize;
        sZDic.setText(txt);
        dNDic.setOnClickListener(v -> {
            DictShowSize--;
            String t = "" + DictShowSize;
            sZDic.setText(t);
            setTexts(tVDic, dNDic, sZDic, uPDic, DictShowSize);
            sharedEdit.putInt("DictShowSize", DictShowSize).apply();
        });
        uPDic.setOnClickListener(v -> {
            DictShowSize++;
            String t = "" + DictShowSize;
            sZDic.setText(t);
            setTexts(tVDic, dNDic, sZDic, uPDic, DictShowSize);
            sharedEdit.putInt("DictShowSize", DictShowSize).apply();
        });

        setLayoutBackGround(setActivity.findViewById(R.id.lBibleRefer));
        tVRefer = setActivity.findViewById(R.id.txtRef);
        dNRefer = setActivity.findViewById(R.id.ref_size_down);
        sZRefer = setActivity.findViewById(R.id.ref_size);
        uPRefer = setActivity.findViewById(R.id.ref_size_up);
        setTexts(tVRefer, dNRefer, sZRefer, uPRefer, DictSize);
        txt = "" + DictSize;
        sZRefer.setText(txt);
        dNRefer.setOnClickListener(v -> {
            DictSize--;
            String t = "" + DictSize;
            sZRefer.setText(t);
            setTexts(tVRefer, dNRefer, sZRefer, uPRefer, DictSize);
            sharedEdit.putInt("DictSize", DictSize).apply();
        });
        uPRefer.setOnClickListener(v -> {
            DictSize++;
            String t = "" + DictSize;
            sZRefer.setText(t);
            setTexts(tVRefer, dNRefer, sZRefer, uPRefer, DictSize);
            sharedEdit.putInt("DictSize", DictSize).apply();
        });

        setLayoutBackGround(setActivity.findViewById(R.id.lBibleSpace));
        tVSpace = setActivity.findViewById(R.id.txtSpace);
        dNSpace = setActivity.findViewById(R.id.space_size_down);
        sZSpace = setActivity.findViewById(R.id.space_size);
        uPSpace = setActivity.findViewById(R.id.space_size_up);
        setTexts(tVSpace, dNSpace, sZSpace, uPSpace, BibleLineSize);
        txt = "" + BibleLineSize;
        sZSpace.setText(txt);
        dNSpace.setOnClickListener(v -> {
            BibleLineSize--;
            String t = "" + BibleLineSize;
            sZSpace.setText(t);
            setTexts(tVSpace, dNSpace, sZSpace, uPSpace, BibleLineSize);
            sharedEdit.putInt("BibleLineSize", BibleLineSize).apply();
        });
        uPSpace.setOnClickListener(v -> {
            BibleLineSize++;
            String t = "" + BibleLineSize;
            sZSpace.setText(t);
            setTexts(tVSpace, dNSpace, sZSpace, uPSpace, BibleLineSize);
            sharedEdit.putInt("BibleLineSize", BibleLineSize).apply();
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
        setTexts(tVDepth, dNDepth, sZDepth, uPDepth, BibleSize);
        txt = "" + searchDepth;
        sZDepth.setText(txt);
        dNDepth.setOnClickListener(v -> {
            searchDepth--;
            String t = "" + searchDepth;
            sZDepth.setText(t);
            setTexts(tVDepth, dNDepth, sZDepth, uPDepth, BibleSize);
            sharedEdit.putInt("searchDepth", searchDepth).apply();
        });
        uPDepth.setOnClickListener(v -> {
            searchDepth++;
            String t = "" + searchDepth;
            sZDepth.setText(t);
            setTexts(tVDepth, dNDepth, sZDepth, uPDepth, BibleSize);
            sharedEdit.putInt("searchDepth", searchDepth).apply();
        });
    }

}