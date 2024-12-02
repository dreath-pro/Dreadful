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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dreadful.R;
import com.example.dreadful.adapters.ViewMap;
import com.example.dreadful.databases.MapDatabase;
import com.example.dreadful.models.Level;
import com.example.dreadful.models.Map;

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
    private int selectedMap;
    private Random random;

    private Handler clockhandler, loadingHandler;
    private Runnable clockrunnable, loadingRunnable;

    private boolean isLoadingDialogShowing = false;
    private MapDatabase mapDatabase;

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
        mapDatabase = new MapDatabase(this);

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

        mapListArray.addAll(mapDatabase.selectAll());

        for(int i = 0; i <= mapListArray.size() - 1; i++)
        {
            if(mapListArray.get(i).getName().equals("Facility"))
            {
                mapListArray.get(i).setImage(R.drawable.map_facility);
            }

            if(mapListArray.get(i).getName().equals("Shadowgrove"))
            {
                mapListArray.get(i).setImage(R.drawable.map_shadowgrove);
            }

            if(mapListArray.get(i).getName().equals("Badlands"))
            {
                mapListArray.get(i).setImage(R.drawable.map_badland);
            }

            if(mapListArray.get(i).getName().equals("Ghost Town"))
            {
                mapListArray.get(i).setImage(R.drawable.map_ghost_town);
                mapListArray.get(i).setRequirements("Discover all the Shadowgrove monsters");
            }

            if(mapListArray.get(i).getName().equals("Abyss"))
            {
                mapListArray.get(i).setImage(R.drawable.map_abyss);
                mapListArray.get(i).setRequirements("Defeat the strongest aberrant in ghost town");
            }

            if(mapListArray.get(i).getName().equals("Celestial"))
            {
                mapListArray.get(i).setImage(R.drawable.map_celestial);
                mapListArray.get(i).setRequirements("Destroy the final aberrant in abyss");
            }
        }

        LinearLayoutManager statusLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mapList.setLayoutManager(statusLayoutManager);
        viewMap = new ViewMap(this, mapListArray);
        viewMap.setOnItemClickListener(this);
        mapList.setAdapter(viewMap);

        selectedMap = 0;
        mapImage.setImageResource(mapListArray.get(0).getImage());
        mapName.setText(mapListArray.get(0).getName());
        exploredBar.setProgress(mapListArray.get(0).getExplorePercentage());
        exploredText.setText(mapListArray.get(0).getExplorePercentage() + "%");

        huntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mapListArray.get(selectedMap).getStatus() == 0)
                {
                    Toast.makeText(MapActivity.this, "Map is locked!", Toast.LENGTH_SHORT).show();
                }else
                {
                    showLoadingDialog();
                }
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
    public void onItemClick(int status, int mapSelected, int imageResId, String mapName, int explored, String requirements) {
        selectedMap = mapSelected;
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
//                    progressText.setText("Waiting for match. \uD83D\uDC40");
                    resultText.setText(level.getWarningMessage(selectedLevel));

                    loadingHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MapActivity.this, BattleActivity.class);
                            intent.putExtra("selectedLevel", selectedLevel);
                            intent.putExtra("selectedMap", selectedMap);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

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
