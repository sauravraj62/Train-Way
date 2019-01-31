package com.example.trainway.trainway;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapterBtw  extends RecyclerView.Adapter<RecyclerViewAdapterBtw.Station1ViewHolder> {

//    private static SharedPreferences sharedPreferences;

    List<Station1> items;
    RecyclerViewAdapterBtw(List<Station1> items)
    {
        this.items = items;
    }

    public static class Station1ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView name,no,arr,dep,time,type;
        ImageView fav;

        Station1ViewHolder(final View itemView)
        {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.trainbtw_card);
            name = (TextView) itemView.findViewById(R.id.btwtrainname);
            no = (TextView) itemView.findViewById(R.id.btwtrainnumber);
            arr = (TextView) itemView.findViewById(R.id.btwtrainarr);
            dep = (TextView) itemView.findViewById(R.id.btwtraindept);
            time = (TextView) itemView.findViewById(R.id.btwtraintime);
            type = (TextView) itemView.findViewById(R.id.btwtraintype);
            fav = (ImageView) itemView.findViewById(R.id.favimg1);

        }

    }

    @Override
    public Station1ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trainbtwcustom, viewGroup, false);
        Station1ViewHolder cvh = new Station1ViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapterBtw.Station1ViewHolder holder, final int i) {

        holder.name.setText(items.get(i).name);
        holder.no.setText(items.get(i).no);
        holder.arr.setText(items.get(i).arr);
        holder.dep.setText(items.get(i).dep);
        holder.time.setText(items.get(i).time);
        holder.type.setText(items.get(i).type);
        holder.fav.setImageResource(R.drawable.heart1);

        final String tnumber;
        tnumber = holder.name.getText().toString();
        final SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("favTrains",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        String Curr;
        Curr = sharedPreferences.getString(tnumber,"No");
        if(Curr.equals("No"))
        {
            holder.fav.setImageResource(R.drawable.heart1);
        }
        else
        {
            holder.fav.setImageResource(R.drawable.heart2);
        }


        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//
//                    SharedPreferences.Editor editor =  itemView.getContext().getSharedPreferences("favTrains", Context.MODE_PRIVATE).edit();
//


                String Check;
                Check = sharedPreferences.getString(tnumber,"No");

                if(Check.equals("Yes"))
                {
                    editor.putString(tnumber,"No");
                    editor.commit();
                    holder.fav.setImageResource(R.drawable.heart1);
                    Toast.makeText(holder.itemView.getContext(),"Removed to Favorites",Toast.LENGTH_LONG).show();

                }
                else
                {
//                    Toast.makeText(holder.itemView.getContext(),tnumber,Toast.LENGTH_LONG).show();
                    editor.putString(tnumber,"Yes");
                    editor.commit();
                    holder.fav.setImageResource(R.drawable.heart2);
                    Toast.makeText(holder.itemView.getContext(),"Added From Favorites",Toast.LENGTH_LONG).show();

                }
//                    editor.putString("name", "Elena");
//                    editor.putInt("idName", 12);
//                    editor.apply();

            }
        });
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
