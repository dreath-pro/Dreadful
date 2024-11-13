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
import androidx.recyclerview.widget.RecyclerView;

import com.example.dreadful.R;
import com.example.dreadful.models.Map;

import java.util.ArrayList;

public class ViewMap extends RecyclerView.Adapter<ViewMap.MyViewHolder> {
    private Context context;
    private ArrayList<Map> mapList;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int status, int mapSelected, int imageResId, String mapName, int explored, String requirements);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ViewMap(Context context, ArrayList<Map> mapList) {
        this.context = context;
        this.mapList = mapList;
    }

    @NonNull
    @Override
    public ViewMap.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_map, parent, false);
        return new ViewMap.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMap.MyViewHolder holder, int position) {
        holder.mapImage.setImageResource(mapList.get(position).getImage());
        holder.mapName.setText(mapList.get(position).getName());

        if (mapList.get(position).getStatus() == 0) {
            holder.lockImage.setVisibility(View.VISIBLE);

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0f);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.mapImage.setColorFilter(filter);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(mapList.get(position).getStatus(), position, mapList.get(position).getImage(),
                        mapList.get(position).getName(), mapList.get(position).getExplorePercentage(),
                        mapList.get(position).getRequirements());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mapList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mapImage;
        TextView mapName;
        ImageView lockImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mapImage = itemView.findViewById(R.id.mapImage);
            mapName = itemView.findViewById(R.id.mapName);
            lockImage = itemView.findViewById(R.id.lockImage);
        }
    }
}
