package com.urrecliner.dicbible;


import static com.urrecliner.dicbible.Vars.LYRIC_ONLY;
import static com.urrecliner.dicbible.Vars.LYRIC_THEN_SHEET;
import static com.urrecliner.dicbible.Vars.SHEET_ONLY;
import static com.urrecliner.dicbible.Vars.SHEET_THEN_LYRIC;
import static com.urrecliner.dicbible.Vars.TAB_MODE_DIC;
import static com.urrecliner.dicbible.Vars.TAB_MODE_HYMN;
import static com.urrecliner.dicbible.Vars.TAB_MODE_NEW;
import static com.urrecliner.dicbible.Vars.TAB_MODE_OLD;
import static com.urrecliner.dicbible.Vars.alwaysOn;
import static com.urrecliner.dicbible.Vars.biblePitch;
import static com.urrecliner.dicbible.Vars.bibleSpeed;
import static com.urrecliner.dicbible.Vars.bookMarkAdapter;
import static com.urrecliner.dicbible.Vars.bookMarkView;
import static com.urrecliner.dicbible.Vars.darkMode;
import static com.urrecliner.dicbible.Vars.hymnAccompany;
import static com.urrecliner.dicbible.Vars.hymnShowWhat;
import static com.urrecliner.dicbible.Vars.hymnSpeed;
import static com.urrecliner.dicbible.Vars.mActivity;
import static com.urrecliner.dicbible.Vars.makeBible;
import static com.urrecliner.dicbible.Vars.makeHymn;
import static com.urrecliner.dicbible.Vars.nowBible;
import static com.urrecliner.dicbible.Vars.nowHymn;
import static com.urrecliner.dicbible.Vars.screenColorBack;
import static com.urrecliner.dicbible.Vars.settingActivity;
import static com.urrecliner.dicbible.Vars.sharedEdit;
import static com.urrecliner.dicbible.Vars.textSizeHymn;
import static com.urrecliner.dicbible.Vars.topTab;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class SettingActivity extends Activity {

    ScrollView scrollView;
    TextView tv;
    View v;
    SwitchCompat switchCompat;
    String txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        settingActivity = this;
        scrollView = findViewById(R.id.setScrollView);

        tv = findViewById(R.id.goBack);
        tv.setOnClickListener(v -> {
            finish();
            if (topTab == TAB_MODE_OLD || topTab == TAB_MODE_NEW) {
                if (nowBible != 0)
                    makeBible.showBibleBody();
                else
                    makeBible.showBibleList();
            }
            else if (topTab == TAB_MODE_HYMN) {
                if (makeHymn == null)
                    makeHymn = new MakeHymn();
                if (nowHymn <= 0)
                    makeHymn.showNumberKey();
                else
                    makeHymn.showHymnBody();
            }
            else if (topTab == TAB_MODE_DIC) {
                makeBible.showDicWord();
            }
        });

        darkModeAlwaysOn();

        reshowAll();
    }

    private void reshowAll() {
        View v = settingActivity.findViewById(R.id.scrollLayout);
        v.setBackgroundColor(screenColorBack);
        SettingBible.set();
        buildSetHymn();
        buildSetPlayHymn();
        buildSetBookMark();
        buildShowAuthor();
        scrollView.invalidate();
    }


    // 0.6f < bibleSpeed < (0.6 + 0.8) f     <==  0 <seekBar < 8
    private void buildSetBibleRead() {

        tv =  findViewById(R.id.speed_size);
        txt = "" + bibleSpeed + "%";  // 60 ~ 140
        tv.setText(txt);
        tv =  findViewById(R.id.speed_size_down);
        tv.setOnClickListener(v -> {
            bibleSpeed -= 5;
            tv =  findViewById(R.id.speed_size);
            String t = "" + bibleSpeed + "%";
            tv.setText(t);
            sharedEdit.putInt("bibleSpeed", bibleSpeed).apply();
        });
        tv =  findViewById(R.id.speed_size_up);
        tv.setOnClickListener(v -> {
            bibleSpeed += 5;
            tv =  findViewById(R.id.speed_size);
            String t = "" + bibleSpeed + "%";
            tv.setText(t);
            sharedEdit.putInt("bibleSpeed", bibleSpeed).apply();
        });
        
        tv =  findViewById(R.id.pitch_size);
        txt = "" + biblePitch + "%";  // 60 ~ 140
        tv.setText(txt);
        tv =  findViewById(R.id.pitch_size_down);
        tv.setOnClickListener(v -> {
            biblePitch -= 5;
            tv =  findViewById(R.id.pitch_size);
            String t = "" + biblePitch + "%";
            tv.setText(t);
            sharedEdit.putInt("biblePitch", biblePitch).apply();
        });
        tv =  findViewById(R.id.pitch_size_up);
        tv.setOnClickListener(v -> {
            biblePitch += 5;
            tv =  findViewById(R.id.pitch_size);
            String t = "" + biblePitch + "%";
            tv.setText(t);
            sharedEdit.putInt("biblePitch", biblePitch).apply();
        });
    }

    private void buildSetHymn() {
        RadioGroup radioGroup1 = findViewById(R.id.hymnShowGrp);
        RadioButton radioButton1;
        switch(hymnShowWhat) {
            case SHEET_THEN_LYRIC:
                radioButton1 = findViewById(R.id.sheetThenLyric); radioButton1.setChecked(true); break;
            case LYRIC_THEN_SHEET:
                radioButton1 = findViewById(R.id.lyricThenSheet); radioButton1.setChecked(true); break;
            case SHEET_ONLY:
                radioButton1 = findViewById(R.id.sheet_only); radioButton1.setChecked(true); break;
            case LYRIC_ONLY:
                radioButton1 = findViewById(R.id.lyric_only); radioButton1.setChecked(true); break;
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

        tv =  findViewById(R.id.hymn_size);
        txt = "" + textSizeHymn;
        tv.setText(txt);
        tv =  findViewById(R.id.hymn_size_down);
        tv.setOnClickListener(v -> {
            textSizeHymn--;
            tv =  findViewById(R.id.hymn_size);
            String t = "" + textSizeHymn;
            tv.setText(t);
            sharedEdit.putInt("textSizeHymnBody", textSizeHymn).apply();
        });
        tv =  findViewById(R.id.hymn_size_up);
        tv.setOnClickListener(v -> {
            textSizeHymn++;
            tv =  findViewById(R.id.hymn_size);
            String t = "" + textSizeHymn;
            tv.setText(t);
            sharedEdit.putInt("textSizeHymnBody", textSizeHymn).apply();
        });

        RadioGroup radioGroup2 = findViewById(R.id.hymnSpeakType);
        RadioButton radioButton2;
        radioButton2 = (hymnAccompany) ? findViewById(R.id.hymnMusic):findViewById(R.id.hymnChoir);
        radioButton2.setChecked(true);

        radioGroup2.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.hymnChoir)
                    hymnAccompany = false;
            else if (checkedId == R.id.hymnMusic)
                    hymnAccompany = true;
            sharedEdit.putBoolean("hymnAccompany", hymnAccompany).apply();
        });
    }

    private void darkModeAlwaysOn() {
        switchCompat = findViewById(R.id.dark_mode);
        switchCompat.setChecked(darkMode);
        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            darkMode = isChecked;
            sharedEdit.putBoolean("darkMode", darkMode).apply();
            ScreenColor.setVars();
            reshowAll();

        });
        switchCompat = findViewById(R.id.always_on);
        switchCompat.setChecked(alwaysOn);
        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            alwaysOn = isChecked;
            sharedEdit.putBoolean("alwaysOn", alwaysOn).apply();
            if (alwaysOn)
                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            else
                mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        });
    }

    private void buildSetPlayHymn() {

        tv =  findViewById(R.id.hymn_size);
        txt = "" + hymnSpeed + "%";  // 60 ~ 140
        tv.setText(txt);
        tv =  findViewById(R.id.hymn_size_down);
        tv.setOnClickListener(v -> {
            hymnSpeed -= 5;
            tv =  findViewById(R.id.hymn_size);
            String t = "" + hymnSpeed + "%";
            tv.setText(t);
            sharedEdit.putInt("hymnSpeed", hymnSpeed).apply();
        });
        tv =  findViewById(R.id.hymn_size_up);
        tv.setOnClickListener(v -> {
            hymnSpeed += 5;
            tv =  findViewById(R.id.hymn_size);
            String t = "" + hymnSpeed + "%";
            tv.setText(t);
            sharedEdit.putInt("hymnSpeed", hymnSpeed).apply();
        });
    }

    private void buildSetBookMark() {
        bookMarkView = findViewById(R.id.book_marks);
        bookMarkAdapter = new BookMarkAdapter();
        bookMarkView.setAdapter(bookMarkAdapter);
    }

    private void buildShowAuthor() {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String build_time = "제작 : "+dateTimeFormat.format(BuildConfig.BUILD_TIME)+", 하원철";
        tv =  findViewById(R.id.build_time);
        tv.setText(build_time);
    }


}