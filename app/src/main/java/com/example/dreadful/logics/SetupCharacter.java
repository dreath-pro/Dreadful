package com.example.dreadful.logics;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

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
    private ImageView yourImage, enemyImage;
    private ConstraintLayout backgroundImage;
    private Player yourPlayer, enemyPlayer;
    private ArrayList<Player> players = new ArrayList<>();
    private Random random = new Random();
    private ResizeImage resizeImage;

    public SetupCharacter(Context context, TextView yourName, ProgressBar yourHealth, TextView yourHealthText, ImageView yourImage,
                          TextView enemyName, ProgressBar enemyHealth, TextView enemyHealthText, ImageView enemyImage,
                          Player yourPlayer, Player enemyPlayer, ConstraintLayout backgroundImage) {

        this.context = context;
        this.yourName = yourName;
        this.yourHealth = yourHealth;
        this.yourHealthText = yourHealthText;
        this.yourImage = yourImage;

        this.enemyName = enemyName;
        this.enemyHealth = enemyHealth;
        this.enemyHealthText = enemyHealthText;
        this.enemyImage = enemyImage;

        this.backgroundImage = backgroundImage;

        this.yourPlayer = yourPlayer;
        this.enemyPlayer = enemyPlayer;

        this.resizeImage = new ResizeImage(context);
    }

    private void initCharacters(ImageView playerImage) {
        players.clear();
        players.add(new Dreath(context, playerImage, backgroundImage));
        players.add(new DreadProphet(context, playerImage, backgroundImage));
        players.add(new KumoNingyo(context, playerImage, backgroundImage));
        players.add(new VoidReaper(context, playerImage, backgroundImage));
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
        yourPlayer = players.get(random.nextInt(players.size()));

        yourName.setText(yourPlayer.getName());
        yourHealthText.setText(String.valueOf(yourPlayer.getHealth()));
        yourHealth.setMax(yourPlayer.getHealth());
        yourHealth.setProgress(yourPlayer.getHealth());
        yourImage.setImageResource(yourPlayer.getImage());

        if (yourPlayer.getImageDirection().equals("left")) {
            yourImage.setScaleX(-1);
        }

        resizeImage.scale(yourImage, yourPlayer.getSize());
    }

    public void initializeEnemyViews() {
        enemyImage.setScaleX(1);

        initCharacters(enemyImage);
        enemyPlayer = players.get(random.nextInt(players.size()));

        enemyName.setText(enemyPlayer.getName());
        enemyHealthText.setText(String.valueOf(enemyPlayer.getHealth()));
        enemyHealth.setMax(enemyPlayer.getHealth());
        enemyHealth.setProgress(enemyPlayer.getHealth());
        enemyImage.setImageResource(enemyPlayer.getImage());

        if (enemyPlayer.getImageDirection().equals("right")) {
            enemyImage.setScaleX(-1);
        }


        ViewGroup.LayoutParams layoutParams = enemyImage.getLayoutParams();
        int widthInDp = enemyPlayer.getSize(); // Desired width in dp
        int heightInDp = enemyPlayer.getSize(); // Desired height in dp

        float scale = context.getResources().getDisplayMetrics().density;
        int widthInPixels = (int) (widthInDp * scale + 0.5f);
        int heightInPixels = (int) (heightInDp * scale + 0.5f);

        layoutParams.width = widthInPixels;
        layoutParams.height = heightInPixels;

        enemyImage.setLayoutParams(layoutParams);
    }
}
