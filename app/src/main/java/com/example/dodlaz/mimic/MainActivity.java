package com.example.dodlaz.mimic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(myhandler);

        text = (TextView) findViewById(R.id.textView);

    }
    View.OnClickListener myhandler = new View.OnClickListener() {
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            //text.setText("Box: " + (sp.getBoolean("Music", false) ? "True" : "False"));
        }
    };
}
