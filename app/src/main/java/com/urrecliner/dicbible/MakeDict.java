package com.urrecliner.dicbible;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.urrecliner.dicbible.Vars.TAB_MODE_KEY;
import static com.urrecliner.dicbible.Vars.TAB_MODE_NEW;
import static com.urrecliner.dicbible.Vars.TAB_MODE_OLD;
import static com.urrecliner.dicbible.Vars.bcvs;
import static com.urrecliner.dicbible.Vars.darkMode;
import static com.urrecliner.dicbible.Vars.dicColorFore;
import static com.urrecliner.dicbible.Vars.fBody;
import static com.urrecliner.dicbible.Vars.history;
import static com.urrecliner.dicbible.Vars.keyTable;
import static com.urrecliner.dicbible.Vars.keyText;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.makeBible;
import static com.urrecliner.dicbible.Vars.menuColorBack;
import static com.urrecliner.dicbible.Vars.menuColorFore;
import static com.urrecliner.dicbible.Vars.nowBible;
import static com.urrecliner.dicbible.Vars.nowChapter;
import static com.urrecliner.dicbible.Vars.nowDic;
import static com.urrecliner.dicbible.Vars.nowVerse;
import static com.urrecliner.dicbible.Vars.packageFolder;
import static com.urrecliner.dicbible.Vars.recentDicts;
import static com.urrecliner.dicbible.Vars.screenMenu;
import static com.urrecliner.dicbible.Vars.scrollView;
import static com.urrecliner.dicbible.Vars.sharedPref;
import static com.urrecliner.dicbible.Vars.shortBibleNames;
import static com.urrecliner.dicbible.Vars.textColorFore;
import static com.urrecliner.dicbible.Vars.textSizeBible66;
import static com.urrecliner.dicbible.Vars.textSizeDic;
import static com.urrecliner.dicbible.Vars.textSizeScript;
import static com.urrecliner.dicbible.Vars.topTab;
import static com.urrecliner.dicbible.Vars.xPixels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.github.chrisbanes.photoview.PhotoView;
import com.urrecliner.dicbible.model.RecentDict;

import java.io.File;

class MakeDict {

    private TextView textView;
    LinearLayout linearLayout;

    void showDictMenu() {

        screenMenu.build();
        initScrollView();

        linearLayout.addView(textView);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout_inp = (LinearLayout) inflater.inflate(R.layout.dict_key_in, null);
        TextView viewTxt = layout_inp.findViewById(R.id.enterDict);
        viewTxt.setBackgroundColor(menuColorBack);
        viewTxt.setTextColor(menuColorFore);
        EditText viewEd = layout_inp.findViewById(R.id.edDict);
        viewEd.setText(keyText);
        viewEd.setTextColor(menuColorFore);
        viewEd.setSelectAllOnFocus(true);
        viewEd.requestFocus();
        if (nowDic != null)
            viewEd.setText(nowDic);
        InputMethodManager imm = (InputMethodManager)
                mContext.getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(viewEd, InputMethodManager.SHOW_IMPLICIT);
        ImageView viewSearch = layout_inp.findViewById(R.id.searchDict);
        viewSearch.setBackgroundColor(menuColorBack);
        Drawable d;
        Drawable wrappedDrawable;
        d = VectorDrawableCompat.create(mContext.getResources(), R.drawable.ic_search, null);
        wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, menuColorFore);
        viewSearch.setImageDrawable(wrappedDrawable);
        linearLayout.addView(layout_inp);

        showRecent(linearLayout);
        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);

        viewSearch.setOnClickListener(v -> {
            nowDic = viewEd.getText().toString();
            showDicWord();
        });
    }

    private void showRecent(LinearLayout linearLayout) {

        Button b;
        recentDicts = RecentDict.read(sharedPref);
        if (recentDicts.size() > 0) {
            for (int i = 0; i < recentDicts.size(); i++) {
                LinearLayout columnLayout = new LinearLayout(mContext);
                columnLayout.setOrientation(LinearLayout.VERTICAL);
                b = new Button(mContext);
                b.setBackgroundResource((darkMode)? R.drawable.button_bible_dark:R.drawable.button_bible);
                b.setText(recentDicts.get(i).dict);
                b.setTag(recentDicts.get(i).dict);
                b.setTextSize(textSizeBible66);
                b.setTextColor(textColorFore);
                b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                columnLayout.addView(b);
                b.setOnClickListener(v -> {
                    nowDic = v.getTag().toString();
                    showDicWord();
                });
                linearLayout.addView(columnLayout);
            }
        }

    }


    void showDicWord() {

        topTab = TAB_MODE_KEY;
        screenMenu.build();
        initScrollView();
        history.push();

        String txt = "dict/" + nowDic + ".txt";
        String [] dicTexts = FileRead.readBibleFile(txt, true);
        if (dicTexts != null) {
            for (String line : dicTexts) {
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
                addText("[관련 성경]");
                LinearLayout columnLayout = new LinearLayout(mContext);
                columnLayout.setOrientation(LinearLayout.HORIZONTAL);
                for (int i = 0; i < bcvs.size(); i++) {
                    Vars.bcv ref = bcvs.get(i);
                    String str = shortBibleNames[ref.b]+ref.c+":"+ref.v+" ";
                    TextView tVLine = new TextView(mContext);
                    tVLine.setTextSize((float) textSizeScript*4/5);
                    tVLine.setTextColor(textColorFore);
//                    tVLine.setGravity(Gravity.START);
//                    tVLine.setWidth(xPixels);
                    tVLine.setTag(ref);
                    tVLine.setLineSpacing(1.2f, 1.2f);
                    tVLine.setText(str);
                    tVLine.setOnClickListener(v -> {
                        Vars.bcv jRef = (Vars.bcv) tVLine.getTag();
                        nowBible = jRef.b;
                        nowChapter = jRef.c;
                        nowVerse = jRef.v;
                        topTab = (nowBible < 40)? TAB_MODE_OLD:TAB_MODE_NEW;
                        makeBible.showBibleBody();
                    });
                    columnLayout.addView(tVLine);
                    if (i%4 == 3) {
                        linearLayout.addView(columnLayout);
                        columnLayout = new LinearLayout(mContext);
                        columnLayout.setOrientation(LinearLayout.HORIZONTAL);
                    }
                }
                linearLayout.addView(columnLayout);
            }

        } else {
            String errText = "[" + nowDic + "] not found";
            Toast.makeText(mContext,errText,Toast.LENGTH_LONG).show();
//            utils.log(logFile, errText);
        }

        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);

    }

    void addText(String text) {
        TextView tVLine = new TextView(mContext);
        tVLine.setTextSize((float) textSizeScript*4/5);
        tVLine.setTextColor(textColorFore);
        tVLine.setGravity(Gravity.START);
        tVLine.setWidth(xPixels);
        tVLine.setLineSpacing(1.2f, 1.2f);
        linearLayout.addView(tVLine);
        tVLine.setText(text);

    }

    private void initScrollView() {
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