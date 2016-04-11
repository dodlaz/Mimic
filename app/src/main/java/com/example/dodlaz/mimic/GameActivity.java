package com.example.dodlaz.mimic;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    private TextView game_textview_timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        game_textview_timer = (TextView) findViewById(R.id.game_textview_timer);


        Fragment fragment = new GameFragment2();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_game, fragment);
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
            Fragment fragment = new GameFragmentButton();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_game, fragment);
            transaction.commit();
            game_textview_timer.setText(getApplicationContext()
                            .getResources()
                            .getString(R.string.game_over)
            );
        }

    };


    public void back(View view) {
        mCountDownTimer.start();
        Fragment fragment = new GameFragment2();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_game, fragment);
        transaction.commit();
    }

    public void red(View view) {
        Fragment fragment = new GameFragmentButton();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_game, fragment);
        transaction.commit();
    }


}
