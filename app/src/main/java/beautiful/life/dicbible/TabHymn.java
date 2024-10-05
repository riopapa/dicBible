package beautiful.life.dicbible;


import static beautiful.life.dicbible.Vars.LYRIC_ONLY;
import static beautiful.life.dicbible.Vars.LYRIC_THEN_SHEET;
import static beautiful.life.dicbible.Vars.SHEET_ONLY;
import static beautiful.life.dicbible.Vars.SHEET_THEN_LYRIC;
import static beautiful.life.dicbible.Vars.darkMode;
import static beautiful.life.dicbible.Vars.fBody;
import static beautiful.life.dicbible.Vars.fileRead;
import static beautiful.life.dicbible.Vars.goBackProcs;
import static beautiful.life.dicbible.Vars.hymnAccompany;
import static beautiful.life.dicbible.Vars.hymnColorImage;
import static beautiful.life.dicbible.Vars.hymnShowWhat;
import static beautiful.life.dicbible.Vars.hymnSpeed;
import static beautiful.life.dicbible.Vars.hymnTitles;
import static beautiful.life.dicbible.Vars.linearLayout;
import static beautiful.life.dicbible.Vars.mActivity;
import static beautiful.life.dicbible.Vars.mContext;
import static beautiful.life.dicbible.Vars.menuColorBack;
import static beautiful.life.dicbible.Vars.menuColorFore;
import static beautiful.life.dicbible.Vars.nowHymn;
import static beautiful.life.dicbible.Vars.packageFolder;
import static beautiful.life.dicbible.Vars.paraColorFore;
import static beautiful.life.dicbible.Vars.screenMenu;
import static beautiful.life.dicbible.Vars.scrollView;
import static beautiful.life.dicbible.Vars.sortedNumbers;
import static beautiful.life.dicbible.Vars.speaking;
import static beautiful.life.dicbible.Vars.textColorFore;
import static beautiful.life.dicbible.Vars.HymnSize;
import static beautiful.life.dicbible.Vars.textSizeHymnKeypad;
import static beautiful.life.dicbible.Vars.textView;
import static beautiful.life.dicbible.Vars.xPixels;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.github.chrisbanes.photoview.PhotoView;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

class TabHymn {

    private String hymnTitle = "";
    final static int BTN_CLEAR = 100, BTN_GO = 200;
    private final int [] ids = {7,8,9,4,5,6,1,2,3,0,BTN_CLEAR,-1,BTN_GO,-1,-1};

    private final String newLine = "\n";

    void showNumberKey() {

        screenMenu.build();
        new FrameScrollView();

        Button b;
        int drawable = (darkMode)? R.drawable.button_bible_dark: R.drawable.button_number;
        hymnTitle = "";
        textView.setText(hymnTitle);
        textView.setTextSize(textSizeHymnKeypad);
        textView.setTextColor(textColorFore);
        textView.setGravity(Gravity.CENTER);
        linearLayout.addView(textView);

        int buttonHeight = 10 * textSizeHymnKeypad;

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
                        buttonWidth = 50;
                }
                LinearLayout columnLayout = new LinearLayout(mContext);
                columnLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                b = new Button(mContext);
                b.setBackgroundResource(drawable);
                b.setTextSize(textSizeHymnKeypad);
                b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                b.setWidth(buttonWidth);
                b.setHeight(buttonHeight);
                b.setText(buttonText);
                b.setTextColor(textColorFore);
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

        for(int row = 0; row < 10; row++) {   // 4
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(rowLayout);
            for(int col = 0; col < 2; col++) {
                int nbr = (row+row+col)*32;
                String text = hymnTitles[sortedNumbers[nbr]];
//                if (text.length())
//                String text = hymnTitles[sortedNumbers[(row+row+col)*32]].substring(0,8)+" ~";     // 32 = 645 / 10 / 2
                LinearLayout columnLayout = new LinearLayout(mContext);
                columnLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                b = new Button(mContext);
                b.setBackgroundResource(drawable);
                b.setTextSize((float) HymnSize *9/10);
                b.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
                b.setWidth(xPixels/2 - 16);
                b.setText(text);
                b.setTextColor(textColorFore);
                b.setSingleLine(true);
                b.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                b.setSelected(true);
                columnLayout.addView(b);
                b.setId(nbr);
                b.setOnClickListener(v -> {
                    showSortedHymnList(v.getId());
                });
                rowLayout.addView(columnLayout);
            }
        }

        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);
        nowHymn = 0;
    }

    void showHymnBody() {

        screenMenu.build();
        new FrameScrollView();
        goBackProcs.push();

        String txt = "Hymn/" + nowHymn + ".txt";
        String [] hymnTexts = fileRead.readBibleFile(txt, true);
        if (hymnTexts == null)
            return;

        TextView tVBody = new TextView(mContext);
        txt = nowHymn+" : "+hymnTitles[nowHymn];
        tVBody.setText(txt);
        tVBody.setTextSize(HymnSize + (float) HymnSize /8);
        tVBody.setPadding(0,20,0,20);
        tVBody.setGravity(Gravity.CENTER_HORIZONTAL);
        tVBody.setWidth(xPixels);
        tVBody.setTextColor(menuColorFore);
        tVBody.setBackgroundColor(menuColorBack);
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
        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);
    }

    private void showHymnText(String[] hymnTexts, LinearLayout linearlayout) {
        textView = new TextView(mContext);
        textView.setTextSize(HymnSize);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setWidth(xPixels);
        textView.setTextColor(textColorFore);

        StringBuilder bodyText = new StringBuilder();
        for (String hymnText : hymnTexts) {
            String workLine = hymnText+"\n";
            bodyText.append(workLine);
        }
        if (hymnShowWhat == SHEET_THEN_LYRIC || hymnShowWhat == LYRIC_ONLY) {
            bodyText.append(newLine).append(newLine);
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
            PhotoView imV = new PhotoView(mContext);
            imV.setBackgroundColor(hymnColorImage);
            imV.setImageBitmap(Bitmap.createScaledBitmap(bitmap, xPixels, height, false));
            imV.requestLayout();
            linearlayout.addView(imV);
        }
    }

    private void showSortedHymnList(int start) {

        screenMenu.build();
        new FrameScrollView();

        nowHymn = -1 - start;

        textView.setText(newLine);
        String text;

        linearLayout.addView(textView);

        for(int row = 0; row < 200;row++) {  // 40 list for one block
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(rowLayout);
            LinearLayout columnLayout = new LinearLayout(mContext);
            columnLayout.setOrientation(LinearLayout.HORIZONTAL);
            textView = new TextView(mContext);
            textView.setText(hymnTitles[sortedNumbers[start]]);
            textView.setId(sortedNumbers[start]);
            textView.setTextColor(textColorFore);
            textView.setTextSize(HymnSize);
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
            textView.setTextSize(HymnSize);
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
    }


    void confirmSpeak() {
        View dialogView = mActivity.getLayoutInflater().inflate(R.layout.speak_or_not, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        TextView textView = dialogView.findViewById(R.id.promptMessage);
        String s = hymnTitles[nowHymn] + "\n" + ((hymnAccompany)? "반주":"찬양"
                + " 속도 : "+hymnSpeed+"%");
        textView.setText(s);
        Button ok_btn = dialogView.findViewById(R.id.ok_btn);
        ok_btn.setText("시작");
        ok_btn.setOnClickListener(v -> {
            alertDialog.dismiss();
            speaking.say();
        });
    }

    void goHymnLeft() {
        nowHymn--;
        if (nowHymn > 0) {
            showHymnBody();
        }
    }

    void goHymnRight() {
        nowHymn++;
        if (nowHymn < 645) {
            showHymnBody();
        }
    }

}