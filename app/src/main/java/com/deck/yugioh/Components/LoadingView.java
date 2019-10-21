package com.deck.yugioh.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.deck.yugioh.R;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingView extends ConstraintLayout {

    private ConstraintLayout constraintLayout;
    private TextView textView;

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

        this.textView = view.findViewById(R.id.view_loading_text);
        this.constraintLayout = view.findViewById(R.id.view_loading);


        this.setSchedule();

    }

    private void setSchedule() {

        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){

                String txt = textView.getText().toString();

                if(txt.length() > 12)
                    txt = getResources().getString(R.string.components_loading_text);

                else
                    txt += ".";

                textView.setText(txt);

            }

        }, 0, 1000);

    }

    public void show() {

        this.constraintLayout.setVisibility(VISIBLE);

    }

    public void hide() {

        this.constraintLayout.setVisibility(INVISIBLE);

    }

}


