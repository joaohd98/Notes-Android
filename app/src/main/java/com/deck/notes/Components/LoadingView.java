package com.deck.notes.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.deck.notes.R;
import com.deck.notes.Utils.Helpers.Helpers;

public class LoadingView extends ConstraintLayout {

    private ConstraintLayout constraintLayout;

    public LoadingView(Context context) {
        super(context);
        this.init(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context);
    }

    private void init(Context context){

        View view = inflate(context, R.layout.sample_loading_view, this);

        this.constraintLayout = view.findViewById(R.id.view_loading);

        view.setEnabled(false);

    }

    public void show() {

        this.changeVisibility(true);

    }

    public void hide() {

        this.changeVisibility(false);

    }

    private void changeVisibility(boolean show) {

        if(show) {

            Helpers.setTouchable(getContext(), false);

            this.constraintLayout.setVisibility(VISIBLE);

        }

        else {

            Helpers.setTouchable(getContext(), true);

            this.constraintLayout.setVisibility(INVISIBLE);

        }

    }

}


