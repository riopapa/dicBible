package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.fileRead;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.menuColorBack;
import static com.urrecliner.dicbible.Vars.menuColorFore;
import static com.urrecliner.dicbible.Vars.setActivity;
import static com.urrecliner.dicbible.Vars.textSizeScript;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SetHistory {

    final Activity hActivity;

    public SetHistory(Activity hActivity) {
        this.hActivity = hActivity;
    }

    public static void set() {

        TextView tv;

        tv = setActivity.findViewById(R.id.build_time);
        tv.setTextColor(menuColorFore);
        tv.setBackgroundColor(menuColorBack);
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yy/MM/dd", Locale.ENGLISH);
        String build_time = "제작 \n"+dateTimeFormat.format(BuildConfig.BUILD_TIME)+", 하원철 (👀)";
        tv.setText(build_time);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setEnabled(true);
        tv.setOnClickListener(v -> {
            showHistory();
            tv.setEnabled(false);
        });
    }

    static void showHistory() {

        LinearLayout tableLayout = setActivity.findViewById(R.id.table);
        TextView tvDate, tvDesc;
        LinearLayout oneLine;
        int textSize = textSizeScript * 3 / 4;

        LayoutInflater faceInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // my Face
        LinearLayout myFaceLayout = (LinearLayout) faceInflater.inflate(
                R.layout.my_face_layout, null);
        ImageView my_face = myFaceLayout.findViewById(R.id.my_face);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(my_face);
        Glide.with(hActivity).load(R.mipmap.my_face_by_ezgif_maker).into(gifImage);
        LinearLayout.LayoutParams myFaceParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        myFaceParams.setMargins(20,20,20,20);
        tableLayout.addView(myFaceLayout, myFaceParams);

        // history lines
        LayoutInflater tLInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ArrayList<String> histories = fileRead.readRawTextFile(mContext, R.raw.history);

        for (String history : histories) {
            String[] cells = history.split(";");
            int typeFace = (cells[1].equals("b")) ? Typeface.BOLD: Typeface.NORMAL;
            oneLine = (LinearLayout) tLInflater.inflate(
                    R.layout.history_table, null);

            tvDate = oneLine.findViewById(R.id.date);
            tvDate.setTextColor(menuColorFore);
            tvDate.setTypeface(null, typeFace);
            tvDate.setText(cells[0]);
            tvDate.setTextSize(textSize);

            tvDesc = oneLine.findViewById(R.id.updates);
            tvDesc.setTextColor(menuColorFore);
            tvDesc.setTypeface(null, typeFace);
            String desc = cells[2].replace("||", "\n");
            tvDesc.setText(desc);
            tvDesc.setTextSize(textSize);

            tableLayout.addView(oneLine);
        }
        ScrollView scrollView = setActivity.findViewById(R.id.setScrollView);
        scrollView.post(() ->
                scrollView.scrollTo(0, scrollView.getHeight() - 1));

    }
}