package com.example.dreadful.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dreadful.R;
import com.example.dreadful.adapters.ViewMap;
import com.example.dreadful.adapters.ViewPrompt;
import com.example.dreadful.models.Map;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {
    private RecyclerView mapList;
    private ImageView mapImage;
    private Button huntButton;
    private ArrayList<Map> mapListArray;
    private ViewMap viewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);

        mapListArray = new ArrayList<>();

        mapList = findViewById(R.id.mapList);
        mapImage = findViewById(R.id.mapImage);
        huntButton = findViewById(R.id.huntButton);

        mapListArray.add(new Map("Shadowgrove", 1, R.drawable.background_dark_forest, 0));
        mapListArray.add(new Map("Hell", 0, R.drawable.background_hell, 0));

        LinearLayoutManager statusLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mapList.setLayoutManager(statusLayoutManager);
        viewMap = new ViewMap(this, mapListArray);
        mapList.setAdapter(viewMap);

        huntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MapActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.map), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
