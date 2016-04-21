package com.example.dodlaz.mimic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    private int completed = 0;
    private TextView game_textview_timer;
    private final Fragment gameFragmentButton = new GameFragmentButton();
    private final Fragment gameFragmentLight = new GameFragmentLight();
    private final Fragment gameFragmentGameOver = new GameFragmentGameOver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        game_textview_timer = (TextView) findViewById(R.id.game_textview_timer);


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_game, gameFragmentLight);
        transaction.commit();

        mCountDownTimer.start();
    }


    CountDownTimer mCountDownTimer = new CountDownTimer(5000, 1000) {

        public void onTick(long millisUntilFinished) {
            game_textview_timer.setText(
                    getApplicationContext().getResources().getString(R.string.sec_remaining)
                            + ": "
                            + millisUntilFinished / 1000);
        }

        public void onFinish() {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_game, gameFragmentGameOver);
            transaction.commit();
            game_textview_timer.setText(getApplicationContext()
                            .getResources()
                            .getString(R.string.game_over)
            );
        }

    };


    public void incCompleted(){
        completed += 1;
    }
    public int getCompleted(){
        return completed;
    }


    public void back(View view) {
        mCountDownTimer.start();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_game, gameFragmentLight);
        transaction.commit();
    }

    public void red(View view) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_game, gameFragmentButton);
        transaction.commit();
    }


}
