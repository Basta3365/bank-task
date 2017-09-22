package com.example.stunba.bankproject.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.source.entities.ActualRate;

import java.util.List;

/**
 * Created by Kseniya_Bastun on 9/8/2017.
 */

public class RecyclerViewAdapterFavorites extends RecyclerView.Adapter<RecyclerViewAdapterFavorites.ViewHolder> {
    private List<ActualRate> favorites;
    private OnTaskCompleted.DeleteFavorite mListener;

    public void setFavorites(List<ActualRate> favorites) {
        this.favorites = favorites;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView favoriteName;
        private ImageButton delete;

        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view_favorites);
            favoriteName = (TextView) itemView.findViewById(R.id.favoriteName);
            delete = (ImageButton) itemView.findViewById(R.id.deleteFavorite);
        }
    }

    public RecyclerViewAdapterFavorites(OnTaskCompleted.DeleteFavorite loadComplete) {
        favorites = null;
        mListener = loadComplete;

    }

    @Override
    public RecyclerViewAdapterFavorites.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item_favotites, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.cv.setTag(position);
        holder.favoriteName.setText(favorites.get(position).getCurAbbreviation());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteItem(favorites.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (favorites != null) {
            return favorites.size();
        } else {
            return 0;
        }
    }

}
