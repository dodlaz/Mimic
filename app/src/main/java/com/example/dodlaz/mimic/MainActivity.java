package com.example.dodlaz.mimic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    Button main_button_settings;
    Button main_button_play;
    TextView h1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_button_play = (Button) findViewById(R.id.main_button_play);
        main_button_play.setOnClickListener(button);
        main_button_settings = (Button) findViewById(R.id.main_button_settings);
        main_button_settings.setOnClickListener(button);

        h1 = (TextView) findViewById(R.id.main_textview_h1);

    }
    View.OnClickListener button = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_button_play:
                    startActivity(new Intent(getApplicationContext(), GameActivity.class));
                    break;
                case R.id.main_button_settings:
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));

                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    //text.setText("Box: " + (sp.getBoolean("Music", false) ? "True" : "False"));
                    break;
            }
        }
    };




}
