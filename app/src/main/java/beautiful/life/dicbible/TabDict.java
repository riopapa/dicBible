package beautiful.life.dicbible;

import static beautiful.life.dicbible.Vars.TAB_DICT;
import static beautiful.life.dicbible.Vars.agpDrawable;
import static beautiful.life.dicbible.Vars.dicts;
import static beautiful.life.dicbible.Vars.fBody;
import static beautiful.life.dicbible.Vars.goBackProcs;
import static beautiful.life.dicbible.Vars.linearLayout;
import static beautiful.life.dicbible.Vars.mContext;
import static beautiful.life.dicbible.Vars.menuColorBack;
import static beautiful.life.dicbible.Vars.menuColorFore;
import static beautiful.life.dicbible.Vars.nowDic;
import static beautiful.life.dicbible.Vars.packageFolder;
import static beautiful.life.dicbible.Vars.screenMenu;
import static beautiful.life.dicbible.Vars.scrollView;
import static beautiful.life.dicbible.Vars.textView;
import static beautiful.life.dicbible.Vars.topTab;

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

class TabDict {

    void showDictMenu() {

        DictAdapter adapter;
        topTab = TAB_DICT;
        File dicFile = new File(packageFolder,"dict");
        screenMenu.build();
        new FrameScrollView();
        if (dicts == null)
            dicts = new DictLoad().get(dicFile);
        goBackProcs.push();

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
        tvDicKey.setBackground(agpDrawable);
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