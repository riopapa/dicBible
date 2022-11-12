package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.fileRead;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.menuColorBack;
import static com.urrecliner.dicbible.Vars.menuColorFore;
import static com.urrecliner.dicbible.Vars.setActivity;
import static com.urrecliner.dicbible.Vars.textSizeScript;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SetHistory {

    static boolean show = false;
    public static void set() {
        TextView tv;

        tv = setActivity.findViewById(R.id.build_time);
        tv.setTextColor(menuColorFore);
        tv.setBackgroundColor(menuColorBack);
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String build_time = "제작 : "+dateTimeFormat.format(BuildConfig.BUILD_TIME)+", 하원철 (?)";
        tv.setText(build_time);
        tv.setOnClickListener(v -> {
            showHistory();
        });
        if (show)
            showHistory();
    }

    static void showHistory() {
        show = true;
        TableLayout tableLayout = setActivity.findViewById(R.id.table);
        TableRow.LayoutParams tLparams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT, 1f);
        TableRow tr;
        LayoutInflater faceInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout myFaceLayout = (LinearLayout) faceInflater.inflate(
                R.layout.my_full_face, null);
        LinearLayout.LayoutParams myFaceParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        myFaceParams.setMargins(20,20,20,20);
        tableLayout.addView(myFaceLayout, myFaceParams);

        LayoutInflater tLInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ArrayList<String> histories = fileRead.readRawTextFile(mContext, R.raw.history);
        TextView tvCell;
        for (String s : histories) {
            String[] oneLines = s.split(";");
            tr = new TableRow(mContext);
            tr.setOrientation(LinearLayout.VERTICAL);
            tr.setBackgroundColor(menuColorBack);
            LinearLayout oneLine = (LinearLayout) tLInflater.inflate(
                    R.layout.history_table, null);
            tvCell = oneLine.findViewById(R.id.date);
            if (oneLines[1].equals("b"))
                tvCell.setTypeface(null, Typeface.BOLD);
            else
                tvCell.setTypeface(null, Typeface.NORMAL);
            tvCell.setText(oneLines[0]);
            tvCell.setTextSize((float) textSizeScript * 4 / 5);
            tvCell.setTextColor(menuColorFore);
            tvCell.setBackgroundColor(menuColorBack);
            tvCell.setBackgroundResource(R.drawable.cell_border);
            tvCell = oneLine.findViewById(R.id.updates);
            if (oneLines[1].equals("b"))
                tvCell.setTypeface(null, Typeface.BOLD);
            else
                tvCell.setTypeface(null, Typeface.NORMAL);
            s = oneLines[2].replace("||", "\n");
            tvCell.setText(s);
            tvCell.setTextSize((float) textSizeScript * 3 / 4);
            tvCell.setTextColor(menuColorFore);
            tvCell.setBackgroundColor(menuColorBack);
            tvCell.setBackgroundResource(R.drawable.cell_border);
            tr.addView(oneLine, tLparams);
            tableLayout.addView(tr);
        }
        ScrollView scrollView = setActivity.findViewById(R.id.setScrollView);
        scrollView.post(() ->
                scrollView.scrollTo(0, scrollView.getHeight() - 1));

    }
}