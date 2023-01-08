package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.dicts;
import static com.urrecliner.dicbible.Vars.fBody;
import static com.urrecliner.dicbible.Vars.history;
import static com.urrecliner.dicbible.Vars.linearLayout;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.menuColorBack;
import static com.urrecliner.dicbible.Vars.menuColorFore;
import static com.urrecliner.dicbible.Vars.nowDic;
import static com.urrecliner.dicbible.Vars.packageFolder;
import static com.urrecliner.dicbible.Vars.screenMenu;
import static com.urrecliner.dicbible.Vars.scrollView;
import static com.urrecliner.dicbible.Vars.textView;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

class MakeDict {

    void showDictMenu() {

        File dicFile = new File(packageFolder,"dict");
        screenMenu.build();
        new FrameScrollView();
        if (dicts == null)
            dicts = new DictLoad(dicFile).get();
        history.push();

        linearLayout.addView(textView);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout_inp = (LinearLayout) inflater.inflate(R.layout.dict_search, null);
        TextView viewTxt = layout_inp.findViewById(R.id.enterDict);
        viewTxt.setBackgroundColor(menuColorBack);
        viewTxt.setTextColor(menuColorFore);
        DictAdapter adapter = new DictAdapter(mContext, dicts, dicFile);
        RecyclerView recyclerView = layout_inp.findViewById(R.id.dictRecycle);
        recyclerView.setAdapter(adapter);
        EditText tvDicKey = layout_inp.findViewById(R.id.dict_key);
        tvDicKey.setBackgroundColor(menuColorBack);
        tvDicKey.setTextColor(menuColorFore);
        tvDicKey.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }
        });
        if (nowDic != null)
            tvDicKey.setText(nowDic);

        //        InputMethodManager imm = (InputMethodManager)
//                mContext.getSystemService(INPUT_METHOD_SERVICE);
//        imm.showSoftInput(tvDicKey, InputMethodManager.SHOW_IMPLICIT);
        linearLayout.addView(layout_inp);

//        showRecent(linearLayout);
        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);

//        viewDict.setOnClickListener(v -> {
//            nowDic = tvDicKey.getText().toString();
//            showDicWord();
//        });
    }

}