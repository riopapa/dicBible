package com.urrecliner.dicbible;


import static com.urrecliner.dicbible.Vars.LYRIC_ONLY;
import static com.urrecliner.dicbible.Vars.LYRIC_THEN_SHEET;
import static com.urrecliner.dicbible.Vars.SHEET_ONLY;
import static com.urrecliner.dicbible.Vars.SHEET_THEN_LYRIC;
import static com.urrecliner.dicbible.Vars.bibleColorFore;
import static com.urrecliner.dicbible.Vars.blank;
import static com.urrecliner.dicbible.Vars.darkMode;
import static com.urrecliner.dicbible.Vars.fBody;
import static com.urrecliner.dicbible.Vars.highLiteMenuColor;
import static com.urrecliner.dicbible.Vars.history;
import static com.urrecliner.dicbible.Vars.hymnColorFore;
import static com.urrecliner.dicbible.Vars.hymnColorImage;
import static com.urrecliner.dicbible.Vars.hymnColorTitle;
import static com.urrecliner.dicbible.Vars.hymnName;
import static com.urrecliner.dicbible.Vars.hymnShowWhat;
import static com.urrecliner.dicbible.Vars.hymnTitles;
import static com.urrecliner.dicbible.Vars.isReadingNow;
import static com.urrecliner.dicbible.Vars.mActivity;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.normalMenuColor;
import static com.urrecliner.dicbible.Vars.nowHymn;
import static com.urrecliner.dicbible.Vars.packageFolder;
import static com.urrecliner.dicbible.Vars.paraColorFore;
import static com.urrecliner.dicbible.Vars.sortedNumbers;
import static com.urrecliner.dicbible.Vars.speaking;
import static com.urrecliner.dicbible.Vars.textColorBack;
import static com.urrecliner.dicbible.Vars.textSizeHymnBody;
import static com.urrecliner.dicbible.Vars.textSizeHymnKeypad;
import static com.urrecliner.dicbible.Vars.vAgpBible;
import static com.urrecliner.dicbible.Vars.vCenterAction;
import static com.urrecliner.dicbible.Vars.vCevBible;
import static com.urrecliner.dicbible.Vars.vHymn;
import static com.urrecliner.dicbible.Vars.vLeftAction;
import static com.urrecliner.dicbible.Vars.vNewBible;
import static com.urrecliner.dicbible.Vars.vOldBible;
import static com.urrecliner.dicbible.Vars.vRightAction;
import static com.urrecliner.dicbible.Vars.vSearch;
import static com.urrecliner.dicbible.Vars.xPixels;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.TextUtils;
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

    private String hymnTitle = "";
    final static int BTN_CLEAR = 100, BTN_GO = 200;
    private final int [] ids = {7,8,9,4,5,6,1,2,3,0,BTN_CLEAR,-1,BTN_GO,-1,-1};

    private final String newLine = "\n";
    private ScrollView scrollView;
    private TextView textView;
    private LinearLayout linearLayout;

    void showNumberKey() {

        buildMenu();
        initScrollView();

        Button b;

        if (nowHymn > 0)
            hymnTitle = nowHymn + " : " + hymnTitles[nowHymn];
        else
            hymnTitle = "";
        textView.setText(hymnTitle);
        textView.setTextSize(textSizeHymnKeypad);
        textView.setTextColor(hymnColorTitle);
        textView.setGravity(Gravity.CENTER);
        linearLayout.addView(textView);

        for(int row = 0; row<5;row++) {
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(rowLayout);
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
                b.setTextColor(hymnColorFore);
                columnLayout.addView(b);
                b.setId(id);
                if (id < 10) {
                    b.setOnClickListener(v -> {
                    int nbr = v.getId();
                    if (nowHymn *10 + nbr <= 645) {
                        nowHymn = nowHymn * 10 + nbr;
                        hymnTitle = nowHymn + " " + hymnTitles[nowHymn];
                        textView.setText(hymnTitle);
                    }
                    });
                }
                else // code is clear
                    if (id==100) b.setOnClickListener(v -> {
                        nowHymn = 0;
                        hymnTitle = "";
                        textView.setText(hymnTitle);
                    });
                else {    // code is go
                    b.setOnClickListener(v -> showHymnBody());
                }
                rowLayout.addView(columnLayout);
            }
        }

        for(int row = 0; row<8;row++) {   // 4
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(rowLayout);
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
                b.setTextColor((darkMode)? mActivity.getColor(R.color.screenBodyColor) : mActivity.getColor(R.color.hymnColorFore));
                columnLayout.addView(b);
                b.setId((row+row+col)*41);    // 81
                b.setOnClickListener(v -> {
                int nbr = v.getId();
                showSortedHymnList(nbr);
                });
                rowLayout.addView(columnLayout);
            }
        }

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

        TextView tVBody = new TextView(mContext);
        txt = nowHymn+" : "+hymnTitles[nowHymn];
        tVBody.setText(txt);
        tVBody.setTextSize(textSizeHymnBody+textSizeHymnBody/5);
        tVBody.setPadding(0,20,0,20);
        tVBody.setGravity(Gravity.CENTER_HORIZONTAL);
        tVBody.setWidth(xPixels);
        tVBody.setTextColor(hymnColorFore);
        tVBody.setBackgroundColor(normalMenuColor | 0x777777);
        linearLayout.addView(tVBody);

        switch (hymnShowWhat) {
            case SHEET_THEN_LYRIC:
                showHymnImage(linearLayout);
                showHymnText(hymnTexts, linearLayout);
                break;
            case LYRIC_THEN_SHEET:
                showHymnText(hymnTexts, linearLayout);
                showHymnImage(linearLayout);
                break;
            case SHEET_ONLY:
                showHymnImage(linearLayout);
                break;
            case LYRIC_ONLY:
                showHymnText(hymnTexts, linearLayout);
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
            String workLine = hymnText+"\n";
            bodyText.append(workLine);
        }

        SpannableString sfBody = new SpannableString(bodyText);
        textView.setText(sfBody);
        textView.setLineSpacing(1.2f, 1.2f);
        linearlayout.addView(textView);
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
    }

    private void showSortedHymnList(int start) {

        buildMenu();
        initScrollView();

        nowHymn = -1 - start;

        textView.setText(newLine);
        String text;

        linearLayout.addView(textView);

        for(int row = 0; row < 41;row++) {  // 81
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(rowLayout);

            LinearLayout columnLayout = new LinearLayout(mContext);
            columnLayout.setOrientation(LinearLayout.HORIZONTAL);
            textView = new TextView(mContext);
            textView.setText(hymnTitles[sortedNumbers[start]]);
            textView.setId(sortedNumbers[start]);
            textView.setTextColor(bibleColorFore);
            textView.setTextSize(textSizeHymnBody);
            textView.setLineSpacing(1.2f, 1.2f);
            columnLayout.addView(textView);
            textView.setOnClickListener(v -> {
                nowHymn = v.getId();
                showHymnBody();
            });
            textView = new TextView(mContext);
            text = "  " + sortedNumbers[start] + " ";
            textView.setText(text);
            textView.setId(sortedNumbers[start]);
            textView.setTextColor(paraColorFore);
            textView.setTextSize(textSizeHymnBody);
            textView.setLineSpacing(1.2f, 1.2f);
            textView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            columnLayout.addView(textView);
            textView.setOnClickListener(v -> {
                nowHymn = v.getId();
                showHymnBody();
            });
            rowLayout.addView(columnLayout);
            start++;
            if (start > 644)
                break;
        }
        LinearLayout rowLayout = new LinearLayout(mContext);
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(rowLayout);
        textView = new TextView(mContext);
        textView.setText(newLine);
        rowLayout.addView(textView);

        fBody.addView(scrollView);
        history.push();
    }

    private void initScrollView() {
        scrollView = new ScrollView(mContext);
        scrollView.setBackgroundColor(textColorBack);
        textView = new TextView(mContext);
        linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearLayout);
    }

    void buildMenu() {

        vSearch.setVisibility(View.GONE);
        vAgpBible.setBackgroundColor(normalMenuColor);
        vOldBible.setBackgroundColor(normalMenuColor);
        vNewBible.setBackgroundColor(normalMenuColor);
        vHymn.setBackgroundColor(highLiteMenuColor);

        vAgpBible.setText(blank);
        vAgpBible.setBackgroundColor(normalMenuColor);
        vCevBible.setText(blank);
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

        vCenterAction.setSingleLine(true);
        vCenterAction.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        vCenterAction.setSelected(true);
    }

    void confirmSpeak() {
        View dialogView = mActivity.getLayoutInflater().inflate(R.layout.speak_or_not, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        TextView textView = dialogView.findViewById(R.id.promptMessage);
        String s = "찬송가 반주를 그만 두려면\n["+hymnTitles[nowHymn]+"] 를 누르세요";
        textView.setText(s);
        Button ok_btn = dialogView.findViewById(R.id.ok_btn);
        ok_btn.setText((isReadingNow)? "반주 그만": "반주 시작");
        ok_btn.setOnClickListener(v -> {
            alertDialog.dismiss();
            speaking.say();
        });

        Button bookMark = dialogView.findViewById(R.id.cancle_btn);
        bookMark.setText((isReadingNow)? "계속 반주":"반주 안 함");
        bookMark.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
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