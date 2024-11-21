package com.example.dreadful.logics;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dreadful.R;
import com.example.dreadful.characters.Carnant;
import com.example.dreadful.characters.Dreath;
import com.example.dreadful.characters.Flamethrower;
import com.example.dreadful.characters.GodOfDeath;
import com.example.dreadful.characters.HellKnight;
import com.example.dreadful.characters.Jimhardcore;
import com.example.dreadful.characters.KumoNingyo;
import com.example.dreadful.characters.DreadProphet;
import com.example.dreadful.characters.Michael;
import com.example.dreadful.characters.VoidReaper;
import com.example.dreadful.databases.MonsterDatabase;
import com.example.dreadful.models.Monster;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class SetupCharacter {
    private Context context;
    private TextView yourName, enemyName;
    private ProgressBar yourHealth, enemyHealth;
    private TextView yourHealthText, enemyHealthText;
    private TextView yourStunText, enemyStunText;
    private ImageView yourImage, enemyImage;
    private ConstraintLayout backgroundImage;
    private Monster yourMonster, enemyMonster;
    private ArrayList<Monster> monsters = new ArrayList<>();
    private Random random = new Random();
    private ResizeImage resizeImage;
    private Prompt prompt;
    private NumberComma numberComma = new NumberComma();

    private ArrayList<Integer> backgroundList;
    private int selectedBackground;
    private ProgressBar yourHealthBar, enemyHealthBar;
    private MonsterDatabase monsterDatabase;

    private int firstPlayerSelected, secondPlayerSelected;

    public SetupCharacter(Context context, TextView yourName, ProgressBar yourHealth, TextView yourHealthText, ImageView yourImage,
                          TextView enemyName, ProgressBar enemyHealth, TextView enemyHealthText, ImageView enemyImage,
                          Monster yourMonster, Monster enemyMonster, ConstraintLayout backgroundImage, TextView yourStunText, TextView enemyStunText,
                          ArrayList<Integer> backgroundList, int selectedBackground, ProgressBar yourHealthBar, ProgressBar enemyHealthBar, Prompt prompt) {

        this.context = context;

        this.yourName = yourName;
        this.yourHealth = yourHealth;
        this.yourHealthText = yourHealthText;
        this.yourImage = yourImage;

        this.enemyName = enemyName;
        this.enemyHealth = enemyHealth;
        this.enemyHealthText = enemyHealthText;
        this.enemyImage = enemyImage;

        this.yourStunText = yourStunText;
        this.enemyStunText = enemyStunText;

        this.backgroundImage = backgroundImage;
        this.resizeImage = new ResizeImage(context);

        this.backgroundList = backgroundList;
        this.selectedBackground = selectedBackground;
        this.yourHealthBar = yourHealthBar;
        this.enemyHealthBar = enemyHealthBar;

        this.prompt = prompt;

        this.yourMonster = yourMonster;
        this.enemyMonster = enemyMonster;

        this.monsterDatabase = new MonsterDatabase(context);
    }

    public ArrayList<Monster> yourMonsters(ImageView playerImage, ImageView opponentImage, ProgressBar playerHealthBar, TextView playerName) {
        monsters.clear();
        ArrayList<String> monsterNames = monsterDatabase.selectAll();
        monsters = monsterListing(monsterNames, playerImage, opponentImage, playerHealthBar, playerName);

        return monsters;
    }

    public ArrayList<Monster> allMonsters(ImageView playerImage, ImageView opponentImage, ProgressBar playerHealthBar, TextView playerName) {
        monsters.clear();
        monsters = monsterListing(null, playerImage, opponentImage, playerHealthBar, playerName);

        return monsters;
    }

    private ArrayList<Monster> monsterListing(ArrayList<String> monsterList, ImageView playerImage, ImageView opponentImage, ProgressBar playerHealthBar, TextView playerName) {
        ArrayList<Monster> finalMonsters = new ArrayList<>();

        monsters.clear();
        monsters.add(new Dreath(context, playerImage, prompt));
        monsters.add(new DreadProphet(context, playerImage, prompt));
        monsters.add(new KumoNingyo(context, playerImage, playerHealthBar, prompt));
        monsters.add(new VoidReaper(context, playerImage, backgroundImage, backgroundList, selectedBackground, prompt));
        monsters.add(new HellKnight(context, playerImage, playerHealthBar, prompt));
        monsters.add(new Carnant(context, playerImage, playerHealthBar, playerName, prompt));
        monsters.add(new GodOfDeath(context, playerImage, prompt));
        monsters.add(new Michael(context, playerImage, prompt, opponentImage));
        monsters.add(new Jimhardcore(context, playerImage, prompt));

        if (monsterList != null) {
            monsters.add(new Flamethrower(context, playerImage, prompt));

            for (int i = 0; i <= monsterList.size() - 1; i++) {
                for (int j = 0; j <= monsters.size() - 1; j++) {
                    if (monsterList.get(i).equals(monsters.get(j).getName())) {
                        finalMonsters.add(monsters.get(j));
                    }
                }
            }
        } else {
            finalMonsters = monsters;
        }

        return finalMonsters;
    }

    private ArrayList<Integer> mapMonsters(ArrayList<Integer> background, ImageView playerImage, ImageView opponentImage, ProgressBar playerHealthBar, TextView playerName,
                                           int selectedLevel, int selectedMap) {
        ArrayList<Integer> newBackground = background;
        monsters.clear();

        switch (selectedMap) {
            case 0:
                //Facility

                switch (selectedLevel) {
                    case 0:
                    case 1:
                    case 2:
                        monsters.add(new Carnant(context, playerImage, playerHealthBar, playerName, prompt));
                        break;
                    case 3:
                    case 4:
                        monsters.add(new Jimhardcore(context, playerImage, prompt));
                        break;
                }

                break;

            case 1:
                //Shadowgrove

                switch (selectedLevel) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        monsters.add(new Michael(context, playerImage, prompt, opponentImage));

                        newBackground.clear();
                        newBackground.add(R.drawable.background_statue);
                        break;
                }

                break;

            case 2:
                //Badlands

                switch (selectedLevel) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        monsters.add(new VoidReaper(context, playerImage, backgroundImage, backgroundList, selectedBackground, prompt));
                        break;
                }

                break;

            case 3:
                //Ghost Town

                switch (selectedLevel) {
                    case 0:
                    case 1:
                    case 2:
                        monsters.add(new KumoNingyo(context, playerImage, playerHealthBar, prompt));
                        break;
                    case 3:
                        monsters.add(new DreadProphet(context, playerImage, prompt));

                        newBackground.clear();
                        newBackground.add(R.drawable.background_cathedral);
                        break;
                    case 4:
                        monsters.add(new DreadProphet(context, playerImage, prompt));

                        newBackground.clear();
                        newBackground.add(R.drawable.background_cathedral);
                        break;
                }

                break;

            case 4:
                //Abyss

                switch (selectedLevel) {
                    case 0:
                    case 1:
                    case 2:
                        monsters.add(new HellKnight(context, playerImage, playerHealthBar, prompt));
                        break;
                    case 3:
                        monsters.add(new Dreath(context, playerImage, prompt));
                        break;
                    case 4:
                        monsters.add(new Dreath(context, playerImage, prompt));
                        break;
                }

                break;

            case 5:
                //Celestial

                switch (selectedLevel) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        monsters.add(new GodOfDeath(context, playerImage, prompt));
                        break;
                }

                break;
        }

        return newBackground;
    }

    public Monster returnYourCharacter() {
        return yourMonster;
    }

    public Monster returnEnemyCharacter() {
        return enemyMonster;
    }

    public void selectYourCharacter(boolean newViews, boolean isBattle, boolean fromSelection, int selectedMonster) {
        yourImage.setScaleX(1);

        if (isBattle) {
            yourMonsters(yourImage, enemyImage, yourHealthBar, yourName);
        } else {
            allMonsters(yourImage, enemyImage, yourHealthBar, yourName);
        }

        if (newViews) {
            if (!fromSelection) {
                if (isBattle) {
                    firstPlayerSelected = 0;
                } else {
                    firstPlayerSelected = random.nextInt(monsters.size());
                }
            } else {
                firstPlayerSelected = selectedMonster;
            }
        }
        yourMonster = monsters.get(firstPlayerSelected);

        yourName.setText(yourMonster.getName());
        yourHealthText.setText(numberComma.convertComma(yourMonster.getHealth()));
        yourHealth.setMax(yourMonster.getHealth());
        yourHealth.setProgress(yourMonster.getHealth());
        yourImage.setImageResource(yourMonster.getImage());

        yourStunText.setText(yourMonster.getStun() + "");

        if (yourMonster.getImageDirection().equals("left")) {
            yourImage.setScaleX(-1);
        }

        resizeImage.scale(yourImage, yourMonster.getSize());
    }

    public ArrayList<Integer> selectEnemyCharacter(ArrayList<Integer> background, boolean newViews, int selectedLevel, int selectedMap, boolean isBattle, boolean fromSelection, int selectedMonster) {
        ArrayList<Integer> newBackground = background;

        enemyImage.setScaleX(1);

        if (isBattle) {
            newBackground = mapMonsters(newBackground, enemyImage, yourImage, enemyHealthBar, enemyName, selectedLevel, selectedMap);
        } else {
            allMonsters(enemyImage, yourImage, enemyHealthBar, enemyName);
        }

        if (newViews) {
            if (!fromSelection) {
                secondPlayerSelected = random.nextInt(monsters.size());
            } else {
                secondPlayerSelected = selectedMonster;
            }
        }
        if (!isBattle) {
            if (firstPlayerSelected == secondPlayerSelected) {
                do {
                    secondPlayerSelected = random.nextInt(monsters.size());
                } while (firstPlayerSelected == secondPlayerSelected);
            }
        }
        enemyMonster = monsters.get(secondPlayerSelected);

        monsters.clear();

        enemyName.setText(enemyMonster.getName());
        enemyHealthText.setText(numberComma.convertComma(enemyMonster.getHealth()));
        enemyHealth.setMax(enemyMonster.getHealth());
        enemyHealth.setProgress(enemyMonster.getHealth());
        enemyImage.setImageResource(enemyMonster.getImage());

        enemyStunText.setText(enemyMonster.getStun() + "");

        if (enemyMonster.getImageDirection().equals("right")) {
            enemyImage.setScaleX(-1);
        }

        resizeImage.scale(enemyImage, enemyMonster.getSize());

        return newBackground;
    }

    public int getFirstPlayerSelected() {
        return firstPlayerSelected;
    }

    public void setFirstPlayerSelected(int firstPlayerSelected) {
        this.firstPlayerSelected = firstPlayerSelected;
    }

    public int getSecondPlayerSelected() {
        return secondPlayerSelected;
    }

    public void setSecondPlayerSelected(int secondPlayerSelected) {
        this.secondPlayerSelected = secondPlayerSelected;
    }
}
