package com.urrecliner.dicbible;


import static com.urrecliner.dicbible.Vars.LYRIC_ONLY;
import static com.urrecliner.dicbible.Vars.LYRIC_THEN_SHEET;
import static com.urrecliner.dicbible.Vars.SHEET_ONLY;
import static com.urrecliner.dicbible.Vars.SHEET_THEN_LYRIC;
import static com.urrecliner.dicbible.Vars.bibleColorFore;
import static com.urrecliner.dicbible.Vars.blank;
import static com.urrecliner.dicbible.Vars.darkMode;
import static com.urrecliner.dicbible.Vars.highLiteMenuColor;
import static com.urrecliner.dicbible.Vars.history;
import static com.urrecliner.dicbible.Vars.hymnColorFore;
import static com.urrecliner.dicbible.Vars.hymnColorImage;
import static com.urrecliner.dicbible.Vars.hymnColorTitle;
import static com.urrecliner.dicbible.Vars.hymnName;
import static com.urrecliner.dicbible.Vars.hymnShowWhat;
import static com.urrecliner.dicbible.Vars.hymnTitles;
import static com.urrecliner.dicbible.Vars.mActivity;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.normalMenuColor;
import static com.urrecliner.dicbible.Vars.nowHymn;
import static com.urrecliner.dicbible.Vars.packageFolder;
import static com.urrecliner.dicbible.Vars.paraColorFore;
import static com.urrecliner.dicbible.Vars.fBody;
import static com.urrecliner.dicbible.Vars.sortedNumbers;
import static com.urrecliner.dicbible.Vars.textColorBack;
import static com.urrecliner.dicbible.Vars.textSizeBibleTitle;
import static com.urrecliner.dicbible.Vars.textSizeHymnBody;
import static com.urrecliner.dicbible.Vars.textSizeHymnKeypad;
import static com.urrecliner.dicbible.Vars.textSizeHymnTitle;
import static com.urrecliner.dicbible.Vars.vAgpBible;
import static com.urrecliner.dicbible.Vars.vCenterAction;
import static com.urrecliner.dicbible.Vars.vCevBible;
import static com.urrecliner.dicbible.Vars.vHymn;
import static com.urrecliner.dicbible.Vars.vLeftAction;
import static com.urrecliner.dicbible.Vars.vNewBible;
import static com.urrecliner.dicbible.Vars.vOldBible;
import static com.urrecliner.dicbible.Vars.vRightAction;
import static com.urrecliner.dicbible.Vars.xPixels;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;

//import uk.co.senab.photoview.PhotoViewAttacher;

class MakeHymn {

    private final String new2Line = "\n\n";

    private TextView tVTitle;
    private String hymnTitle = "";
    final static int BTN_CLEAR = 100, BTN_GO = 200;
    private final int [] ids = {7,8,9,4,5,6,1,2,3,0,BTN_CLEAR,-1,BTN_GO,-1,-1};

    private final String newLine = "\n";
    private ScrollView scrollView;
    private TextView textView;

    void showNumberKey() {

        buildMenu();
        initScrollView();

        Button b;

        LinearLayout linearlayout = new LinearLayout(mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);

        textView.setTextSize(textSizeHymnTitle);
        textView.setTextColor(hymnColorFore);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        linearlayout.addView(textView);

        tVTitle = new TextView(mContext);
        if (nowHymn > 0)
            hymnTitle = nowHymn + " : " + hymnTitles[nowHymn];
        else
            hymnTitle = "";
        tVTitle.setText(hymnTitle);
        tVTitle.setTextSize(textSizeHymnKeypad);
        tVTitle.setTextColor(hymnColorTitle);
        tVTitle.setGravity(Gravity.CENTER);
        tVTitle.setWidth(2000);
        linearlayout.addView(tVTitle);

        for(int row = 0; row<5;row++) {
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearlayout.addView(rowLayout);
            for(int col = 0; col < 3; col++) {
                int id = ids[row*3+col];
                if (id == -1)
                    break;
                String buttonText;
                int buttonHeight = 140;
                int buttonWidth;
                switch (id) {
                    case BTN_CLEAR:
                        buttonWidth = 320;
                        buttonText = "Clear";
                        break;
                    case BTN_GO:
                        buttonWidth = 300;
                        buttonText = "Go";
                        break;
                    default:
                        buttonText = "" + id;
                        buttonWidth = 80;
                }

                LinearLayout columnLayout = new LinearLayout(mContext);
                columnLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                b = new Button(mContext);
                b.setBackgroundResource((darkMode)? R.drawable.button_bible_dark: R.drawable.button_number);
                b.setTextSize(textSizeHymnKeypad);
                b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                b.setWidth(buttonWidth);
                b.setHeight(buttonHeight);
                b.setText(buttonText);
                b.setTextColor(bibleColorFore);
                columnLayout.addView(b);
                b.setId(id);
                if (id < 10) {
                    b.setOnClickListener(v -> {
                    int nbr = v.getId();
                    if (nowHymn *10 + nbr <= 645) {
                        nowHymn = nowHymn * 10 + nbr;
                        hymnTitle = nowHymn + " " + hymnTitles[nowHymn];
                        tVTitle.setText(hymnTitle);
                    }
                    });
                }
                else // code is clear
                    if (id==100) b.setOnClickListener(v -> {
                        nowHymn = 0;
                        hymnTitle = "";
                        tVTitle.setText(hymnTitle);
                    });
                else {    // code is go
                    b.setOnClickListener(v -> showHymnBody());
                }
                rowLayout.addView(columnLayout);
            }
        }
        TextView tVSort = new TextView(mContext);
        tVSort.setTextSize(textSizeHymnKeypad);
        tVSort.setTextColor(hymnColorFore);
        tVSort.setGravity(Gravity.CENTER);
        tVSort.setWidth(2000);
        linearlayout.addView(tVSort);

        for(int row = 0; row<8;row++) {   // 4
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearlayout.addView(rowLayout);
            for(int col = 0; col < 2; col++) {
                String text = hymnTitles[sortedNumbers[(row+row+col)*41]].substring(0,8)+" ~";     // 81
                LinearLayout columnLayout = new LinearLayout(mContext);
                columnLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                b = new Button(mContext);
                b.setBackgroundResource((darkMode)? R.drawable.button_bible_dark: R.drawable.button_number);
                b.setTextSize(textSizeHymnBody*9/10);
                b.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
                b.setWidth(xPixels/2 - 16);
                b.setText(text);
                b.setTextColor((darkMode)? mActivity.getColor(R.color.screenBodyColor) : mActivity.getColor(R.color.bibleColorFore));
                columnLayout.addView(b);
                b.setId((row+row+col)*41);    // 81
                b.setOnClickListener(v -> {
                int nbr = v.getId();
                showSortedHymnList(nbr);
                });
                rowLayout.addView(columnLayout);
            }
        }

//        TextView tTail = new TextView(mContext);
//        tTail.setTextSize(textSizeHymnKeypad);
//        tTail.setWidth(2000);
//        tTail.setText(new2Line);
//        linearlayout.addView(tTail);
        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);
        nowHymn = 0;
    }

    void showHymnBody() {

        buildMenu();
        initScrollView();

        String txt = "Hymn/" + nowHymn + ".txt";
        String [] hymnTexts = FileRead.readBibleFile(txt);
        if (hymnTexts == null) {
//            Toast.makeText(mContext, "찬송가 " + nowHymn + " 가사 파일 없음",Toast.LENGTH_LONG).show();
            return;
        }
        LinearLayout linearlayout = new LinearLayout(mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);

        TextView tVBody = new TextView(mContext);
        txt = nowHymn+" : "+hymnTitles[nowHymn];
        tVBody.setText(txt);
        tVBody.setTextSize(textSizeHymnBody+textSizeHymnBody/5);
        tVBody.setPadding(0,20,0,20);
        tVBody.setGravity(Gravity.CENTER_HORIZONTAL);
        tVBody.setWidth(xPixels);
        tVBody.setTextColor(hymnColorFore);
        tVBody.setBackgroundColor(normalMenuColor | 0x777777);
        linearlayout.addView(tVBody);

        switch (hymnShowWhat) {
            case SHEET_THEN_LYRIC:
                showHymnImage(linearlayout);
                showHymnText(hymnTexts, linearlayout);
                break;
            case LYRIC_THEN_SHEET:
                showHymnText(hymnTexts, linearlayout);
                showHymnImage(linearlayout);
                break;
            case SHEET_ONLY:
                showHymnImage(linearlayout);
                break;
            case LYRIC_ONLY:
                showHymnText(hymnTexts, linearlayout);
                break;
        }
        history.push();
        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);
    }

    private void showHymnText(String[] hymnTexts, LinearLayout linearlayout) {
        textView = new TextView(mContext);
        textView.setTextSize(textSizeHymnBody);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setWidth(xPixels);
        textView.setTextColor(hymnColorFore);

        StringBuilder bodyText = new StringBuilder();
        for (String hymnText : hymnTexts) {
            String workLine = "\n"+hymnText;
            bodyText.append(workLine);
        }
        bodyText.append("\n");
//        if (hymnShowWhat == SHEET_THEN_LYRIC || hymnShowWhat == LYRIC_ONLY)
//                    bodyText.append("\n\n\n");
        SpannableString sfBody = new SpannableString(bodyText);
        textView.setText(sfBody);
        linearlayout.addView(textView);
//        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void showHymnImage(LinearLayout linearlayout) {
        File imgFile = new File(packageFolder, "hymn_png/" + nowHymn + ".pngz");
        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            int height = xPixels * bitmap.getHeight() / bitmap.getWidth();
            ImageView imV = new ImageView(mContext);
            imV.setBackgroundColor(hymnColorImage);
            linearlayout.addView(imV);
            imV.setImageBitmap(Bitmap.createScaledBitmap(bitmap, xPixels, height, false));
            imV.requestLayout();
//            PhotoViewAttacher pA;
//            pA = new PhotoViewAttacher(imV);
//            pA.update();
        }
        TextView tVBody = new TextView(mContext);
        tVBody.setTextSize(textSizeHymnBody);
        tVBody.setGravity(Gravity.CENTER_HORIZONTAL);
        tVBody.setWidth(xPixels);
        tVBody.setTextColor(hymnColorFore);
        linearlayout.addView(tVBody);

        StringBuilder bodyText = new StringBuilder();
        if (hymnShowWhat == SHEET_ONLY || hymnShowWhat == LYRIC_THEN_SHEET)
            bodyText.append(new2Line);
        SpannableString sfBody = new SpannableString(bodyText);
        tVBody.setText(sfBody);
        tVBody.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void showSortedHymnList(int start) {

        buildMenu();
        fBody.removeAllViews();
        fBody.setBackgroundColor(Vars.textColorBack);

        nowHymn = -1 - start;

        TextView titleTV; TextView numberTV;
        String text;
        LinearLayout linearlayout = new LinearLayout(mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);
        TextView tV = new TextView(mContext);
        tV.setText("");
        tV.setTextSize(textSizeHymnBody);
        tV.setWidth(xPixels);
        linearlayout.addView(tV);

        for(int row = 0; row<41;row++) {  // 81
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearlayout.addView(rowLayout);

            LinearLayout columnLayout = new LinearLayout(mContext);
            columnLayout.setOrientation(LinearLayout.HORIZONTAL);
            titleTV = new TextView(mContext);
            titleTV.setText(hymnTitles[sortedNumbers[start]]);
            titleTV.setTextColor(bibleColorFore);
            titleTV.setTextSize(textSizeHymnBody);
            columnLayout.addView(titleTV);
            numberTV = new TextView(mContext);
            text = "  " + sortedNumbers[start] + " ";
            numberTV.setText(text);
            numberTV.setId(sortedNumbers[start]);
            numberTV.setTextColor(paraColorFore);
            numberTV.setTextSize(textSizeHymnBody);
            numberTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            columnLayout.addView(numberTV);
            numberTV.setOnClickListener(v -> {
                nowHymn = v.getId();
                showHymnBody();
            });
            rowLayout.addView(columnLayout);
            start++;
            if (start > 644)
                break;
        }
        TextView tVb = new TextView(mContext);
        tVb.setText(new2Line);
        tVb.setTextSize(textSizeBibleTitle);
        tVb.setWidth(xPixels);
//        tVb.setGravity(Gravity.CENTER);
        linearlayout.addView(tVb);
        fBody.addView(linearlayout);
        history.push();
    }

    private void initScrollView() {
        scrollView = new ScrollView(mContext);
        scrollView.setBackgroundColor(textColorBack);
        textView = new TextView(mContext);
    }

    private void addBlankLine(LinearLayout linearlayout) {
        textView.setText(newLine);
        textView.setTextSize(10);
        textView.setWidth(xPixels);
        textView.setTextColor(0);
        textView.setGravity(Gravity.CENTER);
        linearlayout.addView(textView);
    }

    void buildMenu() {

        String s;
        vOldBible.setBackgroundColor(normalMenuColor);
        vNewBible.setBackgroundColor(normalMenuColor);
        vHymn.setBackgroundColor(highLiteMenuColor);
        vAgpBible.setVisibility(View.GONE);
        vCevBible.setBackgroundColor(normalMenuColor);

        if (nowHymn == 0) {     // show Hymn List
            vLeftAction.setText(blank);
            vRightAction.setText(blank);
            vCenterAction.setText(hymnName);
        } else if (nowHymn < 0) {   // show Hymn
            vLeftAction.setText(blank);
            vRightAction.setText(blank);
            vCenterAction.setText(hymnTitles[sortedNumbers[-nowHymn - 1]].substring(0,8));
        }
        else {
            vLeftAction.setText((nowHymn > 1) ? "" + (nowHymn - 1):blank);
            vCenterAction.setText(hymnTitles[nowHymn]);
            vRightAction.setText((nowHymn < 645) ? "" + (nowHymn + 1):blank);
        }
    }

    void goHymnLeft() {
        nowHymn--;
        if (nowHymn > 0)
            showHymnBody();
    }

    void goHymnRight() {
        nowHymn++;
        if (nowHymn < 645)
            showHymnBody();
    }

}