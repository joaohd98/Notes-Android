package com.deck.yugioh.Components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.deck.yugioh.R;

public class LoadingView extends ConstraintLayout {

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

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r,b);
    }


    private void init(Context context){

        inflate(context, R.layout.sample_loading_view, this);



//        new Timer().scheduleAtFixedRate(new TimerTask(){
//            @Override
//            public void run(){
//
//                String txt = textView.getText().toString();
//
//                if(txt.length() > 15)
//                    txt = defaultText;
//
//                else
//                    txt += ".";
//
//                textView.setText(txt);
//
//            }
//
//        }, 0, 1000);

    }
}
