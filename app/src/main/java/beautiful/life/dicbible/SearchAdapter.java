package beautiful.life.dicbible;

import static beautiful.life.dicbible.SetActivity.setLayoutBackGround;
import static beautiful.life.dicbible.Vars.TAB_NEW;
import static beautiful.life.dicbible.Vars.TAB_OLD;
import static beautiful.life.dicbible.Vars.agpColorFore;
import static beautiful.life.dicbible.Vars.tabBible;
import static beautiful.life.dicbible.Vars.cevColorFore;
import static beautiful.life.dicbible.Vars.keyText;
import static beautiful.life.dicbible.Vars.menuColorFore;
import static beautiful.life.dicbible.Vars.nowBible;
import static beautiful.life.dicbible.Vars.nowChapter;
import static beautiful.life.dicbible.Vars.nowVerse;
import static beautiful.life.dicbible.Vars.searchActivity;
import static beautiful.life.dicbible.Vars.searcheds;
import static beautiful.life.dicbible.Vars.shortBibleNames;
import static beautiful.life.dicbible.Vars.BibleSize;
import static beautiful.life.dicbible.Vars.topTab;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import beautiful.life.dicbible.model.Searched;

class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>  {

    @Override
    public int getItemCount() {
        return  (searcheds == null)? 0:searcheds.size();
    }

    @NonNull
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
            tvVerse.setTextSize((float) BibleSize*7/10);
            tvVerse.setTextColor(menuColorFore);
            tvText.setTextSize((float) BibleSize *9/10);
            tvText.setTextColor(menuColorFore);
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
            tabBible.showBibleBody();
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int pos) {

        Searched searched = searcheds.get(pos);
        String s = shortBibleNames[searched.bible]+"\n"+searched.chapter+":"+searched.verse;
        holder.tvVerse.setText(s);

        String [] kwd = keyText.trim().split("\\^");
        if (kwd.length > 1) {
            kwd[0] = kwd[0].trim();
            kwd[1] = kwd[1].trim();
        }
        s = searched.text;
        SpannableString ss = new SpannableString(s);
        ss.setSpan(new ForegroundColorSpan(agpColorFore & 0x80FFFFFF), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss = markVerse(ss, s);
        ss = markColored(ss, s, kwd[0]);
        if (kwd.length > 1) {
            ss = markColored(ss, s, kwd[1]);
        }
        holder.tvText.setText(ss);
        holder.tvText.setBackgroundResource(R.drawable.cell_border);
    }
    private SpannableString markVerse(SpannableString ss, String line) {
        int p = line.indexOf("[");
        int rtn = line.indexOf("\n", p+2);
        if (rtn == -1)
            rtn = line.length();
        ss.setSpan(new ForegroundColorSpan(menuColorFore), p, rtn, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private SpannableString markColored(SpannableString ss, String l, String sKey) {
        int sPos = l.indexOf(sKey);
        while (sPos > 0) {
            int ePos = sPos + sKey.length();
            ss.setSpan(new ForegroundColorSpan(cevColorFore), sPos, ePos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), sPos, ePos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new UnderlineSpan(), sPos, ePos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sPos = l.indexOf(sKey, ePos);
        }
        return ss;
    }

}