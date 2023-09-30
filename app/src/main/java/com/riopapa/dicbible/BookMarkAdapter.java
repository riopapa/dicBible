package com.riopapa.dicbible;

import static com.riopapa.dicbible.Vars.TAB_NEW;
import static com.riopapa.dicbible.Vars.TAB_OLD;
import static com.riopapa.dicbible.Vars.tabBible;
import static com.riopapa.dicbible.Vars.bookMarkAdapter;
import static com.riopapa.dicbible.Vars.bookMarks;
import static com.riopapa.dicbible.Vars.fullBibleNames;
import static com.riopapa.dicbible.Vars.menuColorFore;
import static com.riopapa.dicbible.Vars.nowBible;
import static com.riopapa.dicbible.Vars.nowChapter;
import static com.riopapa.dicbible.Vars.nowHymn;
import static com.riopapa.dicbible.Vars.nowVerse;
import static com.riopapa.dicbible.Vars.setActivity;
import static com.riopapa.dicbible.Vars.topTab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.riopapa.dicbible.model.BookMark;

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

        ViewHolder(final View itemView) {
            super(itemView);

            tvBibleChapter = itemView.findViewById(R.id.bibleChapter);
            tvDateTime = itemView.findViewById(R.id.dateTime);

            tvBibleChapter.setOnClickListener(view -> {
                pos = getAbsoluteAdapterPosition();
                jump2BookMark();
            });

            tvDateTime.setOnClickListener(view -> {
                pos = getAbsoluteAdapterPosition();
                jump2BookMark();
            });
        }
    }

    private static void jump2BookMark() {
        final BookMark bookMark = bookMarks.get(pos);
        AlertDialog.Builder builder = new AlertDialog.Builder(setActivity);
        builder.setTitle("성경 책갈피");
        String s = fullBibleNames[bookMark.bible] + " " + bookMark.chapter+" 장";
        if (bookMark.verse > 0)
            s += " "+bookMark.verse+" 절";
        builder.setMessage(s);
        builder.setIcon(R.mipmap.icon_riopapa_face);
        builder.setPositiveButton(s+" 로 이동",
            (dialog, which) -> {
                nowBible = bookMark.bible;
                nowChapter = bookMark.chapter;
                nowVerse = bookMark.verse;
                nowHymn = 0;
                topTab = (nowBible < 40) ? TAB_OLD : TAB_NEW;
                if (tabBible == null)
                    tabBible = new TabBible();
                tabBible.showBibleBody();
                setActivity.finish();
                setActivity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
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
        holder.tvDateTime.setTextColor(menuColorFore);
        holder.tvBibleChapter.setTextColor(menuColorFore);
    }
}