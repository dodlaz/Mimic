package com.example.dodlaz.mimic;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.Locale;


public class SettingsActivity extends AppCompatActivity {
    private Switch sound;
    private Button clear_history;
    private static MyDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        db = new MyDB(SettingsActivity.this);


        //Sound
        sound = (Switch) findViewById(R.id.sound_settings);
        loadPrefs();
        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                savePrefs("Sound", sound.isChecked());
                String onoff;
                if(sound.isChecked()){
                    onoff = getApplicationContext().getResources().getString(R.string.on);
                }else{
                    onoff = getApplicationContext().getResources().getString(R.string.off);
                }
                changeText(sound.getText() + ": " + onoff);
            }
        });

        //Clear History
        clear_history = (Button) findViewById(R.id.clear_history);
        clear_history.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ClearDialog newFragment = new ClearDialog ();
                newFragment.show(getSupportFragmentManager().beginTransaction(), "dialog");
            }
        });

    }


    private void changeText(String text) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void savePrefs(String key, boolean value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    private void loadPrefs() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean cbValue = sp.getBoolean("Sound", false);
        sound.setChecked(cbValue);
    }


    public static class ClearDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setTitle(getResources().getString(R.string.clear))
                    .setMessage(getResources().getString(R.string.clear_history_question))
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}})
                    .setPositiveButton(android.R.string.yes,  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Clear
                            db.clearScore();

                            //Notify user
                            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.history_deleted), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    })
                    .create();
        }
    }
}
