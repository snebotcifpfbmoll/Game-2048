package com.serafinebot.dint.game_1024.touch;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeListener implements View.OnTouchListener {
    private final GestureDetector gestureDetector;

    public OnSwipeListener(Context ctx, final OnSwipeListenerDelegate delegate, View mainView) {
        mainView.setOnTouchListener(this);
        gestureDetector = new GestureDetector(ctx, new GestureListener() {
            @Override
            public void onSwipeRight() {
                if (delegate != null) delegate.didSwipe(SwipeDirection.RIGHT);
            }

            @Override
            public void onSwipeLeft() {
                if (delegate != null) delegate.didSwipe(SwipeDirection.LEFT);
            }

            @Override
            public void onSwipeBottom() {
                if (delegate != null) delegate.didSwipe(SwipeDirection.BOTTOM);
            }

            @Override
            public void onSwipeTop() {
                if (delegate != null) delegate.didSwipe(SwipeDirection.TOP);
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
