package com.example.playitsafe.ui.findplace;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.playitsafe.PlaceSafe;
import com.example.playitsafe.R;
import java.util.ArrayList;

public class FindPlaceAdapter extends RecyclerView.Adapter<FindPlaceAdapter.PlaceViewHolder> {
private Context context;
private ArrayList<PlaceSafe> psafearray = new ArrayList<>();


//1st item selected as default 0, -1 no item selected
private int selected = -1;

    public FindPlaceAdapter(Context context, ArrayList<PlaceSafe> ps) {
        this.context = context;
        this.psafearray = ps;
    }

    public void setPlace(ArrayList<PlaceSafe> ps) {
        this.psafearray= new ArrayList<>();
        this.psafearray = ps;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_place, viewGroup, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder placeViewHolder, int position) {
        placeViewHolder.bind(psafearray.get(position));
    }

    @Override
    public int getItemCount() {
        return psafearray.size();
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder {

        private TextView pstext;
        private Button psbtn;

        PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            pstext = itemView.findViewById(R.id.itemList);
            psbtn = itemView.findViewById(R.id.item_button);
        }

        void bind(final PlaceSafe ps) {

            pstext.setText(ps.getResult());

            psbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                     //if selected in the item list display toast
                        notifyItemChanged(selected);
                        selected= getAdapterPosition();
                        String msg = getSelected().getResult();
                        //pass getSelected.name and get.Selected.address to get review screen
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();


                }
            });
        }
    }

    public PlaceSafe getSelected() {
        if (selected != -1) {
            return psafearray.get(selected);
        }
        return null;
    }

}