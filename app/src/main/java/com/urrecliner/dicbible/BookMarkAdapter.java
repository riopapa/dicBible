package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.TAB_NEW;
import static com.urrecliner.dicbible.Vars.TAB_OLD;
import static com.urrecliner.dicbible.Vars.bookMarkAdapter;
import static com.urrecliner.dicbible.Vars.bookMarks;
import static com.urrecliner.dicbible.Vars.fullBibleNames;
import static com.urrecliner.dicbible.Vars.bibleMake;
import static com.urrecliner.dicbible.Vars.menuColorFore;
import static com.urrecliner.dicbible.Vars.nowBible;
import static com.urrecliner.dicbible.Vars.nowChapter;
import static com.urrecliner.dicbible.Vars.nowHymn;
import static com.urrecliner.dicbible.Vars.nowVerse;
import static com.urrecliner.dicbible.Vars.setActivity;
import static com.urrecliner.dicbible.Vars.topTab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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
        builder.setTitle("?????? ?????????");
        String s = fullBibleNames[bookMark.bible] + " " + bookMark.chapter+" ???";
        if (bookMark.verse > 0)
            s += " "+bookMark.verse+" ???";
        builder.setMessage(s);
        builder.setIcon(R.mipmap.icon_riopapa_face);
        builder.setPositiveButton(s+" ??? ??????",
            (dialog, which) -> {
                nowBible = bookMark.bible;
                nowChapter = bookMark.chapter;
                nowVerse = bookMark.verse;
                nowHymn = 0;
                topTab = (nowBible < 40) ? TAB_OLD : TAB_NEW;
                if (bibleMake == null)
                    bibleMake = new BibleMake();
                bibleMake.showBibleBody();
                setActivity.finish();
                setActivity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                });
        builder.setNegativeButton("??????",
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