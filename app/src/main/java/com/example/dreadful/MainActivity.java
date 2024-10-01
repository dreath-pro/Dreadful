package com.example.dreadful;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    //armored crocodile golem, hd detailed cartoon, dark fantasy theme, white background, facing right, standing full view
    //person with spiky armor covered with dark liquid, hd detailed cartoon, dark fantasy theme, white background, facing right, standing full view, red, black, dark-gray, crimson-red

    //experimental
    //inside the cathedral, hd, splash art, minimalist, detailed cartoon, dark fantasy theme, background, red, gray, black, dark-red, crimson-red

    //two swords clashing, simple icon, digital art, dark fantasy theme, vibrant shading, white background, red, crimson-red, black, dark-red

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}