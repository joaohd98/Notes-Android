package com.deck.yugioh.Utils.Helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class Helpers {

    public static LayerDrawable getBorders(int backgroundColor, int borderColor, int left, int top, int right, int bottom){

        ColorDrawable borderColorDrawable = new ColorDrawable(borderColor);
        ColorDrawable backgroundColorDrawable = new ColorDrawable(backgroundColor);

        Drawable[] drawables = new Drawable[]{
                borderColorDrawable,
                backgroundColorDrawable
        };

        LayerDrawable layerDrawable = new LayerDrawable(drawables);

        layerDrawable.setLayerInset(1, left, top, right, bottom);

        return layerDrawable;

    }

    public static void removeFocusClickOutside(Activity activity, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            View v = activity.getCurrentFocus();

            if (v instanceof EditText) {

                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);

                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

                    if (imm != null)
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                }

            }

        }

    }

}
