package com.deck.yugioh.Utils.Helpers;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

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

}
