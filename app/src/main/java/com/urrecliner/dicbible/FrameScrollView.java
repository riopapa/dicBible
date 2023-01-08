package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.linearLayout;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.menuColorBack;
import static com.urrecliner.dicbible.Vars.scrollView;
import static com.urrecliner.dicbible.Vars.textView;

import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class FrameScrollView {
    public FrameScrollView() {
        scrollView = new ScrollView(mContext);
        scrollView.setBackgroundColor(menuColorBack);
        textView = new TextView(mContext);
        linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lParams.setMargins(20, 0, 30, 0);
        scrollView.addView(linearLayout, lParams);
    }
}