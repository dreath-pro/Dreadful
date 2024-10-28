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

import com.example.dreadful.models.Player;
import com.example.dreadful.R;

import java.util.ArrayList;

public class ViewStatus extends RecyclerView.Adapter<ViewStatus.MyViewHolder> {
    private Context context;
    private Player player;
    private ArrayList<String> statusList = new ArrayList<>();
    private ArrayList<Integer> statusValueList = new ArrayList<>();

    public ViewStatus(Context context, Player player) {
        this.context = context;
        this.player = player;

        // Observe statusList LiveData
        player.getStatusList().observe((LifecycleOwner) context, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> newStatusList) {
                statusList = newStatusList != null ? newStatusList : new ArrayList<>();
                notifyDataSetChanged(); // Notify adapter of data change once outside onBindViewHolder
            }
        });

        // Observe statusValueList LiveData
        player.getStatusValueList().observe((LifecycleOwner) context, new Observer<ArrayList<Integer>>() {
            @Override
            public void onChanged(ArrayList<Integer> newStatusValueList) {
                statusValueList = newStatusValueList != null ? newStatusValueList : new ArrayList<>();
                notifyDataSetChanged(); // Notify adapter of data change once outside onBindViewHolder
            }
        });
    }

    @NonNull
    @Override
    public ViewStatus.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_status, parent, false);
        return new ViewStatus.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewStatus.MyViewHolder holder, int position) {
        // Check if position is within bounds before setting values
        if (holder.getAdapterPosition() < statusList.size()) {
            holder.statusName.setText(statusList.get(holder.getAdapterPosition()));
        }
        if (holder.getAdapterPosition() < statusValueList.size()) {
            holder.statusValue.setText(String.valueOf(statusValueList.get(holder.getAdapterPosition())));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getAdapterPosition() < statusList.size()) {
                    Toast.makeText(context, statusList.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView statusValue, statusName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            statusValue = itemView.findViewById(R.id.statusValue);
            statusName = itemView.findViewById(R.id.statusName);
        }
    }
}
