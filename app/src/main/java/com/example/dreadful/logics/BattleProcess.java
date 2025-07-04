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
import com.example.dreadful.adapters.ViewMonster;
import com.example.dreadful.adapters.ViewPrompt;
import com.example.dreadful.adapters.ViewSkill;
import com.example.dreadful.adapters.ViewStatus;
import com.example.dreadful.models.Monster;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class BattleProcess {
    private Context context;

    private ConstraintLayout backgroundImage;
    private TextView yourName, enemyName;
    private ProgressBar yourHealth, enemyHealth;
    private TextView yourHealthText, enemyHealthText;
    private ImageView yourImage, enemyImage;
    private Button backButton, startButton, resetButton;
    private ImageView yourChangeButton, enemyChangeButton;
    private ImageView promptButton;
    private TextView promptView;
    private TextView yourStunText, enemyStunText;
    private LinearLayout yourPlayerLayout, enemyPlayerLayout;

    private Monster yourMonster, enemyMonster;
    private GameMechanics gameMechanics;
    private SetupCharacter setupCharacter;
    private Random random = new Random();
    private ArrayList<Integer> backgroundList = new ArrayList<>();
    private int selectedBackground = 0;
    private ViewStatus viewStatus;
    private ViewSkill viewSkill;
    private ViewPrompt viewPrompt;
    private ViewMonster viewMonster;

    private Prompt prompt;
    private Dialog battleLogsDialog;
    private ArrayList<Monster> monsterList = new ArrayList<>();

    private int firstPlayerSelected, secondPlayerSelected;
    private int selectedMap, selectedLevel;
    private boolean isBattle = false;

    private boolean isCharacterDialogShowing = false, isBattleLogsDialogShowing = false,
            isMonsterSelectionShowing = false;

    public BattleProcess(Context context, ConstraintLayout backgroundImage, TextView yourName,
                         TextView enemyName, ProgressBar yourHealth, ProgressBar enemyHealth,
                         TextView yourHealthText, TextView enemyHealthText, ImageView yourImage,
                         ImageView enemyImage, Button backButton, Button startButton,
                         Button resetButton, ImageView promptButton, TextView promptView,
                         TextView yourStunText, TextView enemyStunText, LinearLayout yourPlayerLayout,
                         LinearLayout enemyPlayerLayout, ImageView yourChangeButtonImageView) {
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
        this.yourChangeButton = yourChangeButtonImageView;


        this.backgroundList.add(R.drawable.background_facility);
        this.backgroundList.add(R.drawable.background_cathedral);
        this.backgroundList.add(R.drawable.background_dark_forest);
        this.backgroundList.add(R.drawable.background_graveyard);
        this.backgroundList.add(R.drawable.background_cave);
        this.backgroundList.add(R.drawable.background_statue);
        this.backgroundList.add(R.drawable.background_hell);
        this.backgroundList.add(R.drawable.background_badlands);
        this.backgroundList.add(R.drawable.background_celestial);
    }

    public BattleProcess(Context context, ConstraintLayout backgroundImage, TextView yourName,
                         TextView enemyName, ProgressBar yourHealth, ProgressBar enemyHealth,
                         TextView yourHealthText, TextView enemyHealthText, ImageView yourImage,
                         ImageView enemyImage, Button backButton, Button startButton,
                         Button resetButton, ImageView promptButton, TextView promptView,
                         TextView yourStunText, TextView enemyStunText, LinearLayout yourPlayerLayout,
                         LinearLayout enemyPlayerLayout, ImageView yourChangeButton, ImageView enemyChangeButton) {
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
        this.yourChangeButton = yourChangeButton;
        this.enemyChangeButton = enemyChangeButton;


        this.backgroundList.add(R.drawable.background_facility);
        this.backgroundList.add(R.drawable.background_cathedral);
        this.backgroundList.add(R.drawable.background_dark_forest);
        this.backgroundList.add(R.drawable.background_graveyard);
        this.backgroundList.add(R.drawable.background_cave);
        this.backgroundList.add(R.drawable.background_statue);
        this.backgroundList.add(R.drawable.background_hell);
        this.backgroundList.add(R.drawable.background_badlands);
        this.backgroundList.add(R.drawable.background_celestial);
    }

    public void receiveData(int selectedLevel, int selectedMap) {
        this.selectedLevel = selectedLevel;
        this.selectedMap = selectedMap;
        isBattle = true;

        backgroundList.clear();
        switch (selectedMap) {
            case 0:
                backgroundList.add(R.drawable.background_facility);
                break;
            case 1:
                backgroundList.add(R.drawable.background_dark_forest);
                break;
            case 2:
                backgroundList.add(R.drawable.background_badlands);
                break;
            case 3:
                backgroundList.add(R.drawable.background_graveyard);
                break;
            case 4:
                backgroundList.add(R.drawable.background_hell);
                break;
            case 5:
                backgroundList.add(R.drawable.background_celestial);
                break;
        }
    }

    public void startConfiguration(boolean newViews, boolean fromSelection, int yourSelectedMonster, int enemySelectedMonster) {
        promptView.setText("");

        prompt = new Prompt();

        ArrayList<String> newBattleMessage = prompt.getBattleMessage().getValue();
        if (newBattleMessage == null) {
            newBattleMessage = new ArrayList<>();
        }
        prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
        newBattleMessage.add("Fate has led them to this pivotal moment of encounter.");
        newBattleMessage.add("Destiny has drawn them to this defining moment of meeting.");
        newBattleMessage.add("Their paths have converged, leading to this crucial encounter.");
        newBattleMessage.add("By fate’s design, they stand face-to-face at this turning point.");
        newBattleMessage.add("All roads have led them to this significant crossing.");
        newBattleMessage.add("Their journey has culminated in this inevitable confrontation.");
        newBattleMessage.add("The moment they were meant to meet has finally arrived.");
        newBattleMessage.add("Providence has brought them to this fated clash.");
        newBattleMessage.add("Their intertwined paths have led to this destined meeting.");
        prompt.selectRandomMessage(null, newBattleMessage, false);

        if (newViews) {
            if (!fromSelection) {
                selectedBackground = random.nextInt(backgroundList.size());
            }
        }

        setupCharacter = new SetupCharacter(context,
                yourName, yourHealth, yourHealthText, yourImage,
                enemyName, enemyHealth, enemyHealthText, enemyImage,
                yourMonster, enemyMonster, backgroundImage, yourStunText,
                enemyStunText, backgroundList, selectedBackground,
                yourHealth, enemyHealth, prompt);

        yourImage.setColorFilter(null);
        enemyImage.setColorFilter(null);

        if (!newViews) {
            setupCharacter.setFirstPlayerSelected(firstPlayerSelected);
            setupCharacter.setSecondPlayerSelected(secondPlayerSelected);
        }

        setupCharacter.selectYourCharacter(newViews, isBattle, fromSelection, yourSelectedMonster);
        if (!isBattle) {
            setupCharacter.selectEnemyCharacter(backgroundList, newViews, selectedLevel, selectedBackground, isBattle, fromSelection, enemySelectedMonster);
        } else {
            backgroundList = setupCharacter.selectEnemyCharacter(backgroundList, newViews, selectedLevel, selectedMap, isBattle, fromSelection, enemySelectedMonster);
        }
        backgroundImage.setBackgroundResource(backgroundList.get(selectedBackground));

        firstPlayerSelected = setupCharacter.getFirstPlayerSelected();
        secondPlayerSelected = setupCharacter.getSecondPlayerSelected();

        yourMonster = setupCharacter.returnYourCharacter();
        enemyMonster = setupCharacter.returnEnemyCharacter();

        gameMechanics = new GameMechanics(context, yourHealth, yourHealthText, enemyHealth,
                enemyHealthText, yourMonster, enemyMonster, promptView, yourStunText, enemyStunText, startButton, isBattle);
    }

    private void invisibleButtons(Boolean invisible) {
        if (invisible) {
            backButton.setVisibility(View.GONE);
            yourChangeButton.setVisibility(View.GONE);
            if (!isBattle) {
                resetButton.setVisibility(View.GONE);
                enemyChangeButton.setVisibility(View.GONE);
            }
        } else {
            backButton.setVisibility(View.VISIBLE);
            yourChangeButton.setVisibility(View.VISIBLE);
            if (!isBattle) {
                resetButton.setVisibility(View.VISIBLE);
                enemyChangeButton.setVisibility(View.VISIBLE);
            }
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
            startConfiguration(false, false, 0, 0);
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
            AtomicBoolean isFirstOpen = new AtomicBoolean(true);

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
                    smoothScroller.setSpeed(1f);
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
                                if (isFirstOpen.get()) {
                                    smoothScroller.setSpeed(1f);
                                    isFirstOpen.set(false);
                                }
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

    public void showMonsterSelection(int playerSelected, boolean isBattle) {
        if (!isMonsterSelectionShowing) {
            isMonsterSelectionShowing = true;

            Dialog monsterDialog = new Dialog(context);
            monsterDialog.setContentView(R.layout.dialog_monster_selection);

            RecyclerView monsterListView = monsterDialog.findViewById(R.id.monsterList);

            monsterList.clear();

            if (isBattle) {
                monsterList = setupCharacter.yourMonsters(yourImage, enemyImage, yourHealth, yourName);
            } else {
                monsterList = setupCharacter.allMonsters(yourImage, enemyImage, yourHealth, yourName);
            }

            int enemyPlayerSelected;
            if (playerSelected == 0) {
                enemyPlayerSelected = secondPlayerSelected;
            } else {
                enemyPlayerSelected = firstPlayerSelected;
            }

            LinearLayoutManager statusLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            monsterListView.setLayoutManager(statusLayoutManager);
            viewMonster = new ViewMonster(context, monsterList, enemyPlayerSelected, isBattle, enemyMonster);
            monsterListView.setAdapter(viewMonster);

            viewMonster.setOnItemClickListener(new ViewMonster.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (playerSelected == 0) {
                        firstPlayerSelected = position;
                        setupCharacter.setFirstPlayerSelected(firstPlayerSelected);
                        yourMonster = setupCharacter.returnYourCharacter();
                    } else {
                        secondPlayerSelected = position;
                        setupCharacter.setSecondPlayerSelected(secondPlayerSelected);
                        enemyMonster = setupCharacter.returnEnemyCharacter();
                    }

                    startConfiguration(true, true, firstPlayerSelected, secondPlayerSelected);
                    monsterDialog.dismiss();
                }
            });

            monsterDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    isMonsterSelectionShowing = false;
                }
            });
            monsterDialog.show();
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

            Monster monster;
            if (selectedPlayer == 0) {
                monster = yourMonster;
            } else {
                monster = enemyMonster;
            }

            playerName.setText(monster.getName());
            playerImage.setImageResource(monster.getImage());

            if (monster.getImageDirection().equals("left")) {
                playerImage.setScaleX(-1);
            }

            LinearLayoutManager statusLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            statusListView.setLayoutManager(statusLayoutManager);
            viewStatus = new ViewStatus(context, monster);
            statusListView.setAdapter(viewStatus);

            LinearLayoutManager skillLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            skillListView.setLayoutManager(skillLayoutManager);
            viewSkill = new ViewSkill(context, monster);
            skillListView.setAdapter(viewSkill);

            monster.getStatusList().observe(lifecycleOwner, statusList -> {
                // Delay the notifyDataSetChanged() call until the layout pass is complete
                statusListView.post(() -> {
                    if (statusList != null) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            viewStatus.notifyDataSetChanged();
                        });
                    }
                });
            });

            monster.getStatusValueList().observe(lifecycleOwner, statusValueList -> {
                statusListView.post(() -> {
                    if (statusValueList != null) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            viewStatus.notifyDataSetChanged();
                        });
                    }
                });
            });

            monster.getSkillNames().observe(lifecycleOwner, skillNamesList -> {
                // Delay the notifyDataSetChanged() call until the layout pass is complete
                skillListView.post(() -> {
                    if (skillNamesList != null) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            viewSkill.notifyDataSetChanged();
                        });
                    }
                });
            });

            monster.getSkillCooldowns().observe(lifecycleOwner, skillCooldownsList -> {
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
