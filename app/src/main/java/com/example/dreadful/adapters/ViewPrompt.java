package com.example.dreadful.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dreadful.R;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;

public class ViewPrompt extends RecyclerView.Adapter<ViewPrompt.MyViewHolder> {
    private Context context;
    private Prompt prompt;
    private ArrayList<String> messageList = new ArrayList<>();

    public ViewPrompt(Context context, Prompt prompt) {
        this.context = context;
        this.prompt = prompt;

        // Observe statusList LiveData
        prompt.getBattleMessage().observe((LifecycleOwner) context, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> newBattleList) {
                messageList = newBattleList != null ? newBattleList : new ArrayList<>();
                notifyDataSetChanged(); // Notify adapter of data change once outside onBindViewHolder
            }
        });
    }

    @NonNull
    @Override
    public ViewPrompt.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_prompt, parent, false);
        return new ViewPrompt.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPrompt.MyViewHolder holder, int position) {
        if (holder.getAdapterPosition() < messageList.size()) {
            holder.resultText.setText(messageList.get(holder.getAdapterPosition()));
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView resultText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            resultText = itemView.findViewById(R.id.resultText);
        }
    }
}
