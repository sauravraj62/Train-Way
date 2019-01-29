package com.example.trainway.trainway;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.StationViewHolder> {


    List<Station> station;

    RecyclerViewAdapter2(List<Station> station)
    {
        this.station = station;
    }

    public static class StationViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView name,arr,dep,dist;

        StationViewHolder(View itemView)
        {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView_trainroute);
            name = (TextView) itemView.findViewById(R.id.routecardtxt);
            arr = (TextView) itemView.findViewById(R.id.routecardarr);
            dep = (TextView) itemView.findViewById(R.id.routecarddep);
            dist = (TextView) itemView.findViewById(R.id.routecarddistance);
        }
    }
//    @NonNull
    @Override
    public StationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.train_route_custom, viewGroup, false);
        StationViewHolder cvh = new StationViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(StationViewHolder holder,final int i) {

        holder.name.setText(station.get(i).station_name);
        holder.arr.setText(station.get(i).arrival);
        holder.dep.setText(station.get(i).depart);
        holder.dist.setText(station.get(i).distance);

    }



    @Override
	    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
	    public int getItemCount() {
        return station.size();
    }
}
