package com.example.dreadful.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dreadful.R;
import com.example.dreadful.adapters.ViewMap;
import com.example.dreadful.adapters.ViewSkill;
import com.example.dreadful.adapters.ViewStatus;
import com.example.dreadful.models.Map;
import com.example.dreadful.models.Player;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements ViewMap.OnItemClickListener {
    private RecyclerView mapList;
    private ImageView mapImage;
    private Button huntButton;
    private Button backButton;
    private ArrayList<Map> mapListArray = new ArrayList<>();
    private ViewMap viewMap;
    private TextView timeText, dateText;
    private TextView mapName;
    private ProgressBar exploredBar;
    private TextView exploredText;
    private LinearLayout progressLayout, lockLayout;
    private TextView requirementsText;

    private Handler handler;
    private Runnable runnable;

    private boolean isLoadingDialogShowing = false;

    private void initViews() {
        mapList = findViewById(R.id.mapList);
        mapImage = findViewById(R.id.mapImage);
        huntButton = findViewById(R.id.huntButton);
        backButton = findViewById(R.id.backButton);
        timeText = findViewById(R.id.timeText);
        dateText = findViewById(R.id.dateText);
        mapName = findViewById(R.id.mapName);
        exploredBar = findViewById(R.id.exploredBar);
        exploredText = findViewById(R.id.exploredText);
        progressLayout = findViewById(R.id.progressLayout);
        lockLayout = findViewById(R.id.lockLayout);
        requirementsText = findViewById(R.id.requirementsText);
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

        mapListArray.add(new Map("Facility", 1, R.drawable.map_facility, 0, ""));
        mapListArray.add(new Map("Shadowgrove", 1, R.drawable.map_shadowgrove, 0, ""));
        mapListArray.add(new Map("Badlands", 1, R.drawable.map_badland, 0, ""));
        mapListArray.add(new Map("Ghost Town", 0, R.drawable.map_ghost_town, 0, "Discover the Spectre King"));
        mapListArray.add(new Map("Abyss", 0, R.drawable.map_abyss, 0, "Defeat the Dread Prophet"));

        LinearLayoutManager statusLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mapList.setLayoutManager(statusLayoutManager);
        viewMap = new ViewMap(this, mapListArray);
        viewMap.setOnItemClickListener(this);
        mapList.setAdapter(viewMap);

        mapImage.setImageResource(mapListArray.get(0).getImage());
        mapName.setText(mapListArray.get(0).getName());
        exploredBar.setProgress(mapListArray.get(0).getExplorePercentage());
        exploredText.setText(mapListArray.get(0).getExplorePercentage() + "%");

        huntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
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
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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

    @Override
    public void onItemClick(int status, int imageResId, String mapName, int explored, String requirements) {
        if (status == 0) {
            lockLayout.setVisibility(View.VISIBLE);
            progressLayout.setVisibility(View.GONE);

            mapImage.setImageResource(imageResId);
            this.mapName.setText(mapName);
            requirementsText.setText("Requirements: " + requirements);
        } else {
            lockLayout.setVisibility(View.GONE);
            progressLayout.setVisibility(View.VISIBLE);

            mapImage.setImageResource(imageResId);
            this.mapName.setText(mapName);
            exploredBar.setProgress(explored);
            exploredText.setText(explored + "%");
        }
    }

    public void showLoadingDialog() {
        if (!isLoadingDialogShowing) {
            isLoadingDialogShowing = true;

            Dialog loadingDialog = new Dialog(this);
            loadingDialog.setContentView(R.layout.dialog_hunt_loading);
            ProgressBar loadingBar = loadingDialog.findViewById(R.id.loadingBar);

            loadingBar.setIndeterminate(true);

            loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    isLoadingDialogShowing = false;
                }
            });
            loadingDialog.show();
        }
    }
}
