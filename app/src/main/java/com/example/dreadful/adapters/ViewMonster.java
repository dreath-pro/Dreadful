package com.example.dreadful.adapters;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dreadful.R;
import com.example.dreadful.models.Player;

import java.util.ArrayList;

public class ViewMonster extends RecyclerView.Adapter<ViewMonster.MyViewHolder> {
    private Context context;
    private ArrayList<Player> playerList;
    private int enemySelectedMonster;

    public ViewMonster(Context context, ArrayList<Player> playerList, int enemySelectedMonster) {
        this.context = context;
        this.playerList = playerList;
        this.enemySelectedMonster = enemySelectedMonster;
    }

    @NonNull
    @Override
    public ViewMonster.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.view_monster, parent, false);
        return new ViewMonster.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMonster.MyViewHolder holder, int position) {
        holder.monsterImage.setImageResource(playerList.get(position).getImage());
        holder.monsterName.setText(playerList.get(position).getName());

        if (position == enemySelectedMonster) {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0f);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.imageContainer.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red));
            holder.imageContainer.getBackground().setColorFilter(filter);
        }
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView imageContainer;
        ImageView monsterImage;
        TextView monsterName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageContainer = itemView.findViewById(R.id.imageContainer);
            monsterImage = itemView.findViewById(R.id.monsterImage);
            monsterName = itemView.findViewById(R.id.monsterName);
        }
    }
}
