package com.example.dodlaz.mimic;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    private static final int TIME = 5*1000;
    private int completed = 0;
    private TextView game_textview_timer;
    private FragmentManager manager;

    private Fragment[] gf;
    private Fragment gameFragmentGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //INIT
        gf = new Fragment[]{//TODO Do this work?
                new GameFragmentLocate(),
                new GameFragmentShake(),
                new GameFragmentLight(),
                new GameFragmentButton(),
                new GameFragmentCode()
        };
        gameFragmentGameOver = new GameFragmentGameOver();
        manager = getSupportFragmentManager();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        game_textview_timer = (TextView) findViewById(R.id.game_textview_timer);

        //Start
        nextLevel();
        mCountDownTimer.start();
    }


    CountDownTimer mCountDownTimer = new CountDownTimer(TIME, 1000) {
        public void onTick(long millisUntilFinished) {
            game_textview_timer.setText(
                    getString(R.string.n_sec, millisUntilFinished / 1000)
            );
        }
        public void onFinish() {
            game_textview_timer.setText(getString(R.string.game_over));

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_game, gameFragmentGameOver);
            transaction.commit();
        }
    };

    public int getCompleted() {
        return completed;
    }
    public void incCompleted() {
        completed += 1;
        nextLevel();
    }
    public void nextLevel() {
        /*
        new Handler().post(new Runnable() {
            public void run() {*/
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_game, gf[completed % gf.length]);
        transaction.commit();

            /*}
        });*/
    }
}
