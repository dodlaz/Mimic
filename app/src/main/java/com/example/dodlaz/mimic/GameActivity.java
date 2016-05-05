package com.example.dodlaz.mimic;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameActivity extends AppCompatActivity {
    private static final int TIME = 120 * 1000;
    private int completed = 0;
    private TextView game_textView_timer;
    private FragmentManager manager;

    private Fragment[] gf;
    private Fragment gameFragmentGameOver;
    private Lock lock = new ReentrantLock();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //INIT
        gf = new Fragment[]{
                new GameFragmentCode(),
                //new GameFragmentSound(),//TODO don't work
                new GameFragmentLocate(),
                new GameFragmentShake(),
                new GameFragmentLight(),
                new GameFragmentButton(),
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


        game_textView_timer = (TextView) findViewById(R.id.game_textview_timer);

        //Start
        nextLevel();
        mCountDownTimer.start();
    }


    CountDownTimer mCountDownTimer = new CountDownTimer(TIME, 1000) {
        public void onTick(long millisUntilFinished) {
            game_textView_timer.setText(
                    getString(R.string.n_sec, millisUntilFinished / 1000)
            );
        }

        public void onFinish() {
            playSound(R.raw.game_over);
            game_textView_timer.setText(getString(R.string.game_over));

            replaceLevel(gameFragmentGameOver);
        }
    };

    public void incCompleted() {
        completed += 1;
        nextLevel();
    }

    public int getCompleted() {
        return completed;
    }

    public void nextLevel() {
        replaceLevel(gf[completed % gf.length]);
    }

    public void replaceLevel(final Fragment location) {
        try {
            lock.lock();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_game, location);
            transaction.commit();
            manager.executePendingTransactions();
        } finally {
            lock.unlock();
        }
    }


    private void playSound(final int sound) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getBoolean("Sound", false)) {
            MediaPlayer mPlayer;
            mPlayer = MediaPlayer.create(GameActivity.this, sound);
            mPlayer.start();
        }
    }


}
