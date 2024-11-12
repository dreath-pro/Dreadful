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
import com.example.dreadful.models.Level;
import com.example.dreadful.models.Map;
import com.example.dreadful.models.Player;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

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
    private Level level;
    private Random random;

    private Handler clockhandler, loadingHandler;
    private Runnable clockrunnable, loadingRunnable;

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

        level = new Level();
        random = new Random();

        clockhandler = new Handler(Looper.getMainLooper());
        clockrunnable = new Runnable() {
            @Override
            public void run() {
                ZonedDateTime currentDateTime = ZonedDateTime.now();

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d"); // Updated date pattern
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a"); // Time pattern

                String formattedDate = currentDateTime.format(dateFormatter);
                String formattedTime = currentDateTime.format(timeFormatter);

                timeText.setText(formattedTime);
                dateText.setText(formattedDate);

                clockhandler.postDelayed(this, 1000);
            }
        };
        clockhandler.post(clockrunnable);

        mapListArray.add(new Map("Facility", 1, R.drawable.map_facility, 0, ""));
        mapListArray.add(new Map("Shadowgrove", 1, R.drawable.map_shadowgrove, 0, ""));
        mapListArray.add(new Map("Badlands", 1, R.drawable.map_badland, 0, ""));
        mapListArray.add(new Map("Ghost Town", 0, R.drawable.map_ghost_town, 0, "Discover all the Shadowgrove monsters"));
        mapListArray.add(new Map("Abyss", 0, R.drawable.map_abyss, 0, "Defeat the strongest aberrant in ghost town"));
        mapListArray.add(new Map("Celestial", 0, R.drawable.map_celestial, 0, "Destroy the final aberrant in abyss"));

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
        clockhandler.removeCallbacks(clockrunnable);
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
            TextView progressText = loadingDialog.findViewById(R.id.progressText);
            TextView resultText = loadingDialog.findViewById(R.id.resultText);
            ImageView skullWarning = loadingDialog.findViewById(R.id.skullWarning);

            int selectedLevel;
            int percentage = random.nextInt(100) + 1;

            if (percentage == 1) {
                selectedLevel = 4;
            } else if (percentage <= 10) {
                selectedLevel = 3;
            } else if (percentage <= 30) {
                selectedLevel = 2;
            } else if (percentage <= 60) {
                selectedLevel = 1;
            } else {
                selectedLevel = 0;
            }

            loadingBar.setIndeterminate(true);

            loadingHandler = new Handler(Looper.getMainLooper());
            loadingRunnable = new Runnable() {
                @Override
                public void run() {
                    resultText.setVisibility(View.VISIBLE);
                    skullWarning.setVisibility(View.VISIBLE);
                    loadingBar.setVisibility(View.GONE);

                    progressText.setText("Waiting for match.");
                    resultText.setText(level.getWarningMessage(selectedLevel));

                    loadingHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismiss();
                        }
                    }, 5000);
                }
            };
            loadingHandler.postDelayed(loadingRunnable, 4000); // Delay execution by 4 seconds

            loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    isLoadingDialogShowing = false;
                    loadingHandler.removeCallbacks(loadingRunnable);
                }
            });
            loadingDialog.show();
        }
    }
}
