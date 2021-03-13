package com.cnux9.fliptimer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TimerView extends androidx.appcompat.widget.AppCompatTextView {
    public int minute;
    private float previousX;
    private int previousMinute;
    public boolean isAdjustable = true;

    public TimerView(@NonNull Context context) {
        super(context);
    }

    public TimerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        minute = context.obtainStyledAttributes(attrs, R.styleable.TimerView)
                .getInteger(R.styleable.TimerView_minute, 60);
        setTextByMin(minute);
    }

    public TimerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        minute = context.obtainStyledAttributes(attrs, R.styleable.TimerView)
                .getInteger(R.styleable.TimerView_minute, 60);
        setTextByMin(minute);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isAdjustable) {
            float x = event.getX();

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    previousX = x;
                    previousMinute = minute;
                    this.setTextSize(TypedValue.COMPLEX_UNIT_DIP,112);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    int delta = (int)((x - previousX) / 50);
                    Log.e("dd",delta+"\n");
                    int temp;
                    if (previousMinute > 59) {
                        temp = (previousMinute / 60) + 59;
                    }else {
                        temp = previousMinute;
                    }
                    temp += delta;

                    Log.e("dd","delta: " + delta+" // temp: " + temp + " // minute: " + minute);
                    if (temp > 59) {
                        temp = (temp - 59) * 60;
                    }
                    minute = temp;

                    if (minute < 1) {
                        minute = 1;
                    } else if (minute > 99 * 60) {
                        minute = 99 * 60;
                    }
                    setTextByMin(minute);
                    return true;
                case MotionEvent.ACTION_UP:
                    this.setTextSize(TypedValue.COMPLEX_UNIT_DIP,96);
            }
        }
        return false;
    }

    public void setTextByMin(int min) {
        setText(String.format("%d:%02d", min / 60, min % 60));
    }
}
