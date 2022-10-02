package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.TAB_MODE_NEW;
import static com.urrecliner.dicbible.Vars.TAB_MODE_OLD;
import static com.urrecliner.dicbible.Vars.bookMarkAdapter;
import static com.urrecliner.dicbible.Vars.bookMarks;
import static com.urrecliner.dicbible.Vars.fullBibleNames;
import static com.urrecliner.dicbible.Vars.handlePrefs;
import static com.urrecliner.dicbible.Vars.history;
import static com.urrecliner.dicbible.Vars.mContext;
import static com.urrecliner.dicbible.Vars.makeBible;
import static com.urrecliner.dicbible.Vars.nowBible;
import static com.urrecliner.dicbible.Vars.nowChapter;
import static com.urrecliner.dicbible.Vars.nowHymn;
import static com.urrecliner.dicbible.Vars.nowVerse;
import static com.urrecliner.dicbible.Vars.settingActivity;
import static com.urrecliner.dicbible.Vars.topTab;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.urrecliner.dicbible.model.BookMark;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.ViewHolder>  {

    @Override
    public int getItemCount() {
        return bookMarks.size();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_mark, parent, false);
        return new ViewHolder(view);
    }

    private static int pos;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvBibleChapter, tvDateTime;
        View lo = itemView.findViewById(R.id.bookMark);

        ViewHolder(final View itemView) {
            super(itemView);

            tvBibleChapter = itemView.findViewById(R.id.bibleChapter);
            tvDateTime = itemView.findViewById(R.id.dateTime);

            tvBibleChapter.setOnClickListener(view -> {
                pos = getAbsoluteAdapterPosition();
                jump2BookMark();
            });
            tvBibleChapter.setOnLongClickListener(view -> {
                pos = getAbsoluteAdapterPosition();
                saveOrNot();
                return true;
            });

            tvDateTime.setOnClickListener(view -> {
                pos = getAbsoluteAdapterPosition();
                jump2BookMark();
            });
            tvDateTime.setOnLongClickListener(view -> {
                pos = getAbsoluteAdapterPosition();
                saveOrNot();
                return true;
            });
        }
    }

    private static void saveOrNot() {
        BookMark bookMark = bookMarks.get(pos);
        bookMark.setSave(!bookMark.isSave());
        bookMarks.set(pos, bookMark);
        handlePrefs.saveArray("bookMark", bookMarks);
        bookMarkAdapter.notifyItemChanged(pos);
    }

    private static void jump2BookMark() {
        final BookMark bookMark = bookMarks.get(pos);
        AlertDialog.Builder builder = new AlertDialog.Builder(settingActivity);
        builder.setTitle("성경 책갈피");
        String s = fullBibleNames[bookMark.bible] + " " + bookMark.chapter+" 장";
        if (bookMark.verse > 0)
            s += " "+bookMark.verse+" 절";
        builder.setMessage(s);
        builder.setPositiveButton(s+" 로 이동",
                (dialog, which) -> {
                    history.push();
                    nowBible = bookMark.bible;
                    nowChapter = bookMark.chapter;
                    nowVerse = bookMark.verse;
                    nowHymn = 0;
                    topTab = (nowBible < 40) ? TAB_MODE_OLD : TAB_MODE_NEW;
                    if (makeBible == null)
                        makeBible = new MakeBible();
                    makeBible.showBibleBody();
                    settingActivity.finish();
                    settingActivity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                });
        builder.setNegativeButton("삭제",
                (dialog, which) -> {
                    bookMarks.remove(pos);
                    bookMarkAdapter.notifyItemRemoved(pos);
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int pos) {

        final SimpleDateFormat sdfDate = new SimpleDateFormat("yy/MM/dd HH:mm", Locale.US);
        String s;
        BookMark bookMark = bookMarks.get(pos);
        s = fullBibleNames[bookMark.bible] + " " + bookMark.chapter;
        if (bookMark.verse> 0)
            s += ":"+bookMark.verse;
        holder.tvBibleChapter.setText(s);
        s = sdfDate.format(bookMark.when);
        holder.tvDateTime.setText(s);
        if (bookMark.isSave()) {
            holder.tvBibleChapter.setTypeface(null, Typeface.BOLD_ITALIC);
            holder.tvDateTime.setTypeface(null, Typeface.BOLD_ITALIC);
        }
        else {
            holder.tvBibleChapter.setTypeface(null, Typeface.NORMAL);
            holder.tvDateTime.setTypeface(null, Typeface.NORMAL);
        }
        int grayed = 200 * pos / (bookMarks.size()+1);
        holder.lo.setBackgroundColor(ContextCompat.getColor(mContext,R.color.screenColorBack) - grayed - grayed * 256 - grayed * 256 * 256);
    }
}