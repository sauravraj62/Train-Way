package com.example.trainway.trainway;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapterTrainAt extends RecyclerView.Adapter<RecyclerViewAdapterTrainAt.Station2ViewHolder> {

    List<Station2> items;

    RecyclerViewAdapterTrainAt(List<Station2> items)
    {
        this.items = items;
    }


    public static class Station2ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView name,no,arr,dep,from,to;

        Station2ViewHolder(View itemView)
        {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.trainbtw_card);
            name = (TextView) itemView.findViewById(R.id.btwtrainname);
            no = (TextView) itemView.findViewById(R.id.btwtrainnumber);
            arr = (TextView) itemView.findViewById(R.id.btwtrainarr);
            dep = (TextView) itemView.findViewById(R.id.btwtraindept);
            from = (TextView) itemView.findViewById(R.id.btwtraintime);
            to = (TextView) itemView.findViewById(R.id.btwtraintype);
        }
    }

    @Override
    public RecyclerViewAdapterTrainAt.Station2ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trainbtwcustom, viewGroup, false);
        RecyclerViewAdapterTrainAt.Station2ViewHolder cvh = new RecyclerViewAdapterTrainAt.Station2ViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterTrainAt.Station2ViewHolder holder, final int i) {

        holder.name.setText(items.get(i).name);
        holder.no.setText(items.get(i).no);
        holder.arr.setText(items.get(i).arr);
        holder.dep.setText(items.get(i).dep);
        holder.from.setText(items.get(i).from);
        holder.to.setText(items.get(i).to);
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
