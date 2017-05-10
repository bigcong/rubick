package com.cc.rubick.model;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.rubick.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by bigcong on 2017/2/28.
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private JsonArray collect;

    public BillAdapter(JsonArray myDataset) {
        collect = myDataset;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView amoutTV;
        public TextView goodsNameTV;
        public TextView paidTimeTV;
        public TextView sourceTV;
        public TextView counterpartyTV;

        Resources resources;

        public ViewHolder(View v) {
            super(v);
            resources = v.getResources();
            amoutTV = (TextView) v.findViewById(R.id.amountTV);
            goodsNameTV = (TextView) v.findViewById(R.id.goodsNameTV);
            paidTimeTV = (TextView) v.findViewById(R.id.paidTimeTV);
            sourceTV = (TextView) v.findViewById(R.id.sourceTV);
            counterpartyTV = (TextView) v.findViewById(R.id.counterpartytTV);

        }
    }


    @Override
    public BillAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bill_deatail_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        BillAdapter.ViewHolder vh = new BillAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(BillAdapter.ViewHolder holder, int position) {

        JsonObject r = collect.get(position).getAsJsonObject();
        String paymentType = r.get("paymentType").getAsString();
        if (paymentType.equals("支出")) {
            paymentType = "-";
        } else if (paymentType.equals("收入")) {
            paymentType = "+";

        }
        holder.amoutTV.setText(paymentType + r.get("amount").getAsFloat() + "");
        holder.goodsNameTV.setText(r.get("goodsName").getAsString());
        holder.paidTimeTV.setText(r.get("paidTime").getAsString());
        holder.sourceTV.setText(r.get("transationSource").getAsString());
        holder.counterpartyTV.setText(r.get("counterparty").getAsString());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return collect.size();
    }
}
