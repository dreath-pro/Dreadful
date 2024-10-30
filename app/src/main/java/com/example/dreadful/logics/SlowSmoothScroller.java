package com.example.dreadful.logics;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;

public class SlowSmoothScroller extends LinearSmoothScroller {

    public SlowSmoothScroller(Context context) {
        super(context);
    }

    @Override
    public PointF computeScrollVectorForPosition(int targetPosition) {
        return ((LinearLayoutManager) getLayoutManager()).computeScrollVectorForPosition(targetPosition);
    }

    @Override
    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return super.calculateSpeedPerPixel(displayMetrics) * 8f;
    }
}
