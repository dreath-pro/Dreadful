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
    private ArrayList<String> eventList = new ArrayList<>();
    private ArrayList<String> dialogueList = new ArrayList<>();

    private int eventIncrement, dialogueIncrement;

    public ViewPrompt(Context context, Prompt prompt) {
        this.context = context;
        this.prompt = prompt;

        // Observe statusList LiveData
        prompt.getEventMessage().observe((LifecycleOwner) context, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> newEventList) {
                eventList = newEventList != null ? newEventList : new ArrayList<>();
                notifyDataSetChanged(); // Notify adapter of data change once outside onBindViewHolder
            }
        });

        // Observe statusValueList LiveData
        prompt.getDialogueMessage().observe((LifecycleOwner) context, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> newDialogueList) {
                dialogueList = newDialogueList != null ? newDialogueList : new ArrayList<>();
                notifyDataSetChanged(); // Notify adapter of data change once outside onBindViewHolder
            }
        });

        this.eventIncrement = 0;
        this.dialogueIncrement = 0;
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
        switch (prompt.getSelectedMessage().get(position)) {
            // 0 = event
            case 0:
                if (holder.getAdapterPosition() < eventList.size()) {
                    holder.resultText.setText(eventList.get(holder.getAdapterPosition()));
                    holder.resultText.setTextColor(prompt.getEventColor().get(eventIncrement));
                    eventIncrement++;
                }
                break;

            //1 = dialogue
            case 1:
                if (holder.getAdapterPosition() < dialogueList.size()) {
                    holder.resultText.setText(dialogueList.get(holder.getAdapterPosition()));
                    holder.resultText.setTextColor(prompt.getDialogueColor().get(dialogueIncrement));
                    dialogueIncrement++;
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return prompt.getTotalMessages();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView resultText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            resultText = itemView.findViewById(R.id.resultText);
        }
    }
}
