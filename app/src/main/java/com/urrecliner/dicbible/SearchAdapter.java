package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.SetActivity.setLayoutBackGround;
import static com.urrecliner.dicbible.Vars.TAB_NEW;
import static com.urrecliner.dicbible.Vars.TAB_OLD;
import static com.urrecliner.dicbible.Vars.agpColorFore;
import static com.urrecliner.dicbible.Vars.bibleMake;
import static com.urrecliner.dicbible.Vars.keyText;
import static com.urrecliner.dicbible.Vars.menuColorFore;
import static com.urrecliner.dicbible.Vars.nowBible;
import static com.urrecliner.dicbible.Vars.nowChapter;
import static com.urrecliner.dicbible.Vars.nowVerse;
import static com.urrecliner.dicbible.Vars.searchActivity;
import static com.urrecliner.dicbible.Vars.searcheds;
import static com.urrecliner.dicbible.Vars.shortBibleNames;
import static com.urrecliner.dicbible.Vars.textSizeScript;
import static com.urrecliner.dicbible.Vars.topTab;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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

            setLayoutBackGround(itemView.findViewById(R.id.search_item));
            tvVerse = itemView.findViewById(R.id.result_Verse);
            tvText = itemView.findViewById(R.id.result_Text);
            tvVerse.setTextSize(textSizeScript);
            tvVerse.setTextColor(menuColorFore);
            tvText.setTextSize((float)textSizeScript*9/10);
            tvText.setTextColor(menuColorFore);

            //            tvText.setTextSize(textSizeScript *2/7);

            View view = itemView.findViewById(R.id.search_item);
            view.setOnClickListener(view1 -> jump2Searched(getAbsoluteAdapterPosition()));
        }

        private static void jump2Searched(int pos) {

            Searched searchResult = searcheds.get(pos);
            nowBible = searchResult.bible;
            nowChapter = searchResult.chapter;
            nowVerse = searchResult.verse;
            topTab = (nowBible < 40) ? TAB_OLD : TAB_NEW;
            searchActivity.finish();
            searchActivity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            bibleMake.showBibleBody();
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int pos) {

        Searched searched = searcheds.get(pos);
        String s = shortBibleNames[searched.bible]+"\n"+searched.chapter+":"+searched.verse;
        holder.tvVerse.setText(s);

        s = searched.text.replace(" ", "\u00A0"); // to prevent word wrap
        String [] kwd = keyText.trim().split(" ");
//        String sKey = keyText.replace(" ", "\u00A0");
        SpannableString ss = new SpannableString(s);
//        String [] lines = s.split("\n");
        ss.setSpan(new ForegroundColorSpan(menuColorFore), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss = markUp(ss, s, kwd[0]);
        if (kwd.length > 1) {
            ss = markUp(ss, s, kwd[1]);
        }
        holder.tvText.setText(ss);
        holder.tvText.setBackgroundResource(R.drawable.cell_border);
    }

    private SpannableString markUp(SpannableString ss, String l, String sKey) {
        int sPos = l.indexOf(sKey);
        while (sPos > 0) {
            ss.setSpan(new ForegroundColorSpan(agpColorFore), sPos, sPos + sKey.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sPos = l.indexOf(sKey, sPos+2);
        }
        return ss;
    }

}