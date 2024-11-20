package com.example.dreadful.adapters;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dreadful.R;
import com.example.dreadful.models.Player;

import java.util.ArrayList;

public class ViewMonster extends RecyclerView.Adapter<ViewMonster.MyViewHolder> {
    private Context context;
    private ArrayList<Player> monsterList;
    private int enemySelectedMonster;
    private boolean isBattle;
    private Player enemyMonster;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ViewMonster(Context context, ArrayList<Player> monsterList, int enemySelectedMonster, boolean isBattle, Player enemyMonster) {
        this.context = context;
        this.monsterList = monsterList;
        this.enemySelectedMonster = enemySelectedMonster;
        this.isBattle = isBattle;
        this.enemyMonster = enemyMonster;
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
        holder.monsterImage.setImageResource(monsterList.get(position).getImage());
        holder.monsterName.setText(monsterList.get(position).getName());

        if (!isBattle) {
            if (position == enemySelectedMonster) {
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

                holder.monsterImage.setColorFilter(filter);
                holder.monsterImage.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
            }
        } else {
            if (monsterList.get(position).getName().equals(enemyMonster.getName())) {
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

                holder.monsterImage.setColorFilter(filter);
                holder.monsterImage.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (!isBattle) {
                        if (holder.getAdapterPosition() != enemySelectedMonster) {
                            listener.onItemClick(holder.getAdapterPosition());
                        }
                    } else {
                        if (!monsterList.get(holder.getAdapterPosition()).getName().equals(enemyMonster.getName())) {
                            listener.onItemClick(holder.getAdapterPosition());
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return monsterList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView monsterImage;
        TextView monsterName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            monsterImage = itemView.findViewById(R.id.monsterImage);
            monsterName = itemView.findViewById(R.id.monsterName);
        }
    }
}
