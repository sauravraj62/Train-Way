package com.example.trainway.trainway;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapterFav extends RecyclerView.Adapter<RecyclerViewAdapterFav.TrainsViewHolder>{

    List<Favtrains> trains;

    RecyclerViewAdapterFav(List<Favtrains> trains)
    {
        this.trains = trains;
    }

    public static class TrainsViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView notxt;

        TrainsViewHolder(View itemView)
        {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_fav);
            notxt = (TextView) itemView.findViewById(R.id.favtrainnumber);
        }
    }

    @Override
    public RecyclerViewAdapterFav.TrainsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fav_custom, viewGroup, false);
        RecyclerViewAdapterFav.TrainsViewHolder cvh = new RecyclerViewAdapterFav.TrainsViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterFav.TrainsViewHolder holder, final int i) {

        holder.notxt.setText(trains.get(i).Number);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return trains.size();
    }
}
