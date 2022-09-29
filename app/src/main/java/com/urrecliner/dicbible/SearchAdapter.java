package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.TAB_MODE_NEW;
import static com.urrecliner.dicbible.Vars.TAB_MODE_OLD;
import static com.urrecliner.dicbible.Vars.history;
import static com.urrecliner.dicbible.Vars.makeBible;
import static com.urrecliner.dicbible.Vars.nowBible;
import static com.urrecliner.dicbible.Vars.nowChapter;
import static com.urrecliner.dicbible.Vars.nowVerse;
import static com.urrecliner.dicbible.Vars.searchActivity;
import static com.urrecliner.dicbible.Vars.searchText;
import static com.urrecliner.dicbible.Vars.searcheds;
import static com.urrecliner.dicbible.Vars.shortBibleNames;
import static com.urrecliner.dicbible.Vars.textSizeBibleBody;
import static com.urrecliner.dicbible.Vars.topTab;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.urrecliner.dicbible.model.Searched;

class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>  {

    @Override
    public int getItemCount() {
        return  (searcheds == null)? 0:searcheds.size();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvVerse, tvText;
        ViewHolder(final View itemView) {
            super(itemView);

            tvVerse = (TextView) itemView.findViewById(R.id.result_Verse);
            tvText = (TextView) itemView.findViewById(R.id.result_Text);
            tvText.setTextSize(textSizeBibleBody*2/7);

            View view = itemView.findViewById(R.id.search_item);
            view.setOnClickListener(view1 -> jump2Searched(getAdapterPosition()));
        }

        private static void jump2Searched(int pos) {

            Searched searchResult = searcheds.get(pos);
            nowBible = searchResult.bible;
            nowChapter = searchResult.chapter;
            nowVerse = searchResult.verse;
            topTab = (nowBible < 40) ? TAB_MODE_OLD : TAB_MODE_NEW;
            searchActivity.finish();
            searchActivity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            history.push();
            history.push();
            makeBible.showBibleBody();
//            mActivity.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    makeBible.makeBibleBody();
//                }
//            });
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int pos) {

        Searched searched = searcheds.get(pos);
//        holder.tvBible.setText(shortBibleNames[searched.getBible()]);
        String s = shortBibleNames[searched.bible]+"\n"+searched.chapter+":"+searched.verse;
        holder.tvVerse.setText(s);
        s = searched.text;
        String[] verses = s.split("\n");
        SpannableString ss = new SpannableString(s);
        int sLen = verses[0].length();
        int tLen = 0;
        tLen += sLen + 1;
        sLen = verses[1].length();
        ss.setSpan(new BackgroundColorSpan(0x3845B2D9), tLen, tLen + sLen, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int kPos = s.indexOf(searchText);
        while (kPos > 0) {
            ss.setSpan(new BackgroundColorSpan(0xFF00FF00), kPos, kPos + searchText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            kPos = s.indexOf(searchText, kPos+2);
        }

        holder.tvText.setText(ss);

    }

}