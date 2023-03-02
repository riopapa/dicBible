package com.riopapa.dicbible;


import static com.riopapa.dicbible.SetActivity.setLayoutBackGround;
import static com.riopapa.dicbible.SetActivity.setTextBackGround;
import static com.riopapa.dicbible.Vars.TAB_NEW;
import static com.riopapa.dicbible.Vars.TAB_OLD;
import static com.riopapa.dicbible.Vars.fileRead;
import static com.riopapa.dicbible.Vars.keyText;
import static com.riopapa.dicbible.Vars.mContext;
import static com.riopapa.dicbible.Vars.menuColorFore;
import static com.riopapa.dicbible.Vars.nbrOfChapters;
import static com.riopapa.dicbible.Vars.nowBible;
import static com.riopapa.dicbible.Vars.nowChapter;
import static com.riopapa.dicbible.Vars.searchActivity;
import static com.riopapa.dicbible.Vars.searchDepth;
import static com.riopapa.dicbible.Vars.searchNext;
import static com.riopapa.dicbible.Vars.searcheds;
import static com.riopapa.dicbible.Vars.shortBibleNames;
import static com.riopapa.dicbible.Vars.topTab;
import static com.riopapa.dicbible.Vars.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.riopapa.dicbible.model.Searched;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    RecyclerView recyclerView;
    TextView  tvFrom, tvSearchKey;
    ImageView ivSearch, ivTextClear, ivSearchNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchActivity = this;
        setLayoutBackGround(this.findViewById(R.id.search_scroll));
        setLayoutBackGround(this.findViewById(R.id.lSearch));
        setTextBackGround(this.findViewById(R.id.tSearch));

        tvFrom = findViewById(R.id.searchStartVerse);
        String s = shortBibleNames[nowBible]+" "+nowChapter+":1~";
        tvFrom.setText(s);
        tvFrom.setTextColor(menuColorFore);
        searcheds = null;
        tvSearchKey = findViewById(R.id.txtSearch);
        tvSearchKey.setText(keyText);
        tvSearchKey.setTextColor(menuColorFore);
        tvSearchKey.setSelectAllOnFocus(true);
        tvSearchKey.requestFocus();
        InputMethodManager imm = (InputMethodManager)
                getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(tvSearchKey, InputMethodManager.SHOW_IMPLICIT);
        View.OnKeyListener keyListenerEnter = (v, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_ENTER)       // response to 다음 on keyboard
                searchQuick();
            return false;
        };
        tvSearchKey.setOnKeyListener(keyListenerEnter);

        ivSearch = findViewById(R.id.searchKey);
        ivSearch.setOnClickListener(v -> searchQuick());
        Drawable d = VectorDrawableCompat.create(mContext.getResources(), R.drawable.ic_search, null);
        Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, menuColorFore);
        ivSearch.setImageDrawable(wrappedDrawable);

        ivSearchNext = findViewById(R.id.searchNext);
//        ivSearchNext.setBackgroundColor(menuColorBack);
        d = VectorDrawableCompat.create(mContext.getResources(), R.drawable.ic_search_next, null);
        wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, menuColorFore);
        ivSearchNext.setImageDrawable(wrappedDrawable);

        ivSearchNext.setOnClickListener(v -> {
            if ((topTab == TAB_OLD || topTab == TAB_NEW)) {
                keyText = tvSearchKey.getText().toString();
                search_BibleNext();
                recyclerView = findViewById(R.id.searchedList);
                SearchAdapter searchAdapter = new SearchAdapter();
                recyclerView.setAdapter(searchAdapter);
            }
        });
        ivTextClear = findViewById(R.id.text_clear);
        d = VectorDrawableCompat.create(mContext.getResources(), R.drawable.clear_text, null);
        wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, menuColorFore);
        ivTextClear.setImageDrawable(wrappedDrawable);
        ivTextClear.setOnClickListener(v -> {
            tvSearchKey.setText("");
            tvSearchKey.requestFocus();
            imm.showSoftInput(tvSearchKey, InputMethodManager.SHOW_IMPLICIT);
        });
        if (searchNext) {
            searchNext = false;
            search_Bible(keyText);
            recyclerView = findViewById(R.id.searchedList);
            SearchAdapter searchAdapter = new SearchAdapter();
            recyclerView.setAdapter(searchAdapter);
            utils.hideKeyboard(searchActivity);
        }
    }

    void searchQuick() {
        if ((topTab == TAB_OLD || topTab == TAB_NEW)) {
            keyText = tvSearchKey.getText().toString();
            search_Bible(keyText);
            recyclerView = findViewById(R.id.searchedList);
            SearchAdapter searchAdapter = new SearchAdapter();
            recyclerView.setAdapter(searchAdapter);
//            tvSearchKey.requestFocus();
            utils.hideKeyboard(searchActivity);
        }

    }
    String[] bibleVerses;
    void search_Bible(String text) {
        int bible = nowBible;
        int chapter = nowChapter;
        int depth = searchDepth;
        searcheds = new ArrayList<>();
        String [] kwd = text.trim().split("\\^");
        if (kwd.length> 1) {
            kwd[0] = kwd[0].trim();
            kwd[1] = kwd[1].trim();
        }
        while (depth > 0) {
            String file2read = "bible/" + bible + "/" + chapter + ".txt";
            bibleVerses = fileRead.readBibleFile(file2read, false);
            for (int i = 0; i < bibleVerses.length; i++) {
                String s = extractVerse(bibleVerses[i]);
                if (s.contains(kwd[0])) {
                    if (kwd.length == 1 || s.contains(kwd[1])) {
                        String result = "";
                        if (i > 0)
                            result = extractVerse(bibleVerses[i - 1]);
                        result += (result.equals("") ? "":"\n") + "["+(i+1)+"] "+s;
                        if (i < bibleVerses.length - 1)
                            result += "\n" + extractVerse(bibleVerses[i + 1]);
                        Searched searchResult = new Searched(bible, chapter, i + 1, result);
                        searcheds.add(searchResult);
                    }
                }
            }
            depth--;
            chapter++;
            if (chapter > nbrOfChapters[bible]) {
                bible++;
                if (bible > 66) {
                    depth = 0;
                } else {
                    chapter = 1;
                }
            }
        }
        if (searcheds.size() == 0)
            utils.showSnackBar(keyText +" 없음."," ⟫ 을 누르거나 검색장수("+searchDepth+")를 늘려보세요.");
    }

    private String extractVerse(String bibleVers) {
        final String match = "[^\uAC00-\uD7A3xfe/,\\s]"; // 한글만 OK
        String s = bibleVers.substring(0, bibleVers.indexOf("`"));
        if (s.indexOf('}') > 0)
            s = s.substring(s.indexOf('}') + 1);
        s = s.replaceAll(match, "");
        return s;
    }

    void search_BibleNext() {
        int depth = searchDepth;
        while (depth > 0) {
            depth--;
            nowChapter++;
            if (nowChapter > nbrOfChapters[nowBible]) {
                nowBible++;
                if (nowBible > 66) {
                    depth = 0;
                } else {
                    nowChapter = 1;
                }
            }
        }
        searchNext = true;
        finish();
        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);
    }
}