package com.riopapa.dicbible;

import static com.riopapa.dicbible.Vars.fileRead;
import static com.riopapa.dicbible.Vars.menuColorFore;
import static com.riopapa.dicbible.Vars.nowDic;
import static com.riopapa.dicbible.Vars.BibleSize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DictAdapter extends  RecyclerView.Adapter<DictAdapter.MyViewHolder> implements Filterable {

    Context context;
    List<String> fullList;
    List<String> shortList;
    final File dicFolder;

    public DictAdapter(Context context, List<String> list, File dicFolder) {
        super();
        this.context = context;
        fullList = new ArrayList<>();
        fullList.addAll(list);
        shortList = new ArrayList<>();
        shortList.addAll(list);
        this.dicFolder = dicFolder;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dict_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (shortList.size() < position)
            return;
        holder.textView.setTextColor(menuColorFore);
        holder.textView.setTextSize(BibleSize);
        holder.textView.setTag(shortList.get(position));
        holder.textView.setOnClickListener(v -> {
            nowDic = v.getTag().toString();
            new DictShow().show();
        });
        String key = shortList.get(position);
        String s;
        if (key.startsWith("[")) {  // 성경 해설
            s = key + " 요약";
        } else if (key.startsWith("_인용")) {
            s = key;
        } else {
            String [] dicTexts = fileRead.readDicFile(key, true);
            s = dicTexts[0].substring(1);
        }
        holder.textView.setText(s);
    }

    @Override
    public int getItemCount() {
        return shortList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.dict_name);
        }
    }

    @Override
    public Filter getFilter() {
        shortList = new ArrayList<>();
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    shortList = new ArrayList<>();
                } else {
                    ArrayList<String> filteringList = new ArrayList<>();
                    for(String name : fullList) {
                        if(name.toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(name);
                        }
                    }
                    shortList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = shortList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                shortList = (List<String>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}