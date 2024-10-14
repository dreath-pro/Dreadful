package com.example.dreadful.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dreadful.R;
import com.example.dreadful.models.Prompt;

public class ViewPrompt extends RecyclerView.Adapter<ViewPrompt.MyViewHolder> {
    private Context context;
    private Prompt prompt;

    private int eventIncrement, dialogueIncrement;

    public ViewPrompt(Context context, Prompt prompt) {
        this.context = context;
        this.prompt = prompt;

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
                holder.resultText.setText(prompt.getEventMessage().get(eventIncrement++));
                break;

            //1 = dialogue
            case 1:
                holder.resultText.setText(prompt.getDialogueMessage().get(dialogueIncrement++));
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
