package com.urrecliner.dicbible;

import static android.graphics.Typeface.BOLD;
import static com.urrecliner.dicbible.Vars.TAB_MODE_DIC;
import static com.urrecliner.dicbible.Vars.TAB_MODE_NEW;
import static com.urrecliner.dicbible.Vars.TAB_MODE_OLD;
import static com.urrecliner.dicbible.Vars.agpColorFore;
import static com.urrecliner.dicbible.Vars.agpShow;
import static com.urrecliner.dicbible.Vars.bibleColorFore;
import static com.urrecliner.dicbible.Vars.bibleTexts;
import static com.urrecliner.dicbible.Vars.blank;
import static com.urrecliner.dicbible.Vars.bookMarks;
import static com.urrecliner.dicbible.Vars.btmLayout;
import static com.urrecliner.dicbible.Vars.cevColorFore;
import static com.urrecliner.dicbible.Vars.cevShow;
import static com.urrecliner.dicbible.Vars.darkMode;
import static com.urrecliner.dicbible.Vars.dicColorFore;
import static com.urrecliner.dicbible.Vars.fBody;
import static com.urrecliner.dicbible.Vars.fullBibleNames;
import static com.urrecliner.dicbible.Vars.history;
import static com.urrecliner.dicbible.Vars.isReadingNow;
import static com.urrecliner.dicbible.Vars.logFile;
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
import static com.urrecliner.dicbible.Vars.numberColorFore;
import static com.urrecliner.dicbible.Vars.packageFolder;
import static com.urrecliner.dicbible.Vars.paraColorFore;
import static com.urrecliner.dicbible.Vars.referColorFore;
import static com.urrecliner.dicbible.Vars.screenColorBack;
import static com.urrecliner.dicbible.Vars.scriptColorFore;
import static com.urrecliner.dicbible.Vars.shortBibleNames;
import static com.urrecliner.dicbible.Vars.speaking;
import static com.urrecliner.dicbible.Vars.textSizeBible66;
import static com.urrecliner.dicbible.Vars.textSizeBibleNumber;
import static com.urrecliner.dicbible.Vars.textSizeDic;
import static com.urrecliner.dicbible.Vars.textSizeRefer;
import static com.urrecliner.dicbible.Vars.textSizeScript;
import static com.urrecliner.dicbible.Vars.textSizeSpace;
import static com.urrecliner.dicbible.Vars.topLayout;
import static com.urrecliner.dicbible.Vars.topTab;
import static com.urrecliner.dicbible.Vars.utils;
import static com.urrecliner.dicbible.Vars.vAgpBible;
import static com.urrecliner.dicbible.Vars.vBackAction;
import static com.urrecliner.dicbible.Vars.vCenterAction;
import static com.urrecliner.dicbible.Vars.vCevBible;
import static com.urrecliner.dicbible.Vars.vHymn;
import static com.urrecliner.dicbible.Vars.vLeftAction;
import static com.urrecliner.dicbible.Vars.vNewBible;
import static com.urrecliner.dicbible.Vars.vOldBible;
import static com.urrecliner.dicbible.Vars.vRightAction;
import static com.urrecliner.dicbible.Vars.vSearch;
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
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.urrecliner.dicbible.model.BookMark;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

class MakeBible {

    private final String newLine = "\n";
    private ScrollView scrollView;
    private TextView textView;
    private LinearLayout linearLayout;
    private final String markChar = "†";

    void showBibleList() {
        buildMenu();
        initScrollView();

        int loop = (topTab == TAB_MODE_OLD) ?  39: 27;
        int start = (topTab == TAB_MODE_OLD) ? 1 : 40;
        int count = 1;
        final int nbrColumn = 3;
        int buttonWidth = xPixels / nbrColumn;

        addBlankLine(linearLayout);

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
                b.setTextColor(bibleColorFore);
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
        scrollView.setBackgroundColor(screenColorBack);
        textView = new TextView(mContext);
        linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearLayout);
    }

    private void addBlankLine(LinearLayout linearlayout) {
        textView.setText(newLine);
        textView.setTextSize(10);
        textView.setWidth(xPixels);
        textView.setTextColor(0);
        textView.setGravity(Gravity.CENTER);
        linearlayout.addView(textView);
    }

    void showChapterList() {
        buildMenu();
        initScrollView();
        textView.setText(newLine);
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
                tVNbr.setTextColor(numberColorFore);
                tVNbr.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                tVNbr.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                tVNbr.setWidth(buttonWidth);
                tVNbr.setGravity(Gravity.CENTER_HORIZONTAL);
                tVNbr.setPadding(0,16,0,16);
                tVNbr.setId(chapter);
                tVNbr.setTextSize(textSizeBibleNumber);
                tVNbr.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
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

    private final int [] crossF = new int[TABLE_SIZE];
    private final int [] crossT = new int[TABLE_SIZE];
    private final int [] crossV = new int[TABLE_SIZE];
    private final String [] crossS = new String[TABLE_SIZE];
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
    int paraSize, dicSize, referSize, spaceSize;

    void showBibleBody() {
        buildMenu();
        initScrollView();

        String file2read = "bible/" + nowBible + "/" + nowChapter + ".txt";
        bibleTexts = FileRead.readBibleFile(file2read);
        if (bibleTexts == null) {
            Toast.makeText(mContext, "Bible source not found " + fullBibleNames[nowBible] + " " + nowChapter,Toast.LENGTH_LONG).show();
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

        final ViewGroup.MarginLayoutParams lp =(ViewGroup.MarginLayoutParams)linearLayout.getLayoutParams();
        lp.setMargins(20,16,20,16);
        linearLayout.setLayoutParams(lp);
        bodyText = new StringBuilder();
        bodyText.append(newLine);
        ptrBody = 1;
        makeBibleAllVerses();
        SpannableString ss = settleSpannableString(bodyText);

        history.push();
        textView.setText(ss);
        textView.setTextSize(textSizeScript);
        textView.setLineSpacing(1.1f, 1.1f);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        linearLayout.addView(textView);

        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);
        zoomControl.set();
        fBody.post(() -> new Timer().schedule(new TimerTask() {
            public void run() {
                int pos = fBody.getBottom() * nowVerse / maxVerse;
                Log.w("scroll "+pos,"getBottom="+fBody.getBottom());
                scrollView.scrollTo(0, textView.getBottom() * versePtr / ptrBody);
            }
        }, 200));
    }

    private SpannableString settleSpannableString(StringBuilder bodyText) {

        paraSize = textSizeScript * 11 / 10;
        dicSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSizeDic, mContext.getResources().getDisplayMetrics());
        referSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSizeRefer, mContext.getResources().getDisplayMetrics());
        spaceSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSizeSpace, mContext.getResources().getDisplayMetrics());

        SpannableString ss = new SpannableString(bodyText);

        for (int i = 0; i < idxText; i++) {
            ss.setSpan(new AbsoluteSizeSpan(textSizeScript, true), textF[i], textT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(scriptColorFore), textF[i], textT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxKeyword; i++) {
            ss.setSpan(new keywordSpan(keywords[i], keywordV[i]), keywordF[i], keywordT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxVerse; i++) {
            ss.setSpan(new verseSpan(nowBible, nowChapter, i+1), verseF[i], verseT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(scriptColorFore), verseF[i], verseT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxPara; i++) {
            ss.setSpan(new ForegroundColorSpan(paraColorFore), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(paraSize, true), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ss.setSpan(new UnderlineSpan(), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new StyleSpan(BOLD), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxRefer; i++) {
            ss.setSpan(new referSpan(crossS[i], crossV[i]), crossF[i], crossT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxCev; i++) {
            ss.setSpan(new ForegroundColorSpan(cevColorFore), cevF[i], cevT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ss.setSpan(new BackgroundColorSpan(cevColorBlack), cevF[i], cevT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxAgp; i++) {
            ss.setSpan(new ForegroundColorSpan(agpColorFore), agpF[i], agpT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ss.setSpan(new BackgroundColorSpan(agpColorBlack), agpF[i], agpT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxSpace; i++) {
            ss.setSpan(new AbsoluteSizeSpan(spaceSize, false), spaceF[i], spaceT[i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
            String verseString = (line+1)+(marked[line+1] ? markChar:spacing);
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
                    spaceF[idxSpace] = ptrBody;
                    bodyText.append(" " + newLine);
                    spaceT[idxSpace] = ptrBody + 2;
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
            crossF[idxRefer] = ptrBody;
            crossT[idxRefer] = ptrBody + showWord.length();
            crossV[idxRefer] = verse;
            crossS[idxRefer] = keyword.substring(1);  // save 01#12:34
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
//            } else {
//                if (tilde != -1)
//                    searchKey = searchKey.substring(0,searchKey.length()-1);
//                bodyText.append(searchKey);
//                ptrBody += searchKey.length();
//            }
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
                Toast.makeText(mContext, fullBibleNames[bible]+" "+chapter+" 장"+verse+" 절이\n북마크 해제 되었습니다",Toast.LENGTH_LONG).show();
            } else {
                bookMarks.add(0, new BookMark(bible, chapter, verse, System.currentTimeMillis(), false));
                Toast.makeText(mContext, fullBibleNames[bible]+" "+chapter+" 장"+verse+" 절이\n북마크 설정 되었습니다",Toast.LENGTH_LONG).show();
            }
            HandlePrefs.saveArray("bookMark", bookMarks);
            nowVerse = verse;

            showBibleBody();
        }
    }

    public class keywordSpan extends ClickableSpan {

        String key;
        int verse;
        keywordSpan(String key, int verse) { this.key = key; this.verse = verse;}

        Typeface boldface = Typeface.create(Typeface.DEFAULT, BOLD);
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(dicColorFore);
            ds.setTypeface(boldface);
            ds.setTextSize(dicSize);
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
            ds.setColor(referColorFore);
            ds.setTextSize(referSize);  // double size
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
        buildMenu();
        initScrollView();

        int verse = nowVerse;
        String txt = "dict/" + nowDic + ".txt";
        history.pop();
        nowVerse = verse;
        history.push();
        String [] dicTexts = FileRead.readBibleFile(txt);
        if (dicTexts != null) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
            lp.setMargins(60,36,60,40);
            linearLayout.setLayoutParams(lp);
            for (String line : dicTexts) {
                switch (line.substring(0, 1)) {
                    case "@":       // contains image file name
                        File imgFile = new File(packageFolder, "dict_img/" + line.substring(1));
                        if (imgFile.exists()) {
                            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            int height = xPixels * bitmap.getHeight() / bitmap.getWidth();
                            ImageView imV = new ImageView(mContext);
                            linearLayout.addView(imV);
                            imV.setImageBitmap(Bitmap.createScaledBitmap(bitmap, xPixels, height, false));
                            imV.requestLayout();

//                            PhotoViewAttacher pA;
//                            pA = new PhotoViewAttacher(imV);
//                            pA.update();
                        }
                        break;
                    case "~": { // contains subject name
                        TextView tVLine = new TextView(mContext);
                        tVLine.setTextSize(textSizeDic);
                        tVLine.setTextColor(dicColorFore);
                        tVLine.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                        tVLine.setGravity(Gravity.CENTER_HORIZONTAL);
                        tVLine.setWidth(xPixels);
                        tVLine.setLineSpacing(1.4f, 1.4f);
                        linearLayout.addView(tVLine);
                        tVLine.setText(line.substring(1));
                        break;
                    }
                    default: {
                        TextView tVLine = new TextView(mContext);
                        tVLine.setTextSize(textSizeScript*4/5);
                        tVLine.setTextColor(scriptColorFore);
                        tVLine.setGravity(Gravity.START);
                        tVLine.setWidth(xPixels);
                        tVLine.setLineSpacing(1.2f, 1.2f);
                        linearLayout.addView(tVLine);
                        tVLine.setText(line);
                        break;
                    }
                }
            }
        }
        else {
            String errText = "[" + nowDic + "] not found";
            Toast.makeText(mContext,errText,Toast.LENGTH_LONG).show();
            utils.log(logFile, errText);
        }
        topTab = TAB_MODE_DIC;
        history.push();

        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);

    }

    private void makeRefer() {

        String refer = nowDic; // 41#4:18
        int verse = nowVerse;
        history.pop();
        nowVerse = verse;
        history.push();
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

    public void buildMenu() {

        String s;

        topLayout.setBackgroundColor(menuColorBack);
        btmLayout.setBackgroundColor(menuColorBack);
        fBody.setBackgroundColor(screenColorBack);
        vBackAction.setBackgroundColor(menuColorBack);
        vBackAction.setTextColor(menuColorFore);

        if ((topTab == TAB_MODE_OLD || topTab == TAB_MODE_NEW) && (nowBible > 0 && nowChapter > 0))
            vSearch.setVisibility(View.VISIBLE);
        else
            vSearch.setVisibility(View.GONE);

        vOldBible.setBackgroundColor(menuColorBack);
        vOldBible.setTextColor(menuColorFore);
        vNewBible.setBackgroundColor(menuColorBack);
        vNewBible.setTextColor(menuColorFore);
        vHymn.setBackgroundColor(menuColorBack);
        vHymn.setTextColor(menuColorFore);

        if (topTab == TAB_MODE_NEW) {
            vNewBible.setBackgroundResource(R.drawable.bible_border);
        } else if (topTab == TAB_MODE_OLD) {
            vOldBible.setBackgroundResource(R.drawable.bible_border);
        } else if (topTab == TAB_MODE_DIC) {
            vAgpBible.setText(blank);
            vAgpBible.setBackgroundColor(menuColorBack);
            vOldBible.setText(blank);
            vNewBible.setText(blank);
            vHymn.setText(blank);
            vOldBible.setBackgroundColor(menuColorBack);
            vNewBible.setBackgroundColor(menuColorBack);
            vHymn.setBackgroundColor(menuColorBack);
            vCevBible.setText(blank);
            vCevBible.setBackgroundColor(menuColorBack);
            vLeftAction.setText(blank);
            vLeftAction.setBackgroundColor(menuColorBack);
            vRightAction.setText(blank);
            vRightAction.setBackgroundColor(menuColorBack);
            vCenterAction.setText(nowDic);
            vCenterAction.setBackgroundColor(menuColorBack);
            return;
        }
        // from now NEW or OLD only
        if (nowBible > 0 && nowChapter > 0) {
            vAgpBible.setText("agp");
            vAgpBible.setTextColor(menuColorFore);
            vAgpBible.setBackgroundColor((agpShow)? agpColorFore : menuColorBack);
            vCevBible.setText("cev");
            vCevBible.setTextColor(menuColorFore);
            vCevBible.setBackgroundColor((cevShow)? cevColorFore : menuColorBack);
        } else {
            vAgpBible.setText(blank);
            vAgpBible.setBackgroundColor(menuColorBack);
            vCevBible.setText(blank);
            vCevBible.setBackgroundColor(menuColorBack);
        }
        vSearch.setBackgroundColor(menuColorBack);

        vLeftAction.setTextColor(menuColorFore);
        vRightAction.setTextColor(menuColorFore);
        vCenterAction.setTextColor(menuColorFore);
        vLeftAction.setBackgroundColor(menuColorBack);
        vRightAction.setBackgroundColor(menuColorBack);
        vCenterAction.setBackgroundColor(menuColorBack);

        if (nowBible == 0) {        // show bible list
            vLeftAction.setText(blank);
            vRightAction.setText(blank);
            vCenterAction.setText(blank);
        } else if (nowChapter == 0) {
            // bible = 33, chapt = 0
            // 마, 막, 누
            vLeftAction.setText((nowBible == 0) ? blank: shortBibleNames[nowBible-1]);
            vRightAction.setText((nowBible == 65) ? blank: shortBibleNames[nowBible+1]);
            vCenterAction.setText(fullBibleNames[nowBible]);
        } else {
            // bible = 33, chapt = 3
            // 마3 마태복음 마4
            if (nowChapter > 1) {
                s = shortBibleNames[nowBible]+(nowChapter-1);
            } else {
                if (nowBible > 1) {
                    s = shortBibleNames[nowBible-1]+nbrOfChapters[nowBible-1];
                } else {
                    s = blank;
                }
            }
            vLeftAction.setText(s);

            vCenterAction.setText(fullBibleNames[nowBible]+nowChapter);
            vCenterAction.setSingleLine(true);
            vCenterAction.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            vCenterAction.setSelected(true);

            if (nowChapter < nbrOfChapters[nowBible]) {
                s = shortBibleNames[nowBible]+(nowChapter+1);
            } else {
                if (nowBible < 66) {
                    s = shortBibleNames[nowBible+1]+"1";
                } else {
                    s = blank;
                }
            }
            vRightAction.setText(s);
        }
    }

    void goBibleLeft() {
        int prevChapter = nowChapter - 1;
        if (prevChapter == 0) {   // prev bible required
            int prevBible = nowBible - 1;
            if (prevBible == 0)
                return;
            else {
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
        String s = "성경 읽는 중에 중지하려면\n["+fullBibleNames[nowBible]+nowChapter+"] 를 누르세요";
        textView.setText(s);
        Button ok_btn = dialogView.findViewById(R.id.ok_btn);
        ok_btn.setText((isReadingNow)? "그만 읽기": "읽기 시작");
        ok_btn.setOnClickListener(v -> {
            alertDialog.dismiss();
            speaking.say();
        });

        Button bookMark = dialogView.findViewById(R.id.cancle_btn);
        bookMark.setText((isReadingNow)? "계속 읽기":"읽기 안 함");
        bookMark.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }

}