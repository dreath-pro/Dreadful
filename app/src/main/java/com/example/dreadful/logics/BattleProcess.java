package com.example.dreadful.logics;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dreadful.R;
import com.example.dreadful.adapters.ViewPrompt;
import com.example.dreadful.adapters.ViewSkill;
import com.example.dreadful.adapters.ViewStatus;
import com.example.dreadful.models.Player;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class BattleProcess {
    private Context context;

    private ConstraintLayout backgroundImage;
    private TextView yourName, enemyName;
    private ProgressBar yourHealth, enemyHealth;
    private TextView yourHealthText, enemyHealthText;
    private ImageView yourImage, enemyImage;
    private Button backButton, startButton, resetButton;
    private ImageView promptButton;
    private TextView promptView;
    private TextView yourStunText, enemyStunText;
    private LinearLayout yourPlayerLayout, enemyPlayerLayout;

    private Player yourPlayer, enemyPlayer;
    private GameMechanics gameMechanics;
    private SetupCharacter setupCharacter;
    private Random random = new Random();
    private final int[] backgroundList = {R.drawable.background_cathedral, R.drawable.background_dark_forest,
            R.drawable.background_graveyard, R.drawable.background_cave, R.drawable.background_statue,
            R.drawable.background_hell};
    private int selectedBackground = 0;
    private ViewStatus viewStatus;
    private ViewSkill viewSkill;
    private ViewPrompt viewPrompt;

    private Prompt prompt;
    private Dialog battleLogsDialog;

    private int firstPlayerSelected, secondPlayerSelected;
    private int selectedMap, selectedLevel;
    private boolean isBattle = false;

    private boolean isCharacterDialogShowing = false, isBattleLogsDialogShowing = false;

    public BattleProcess(Context context, ConstraintLayout backgroundImage, TextView yourName,
                         TextView enemyName, ProgressBar yourHealth, ProgressBar enemyHealth,
                         TextView yourHealthText, TextView enemyHealthText, ImageView yourImage,
                         ImageView enemyImage, Button backButton, Button startButton,
                         Button resetButton, ImageView promptButton, TextView promptView,
                         TextView yourStunText, TextView enemyStunText, LinearLayout yourPlayerLayout,
                         LinearLayout enemyPlayerLayout) {
        this.context = context;
        this.backgroundImage = backgroundImage;
        this.yourName = yourName;
        this.enemyName = enemyName;
        this.yourHealth = yourHealth;
        this.enemyHealth = enemyHealth;
        this.yourHealthText = yourHealthText;
        this.enemyHealthText = enemyHealthText;
        this.yourImage = yourImage;
        this.enemyImage = enemyImage;
        this.backButton = backButton;
        this.startButton = startButton;
        this.resetButton = resetButton;
        this.promptButton = promptButton;
        this.promptView = promptView;
        this.yourStunText = yourStunText;
        this.enemyStunText = enemyStunText;
        this.yourPlayerLayout = yourPlayerLayout;
        this.enemyPlayerLayout = enemyPlayerLayout;
    }

    public void receiveData(int selectedLevel, int selectedMap) {
        this.selectedLevel = selectedLevel;
        this.selectedMap = selectedMap;
        isBattle = true;
    }

    public void startConfiguration(boolean newViews) {
        promptView.setText("");

        prompt = new Prompt();

        ArrayList<String> newBattleMessage = prompt.getBattleMessage().getValue();
        if (newBattleMessage == null) {
            newBattleMessage = new ArrayList<>();
        }
        prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
        newBattleMessage.add("Fate has led them to this pivotal moment of encounter.");
        prompt.selectRandomMessage(null, newBattleMessage, false);

        if (newViews) {
            selectedBackground = random.nextInt(backgroundList.length);
        }
        backgroundImage.setBackgroundResource(backgroundList[selectedBackground]);

        setupCharacter = new SetupCharacter(context,
                yourName, yourHealth, yourHealthText, yourImage,
                enemyName, enemyHealth, enemyHealthText, enemyImage,
                yourPlayer, enemyPlayer, backgroundImage, yourStunText,
                enemyStunText, backgroundList, selectedBackground,
                yourHealth, enemyHealth, prompt);

        yourImage.setColorFilter(null);
        enemyImage.setColorFilter(null);

        if (!newViews) {
            setupCharacter.setFirstPlayerSelected(firstPlayerSelected);
            setupCharacter.setSecondPlayerSelected(secondPlayerSelected);
        }

        setupCharacter.selectYourCharacter(newViews, isBattle);
        setupCharacter.selectEnemyCharacter(newViews, selectedLevel, selectedMap, isBattle);

        firstPlayerSelected = setupCharacter.getFirstPlayerSelected();
        secondPlayerSelected = setupCharacter.getSecondPlayerSelected();

        yourPlayer = setupCharacter.returnYourCharacter();
        enemyPlayer = setupCharacter.returnEnemyCharacter();

        gameMechanics = new GameMechanics(context, yourHealth, yourHealthText, enemyHealth,
                enemyHealthText, yourPlayer, enemyPlayer, promptView, yourStunText, enemyStunText, startButton);
    }

    public void invisibleButtons(Boolean invisible) {
        if (invisible) {
            backButton.setVisibility(View.GONE);
            resetButton.setVisibility(View.GONE);
        } else {
            backButton.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.VISIBLE);
        }
    }

    public void editButton() {
        if (startButton.getText().toString().equals(context.getString(R.string.start_button))) {
            invisibleButtons(true);
            startButton.setText("Stop");
            gameMechanics.battleLoop();
        } else {
            invisibleButtons(false);
            startButton.setText(context.getString(R.string.start_button));
            gameMechanics.stopBattleLoop();
            startConfiguration(false);
        }
    }

    public void battleLogValidation(LifecycleOwner lifecycleOwner) {
        if (battleLogsDialog != null && battleLogsDialog.isShowing()) {
            battleLogsDialog.dismiss();
        }
        showBattleLogs(lifecycleOwner);
    }

    private void showBattleLogs(LifecycleOwner lifecycleOwner) {
        if (!isBattleLogsDialogShowing) {
            isBattleLogsDialogShowing = true;

            battleLogsDialog = new Dialog(context);
            battleLogsDialog.setContentView(R.layout.dialog_battle_log);

            RecyclerView promptList = battleLogsDialog.findViewById(R.id.promptList);
            CheckBox autoScroll = battleLogsDialog.findViewById(R.id.autoScroll);
            ImageView downButton = battleLogsDialog.findViewById(R.id.downButton);

            LinearLayoutManager statusLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            promptList.setLayoutManager(statusLayoutManager);
            viewPrompt = new ViewPrompt(context, prompt);
            promptList.setAdapter(viewPrompt);

            autoScroll.setChecked(true);

            downButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SlowSmoothScroller smoothScroller = new SlowSmoothScroller(context); // Create custom scroller
                    smoothScroller.setTargetPosition(viewPrompt.getItemCount() - 1); // Set target position
                    statusLayoutManager.startSmoothScroll(smoothScroller); // Start smooth scroll
                }
            });

            prompt.getBattleMessage().observe(lifecycleOwner, messageList -> {
                // Delay the notifyDataSetChanged() call until the layout pass is complete
                promptList.post(() -> {
                    if (messageList != null) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            viewPrompt.notifyDataSetChanged();
                            if (autoScroll.isChecked()) {
                                SlowSmoothScroller smoothScroller = new SlowSmoothScroller(context); // Create custom scroller
                                smoothScroller.setTargetPosition(viewPrompt.getItemCount() - 1); // Set target position
                                statusLayoutManager.startSmoothScroll(smoothScroller); // Start smooth scroll
                            }
                        });
                    }
                });
            });

            battleLogsDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    isBattleLogsDialogShowing = false;
                }
            });
            battleLogsDialog.show();
        }
    }

    public void showCharacterDetails(int selectedPlayer, LifecycleOwner lifecycleOwner) {
        if (!isCharacterDialogShowing) {
            isCharacterDialogShowing = true;

            Dialog characterDialog = new Dialog(context);
            characterDialog.setContentView(R.layout.dialog_character_details);

            TextView playerName = characterDialog.findViewById(R.id.playerName);
            ImageView playerImage = characterDialog.findViewById(R.id.playerImage);
            RecyclerView statusListView = characterDialog.findViewById(R.id.statusList);
            RecyclerView skillListView = characterDialog.findViewById(R.id.skillList);

            Player player;
            if (selectedPlayer == 0) {
                player = yourPlayer;
            } else {
                player = enemyPlayer;
            }

            playerName.setText(player.getName());
            playerImage.setImageResource(player.getImage());

            if (player.getImageDirection().equals("left")) {
                playerImage.setScaleX(-1);
            }

            LinearLayoutManager statusLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            statusListView.setLayoutManager(statusLayoutManager);
            viewStatus = new ViewStatus(context, player);
            statusListView.setAdapter(viewStatus);

            LinearLayoutManager skillLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            skillListView.setLayoutManager(skillLayoutManager);
            viewSkill = new ViewSkill(context, player);
            skillListView.setAdapter(viewSkill);

            player.getStatusList().observe(lifecycleOwner, statusList -> {
                // Delay the notifyDataSetChanged() call until the layout pass is complete
                statusListView.post(() -> {
                    if (statusList != null) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            viewStatus.notifyDataSetChanged();
                        });
                    }
                });
            });

            player.getStatusValueList().observe(lifecycleOwner, statusValueList -> {
                statusListView.post(() -> {
                    if (statusValueList != null) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            viewStatus.notifyDataSetChanged();
                        });
                    }
                });
            });

            player.getSkillNames().observe(lifecycleOwner, skillNamesList -> {
                // Delay the notifyDataSetChanged() call until the layout pass is complete
                skillListView.post(() -> {
                    if (skillNamesList != null) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            viewSkill.notifyDataSetChanged();
                        });
                    }
                });
            });

            player.getSkillCooldowns().observe(lifecycleOwner, skillCooldownsList -> {
                skillListView.post(() -> {
                    if (skillCooldownsList != null) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            viewSkill.notifyDataSetChanged();
                        });
                    }
                });
            });

            characterDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    isCharacterDialogShowing = false;
                }
            });
            characterDialog.show();
        }
    }
}
