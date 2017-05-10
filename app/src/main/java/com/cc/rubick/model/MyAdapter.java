package com.cc.rubick.model;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.rubick.R;

import java.util.List;

/**
 * Created by bigcong on 13/02/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<RecordEntity> collect;

    public MyAdapter(List<RecordEntity> myDataset) {
        collect = myDataset;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public TextView recordNameTV;
        public ImageView typeTV;
        public TextView durationTV;
        public TextView dateTv;
        Resources resources;

        public ViewHolder(View v) {
            super(v);
            resources = v.getResources();
            mTextView = (TextView) v.findViewById(R.id.my_text_view);
            recordNameTV = (TextView) v.findViewById(R.id.record_name);
            typeTV = (ImageView) v.findViewById(R.id.record_type);
            durationTV = (TextView) v.findViewById(R.id.record_duration);
            dateTv = (TextView) v.findViewById(R.id.record_dateTime);
        }
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.call_records_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        RecordEntity r = collect.get(position);
        holder.mTextView.setText(r.getNumber());
        String name = r.getName();
        if (name == null) {
            name = "陌生人";
        }
        holder.recordNameTV.setText(name);
        Long duration = r.getDuration();
        Long minute = duration % 60;
        Long sec = duration / 60;
        String durationTxt = sec + "'" + minute + "\"";
        if (sec == 0) {
            durationTxt = minute + "\"";
        }
        holder.durationTV.setText(durationTxt);


        Drawable type = holder.resources.getDrawable(R.drawable.ic_call_in);
        if (r.getType() == 1) {
            type = holder.resources.getDrawable(R.drawable.ic_call_in);
        } else if (r.getType() == 2) {
            type = holder.resources.getDrawable(R.drawable.ic_call_out);
        } else if (r.getType() == 3) {
            type = holder.resources.getDrawable(R.drawable.ic_call_missed);
        }
        holder.typeTV.setImageDrawable(type);
        holder.dateTv.setText(r.getDate().substring(0, r.getDate().length() - 2));


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return collect.size();
    }
}
