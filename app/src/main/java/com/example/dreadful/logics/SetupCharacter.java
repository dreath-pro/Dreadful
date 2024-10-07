package com.example.dreadful.logics;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dreadful.R;
import com.example.dreadful.characters.Dreath;
import com.example.dreadful.characters.KumoNingyo;
import com.example.dreadful.characters.DreadProphet;
import com.example.dreadful.characters.VoidReaper;
import com.example.dreadful.models.Player;

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
    private Player yourPlayer, enemyPlayer;
    private ArrayList<Player> players = new ArrayList<>();
    private Random random = new Random();
    private ResizeImage resizeImage;

    private int[] backgroundList;
    private int selectedBackground = 0;

    private int firstPlayerSelected, secondPlayerSelected;

    public SetupCharacter(Context context, TextView yourName, ProgressBar yourHealth, TextView yourHealthText, ImageView yourImage,
                          TextView enemyName, ProgressBar enemyHealth, TextView enemyHealthText, ImageView enemyImage,
                          Player yourPlayer, Player enemyPlayer, ConstraintLayout backgroundImage, TextView yourStunText, TextView enemyStunText,
                          int[] backgroundList, int selectedBackground) {

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

        this.yourPlayer = yourPlayer;
        this.enemyPlayer = enemyPlayer;
        this.firstPlayerSelected = 0;
        this.secondPlayerSelected = 0;
    }

    private void initCharacters(ImageView playerImage) {
        players.clear();
        players.add(new Dreath(context, playerImage, backgroundImage, backgroundList, selectedBackground));
        players.add(new DreadProphet(context, playerImage, backgroundImage, backgroundList, selectedBackground));
        players.add(new KumoNingyo(context, playerImage, backgroundImage, backgroundList, selectedBackground));
        players.add(new VoidReaper(context, playerImage, backgroundImage, backgroundList, selectedBackground));
    }

    public Player returnYourCharacter() {
        return yourPlayer;
    }

    public Player returnEnemyCharacter() {
        return enemyPlayer;
    }

    public void initializeYourViews() {
        yourImage.setScaleX(1);

        initCharacters(yourImage);
        firstPlayerSelected = random.nextInt(players.size());
        yourPlayer = players.get(firstPlayerSelected);

        yourName.setText(yourPlayer.getName());
        yourHealthText.setText(String.valueOf(yourPlayer.getHealth()));
        yourHealth.setMax(yourPlayer.getHealth());
        yourHealth.setProgress(yourPlayer.getHealth());
        yourImage.setImageResource(yourPlayer.getImage());

        yourStunText.setText(yourPlayer.getStun() + "");

        if (yourPlayer.getImageDirection().equals("left")) {
            yourImage.setScaleX(-1);
        }

        resizeImage.scale(yourImage, yourPlayer.getSize());
    }

    public void initializeEnemyViews() {
        enemyImage.setScaleX(1);

        initCharacters(enemyImage);
        do {
            secondPlayerSelected = random.nextInt(players.size());
        } while (firstPlayerSelected == secondPlayerSelected);
        enemyPlayer = players.get(secondPlayerSelected);

        players.clear();

        enemyName.setText(enemyPlayer.getName());
        enemyHealthText.setText(String.valueOf(enemyPlayer.getHealth()));
        enemyHealth.setMax(enemyPlayer.getHealth());
        enemyHealth.setProgress(enemyPlayer.getHealth());
        enemyImage.setImageResource(enemyPlayer.getImage());

        enemyStunText.setText(enemyPlayer.getStun() + "");

        if (enemyPlayer.getImageDirection().equals("right")) {
            enemyImage.setScaleX(-1);
        }

        resizeImage.scale(enemyImage, enemyPlayer.getSize());
    }
}
