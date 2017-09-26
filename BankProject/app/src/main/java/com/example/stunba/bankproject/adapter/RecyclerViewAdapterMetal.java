package com.example.stunba.bankproject.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.source.entities.ActualAllIngot;
import com.example.stunba.bankproject.source.entities.MetalName;

import java.util.List;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 9/6/2017.
 */

public class RecyclerViewAdapterMetal extends RecyclerView.Adapter<RecyclerViewAdapterMetal.ViewHolder> {

    private List<ActualAllIngot> actualData;
    private Map<Integer, MetalName> names;
    private Context context;

    public void setActualData(List<ActualAllIngot> actualData) {
        this.actualData = actualData;
    }

    public void setNames(Map<Integer, MetalName> names) {
        this.names = names;
    }

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
            imageView = (ImageView) itemView.findViewById(R.id.imageViewMetal);
        }

    }


    public RecyclerViewAdapterMetal(Context context) {
        actualData = null;
        names = null;
        this.context = context;

    }

    @Override
    public RecyclerViewAdapterMetal.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item_metal, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterMetal.ViewHolder holder, int position) {
        holder.cv.setTag(position);
        String language = context.getResources().getConfiguration().locale.getLanguage();
        switch (language) {
            case "en":
                holder.metalName.setText(names.get(actualData.get(position).getMetalID()).getNameEng());
                break;
            case "ru":
                holder.metalName.setText(names.get(actualData.get(position).getMetalID()).getName());
                break;
            default:
                holder.metalName.setText(names.get(actualData.get(position).getMetalID()).getNameBel());
                break;
        }
        holder.nominal.setText(String.valueOf(actualData.get(position).getNominal()));
        holder.totalPrice.setText(String.valueOf(actualData.get(position).getBanksRubles()) + " BYN");
    }

    @Override
    public int getItemCount() {
        if (actualData != null) {
            return actualData.size();
        } else {
            return 0;
        }
    }
}
