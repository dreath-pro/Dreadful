package com.example.dreadful.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dreadful.R;
import com.example.dreadful.models.Player;

import java.util.ArrayList;

public class ViewSkill extends RecyclerView.Adapter<ViewSkill.MyViewHolder> {
    private Context context;
    private ArrayList<String> skillNames = new ArrayList<>();
    private ArrayList<Integer> skillCooldowns = new ArrayList<>();

    public ViewSkill(Context context, Player player) {
        this.context = context;

        // Observe statusList LiveData
        player.getSkillNames().observe((LifecycleOwner) context, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> newSkillNamesList) {
                skillNames = newSkillNamesList != null ? newSkillNamesList : new ArrayList<>();
                notifyDataSetChanged(); // Notify adapter of data change once outside onBindViewHolder
            }
        });

        // Observe statusValueList LiveData
        player.getSkillCooldowns().observe((LifecycleOwner) context, new Observer<ArrayList<Integer>>() {
            @Override
            public void onChanged(ArrayList<Integer> newSkillCooldownsList) {
                skillCooldowns = newSkillCooldownsList != null ? newSkillCooldownsList : new ArrayList<>();
                notifyDataSetChanged(); // Notify adapter of data change once outside onBindViewHolder
            }
        });
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
        // Check if position is within bounds before setting values
        if (holder.getAdapterPosition() < skillNames.size()) {
            holder.skillName.setText(skillNames.get(holder.getAdapterPosition()));
        }
        if (holder.getAdapterPosition() < skillCooldowns.size()) {
            holder.skillValue.setText(String.valueOf(skillCooldowns.get(holder.getAdapterPosition())));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getAdapterPosition() < skillNames.size()) {
                    Toast.makeText(context, skillNames.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return skillNames.size();
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
