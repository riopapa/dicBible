package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.TAB_MODE_KEY;
import static com.urrecliner.dicbible.Vars.TAB_MODE_NEW;
import static com.urrecliner.dicbible.Vars.TAB_MODE_OLD;
import static com.urrecliner.dicbible.Vars.bcvs;
import static com.urrecliner.dicbible.Vars.dicColorFore;
import static com.urrecliner.dicbible.Vars.fBody;
import static com.urrecliner.dicbible.Vars.fileRead;
import static com.urrecliner.dicbible.Vars.history;
import static com.urrecliner.dicbible.Vars.keyTable;
import static com.urrecliner.dicbible.Vars.linearLayout;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.makeBible;
import static com.urrecliner.dicbible.Vars.nowBible;
import static com.urrecliner.dicbible.Vars.nowChapter;
import static com.urrecliner.dicbible.Vars.nowDic;
import static com.urrecliner.dicbible.Vars.nowVerse;
import static com.urrecliner.dicbible.Vars.packageFolder;
import static com.urrecliner.dicbible.Vars.screenMenu;
import static com.urrecliner.dicbible.Vars.scrollView;
import static com.urrecliner.dicbible.Vars.shortBibleNames;
import static com.urrecliner.dicbible.Vars.textColorFore;
import static com.urrecliner.dicbible.Vars.textSizeDic;
import static com.urrecliner.dicbible.Vars.textSizeScript;
import static com.urrecliner.dicbible.Vars.topTab;
import static com.urrecliner.dicbible.Vars.xPixels;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

public class DictKey {
    final String markChar = "†";    // Dagger char

    public void show() {

        topTab = TAB_MODE_KEY;
        screenMenu.build();
        new FrameScrollView();
        history.push();

//        String txt = "dict/" + nowDic + ".txt";
        String [] dicTexts = fileRead.readDicFile(nowDic, true);
        if (dicTexts != null) {
            for (String line : dicTexts) {
                if (line.length() == 0) {
                    TextView tVLine = new TextView(mContext);
                    tVLine.setTextSize(textSizeDic);
                    tVLine.setTextColor(dicColorFore);
                    tVLine.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                    tVLine.setGravity(Gravity.CENTER_HORIZONTAL);
                    tVLine.setWidth(xPixels);
                    tVLine.setLineSpacing(1.5f, 1.5f);
                    linearLayout.addView(tVLine);
                    tVLine.setText("");
                    continue;
                }
                switch (line.substring(0, 1)) {
                    case "@":       // contains image file name
                        File imgFile = new File(packageFolder, "dict_img/" + line.substring(1));
                        if (imgFile.exists()) {
                            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            int height = xPixels * bitmap.getHeight() / bitmap.getWidth();
                            PhotoView imV = new PhotoView(mContext);
                            imV.setImageBitmap(Bitmap.createScaledBitmap(bitmap, xPixels, height, false));
                            imV.requestLayout();
                            linearLayout.addView(imV);
                        }
                        break;
                    case "~": { // contains subject name
                        TextView tVLine = new TextView(mContext);
                        tVLine.setTextSize(textSizeDic);
                        tVLine.setTextColor(dicColorFore);
                        tVLine.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                        tVLine.setGravity(Gravity.CENTER_HORIZONTAL);
                        tVLine.setWidth(xPixels);
                        tVLine.setLineSpacing(1.5f, 1.5f);
                        linearLayout.addView(tVLine);
                        tVLine.setText(line.substring(1));
                        break;
                    }
                    default: {
                        TextView tVLine = new TextView(mContext);
                        tVLine.setTextSize((float) textSizeScript*4/5);
                        tVLine.setTextColor(textColorFore);
                        tVLine.setGravity(Gravity.START);
                        tVLine.setWidth(xPixels);
                        tVLine.setLineSpacing(1.2f, 1.2f);
                        linearLayout.addView(tVLine);
                        tVLine.setText(line);
                        break;
                    }
                }
            }
            bcvs = keyTable.where(nowDic);
            if (bcvs != null) {
                addHeader();
                StringBuilder sb = new StringBuilder();
                int [] sFrm = new int[bcvs.size()];
                int [] sTo = new int[bcvs.size()];
                int sIdx = 0;
                int ptr = 0;
                for (int i = 0; i < bcvs.size(); i++) {
                    Vars.bcv ref = bcvs.get(i);
                    String str = " " + markChar + shortBibleNames[ref.b] + ref.c + ":" + ref.v + markChar + " ";
                    sb.append(str);
                    int strLen = str.length();
                    sFrm[sIdx] = ptr;
                    sTo[sIdx] = ptr + strLen;
                    sIdx++;
                    ptr += strLen;
                }
                SpannableString ss = new SpannableString(sb);
                for (int i = 0; i < bcvs.size(); i++) {
                    ss.setSpan(new referSpan(bcvs.get(i)), sFrm[i], sTo[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new ForegroundColorSpan(dicColorFore), sFrm[i], sTo[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                TextView tVLine = new TextView(mContext);
                tVLine.setTextSize((float) textSizeScript*4/5);
                tVLine.setTextColor(textColorFore);
                tVLine.setText(ss);
                tVLine.setLineSpacing(1.2f, 1.2f);
                tVLine.setMovementMethod(LinkMovementMethod.getInstance());
//                columnLayout.addView(tVLine);
                linearLayout.addView(tVLine);
            }

        } else {
            String errText = "[" + nowDic + "] not found";
            Toast.makeText(mContext,errText,Toast.LENGTH_LONG).show();
//            utils.log(logFile, errText);
        }

        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);

    }

    void addHeader() {
        String text = "\n[이 단어가 등장는 구절들]\n";
        TextView tVLine = new TextView(mContext);
        tVLine.setTextSize((float) textSizeScript*4/5);
        tVLine.setTextColor(textColorFore);
        tVLine.setGravity(Gravity.START);
        tVLine.setWidth(xPixels);
        tVLine.setLineSpacing(1.2f, 1.2f);
        linearLayout.addView(tVLine);
        tVLine.setText(text);
    }

    static class referSpan extends ClickableSpan {

        int b, c, v;
        referSpan(Vars.bcv bcv) { this.b = bcv.b; this.c = bcv.c; this.v = bcv.v;}
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);    // this remove the underline
        }

        @Override
        public void onClick(@NonNull View widget) {
            Log.w("dict","Clicked");
            nowBible = b;
            nowChapter = c;
            nowVerse = v;
            topTab =  (nowBible < 40) ? TAB_MODE_OLD : TAB_MODE_NEW;
            makeBible.showBibleBody();
        }
    }

 }