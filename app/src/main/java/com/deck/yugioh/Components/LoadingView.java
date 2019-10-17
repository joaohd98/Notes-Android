package com.deck.yugioh.Components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.deck.yugioh.R;

public class LoadingView extends View {

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    private void init(){

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
