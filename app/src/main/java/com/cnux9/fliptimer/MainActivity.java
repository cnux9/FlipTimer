package com.cnux9.fliptimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class MainActivity extends AppCompatActivity {

    private static ConstraintLayout constraintLayout;
    private static TimerView breakTimerView, workTimerView;
    private static TextView guide;
    private static View onBreak, onWork;
    private static TextView button;
    private boolean isWorking = false;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.constraintLayout);
        workTimerView = findViewById(R.id.workTimerView);
        breakTimerView = findViewById(R.id.breakTimerView);
        guide = findViewById(R.id.guide);
        onWork = findViewById(R.id.onWork);
        onBreak = findViewById(R.id.onBreak);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener {
            if (button.getText().toString().equals(getResources().getString(R.string.start))) {
                swapStatus();
                button.setText(getResources().getString(R.string.stop));
                workTimerView.isAdjustable = false;
                breakTimerView.isAdjustable = false;
                guide.setVisibility(View.INVISIBLE);
            } else {
                updateView(R.layout.activity_main);
                if (timer != null) {
                    timer.cancel();
                }
                button.setText(getResources().getString(R.string.start));
                isWorking = false;
                workTimerView.isAdjustable = true;
                breakTimerView.isAdjustable = true;
                breakTimerView.setTextByMin(breakTimerView.minute);
                workTimerView.setTextByMin(workTimerView.minute);
            }
        })
        guide.setVisibility(View.VISIBLE);
//        Window w = getWindow();
//        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public void swapStatus() {
        int minute;
        if (isWorking) {
            updateView(R.layout.activity_main_break);
            minute = breakTimerView.minute;
            workTimerView.setTextByMin(workTimerView.minute);
        } else {
            updateView(R.layout.activity_main_working);
            minute = workTimerView.minute;
            breakTimerView.setTextByMin(breakTimerView.minute);
        }
        timer = new CountDownTimer(minute * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                onTickAnim(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                swapStatus();
            }
        };
        timer.start();
        isWorking = !isWorking;
    }

    public void onTickAnim(long millisUntilFinished) {
        View indicator;
        //시간 표시 바꾸기
        if (isWorking) {
            indicator = onWork;
            workTimerView.setTextByMin((int) millisUntilFinished / 1000);
        } else {
            indicator = onBreak;
            breakTimerView.setTextByMin((int) millisUntilFinished / 1000);
        }
        //깜박깜박
        if (indicator.getVisibility() == View.INVISIBLE) {
            indicator.setVisibility(View.VISIBLE);
        } else {
            indicator.setVisibility(View.INVISIBLE);
        }
        //만들거 - 진행바
    }

    public void updateView(int id) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this, id);
        constraintSet.applyTo(constraintLayout);

        ChangeBounds trans = new ChangeBounds();
        trans.setInterpolator(new AccelerateInterpolator());
        TransitionManager.beginDelayedTransition(constraintLayout, trans);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}