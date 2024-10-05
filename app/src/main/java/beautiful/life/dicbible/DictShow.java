package beautiful.life.dicbible;

import static beautiful.life.dicbible.Vars.SHOW_DICT;
import static beautiful.life.dicbible.Vars.TAB_NEW;
import static beautiful.life.dicbible.Vars.TAB_OLD;
import static beautiful.life.dicbible.Vars.bcvs;
import static beautiful.life.dicbible.Vars.tabBible;
import static beautiful.life.dicbible.Vars.dicColorFore;
import static beautiful.life.dicbible.Vars.fBody;
import static beautiful.life.dicbible.Vars.fileRead;
import static beautiful.life.dicbible.Vars.goBackProcs;
import static beautiful.life.dicbible.Vars.dictTable;
import static beautiful.life.dicbible.Vars.linearLayout;
import static beautiful.life.dicbible.Vars.mContext;
import static beautiful.life.dicbible.Vars.nowBible;
import static beautiful.life.dicbible.Vars.nowChapter;
import static beautiful.life.dicbible.Vars.nowDic;
import static beautiful.life.dicbible.Vars.nowVerse;
import static beautiful.life.dicbible.Vars.packageFolder;
import static beautiful.life.dicbible.Vars.screenMenu;
import static beautiful.life.dicbible.Vars.scrollView;
import static beautiful.life.dicbible.Vars.shortBibleNames;
import static beautiful.life.dicbible.Vars.textColorFore;
import static beautiful.life.dicbible.Vars.DictShowSize;
import static beautiful.life.dicbible.Vars.BibleSize;
import static beautiful.life.dicbible.Vars.topTab;
import static beautiful.life.dicbible.Vars.xPixels;

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
import java.util.ArrayList;
import java.util.List;

public class DictShow {
    final String markChar = "†";    // Dagger char

    public void show() {

        topTab = SHOW_DICT;
        screenMenu.build();
        new FrameScrollView();
        goBackProcs.push();

//        String txt = "dict/" + nowDic + ".txt";
        String [] dicTexts = fileRead.readDicFile(nowDic, true);
        if (dicTexts == null) {
            String errText = "[" + nowDic + "] not found";
            Toast.makeText(mContext,errText,Toast.LENGTH_LONG).show();
            return;
        }
        if (nowDic.startsWith("_인")) {   //
            String [] refs = dicTexts[0].split(";");
            addHeader(nowDic, true);
            StringBuilder sb = new StringBuilder();
            int[] sFrm = new int[refs.length];
            int[] sTo = new int[refs.length];
            List<Vars.bcv> bcvs1 = new ArrayList<>();
            bcvs1.add (new Vars.bcv(0,0,0));
            int ptr = 0;
            for (int i = 1; i < refs.length; i++) {
                String [] ref = refs[i].split(",");
                sb.append(" ").append(ref[0]).append(" ");
                sFrm[i] = ptr;
                ptr += ref[0].length()+2;
                sTo[i] = ptr;
                bcvs1.add(new Vars.bcv(Integer.parseInt(ref[1]),Integer.parseInt(ref[2]),
                        Integer.parseInt(ref[3])));
            }
            SpannableString ss = new SpannableString(sb);
            for (int i = 1; i < bcvs1.size() ; i++) {
                ss.setSpan(new dictSpan(bcvs1.get(i)), sFrm[i], sTo[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(dicColorFore), sFrm[i], sTo[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            TextView tVLine = new TextView(mContext);
            tVLine.setLineSpacing(1.3f, 1.3f);
            tVLine.setText(ss);
            tVLine.setMovementMethod(LinkMovementMethod.getInstance());
            linearLayout.addView(tVLine);

        } else {
            for (String line : dicTexts) {
                if (line.length() == 0) {
                    addHeader("", false);
                    continue;
                }
                switch (line.substring(0, 1)) {
                    case "@":       // contains image file name
                        addImage(line);
                        break;
                    case "~":
                        addHeader(line.substring(1), true);
                        break;
                    default:
                        TextView tVLine = new TextView(mContext);
                        tVLine.setTextSize((float) BibleSize * 4 / 5);
                        tVLine.setTextColor(textColorFore);
                        tVLine.setGravity(Gravity.START);
//                        tVLine.setWidth(xPixels);
                        tVLine.setLineSpacing(0.5f, 1.2f);
                        linearLayout.addView(tVLine);
                        tVLine.setText(line);
                        break;
                }
            }
            bcvs = dictTable.where(nowDic);
            if (bcvs != null) {
                addReferHeader();
                StringBuilder sb = new StringBuilder();
                int[] sFrm = new int[bcvs.size()];
                int[] sTo = new int[bcvs.size()];
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
                    ss.setSpan(new dictSpan(bcvs.get(i)), sFrm[i], sTo[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new ForegroundColorSpan(dicColorFore), sFrm[i], sTo[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                TextView tVLine = new TextView(mContext);
                tVLine.setTextSize((float) BibleSize * 4 / 5);
                tVLine.setTextColor(textColorFore);
                tVLine.setText(ss);
                tVLine.setLineSpacing(0.2f, 1.2f);
                tVLine.setMovementMethod(LinkMovementMethod.getInstance());
                linearLayout.addView(tVLine);
            }
        }
        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);

    }

    private void addHeader(String text, boolean bigger) {
        TextView tVLine = new TextView(mContext);
        tVLine.setTextSize((bigger) ? (DictShowSize *5f/8): DictShowSize *2f/8);
        tVLine.setTextColor(dicColorFore);
        tVLine.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tVLine.setGravity(Gravity.CENTER_HORIZONTAL);
        tVLine.setWidth(xPixels);
        tVLine.setLineSpacing(0.5f, 0.5f);
        linearLayout.addView(tVLine);
        tVLine.setText(text);
    }

    private void addImage(String line) {
        File imgFile = new File(packageFolder, "dict_img/" + line.substring(1));
        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            int height = xPixels * bitmap.getHeight() / bitmap.getWidth();
            PhotoView imV = new PhotoView(mContext);
            imV.setImageBitmap(Bitmap.createScaledBitmap(bitmap, xPixels, height, false));
            imV.requestLayout();
            linearLayout.addView(imV);
        }
    }

    void addReferHeader() {
        String text = "\n[이 단어가 나오는 구절들]\n";
        TextView tVLine = new TextView(mContext);
        tVLine.setTextSize((float) BibleSize *4/5);
        tVLine.setTextColor(textColorFore);
        tVLine.setGravity(Gravity.START);
        tVLine.setWidth(xPixels);
        tVLine.setLineSpacing(0.2f, 0.3f);
        linearLayout.addView(tVLine);
        tVLine.setText(text);
    }

    static class dictSpan extends ClickableSpan {

        int b, c, v;
        dictSpan(Vars.bcv bcv) { this.b = bcv.b; this.c = bcv.c; this.v = bcv.v;}
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
            topTab =  (nowBible < 40) ? TAB_OLD : TAB_NEW;
            tabBible.showBibleBody();
        }
    }

 }