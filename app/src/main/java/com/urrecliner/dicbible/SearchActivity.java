package com.urrecliner.dicbible;


import static com.urrecliner.dicbible.Vars.TAB_MODE_HYMN;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.nbrOfChapters;
import static com.urrecliner.dicbible.Vars.nowBible;
import static com.urrecliner.dicbible.Vars.nowChapter;
import static com.urrecliner.dicbible.Vars.searchActivity;
import static com.urrecliner.dicbible.Vars.searchDepth;
import static com.urrecliner.dicbible.Vars.searchNext;
import static com.urrecliner.dicbible.Vars.searchText;
import static com.urrecliner.dicbible.Vars.searcheds;
import static com.urrecliner.dicbible.Vars.shortBibleNames;
import static com.urrecliner.dicbible.Vars.topTab;
import static com.urrecliner.dicbible.Vars.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.urrecliner.dicbible.model.Searched;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    RecyclerView recyclerView;
    TextView  tvFrom, tvSearchKey;
    ImageView ivQuickSearch, ivTextClear, ivSearchNext;

//    final String logID = "search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchActivity = this;
//        utils.log(logID, "searchNext "+searchNext);

        tvFrom = findViewById(R.id.searchStartVerse);
        String s = shortBibleNames[nowBible]+" "+nowChapter+":1~";
        tvFrom.setText(s);
        searcheds = null;
        tvSearchKey = findViewById(R.id.txtSearch);
        if (searchText != null) {
            tvSearchKey.setText(searchText);
            tvSearchKey.setSelectAllOnFocus(true);
            tvSearchKey.requestFocus();
        }
        View.OnKeyListener keyListenerEnter = (v, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_ENTER)       // response to 다음 on keyboard
                searchQuick();
            return false;
        };
        tvSearchKey.setOnKeyListener(keyListenerEnter);

        ivQuickSearch = findViewById(R.id.quickSearch);
        ivQuickSearch.setOnClickListener(v -> searchQuick());

        ivSearchNext = findViewById(R.id.searchNext);
        ivSearchNext.setOnClickListener(v -> {
            if (topTab < TAB_MODE_HYMN) {
                searchText = tvSearchKey.getText().toString();
                search_BibleNext();
                recyclerView = findViewById(R.id.searchedList);
                SearchAdapter searchAdapter = new SearchAdapter();
                recyclerView.setAdapter(searchAdapter);
//                tvSearchKey.requestFocus();
            }
        });
        ivTextClear = findViewById(R.id.text_clear);
        ivTextClear.setOnClickListener(v -> {
            tvSearchKey.setText("");
            tvSearchKey.setFocusable(true);
        });
        if (searchNext) {
            searchNext = false;
            search_Bible(searchText);
            recyclerView = (RecyclerView) findViewById(R.id.searchedList);
            SearchAdapter searchAdapter = new SearchAdapter();
            recyclerView.setAdapter(searchAdapter);
//            tvSearchKey.requestFocus();
            utils.hideKeyboard(searchActivity);
        }

    }

    void searchQuick() {
        if (topTab < TAB_MODE_HYMN) {
            searchText = tvSearchKey.getText().toString();
            search_Bible(searchText);
            recyclerView = (RecyclerView) findViewById(R.id.searchedList);
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

        while (depth > 0) {
            String file2read = "bible/" + bible + "/" + chapter + ".txt";
            bibleVerses = FileRead.readBibleFile(file2read);
            for (int i = 0; i < bibleVerses.length; i++) {
                String s = extractVerse(bibleVerses[i]);
                if (s.contains(text)) {
                    String result = (i>1) ? (i)+")"+extractVerse(bibleVerses[i-1]) + "\n" + (i+1)+")"+s : (i+1)+")"+s;
                    if (i < bibleVerses.length-1)
                        result += "\n" + (i+2)+")"+extractVerse(bibleVerses[i+1]);
                    Searched searchResult = new Searched(bible, chapter, i+1, result);
                    searcheds.add(searchResult);
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
            Toast.makeText(mContext," 검색되지 않습니다. 계속 버튼을 누르거나\n설정에서 검색장수("+searchDepth+")를 늘려보세요.",Toast.LENGTH_LONG).show();
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