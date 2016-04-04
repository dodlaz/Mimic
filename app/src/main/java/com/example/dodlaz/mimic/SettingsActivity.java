package com.example.dodlaz.mimic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;


public class SettingsActivity extends AppCompatActivity {
    CheckBox cb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cb = (CheckBox) findViewById(R.id.checkBox);
        loadPrefs();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePrefs("b",cb.isChecked());
                changeText("HEJ");
            }
        });


    }

    private void changeText(String text){
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void savePrefs(String key, boolean value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    private void loadPrefs(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean cbValue = sp.getBoolean("b", false);
        cb.setChecked(cbValue);
    }

}
