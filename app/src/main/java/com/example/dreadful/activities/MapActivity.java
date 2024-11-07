package com.example.dreadful.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.dreadful.models.Map;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {
    private RecyclerView mapList;
    private ImageView mapImage;
    private Button huntButton;
    private Button backButton;
    private ArrayList<Map> mapListArray = new ArrayList<>();
    private ViewMap viewMap;
    private TextView timeText, dateText;

    private Handler handler;
    private Runnable runnable;

    private void initViews() {
        mapList = findViewById(R.id.mapList);
        mapImage = findViewById(R.id.mapImage);
        huntButton = findViewById(R.id.huntButton);
        backButton = findViewById(R.id.backButton);
        timeText = findViewById(R.id.timeText);
        dateText = findViewById(R.id.dateText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);

        initViews();

        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                ZonedDateTime currentDateTime = ZonedDateTime.now();

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d"); // Updated date pattern
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a"); // Time pattern

                String formattedDate = currentDateTime.format(dateFormatter);
                String formattedTime = currentDateTime.format(timeFormatter);

                timeText.setText(formattedTime);
                dateText.setText(formattedDate);

                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);

        mapListArray.add(new Map("Shadowgrove", 1, R.drawable.map_shadowgrove, 0));
        mapListArray.add(new Map("Abyss", 0, R.drawable.map_abyss, 0));

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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
