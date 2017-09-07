package com.example.stunba.bankproject.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.source.entities.ActualAllIngot;

import java.util.List;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 9/6/2017.
 */

public class RecyclerViewAdapterMetal  extends RecyclerView.Adapter<RecyclerViewAdapterMetal.ViewHolder> {

    private List<ActualAllIngot> data;
    private Map<Integer,String> names;


    static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView metalName;
        private TextView nominal;
        private TextView totalPrice;
        private ImageView imageView;
        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            metalName = (TextView) itemView.findViewById(R.id.metalName);
            nominal = (TextView) itemView.findViewById(R.id.nominal);
            totalPrice = (TextView) itemView.findViewById(R.id.price);
            imageView= (ImageView) itemView.findViewById(R.id.imageViewMetal);
        }

    }
    public RecyclerViewAdapterMetal(List<ActualAllIngot> actualAllIngots, Map<Integer,String> names) {
        data = actualAllIngots;
        this.names=names;

    }

    public RecyclerViewAdapterMetal() {
        data = null;
        names=null;

    }

    @Override
    public RecyclerViewAdapterMetal.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterMetal.ViewHolder holder, int position) {
        holder.cv.setTag(position);
        switch (data.get(position).getMetalID()){
            case 0: holder.imageView.setImageResource(R.drawable.usd);
                break;
            case 1: holder.imageView.setImageResource(R.drawable.usd);
                break;
            case 2: holder.imageView.setImageResource(R.drawable.usd);
                break;
            case 3: holder.imageView.setImageResource(R.drawable.usd);
                break;
        }
        holder.metalName.setText(names.get(data.get(position).getMetalID()));
        holder.nominal.setText(String.valueOf(data.get(position).getNominal()));
        holder.totalPrice.setText(String.valueOf(data.get(position).getBanksRubles())+" BYN");
    }

    @Override
    public int getItemCount() {
        if(data!=null) {
            return data.size();
        }else {
            return 0;
        }
    }
}
