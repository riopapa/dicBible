package com.riopapa.dicbible;

import static com.riopapa.dicbible.Vars.dicts;
import static com.riopapa.dicbible.Vars.fBody;
import static com.riopapa.dicbible.Vars.history;
import static com.riopapa.dicbible.Vars.linearLayout;
import static com.riopapa.dicbible.Vars.mContext;
import static com.riopapa.dicbible.Vars.menuColorBack;
import static com.riopapa.dicbible.Vars.menuColorFore;
import static com.riopapa.dicbible.Vars.nowDic;
import static com.riopapa.dicbible.Vars.packageFolder;
import static com.riopapa.dicbible.Vars.screenMenu;
import static com.riopapa.dicbible.Vars.scrollView;
import static com.riopapa.dicbible.Vars.textView;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

class DictMake {

    void showDictMenu() {

        DictAdapter adapter;
        File dicFile = new File(packageFolder,"dict");
        screenMenu.build();
        new FrameScrollView();
        if (dicts == null)
            dicts = new DictLoad().get(dicFile);
        history.push();

        linearLayout.addView(textView);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout_inp = (LinearLayout) inflater.inflate(R.layout.dict_search, null);
        TextView viewTxt = layout_inp.findViewById(R.id.enterDict);
        viewTxt.setBackgroundColor(menuColorBack);
        viewTxt.setTextColor(menuColorFore);

        NestedScrollView dict_scroll = (NestedScrollView) inflater.inflate(R.layout.dict_scroll, null);
        dict_scroll.setBackgroundColor(menuColorBack);
        adapter = new DictAdapter(mContext, dicts, dicFile);
        RecyclerView recyclerView = dict_scroll.findViewById(R.id.dictRecycle);
        recyclerView.setAdapter(adapter);

        linearLayout.addView(layout_inp);
        linearLayout.addView(dict_scroll);
        EditText tvDicKey = layout_inp.findViewById(R.id.dict_key);
        tvDicKey.setBackgroundColor(menuColorBack);
        tvDicKey.setTextColor(menuColorFore);
        tvDicKey.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }
        });
        if (nowDic != null)
            tvDicKey.setText(nowDic);

        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);
    }

}