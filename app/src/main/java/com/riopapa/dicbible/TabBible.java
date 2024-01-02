package com.riopapa.dicbible;

import static android.graphics.Typeface.BOLD;
import static com.riopapa.dicbible.Vars.TAB_NEW;
import static com.riopapa.dicbible.Vars.TAB_OLD;
import static com.riopapa.dicbible.Vars.agpColorFore;
import static com.riopapa.dicbible.Vars.agpShow;
import static com.riopapa.dicbible.Vars.biblePitch;
import static com.riopapa.dicbible.Vars.bibleSpeed;
import static com.riopapa.dicbible.Vars.bibleTexts;
import static com.riopapa.dicbible.Vars.bookMarkChar;
import static com.riopapa.dicbible.Vars.bookMarks;
import static com.riopapa.dicbible.Vars.cevColorFore;
import static com.riopapa.dicbible.Vars.cevShow;
import static com.riopapa.dicbible.Vars.darkMode;
import static com.riopapa.dicbible.Vars.dicColorFore;
import static com.riopapa.dicbible.Vars.fBody;
import static com.riopapa.dicbible.Vars.fileRead;
import static com.riopapa.dicbible.Vars.fullBibleNames;
import static com.riopapa.dicbible.Vars.goBackProcs;
import static com.riopapa.dicbible.Vars.linearLayout;
import static com.riopapa.dicbible.Vars.mActivity;
import static com.riopapa.dicbible.Vars.mContext;
import static com.riopapa.dicbible.Vars.markColorBack;
import static com.riopapa.dicbible.Vars.menuColorFore;
import static com.riopapa.dicbible.Vars.nbrOfChapters;
import static com.riopapa.dicbible.Vars.nowBible;
import static com.riopapa.dicbible.Vars.nowChapter;
import static com.riopapa.dicbible.Vars.nowDic;
import static com.riopapa.dicbible.Vars.nowVerse;
import static com.riopapa.dicbible.Vars.paraColorFore;
import static com.riopapa.dicbible.Vars.screenMenu;
import static com.riopapa.dicbible.Vars.scrollView;
import static com.riopapa.dicbible.Vars.shortBibleNames;
import static com.riopapa.dicbible.Vars.speaking;
import static com.riopapa.dicbible.Vars.textColorFore;
import static com.riopapa.dicbible.Vars.BibleNameSize;
import static com.riopapa.dicbible.Vars.DictShowSize;
import static com.riopapa.dicbible.Vars.DictSize;
import static com.riopapa.dicbible.Vars.BibleSize;
import static com.riopapa.dicbible.Vars.BibleLineSize;
import static com.riopapa.dicbible.Vars.textView;
import static com.riopapa.dicbible.Vars.topTab;
import static com.riopapa.dicbible.Vars.utils;
import static com.riopapa.dicbible.Vars.verMax;
import static com.riopapa.dicbible.Vars.xPixels;
import static java.lang.Integer.parseInt;

import android.app.AlertDialog;
import android.graphics.Paint;
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
import android.text.style.LineHeightSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.riopapa.dicbible.model.BookMark;

import java.util.Timer;
import java.util.TimerTask;

class TabBible {

    private final String newLine = "\n";
    final String spacing = "\u00A0"; // to prevent word wrap

    void showBibleList() {

        screenMenu.build();
        new FrameScrollView();
        nowBible = 0;
        nowChapter = 0;
        nowVerse = 0;

        int loop = (topTab == TAB_OLD) ?  39: 27;
        int bibNbr = (topTab == TAB_OLD) ? 1 : 40;
        int count = 1;
        final int nbrColumn = 3;
        int buttonWidth = xPixels / nbrColumn;

        textView.setText(newLine);
        textView.setTextSize(30);
        textView.setWidth(xPixels);
        textView.setTextColor(0);
        textView.setGravity(Gravity.CENTER);
        linearLayout.addView(textView);

        for(int i = 0; i< 15 ; i++) {
            Button b;
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.addView(rowLayout);
            for(int j = 0; j < nbrColumn; j++) {
                LinearLayout columnLayout = new LinearLayout(mContext);
                columnLayout.setOrientation(LinearLayout.VERTICAL);
                b = new Button(mContext);
                b.setBackgroundResource((darkMode)? R.drawable.button_bible_dark:R.drawable.button_bible);
                b.setText(fullBibleNames[bibNbr]);
                b.setId(bibNbr);
                b.setWidth(buttonWidth);
                b.setTextSize(BibleNameSize);
                b.setTextColor(textColorFore);
                b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                columnLayout.addView(b);
                b.setOnClickListener(v -> {
                    nowBible = v.getId();
                    showChapterList();
                });
                rowLayout.addView(columnLayout);
                bibNbr++;
                count++;
                if (count > loop)
                    break;
            }
            if (count > loop) {
                break;
            }
        }
        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);
    }

    void showChapterList() {
        screenMenu.build();
        new FrameScrollView();
        String bibleHead = fullBibleNames[nowBible]+" [?]";
        textView.setText(bibleHead);
        textView.setTextColor(menuColorFore);
        textView.setTextSize(BibleSize);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setOnClickListener(view -> {
            nowDic = "[ "+fullBibleNames[nowBible]+" ]#"+(""+(100+nowBible)).substring(1,3);
            nowChapter = 0;
            nowVerse = 0;
            new DictShow().show();
        });

        linearLayout.addView(textView);

        int chapMax = nbrOfChapters[nowBible];
        int chapNbr = 1;
        TextView tVNbr;
        final int nbrColumn = 5;
        int buttonWidth = xPixels / nbrColumn;

        for(int i = 0; i < 40; i++) {   // max 40 * 5  psalms = 150
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.addView(rowLayout);
            for(int j = 0; j < nbrColumn; j++) {
                LinearLayout columnLayout = new LinearLayout(mContext);
                tVNbr = new TextView(mContext);
                String text = ""+chapNbr;
                tVNbr.setText(text);
                tVNbr.setTextColor(menuColorFore);
                tVNbr.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                tVNbr.setTextSize(BibleSize);
                tVNbr.setWidth(buttonWidth);
                tVNbr.setGravity(Gravity.CENTER_HORIZONTAL);
                tVNbr.setPadding(0,16,0,16);
                tVNbr.setId(chapNbr);
                columnLayout.addView(tVNbr);
                tVNbr.setOnClickListener(v -> {
                    nowChapter = v.getId();
                    nowVerse = 0;
                    showBibleBody();
                });
                rowLayout.addView(columnLayout);
                chapNbr++;
                if (chapNbr > chapMax)
                    break;
            }
            if (chapNbr > chapMax)
                break;
        }

        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);
    }

    private final int TABLE_SIZE = 600;

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

    private final int [] refFrm = new int[TABLE_SIZE];
    private final int [] refTo = new int[TABLE_SIZE];
    private final int [] referV = new int[TABLE_SIZE];
    private final String [] referS = new String[TABLE_SIZE];
    private int idxRefer;

    private final int [] paraFrm = new int[30];
    private final int [] paraTo = new int[30];
    private int idxPara;

    private final int [] agpFrm = new int[VERSE_SIZE];
    private final int [] agpTo = new int[VERSE_SIZE];
    private int idxAgp;

    private final int [] cevFrm = new int[VERSE_SIZE];
    private final int [] cevTo = new int[VERSE_SIZE];
    private int idxCev;

    private final int [] spaceFrm = new int[VERSE_SIZE];
    private final int [] spaceTo = new int[VERSE_SIZE];
    private int idxSpace;
    private boolean [] marked;

    private int versePtr;
    private int highLightF, highLightT;

    private int ptrBody;
    private StringBuilder bodyText;
    int paraSize;

    void showBibleBody() {
        screenMenu.build();
        new FrameScrollView();
        goBackProcs.push();
        String file2read = "bible/" + nowBible + "/" + nowChapter + ".txt";
        bibleTexts = fileRead.readBibleFile(file2read, false);
        if (bibleTexts == null) {
            utils.showSnackBar("성경 말씀 파일 없음 ", fullBibleNames[nowBible] + " " + nowChapter);
            return;
        }
        verMax = bibleTexts.length;

        marked = new boolean[verMax +1];
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
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        linearLayout.addView(textView);

        fBody.removeAllViewsInLayout();
        fBody.addView(scrollView);
        scrollView.post(() -> new Timer().schedule(new TimerTask() {
            public void run() {
                mActivity.runOnUiThread(() -> scrollView.scrollTo(0, textView.getBottom() * versePtr / ptrBody));
            }
        }, 30));
    }

    private SpannableString settleSpannableString(StringBuilder bodyText) {

        paraSize = BibleSize * 11 / 10;
        SpannableString ss = new SpannableString(bodyText);
        for (int i = 0; i < idxText; i++) {
            ss.setSpan(new AbsoluteSizeSpan(BibleSize, true), textF[i], textT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(textColorFore), textF[i], textT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ss.setSpan(new LineSpan(0), textF[i], textT[i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxKeyword; i++) {
            ss.setSpan(new keywordClick(keywords[i], keywordV[i]), keywordF[i], keywordT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(DictShowSize, true), keywordF[i], keywordT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(dicColorFore), keywordF[i], keywordT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxVerse; i++) {
            ss.setSpan(new verseSpan(nowBible, nowChapter, i+1), verseF[i], verseT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(textColorFore), verseF[i], verseT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxPara; i++) {
            ss.setSpan(new ForegroundColorSpan(paraColorFore), paraFrm[i], paraTo[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(paraSize, true), paraFrm[i], paraTo[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ss.setSpan(new UnderlineSpan(), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new StyleSpan(BOLD), paraFrm[i], paraTo[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxRefer; i++) {
            ss.setSpan(new AbsoluteSizeSpan(DictSize, true), refFrm[i], refTo[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new referSpan(referS[i], referV[i]), refFrm[i], refTo[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxCev; i++) {
            ss.setSpan(new ForegroundColorSpan(cevColorFore), cevFrm[i], cevTo[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxAgp; i++) {
            ss.setSpan(new ForegroundColorSpan(agpColorFore), agpFrm[i], agpTo[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxSpace; i++) {
            ss.setSpan(new AbsoluteSizeSpan(BibleLineSize /2, true), spaceFrm[i], spaceTo[i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (highLightF > 0)
            ss.setSpan(new BackgroundColorSpan(markColorBack), highLightF, highLightT, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private void makeBibleAllVerses() {
        versePtr = 0;
        highLightF = 0;
        for (int line = 0; line < verMax; line++) {
            if (line == (nowVerse-2))   // to show this verse no too top or above top
                versePtr = ptrBody;
            String str;
            String workLine = bibleTexts[line].trim() + "~";// "~" is end character
            int lenWorkLine = workLine.length() - 1;
            int idx = workLine.indexOf("`a");
            int idx2nd = workLine.indexOf("`c");
            String agpText = workLine.substring(idx + 2, idx2nd);
            if (agpText.length() == 0)
                agpText = " ";
            String cevText = workLine.substring(idx2nd + 2, lenWorkLine);
            if (cevText.length() == 0)
                cevText = " ";
            workLine = workLine.substring(0, idx).replace(" ", spacing);
            // to prevent word wrap
            String verseString = (line+1)+(marked[line+1] ? bookMarkChar :spacing);
            if (line < verMax -1) {
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
                    paraFrm[idxPara] = ptrBody;
                    paraTo[idxPara] = ptrBody + str.length() + 1;
                    idxPara++;
                    ptrBody += str.length() + 1;
                    spaceFrm[idxSpace] = ptrBody-1;
                    bodyText.append(" " + newLine);
                    spaceTo[idxSpace] = ptrBody + 3;
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
                    agpText = newLine + agpText.replace(" ", spacing);  // no word wrap
                    bodyText.append(agpText);
                    agpFrm[idxAgp] = ptrBody;
                    agpTo[idxAgp] = ptrBody + agpText.length();
                    idxAgp++;
                    ptrBody += agpText.length();
                }
                if (cevShow) {
                    cevText = newLine + cevText;    // cev allows word wrap
                    bodyText.append(cevText);
                    cevFrm[idxCev] = ptrBody;
                    cevTo[idxCev] = ptrBody + cevText.length();
                    idxCev++;
                    ptrBody += cevText.length();
                }
                textT[idxText] = ptrBody;
                idxText++;
                ptrBody++;
                bodyText.append(newLine);
                spaceFrm[idxSpace] = ptrBody;
                bodyText.append(" " + newLine);
                spaceTo[idxSpace] = ptrBody + 2;
                ptrBody += 2;
                idxSpace++;
            }
        }
        bodyText.append(newLine);
    }

    private int checkDicWords(String workLine, int verse) {
        int ptr;
        //   refer [_$44#17:26_]   dic  [_가나안 사람_] [_에덴~2_]
        ptr = workLine.indexOf("_]");
        String keyword = workLine.substring(2, ptr);
        if (keyword.charAt(0) == '$') {   // reference
            String bibShort = shortBibleNames[parseInt(keyword.substring(1, 3))];
            String showWord = " (" + bibShort + keyword.substring(4) + ") ";
                            // $01#12:34 -> (창12:34) 로 표시
            bodyText.append(showWord);
            refFrm[idxRefer] = ptrBody;
            refTo[idxRefer] = ptrBody + showWord.length();
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

    class LineSpan implements LineHeightSpan {
        private final int height;

        LineSpan(int height) {
            this.height = height;
        }

        @Override
        public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v,
                                 Paint.FontMetricsInt fm) {
            fm.bottom += height;
            fm.descent += height;
        }
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
            nowDic = key.replace(spacing," ");
            nowVerse = verse;
            new DictShow().show();
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

    private void makeRefer() {

        String refer = nowDic; // 41#4:18
        nowBible = parseInt(refer.substring(0,2));
        topTab = (nowBible < 40) ? TAB_OLD : TAB_NEW;
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
        nowVerse = 1;
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
        nowVerse = 1;
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