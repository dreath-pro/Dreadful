package com.example.dreadful.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreadful.models.Player;
import com.example.dreadful.R;

public class ViewStatus extends RecyclerView.Adapter<ViewStatus.MyViewHolder> {
    private Context context;
    private Player player;

    public ViewStatus(Context context, Player player) {
        this.context = context;
        this.player = player;
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
        holder.statusValue.setText(player.getStatusValue().get(position));
        holder.statusName.setText(player.getStatus().get(position));
    }

    @Override
    public int getItemCount() {
        return player.getStatus().size();
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
