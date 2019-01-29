package com.example.trainway.trainway;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapterBtw  extends RecyclerView.Adapter<RecyclerViewAdapterBtw.Station1ViewHolder> {

    List<Station1> items;

    RecyclerViewAdapterBtw(List<Station1> items)
    {
        this.items = items;
    }

    public static class Station1ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView name,no,arr,dep,time,type;

        Station1ViewHolder(View itemView)
        {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.trainbtw_card);
            name = (TextView) itemView.findViewById(R.id.btwtrainname);
            no = (TextView) itemView.findViewById(R.id.btwtrainnumber);
            arr = (TextView) itemView.findViewById(R.id.btwtrainarr);
            dep = (TextView) itemView.findViewById(R.id.btwtraindept);
            time = (TextView) itemView.findViewById(R.id.btwtraintime);
            type = (TextView) itemView.findViewById(R.id.btwtraintype);
        }
    }

    @Override
    public Station1ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trainbtwcustom, viewGroup, false);
        Station1ViewHolder cvh = new Station1ViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterBtw.Station1ViewHolder holder, final int i) {

        holder.name.setText(items.get(i).name);
        holder.no.setText(items.get(i).no);
        holder.arr.setText(items.get(i).arr);
        holder.dep.setText(items.get(i).dep);
        holder.time.setText(items.get(i).time);
        holder.type.setText(items.get(i).type);
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
