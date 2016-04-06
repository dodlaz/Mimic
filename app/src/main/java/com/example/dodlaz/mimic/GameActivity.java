package com.example.dodlaz.mimic;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        game_textview_timer = (TextView) findViewById(R.id.game_textview_timer);

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                game_textview_timer.setText(
                        getApplicationContext().getResources().getString(R.string.sec_remaining)
                                + ": "
                                + millisUntilFinished / 1000);
            }

            public void onFinish() {
                game_textview_timer.setText(getApplicationContext()
                                .getResources()
                                .getString(R.string.game_over)
                );
            }
        }.start();
    }

}
