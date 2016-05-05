package com.example.dodlaz.mimic;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Button donate = (Button) findViewById(R.id.donate_button);
        donate.setOnClickListener(button);

    }

    View.OnClickListener button = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.donate_button:
                    Intent viewIntent = new Intent("android.intent.action.VIEW",
                            Uri.parse(getString(R.string.donate_url))
                    );
                    startActivity(viewIntent);
                    break;
            }
        }
    };
}
