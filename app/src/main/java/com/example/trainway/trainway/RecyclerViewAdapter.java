package com.example.trainway.trainway;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ClubViewHolder> {
    private Context con;
    List<Items> clubs;

    RecyclerViewAdapter(List<Items> clubs){
        this.clubs = clubs;
    }


    public static class ClubViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView clubName;
        ImageView clubLogo;

        ClubViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardView);
            clubName = (TextView)itemView.findViewById(R.id.club_name);
            clubLogo = (ImageView)itemView.findViewById(R.id.logo);
        }
    }

    @Override
    public ClubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout, parent, false);
        ClubViewHolder cvh = new ClubViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(ClubViewHolder holder, final int position) {

        holder.clubName.setText(clubs.get(position).name);
        holder.clubLogo.setImageResource(clubs.get(position).logoId);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent intent;
                int Pos = position;
                con = v.getContext();
                if(Pos==0)
                {
                    intent = new Intent(con,Pnr_Details_Entry.class);
                }
                else if(Pos==1)
                {
                    intent = new Intent(con,TrainRouteDetails.class);
                }
                else if(Pos==2)
                {
                    intent = new Intent(con,FareQueryDetails.class);
                }
                else if(Pos==3)
                {
                    intent = new Intent(con,TrainBtwStations.class);
                }
                else if(Pos==4)
                {
                    intent = new Intent(con,TrainsAtStation.class);
                }
                else if(Pos==5)
                {
                    intent = new Intent(con,LiveStatus.class);
                }
                else
                {
                    intent = new Intent(con,FavoriteTrains.class);
                }

                con.startActivity(intent);


            }
        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }
}