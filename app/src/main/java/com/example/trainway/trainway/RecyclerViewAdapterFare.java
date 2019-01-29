package com.example.trainway.trainway;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapterFare extends RecyclerView.Adapter<RecyclerViewAdapterFare.ItemsViewHolder>  {

    List<Items1> items;

    RecyclerViewAdapterFare(List<Items1> items)
    {
        this.items = items;
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView descrip;

        ItemsViewHolder(View itemView)
        {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView_fare);
            descrip = (TextView) itemView.findViewById(R.id.farecardtxt);
        }
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fare_custom, viewGroup, false);
        ItemsViewHolder cvh = new ItemsViewHolder(view);
        return cvh;
    }


    @Override
    public void onBindViewHolder(RecyclerViewAdapterFare.ItemsViewHolder holder, final int i) {

        holder.descrip.setText(items.get(i).description);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
