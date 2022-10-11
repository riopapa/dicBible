package com.urrecliner.dicbible;

import static android.graphics.Typeface.BOLD;
import static com.urrecliner.dicbible.Vars.TAB_MODE_DIC;
import static com.urrecliner.dicbible.Vars.TAB_MODE_NEW;
import static com.urrecliner.dicbible.Vars.TAB_MODE_OLD;
import static com.urrecliner.dicbible.Vars.agpColorFore;
import static com.urrecliner.dicbible.Vars.agpShow;
import static com.urrecliner.dicbible.Vars.biblePitch;
import static com.urrecliner.dicbible.Vars.bibleSpeed;
import static com.urrecliner.dicbible.Vars.bibleTexts;
import static com.urrecliner.dicbible.Vars.bookMarks;
import static com.urrecliner.dicbible.Vars.cevColorFore;
import static com.urrecliner.dicbible.Vars.cevShow;
import static com.urrecliner.dicbible.Vars.darkMode;
import static com.urrecliner.dicbible.Vars.dicColorFore;
import static com.urrecliner.dicbible.Vars.fBody;
import static com.urrecliner.dicbible.Vars.fullBibleNames;
import static com.urrecliner.dicbible.Vars.history;
import static com.urrecliner.dicbible.Vars.mActivity;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.markColorBack;
import static com.urrecliner.dicbible.Vars.maxVerse;
import static com.urrecliner.dicbible.Vars.menuColorBack;
import static com.urrecliner.dicbible.Vars.menuColorFore;
import static com.urrecliner.dicbible.Vars.nbrOfChapters;
import static com.urrecliner.dicbible.Vars.nowBible;
import static com.urrecliner.dicbible.Vars.nowChapter;
import static com.urrecliner.dicbible.Vars.nowDic;
import static com.urrecliner.dicbible.Vars.nowVerse;
import static com.urrecliner.dicbible.Vars.packageFolder;
import static com.urrecliner.dicbible.Vars.paraColorFore;
import static com.urrecliner.dicbible.Vars.screenMenu;
import static com.urrecliner.dicbible.Vars.scrollView;
import static com.urrecliner.dicbible.Vars.shortBibleNames;
import static com.urrecliner.dicbible.Vars.speaking;
import static com.urrecliner.dicbible.Vars.textColorFore;
import static com.urrecliner.dicbible.Vars.textSizeBible66;
import static com.urrecliner.dicbible.Vars.textSizeDic;
import static com.urrecliner.dicbible.Vars.textSizeRefer;
import static com.urrecliner.dicbible.Vars.textSizeScript;
import static com.urrecliner.dicbible.Vars.textSizeSpace;
import static com.urrecliner.dicbible.Vars.topTab;
import static com.urrecliner.dicbible.Vars.utils;
import static com.urrecliner.dicbible.Vars.xPixels;
import static com.urrecliner.dicbible.Vars.zoomControl;
import static java.lang.Integer.parseInt;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.github.chrisbanes.photoview.PhotoView;
import com.urrecliner.dicbible.model.BookMark;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

class MakeBible {

    private final String newLine = "\n";
    private TextView textView;
    LinearLayout linearLayout;

    void showBibleList() {

        screenMenu.build();
        initScrollView();

        int loop = (topTab == TAB_MODE_OLD) ?  39: 27;
        int start = (topTab == TAB_MODE_OLD) ? 1 : 40;
        int count = 1;
        final int nbrColumn = 3;
        int buttonWidth = xPixels / nbrColumn;

        textView.setText(newLine);
        textView.setTextSize(10);
        textView.setWidth(xPixels);
        textView.setTextColor(0);
        textView.setGravity(Gravity.CENTER);
        linearLayout.addView(textView);

        for(int i = 0; i<15;i++) {
            Button b;
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.addView(rowLayout);
            for(int j = 0; j < nbrColumn; j++) {
                LinearLayout columnLayout = new LinearLayout(mContext);
                columnLayout.setOrientation(LinearLayout.VERTICAL);
                b = new Button(mContext);
                b.setBackgroundResource((darkMode)? R.drawable.button_bible_dark:R.drawable.button_bible);
                b.setText(fullBibleNames[start]);
                b.setId(start);
                b.setWidth(buttonWidth);
                b.setTextSize(textSizeBible66);
                b.setTextColor(textColorFore);
                b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                columnLayout.addView(b);
                b.setOnClickListener(v -> {
                    nowBible = v.getId();
                    showChapterList();
                });
                rowLayout.addView(columnLayout);
                start++;
                count++;
                if (count > loop)
                    break;
            }
            if (count > loop)
                break;
        }
        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);
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

    void showChapterList() {
        screenMenu.build();
        initScrollView();
        textView.setText(fullBibleNames[nowBible]);
        textView.setTextColor(menuColorFore);
        textView.setTextSize(textSizeScript);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(textView);

        int chapterMax = nbrOfChapters[nowBible];
        int chapter = 1;
        TextView tVNbr;
        final int nbrColumn = 5;
        int buttonWidth = xPixels / nbrColumn;

        for(int i = 0; i<50;i++) {
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.addView(rowLayout);
            for(int j = 0; j < nbrColumn; j++) {
                LinearLayout columnLayout = new LinearLayout(mContext);
                tVNbr = new TextView(mContext);
                String text = ""+chapter;
                tVNbr.setText(text);
                tVNbr.setTextColor(menuColorFore);
                tVNbr.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                tVNbr.setTextSize(textSizeScript);
                tVNbr.setWidth(buttonWidth);
                tVNbr.setGravity(Gravity.CENTER_HORIZONTAL);
                tVNbr.setPadding(0,16,0,16);
                tVNbr.setId(chapter);
                columnLayout.addView(tVNbr);
                tVNbr.setOnClickListener(v -> {
                    nowChapter = v.getId();
                    nowVerse = 0;
                    showBibleBody();
                });
                rowLayout.addView(columnLayout);
                chapter++;
                if (chapter > chapterMax)
                    break;
            }
            if (chapter > chapterMax)
                break;
        }

        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);
    }

    private final int TABLE_SIZE = 500;

    private final int [] keywordF = new int[TABLE_SIZE];           // ..F from byte pointer
    private final int [] keywordT = new int[TABLE_SIZE];           // ..T to byte pointer
    private final int [] keywordV = new int[TABLE_SIZE];           // ..V now Verse
    private final String [] keywords = new String[TABLE_SIZE];
    private int idxKeyword;

    private final int VERSE_SIZE = 180; // max verse number is 176
    private final int [] textF = new int[VERSE_SIZE];           // ..F from byte pointer
    private final int [] textT = new int[VERSE_SIZE];           // ..F from byte pointer
    private final int [] verseF = new int[VERSE_SIZE];
    private final int [] verseT = new int[VERSE_SIZE];
    private int idxText, idxVerse;

    private final int [] referF = new int[TABLE_SIZE];
    private final int [] referT = new int[TABLE_SIZE];
    private final int [] referV = new int[TABLE_SIZE];
    private final String [] referS = new String[TABLE_SIZE];
    private int idxRefer;

    private final int [] paraF = new int[30];
    private final int [] paraT = new int[30];
    private int idxPara;

    private final int [] agpF = new int[VERSE_SIZE];
    private final int [] agpT = new int[VERSE_SIZE];
    private int idxAgp;

    private final int [] cevF = new int[VERSE_SIZE];
    private final int [] cevT = new int[VERSE_SIZE];
    private int idxCev;

    private final int [] spaceF = new int[VERSE_SIZE];
    private final int [] spaceT = new int[VERSE_SIZE];
    private int idxSpace;
    private boolean [] marked;

    private int versePtr;
    private int highLightF, highLightT;

    private int ptrBody;
    private StringBuilder bodyText;
    int paraSize;

    void showBibleBody() {
        screenMenu.build();
        initScrollView();
        history.push();
        String file2read = "bible/" + nowBible + "/" + nowChapter + ".txt";
        bibleTexts = FileRead.readBibleFile(file2read);
        if (bibleTexts == null) {
            utils.showSnackBar("성경 말씀 파일 없음 ", fullBibleNames[nowBible] + " " + nowChapter);
            return;
        }
        maxVerse  = bibleTexts.length;

        marked = new boolean[maxVerse+1];
        for (BookMark bm: bookMarks) {
            if (bm.bible == nowBible && bm.chapter == nowChapter)
                marked[bm.verse] = true;
        }
        idxText = 0;
        idxVerse = 0;
        idxRefer = 0;
        idxKeyword = 0;
        idxPara = 0;
        idxAgp = 0;
        idxCev = 0;
        idxSpace = 0;

        bodyText = new StringBuilder();
        bodyText.append(newLine);
        ptrBody = 1;
        makeBibleAllVerses();
        SpannableString ss = settleSpannableString(bodyText);

        textView.setText(ss);
        textView.setTextSize(textSizeScript);


//        textView.setLineSpacing(0.1f, 1.0f);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        linearLayout.addView(textView);

        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);
        zoomControl.set();
        scrollView.post(() -> new Timer().schedule(new TimerTask() {
            public void run() {
                mActivity.runOnUiThread(() -> scrollView.scrollTo(0, textView.getBottom() * versePtr / ptrBody));
            }
        }, 30));
    }

    private SpannableString settleSpannableString(StringBuilder bodyText) {

        paraSize = textSizeScript * 11 / 10;

        SpannableString ss = new SpannableString(bodyText);

        for (int i = 0; i < idxText; i++) {
            ss.setSpan(new AbsoluteSizeSpan(textSizeScript, true), textF[i], textT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(textColorFore), textF[i], textT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxKeyword; i++) {
            ss.setSpan(new keywordClick(keywords[i], keywordV[i]), keywordF[i], keywordT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(textSizeDic, true), keywordF[i], keywordT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(dicColorFore), keywordF[i], keywordT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxVerse; i++) {
            ss.setSpan(new verseSpan(nowBible, nowChapter, i+1), verseF[i], verseT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(textColorFore), verseF[i], verseT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxPara; i++) {
            ss.setSpan(new ForegroundColorSpan(paraColorFore), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(paraSize, true), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ss.setSpan(new UnderlineSpan(), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new StyleSpan(BOLD), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxRefer; i++) {
            ss.setSpan(new AbsoluteSizeSpan(textSizeRefer, true), referF[i], referT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new referSpan(referS[i], referV[i]), referF[i], referT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxCev; i++) {
            ss.setSpan(new ForegroundColorSpan(cevColorFore), cevF[i], cevT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxAgp; i++) {
            ss.setSpan(new ForegroundColorSpan(agpColorFore), agpF[i], agpT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxSpace; i++) {
            ss.setSpan(new AbsoluteSizeSpan(textSizeSpace/2, true), spaceF[i], spaceT[i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (highLightF > 0)
            ss.setSpan(new BackgroundColorSpan(markColorBack), highLightF, highLightT, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    final String spacing = "\u00A0"; // to prevent word wrap
    private void makeBibleAllVerses() {
        versePtr = 0;
        highLightF = 0;
        for (int line = 0; line < maxVerse; line++) {
            if (line == (nowVerse-2))   // to show this verse no too top or above top
                versePtr = ptrBody;
            String str;
            String workLine = bibleTexts[line] + "~";// "~" is end character
            int lenWorkLine = workLine.length() - 1;
            int idx = workLine.indexOf("`a");
            int idx2nd = workLine.indexOf("`c");
            String agpText = workLine.substring(idx + 2, idx2nd).replace(" ", spacing);
            if (agpText.length() == 0)
                agpText = " ";
            String cevText = workLine.substring(idx2nd + 2, lenWorkLine);
            if (cevText.length() == 0)
                cevText = " ";
            workLine = workLine.substring(0, idx).replace(" ", spacing);
            // to prevent word wrap
            String markChar = "†";
            String verseString = (line+1)+(marked[line+1] ? markChar :spacing);
            if (line < maxVerse-1) {
                String nextLine = bibleTexts[line+1].substring(0, bibleTexts[line+1].indexOf("`a")).trim();
                if (nextLine.length() == 0)
                    verseString += "-" +(line+2);
            }
            lenWorkLine = workLine.length();
            if (lenWorkLine > 0) {

// bible script sample
// {천지 창조}[_태초_]에 [_하나님_]이 [_천지_]를 [_창조_]하시니라[_#v43#1:3_][_$58#1:10_]
// `a태초에 하나님께서 하늘과 땅을 창조하셨습니다.
// `cIn the beginning God created the heavens and the earth.
                String c = workLine.substring(0, 1);
                if (c.equals("{")) {    // first column might have paragraph name
                    int endIdx = workLine.indexOf("}");
                    str = workLine.substring(1, endIdx);
                    workLine = workLine.substring(endIdx + 1);
                    lenWorkLine = workLine.length();
                    bodyText.append(str);
                    bodyText.append(newLine);
                    paraF[idxPara] = ptrBody;
                    paraT[idxPara] = ptrBody + str.length() + 1;
                    idxPara++;
                    ptrBody += str.length() + 1;
                    spaceF[idxSpace] = ptrBody-1;
                    bodyText.append(" " + newLine);
                    spaceT[idxSpace] = ptrBody + 3;
                    ptrBody += 2;
                    idxSpace++;
                }
                if (nowVerse > 0 && line == (nowVerse-1))
                    highLightF = ptrBody;
                str = spacing + spacing + verseString + spacing;
                textF[idxText] = ptrBody;
                verseF[idxVerse] = ptrBody;
                verseT[idxVerse] = ptrBody + str.length();
                idxVerse++;
                ptrBody += str.length();
                bodyText.append(str);
                if (nowVerse > 0 && line == (nowVerse-1))
                    highLightT = ptrBody;

                while (lenWorkLine > 0) {
                    idx = workLine.indexOf("[_");
                    if (idx == -1) { // no more keyword
                        bodyText.append(workLine);
                        ptrBody += lenWorkLine;
                        break;
                    } else {  // contains keyword
                        if (idx != 0) {   // has string before keyword
                            bodyText.append(workLine, 0, idx);
                            ptrBody += idx;
                            workLine = workLine.substring(idx);
                        }
                        // string starts with [_ & keyword
                        idx2nd = checkDicWords(workLine, line + 1);
                        workLine = workLine.substring(idx2nd + 2);
                        lenWorkLine = workLine.length();
                    }
                }
                if (agpShow) {
                    agpText = newLine + agpText;
                    bodyText.append(agpText);
                    agpF[idxAgp] = ptrBody;
                    agpT[idxAgp] = ptrBody + agpText.length();
                    idxAgp++;
                    ptrBody += agpText.length();
                }
                if (cevShow) {
                    cevText = newLine + cevText;
                    bodyText.append(cevText);
                    cevF[idxCev] = ptrBody;
                    cevT[idxCev] = ptrBody + cevText.length();
                    idxCev++;
                    ptrBody += cevText.length();
                }
                textT[idxText] = ptrBody;
                idxText++;
                ptrBody++;
                bodyText.append(newLine);
                spaceF[idxSpace] = ptrBody;
                bodyText.append(" " + newLine);
                spaceT[idxSpace] = ptrBody + 2;
                ptrBody += 2;
                idxSpace++;
            }
        }
    }

    private int checkDicWords(String workLine, int verse) {
        int ptr;
        ptr = workLine.indexOf("_]");
        String keyword = workLine.substring(2, ptr);
        if (keyword.charAt(0) == '$') {   // reference
            String bibShort = shortBibleNames[parseInt(keyword.substring(1, 3))];
            String showWord = " (" + bibShort + keyword.substring(4) + ") ";      // $01#12:34 -> (창12:34) 로 표시
            bodyText.append(showWord);
            referF[idxRefer] = ptrBody;
            referT[idxRefer] = ptrBody + showWord.length();
            referV[idxRefer] = verse;
            referS[idxRefer] = keyword.substring(1);  // save 01#12:34
            idxRefer++;
            ptrBody += showWord.length();
        } else {  // keyword case
            int tilde = keyword.indexOf("~");
            String searchKey = (tilde != -1)? keyword.substring(0, tilde) + keyword.substring(tilde + 1) : keyword;
//            if (isNewKeyword(searchKey, keywords, idxKeyword)) {
                keywordF[idxKeyword] = ptrBody;
                keywordV[idxKeyword] = verse;
                if (tilde != -1) {
                    keywordT[idxKeyword] = ptrBody + tilde;
                    keywords[idxKeyword] = searchKey;
                    ptrBody += tilde;
                    bodyText.append(keyword,0, tilde);
                } else {
                    keywordT[idxKeyword] = ptrBody + keyword.length();
                    keywords[idxKeyword] = keyword;
                    ptrBody += keyword.length();
                    bodyText.append(keyword);
                }
                idxKeyword++;
        }
        return ptr;
    }

    public class verseSpan extends ClickableSpan {

        int bible, chapter, verse;
        verseSpan(int bible, int chapter, int verse) { this.bible = bible; this.chapter = chapter; this.verse = verse;}

        Typeface boldface = Typeface.create(Typeface.DEFAULT, BOLD);
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setTypeface(boldface);
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(@NonNull View widget) {
            if (marked[verse]) {
                for (int i = 0; i < bookMarks.size(); i++) {
                    BookMark bm = bookMarks.get(i);
                    if (bm.bible == nowBible && bm.chapter == nowChapter && bm.verse == verse) {
                        bookMarks.remove(i);
                        break;
                    }
                }
                utils.showSnackBar( "북마크가 해제 되었습니다", fullBibleNames[bible]+" "+chapter+" 장"+verse+" 절");
            } else {
                bookMarks.add(0, new BookMark(bible, chapter, verse, System.currentTimeMillis()));
                utils.showSnackBar( "북마크가 설정 되었습니다", fullBibleNames[bible]+" "+chapter+" 장"+verse+" 절");
            }
            HandlePrefs.saveArray("bookMark", bookMarks);
            nowVerse = verse;
            showBibleBody();
        }
    }

    public class keywordClick extends ClickableSpan {

        String key;
        int verse;
        keywordClick(String key, int verse) { this.key = key; this.verse = verse;}

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(@NonNull View widget) {
            nowDic = key;
            nowVerse = verse;
            showDicWord();
        }
    }

    public class referSpan extends ClickableSpan {

        String key;
        int verse;
        referSpan(String key, int verse) { this.key = key; this.verse = verse;}
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);    // this remove the underline
        }

        @Override
        public void onClick(@NonNull View widget) {
            nowDic = key;
            nowVerse = verse;
            makeRefer();
        }
    }

    void showDicWord() {

        topTab = TAB_MODE_DIC;
        screenMenu.build();
        initScrollView();
        history.push();

        String txt = "dict/" + nowDic + ".txt";
        String [] dicTexts = FileRead.readBibleFile(txt);
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
//                        tVLine.setLineSpacing(0.1f, 1.1f);
                        linearLayout.addView(tVLine);
                        tVLine.setText(line);
                        break;
                    }
                }
            }
        } else {
            String errText = "[" + nowDic + "] not found";
            Toast.makeText(mContext,errText,Toast.LENGTH_LONG).show();
//            utils.log(logFile, errText);
        }

        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);

    }

    private void makeRefer() {

        String refer = nowDic; // 41#4:18
        nowBible = parseInt(refer.substring(0,2));
        topTab = (nowBible < 40) ? TAB_MODE_OLD : TAB_MODE_NEW;
        refer = refer.substring(3) + "z";
        char[] chars  = refer.toCharArray();
        int ptr = getNumberPtr(chars,0);
        nowChapter = parseInt(refer.substring(0,ptr));
        int ptr2 = getNumberPtr(chars,ptr+2);
        nowVerse = parseInt(refer.substring(ptr+1,ptr2));
        showBibleBody();
    }

    private int getNumberPtr(char[] chars, int ptr) {
        for (int i = ptr ; i < chars.length; i++) {
            if (!Character.isDigit(chars[i]))
                return i;
        }
        return 0;
    }

    void goBibleLeft() {

        int prevChapter = nowChapter - 1;
        if (prevChapter == 0) {   // prev bible required
            int prevBible = nowBible - 1;
            if (prevBible == 0) {
                return;
            } else {
                nowBible = prevBible;
                nowChapter = nbrOfChapters[prevBible];
            }
        } else
            nowChapter = prevChapter;
        nowVerse = 0;
        showBibleBody();
    }


    void goBibleRight() {
        int nextChapter = nowChapter + 1;
        if (nextChapter > nbrOfChapters[nowBible]) {   // next bible required
            int prevBible = nowBible + 1;
            if (prevBible > 66) {
                return;
            } else {
                nowBible = prevBible;
                nowChapter = 1;
            }
        } else {
            nowChapter = nextChapter;
        }
        nowVerse = 0;
        showBibleBody();
    }

    void confirmSpeak() {
        View dialogView = mActivity.getLayoutInflater().inflate(R.layout.speak_or_not, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        TextView textView = dialogView.findViewById(R.id.promptMessage);
        String s = fullBibleNames[nowBible]+nowChapter+"\n속도 : "
                + bibleSpeed+"% 높낮이 : "+biblePitch+"%";
        textView.setText(s);
        Button ok_btn = dialogView.findViewById(R.id.ok_btn);
        ok_btn.setText("읽기 시작");
        ok_btn.setOnClickListener(v -> {
            alertDialog.dismiss();
            speaking.say();
        });
    }
}