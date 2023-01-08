package com.urrecliner.dicbible;

import static com.urrecliner.dicbible.Vars.menuColorFore;
import static com.urrecliner.dicbible.Vars.nowDic;
import static com.urrecliner.dicbible.Vars.textSizeScript;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DictAdapter extends  RecyclerView.Adapter<DictAdapter.MyViewHolder> implements Filterable {

    Context context;
    List<String> unFilteredlist;
    List<String> filteredList;
    final File dicFolder;

    public DictAdapter(Context context, List<String> list, File dicFolder) {
        super();
        this.context = context;
        unFilteredlist = new ArrayList<>();
        unFilteredlist.addAll(list);
        filteredList = new ArrayList<>();
        filteredList.addAll(list);
        this.dicFolder = dicFolder;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dict_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setTextColor(menuColorFore);
        holder.textView.setTextSize(textSizeScript);
        holder.textView.setTag(filteredList.get(position));
        holder.textView.setOnClickListener(v -> {
            nowDic = v.getTag().toString();
            new DictKey().show();
        });
        String key = filteredList.get(position);
        if (key.startsWith("[")) {  // 성경 해설
            int pos = key.indexOf("#");
            String bible = key.substring(0,pos);
            holder.textView.setText(bible+" 요약");
        } else {
            String [] dicTexts = FileRead.readDicFile(key, true);
            String s = dicTexts[0].substring(1);
            holder.textView.setText(s);
        }

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.dict_name);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    filteredList = unFilteredlist;
                } else {
                    ArrayList<String> filteringList = new ArrayList<>();
                    for(String name : unFilteredlist) {
                        if(name.toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(name);
                        }
                    }
                    filteredList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<String>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}