package com.example.dreadful.utils;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dreadful.characters.Dreath;
import com.example.dreadful.characters.KumoNingyo;
import com.example.dreadful.characters.DreadProphet;
import com.example.dreadful.models.Character;

import java.util.ArrayList;
import java.util.Random;

public class SetupCharacter {
    private Context context;
    private TextView yourName, enemyName;
    private ProgressBar yourHealth, enemyHealth;
    private TextView yourHealthText, enemyHealthText;
    private ImageView yourImage, enemyImage;
    private Character yourCharacter, enemyCharacter;
    private ArrayList<Character> characters = new ArrayList<>();
    private Random random = new Random();

    public SetupCharacter(Context context, TextView yourName, ProgressBar yourHealth, TextView yourHealthText, ImageView yourImage,
                          TextView enemyName, ProgressBar enemyHealth, TextView enemyHealthText, ImageView enemyImage,
                          Character yourCharacter, Character enemyCharacter) {

        this.context = context;
        this.yourName = yourName;
        this.yourHealth = yourHealth;
        this.yourHealthText = yourHealthText;
        this.yourImage = yourImage;

        this.enemyName = enemyName;
        this.enemyHealth = enemyHealth;
        this.enemyHealthText = enemyHealthText;
        this.enemyImage = enemyImage;

        this.yourCharacter = yourCharacter;
        this.enemyCharacter = enemyCharacter;
    }

    private void initCharacters() {
        characters.clear();
        characters.add(new Dreath());
        characters.add(new DreadProphet());
        characters.add(new KumoNingyo());
    }

    public Character returnYourCharacter() {
        return yourCharacter;
    }

    public Character returnEnemyCharacter() {
        return enemyCharacter;
    }

    public void initializeYourViews() {
        yourImage.setScaleX(1);

        initCharacters();
        yourCharacter = characters.get(random.nextInt(characters.size()));

        yourName.setText(yourCharacter.getName());
        yourHealthText.setText(String.valueOf(yourCharacter.getHealth()));
        yourHealth.setMax(yourCharacter.getHealth());
        yourHealth.setProgress(yourCharacter.getHealth());
        yourImage.setImageResource(context.getResources().getIdentifier(yourCharacter.getImage(), "drawable", context.getPackageName()));

        if (yourCharacter.getImageDirection().equals("left")) {
            yourImage.setScaleX(-1);
        }

        ViewGroup.LayoutParams layoutParams = yourImage.getLayoutParams();
        int widthInDp = yourCharacter.getSize(); // Desired width in dp
        int heightInDp = yourCharacter.getSize(); // Desired height in dp

        float scale = context.getResources().getDisplayMetrics().density;
        int widthInPixels = (int) (widthInDp * scale + 0.5f);
        int heightInPixels = (int) (heightInDp * scale + 0.5f);

        layoutParams.width = widthInPixels;
        layoutParams.height = heightInPixels;

        yourImage.setLayoutParams(layoutParams);
    }

    public void initializeEnemyViews() {
        enemyImage.setScaleX(1);

        initCharacters();
        enemyCharacter = characters.get(random.nextInt(characters.size()));

        enemyName.setText(enemyCharacter.getName());
        enemyHealthText.setText(String.valueOf(enemyCharacter.getHealth()));
        enemyHealth.setMax(enemyCharacter.getHealth());
        enemyHealth.setProgress(enemyCharacter.getHealth());
        enemyImage.setImageResource(context.getResources().getIdentifier(enemyCharacter.getImage(), "drawable", context.getPackageName()));

        if (enemyCharacter.getImageDirection().equals("right")) {
            enemyImage.setScaleX(-1);
        }


        ViewGroup.LayoutParams layoutParams = enemyImage.getLayoutParams();
        int widthInDp = enemyCharacter.getSize(); // Desired width in dp
        int heightInDp = enemyCharacter.getSize(); // Desired height in dp

        float scale = context.getResources().getDisplayMetrics().density;
        int widthInPixels = (int) (widthInDp * scale + 0.5f);
        int heightInPixels = (int) (heightInDp * scale + 0.5f);

        layoutParams.width = widthInPixels;
        layoutParams.height = heightInPixels;

        enemyImage.setLayoutParams(layoutParams);
    }
}
