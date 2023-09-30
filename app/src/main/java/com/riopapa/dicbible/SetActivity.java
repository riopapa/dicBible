package com.riopapa.dicbible;


import static com.riopapa.dicbible.Vars.TAB_HYMN;
import static com.riopapa.dicbible.Vars.SHOW_DICT;
import static com.riopapa.dicbible.Vars.TAB_NEW;
import static com.riopapa.dicbible.Vars.TAB_OLD;
import static com.riopapa.dicbible.Vars.alwaysOn;
import static com.riopapa.dicbible.Vars.tabBible;
import static com.riopapa.dicbible.Vars.bookMarkAdapter;
import static com.riopapa.dicbible.Vars.bookMarkView;
import static com.riopapa.dicbible.Vars.darkMode;
import static com.riopapa.dicbible.Vars.tabHymn;
import static com.riopapa.dicbible.Vars.mActivity;
import static com.riopapa.dicbible.Vars.menuColorBack;
import static com.riopapa.dicbible.Vars.menuColorFore;
import static com.riopapa.dicbible.Vars.nowBible;
import static com.riopapa.dicbible.Vars.nowHymn;
import static com.riopapa.dicbible.Vars.screenMenu;
import static com.riopapa.dicbible.Vars.setActivity;
import static com.riopapa.dicbible.Vars.sharedEdit;
import static com.riopapa.dicbible.Vars.textColorFore;
import static com.riopapa.dicbible.Vars.topTab;
import static com.riopapa.dicbible.Vars.utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

public class SetActivity extends Activity {

    ScrollView scrollView;
    TextView tv;
    SwitchCompat switchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        setActivity = this;
        scrollView = findViewById(R.id.setScrollView);
//        ScrollView setScrollView = findViewById(R.id.setScrollView);
        scrollView.setBackgroundColor(menuColorBack);
        utils.setFullScreen();
        tv = findViewById(R.id.goBack);
        tv.setOnClickListener(v -> {
            finish();
            if (topTab == TAB_OLD || topTab == TAB_NEW) {
                if (nowBible != 0)
                    tabBible.showBibleBody();
                else
                    tabBible.showBibleList();
            }
            else if (topTab == TAB_HYMN) {
                if (tabHymn == null)
                    tabHymn = new TabHymn();
                if (nowHymn <= 0)
                    tabHymn.showNumberKey();
                else
                    tabHymn.showHymnBody();
            }
            else if (topTab == SHOW_DICT) {
                new DictShow().show();
            }
        });

        darkModeAlwaysOn();

        reshowAll();
    }

    private void reshowAll() {

        View v = setActivity.findViewById(R.id.scrollLayout);
        v.setBackgroundColor(menuColorBack);
        ScrollView setScrollView = findViewById(R.id.setScrollView);
        setScrollView.setBackgroundColor(menuColorBack);
        setLayoutBackGround(setActivity.findViewById(R.id.set));
        setTextBackGround(setActivity.findViewById(R.id.goBack));
        setTextBackGround(setActivity.findViewById(R.id.head));

        setLayoutBackGround(setActivity.findViewById(R.id.lDarkMode));
        switchCompat = findViewById(R.id.dark_mode);
        switchCompat.setBackgroundColor(menuColorBack);
        switchCompat.setTextColor(menuColorFore);
        setLayoutBackGround(setActivity.findViewById(R.id.lAlwaysOn));
        switchCompat = findViewById(R.id.always_on);
        switchCompat.setBackgroundColor(menuColorBack);
        switchCompat.setTextColor(menuColorFore);
        switchCompat.setChecked(alwaysOn);

        SetBible.set();
        SetHymn.set();
        showBookMark();
        new SetHistory(mActivity).set();
        scrollView.invalidate();
    }

    private void darkModeAlwaysOn() {

        setLayoutBackGround(setActivity.findViewById(R.id.lDarkMode));
        switchCompat = findViewById(R.id.dark_mode);
        switchCompat.setBackgroundColor(menuColorBack);
        switchCompat.setTextColor(menuColorFore);
        switchCompat.setChecked(darkMode);
        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            darkMode = isChecked;
            sharedEdit.putBoolean("darkMode", darkMode).apply();
            ScreenColor.apply();
            screenMenu.buildButtonColor();
            reshowAll();
        });

        setLayoutBackGround(setActivity.findViewById(R.id.lAlwaysOn));
        switchCompat = findViewById(R.id.always_on);
        switchCompat.setBackgroundColor(menuColorBack);
        switchCompat.setTextColor(menuColorFore);
        switchCompat.setChecked(alwaysOn);
        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            alwaysOn = isChecked;
            sharedEdit.putBoolean("alwaysOn", alwaysOn).apply();
            utils.setKeepScreen();
        });
    }

    private void showBookMark() {
        setLayoutBackGround(setActivity.findViewById(R.id.lBookMark));
        setTextBackGround(setActivity.findViewById(R.id.tBookMark));
        setTextBackGround(setActivity.findViewById(R.id.tBookMarkDesc));
        bookMarkView = findViewById(R.id.book_marks);
        bookMarkAdapter = new BookMarkAdapter();
        bookMarkView.setAdapter(bookMarkAdapter);
    }

    static void setTextBackGround(TextView view) {
        view.setBackgroundColor(menuColorBack);
        view.setTextColor(menuColorFore);
    }
    static void setLayoutBackGround(View view) {
        view.setBackgroundColor(menuColorBack);
    }

    static void setTexts(TextView tV, TextView dN, TextView sZ, TextView uP, int textColor) {
        tV.setBackgroundColor(menuColorBack); tV.setTextColor(textColorFore); tV.setTextSize(textColor);
        dN.setBackgroundColor(menuColorBack); dN.setTextColor(textColorFore); dN.setTextSize(textColor);
        sZ.setBackgroundColor(menuColorBack); sZ.setTextColor(textColorFore); sZ.setTextSize(textColor);
        uP.setBackgroundColor(menuColorBack); uP.setTextColor(textColorFore); uP.setTextSize(textColor);
    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        if (topTab == TAB_NEW || topTab == TAB_OLD) {
            if (nowBible != 0)
                tabBible.showBibleBody();
            else
                tabBible.showBibleList();
        }
        else if (topTab == TAB_HYMN) {
            if (tabHymn == null)
                tabHymn = new TabHymn();
            if (nowHymn <= 0)
                tabHymn.showNumberKey();
            else
                tabHymn.showHymnBody();
        } else if (topTab == SHOW_DICT) {
            new DictShow().show();
        }
    }

}