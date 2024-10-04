package com.example.dreadful.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dreadful.R;
import com.example.dreadful.models.Player;

public class ViewSkill extends RecyclerView.Adapter<ViewSkill.MyViewHolder> {
    private Context context;
    private Player player;

    public ViewSkill(Context context, Player player) {
        this.context = context;
        this.player = player;
    }

    @NonNull
    @Override
    public ViewSkill.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_skill, parent, false);
        return new ViewSkill.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewSkill.MyViewHolder holder, int position) {
        holder.skillValue.setText(player.getSkillCooldowns()[position] + "");
        holder.skillName.setText(player.getSkillNames()[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, player.getSkillNames()[holder.getAdapterPosition()], Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return player.getSkillNames().length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView skillValue, skillName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            skillValue = itemView.findViewById(R.id.skillValue);
            skillName = itemView.findViewById(R.id.skillName);
        }
    }
}
