package com.riopapa.dicbible;

import static com.riopapa.dicbible.SetActivity.setLayoutBackGround;
import static com.riopapa.dicbible.SetActivity.setTextBackGround;
import static com.riopapa.dicbible.SetActivity.setTexts;
import static com.riopapa.dicbible.Vars.LYRIC_ONLY;
import static com.riopapa.dicbible.Vars.LYRIC_THEN_SHEET;
import static com.riopapa.dicbible.Vars.SHEET_ONLY;
import static com.riopapa.dicbible.Vars.SHEET_THEN_LYRIC;
import static com.riopapa.dicbible.Vars.bibleSpeed;
import static com.riopapa.dicbible.Vars.hymnAccompany;
import static com.riopapa.dicbible.Vars.hymnShowWhat;
import static com.riopapa.dicbible.Vars.hymnSpeed;
import static com.riopapa.dicbible.Vars.setActivity;
import static com.riopapa.dicbible.Vars.sharedEdit;
import static com.riopapa.dicbible.Vars.HymnSize;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SetHymn {

    public static void set() {
        TextView tVSize, dNSize, sZSize, uPSize;
        TextView tVSpeed, dNSpeed, sZSpeed, uPSpeed;
        String txt;

        setTextBackGround(setActivity.findViewById(R.id.tHymn));
        setTextBackGround(setActivity.findViewById(R.id.tHymnLayout));

        setLayoutBackGround(setActivity.findViewById(R.id.lHymnShowGrp));

        setTextBackGround(setActivity.findViewById(R.id.sheetThenLyric));
        setTextBackGround(setActivity.findViewById(R.id.lyricThenSheet));
        setTextBackGround(setActivity.findViewById(R.id.sheet_only));
        setTextBackGround(setActivity.findViewById(R.id.lyric_only));

        RadioGroup radioGroup1 = setActivity.findViewById(R.id.rHymnShowWhat);
        RadioButton radioButton1;
        switch(hymnShowWhat) {
            case SHEET_THEN_LYRIC:
                radioButton1 = setActivity.findViewById(R.id.sheetThenLyric); radioButton1.setChecked(true); break;
            case LYRIC_THEN_SHEET:
                radioButton1 = setActivity.findViewById(R.id.lyricThenSheet); radioButton1.setChecked(true); break;
            case SHEET_ONLY:
                radioButton1 = setActivity.findViewById(R.id.sheet_only); radioButton1.setChecked(true); break;
            case LYRIC_ONLY:
                radioButton1 = setActivity.findViewById(R.id.lyric_only); radioButton1.setChecked(true); break;
        }
        radioGroup1.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.sheetThenLyric)
                hymnShowWhat = SHEET_THEN_LYRIC;
            else if (checkedId == R.id.lyricThenSheet)
                hymnShowWhat = LYRIC_THEN_SHEET;
            else if (checkedId == R.id.sheet_only)
                hymnShowWhat = SHEET_ONLY;
            else if (checkedId == R.id.lyric_only)
                hymnShowWhat = LYRIC_ONLY;
            sharedEdit.putInt("hymnShowWhat", hymnShowWhat).apply();
        });

        setLayoutBackGround(setActivity.findViewById(R.id.lHymnSize));
        tVSize = setActivity.findViewById(R.id.txtHymnSize);
        dNSize = setActivity.findViewById(R.id.hymn_size_down);
        sZSize = setActivity.findViewById(R.id.hymn_size);
        uPSize = setActivity.findViewById(R.id.hymn_size_up);
        setTexts(tVSize, dNSize, sZSize, uPSize, HymnSize);
        txt = "" + HymnSize;
        sZSize.setText(txt);
        dNSize.setOnClickListener(v -> {
            HymnSize--;
            String t = "" + HymnSize;
            sZSize.setText(t);
            setTexts(tVSize, dNSize, sZSize, uPSize, HymnSize);
            sharedEdit.putInt("HymnSize", HymnSize).apply();
        });
        uPSize.setOnClickListener(v -> {
            HymnSize++;
            String t = "" + HymnSize;
            sZSize.setText(t);
            setTexts(tVSize, dNSize, sZSize, uPSize, HymnSize);
            sharedEdit.putInt("HymnSize", HymnSize).apply();
        });

        setLayoutBackGround(setActivity.findViewById(R.id.lHymnSpeed));
        tVSpeed = setActivity.findViewById(R.id.txtHymnSpeed);
        dNSpeed = setActivity.findViewById(R.id.hymn_speed_down);
        sZSpeed = setActivity.findViewById(R.id.hymn_speed_size);
        uPSpeed = setActivity.findViewById(R.id.hymn_speed_up);
        setTexts(tVSpeed, dNSpeed, sZSpeed, uPSpeed, hymnSpeed*20/100);
        txt = "" + hymnSpeed + "%";
        sZSpeed.setText(txt);
        dNSpeed.setOnClickListener(v -> {
            hymnSpeed -= 5;
            String t = "" + hymnSpeed + "%";
            sZSpeed.setText(t);
            setTexts(tVSpeed, dNSpeed, sZSpeed, uPSpeed, hymnSpeed*20/100);
            sharedEdit.putInt("hymnSpeed", bibleSpeed).apply();
        });
        uPSpeed.setOnClickListener(v -> {
            hymnSpeed += 5;
            String t = "" + hymnSpeed + "%";
            sZSpeed.setText(t);
            setTexts(tVSpeed, dNSpeed, sZSpeed, uPSpeed, hymnSpeed*20/100);
            sharedEdit.putInt("hymnSpeed", hymnSpeed).apply();
        });

        setLayoutBackGround(setActivity.findViewById(R.id.lHymnType));
        setTextBackGround(setActivity.findViewById(R.id.accompany_choir));
        setTextBackGround(setActivity.findViewById(R.id.hymnChoir));
        setTextBackGround(setActivity.findViewById(R.id.hymnMusic));

        RadioGroup radioGroup2 = setActivity.findViewById(R.id.hymnSpeakType);
        RadioButton radioButton2;
        radioButton2 = (hymnAccompany) ? setActivity.findViewById(R.id.hymnMusic):setActivity.findViewById(R.id.hymnChoir);
        radioButton2.setChecked(true);

        radioGroup2.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.hymnChoir)
                hymnAccompany = false;
            else if (checkedId == R.id.hymnMusic)
                hymnAccompany = true;
            sharedEdit.putBoolean("hymnAccompany", hymnAccompany).apply();
        });
    }
}