package com.example.dreadful.logics;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ResizeImage {
    private Context context;

    public ResizeImage(Context context) {
        this.context = context;
    }

    // average = 150, huge = 170, titan = 210
    public void scale(ImageView yourImage, int size) {
        ViewGroup.LayoutParams layoutParams = yourImage.getLayoutParams();

        float scale = context.getResources().getDisplayMetrics().density;
        int widthInPixels = (int) (size * scale + 0.5f);
        int heightInPixels = (int) (size * scale + 0.5f);

        layoutParams.width = widthInPixels;
        layoutParams.height = heightInPixels;

        yourImage.setLayoutParams(layoutParams);
    }
}
