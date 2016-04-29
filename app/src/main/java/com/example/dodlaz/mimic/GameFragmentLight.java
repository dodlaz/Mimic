package com.example.dodlaz.mimic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by dodlaz on 2016-04-07.
 */
public class GameFragmentLight extends Fragment {
    private static final String TAG = "GameFragmentButton";

    TextView textLIGHT_available, textLIGHT_reading;
    LinearLayout light_game;

    private TextView guidance_text;
    private Boolean toDark;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gamefragment_light, container, false);

        toDark = Math.random() < 0.5;
        guidance_text = (TextView) rootView.findViewById(R.id.guidance_text);
        if (toDark) {
            guidance_text.setText(getResources().getString(R.string.ins_light_game_to_dark));
        } else {
            guidance_text.setText(getResources().getString(R.string.ins_light_game_to_bright));
        }

        light_game = (LinearLayout) rootView.findViewById(R.id.light_game);

        textLIGHT_available = (TextView) rootView.findViewById(R.id.LIGHT_available);
        textLIGHT_reading = (TextView) rootView.findViewById(R.id.LIGHT_reading);

        SensorManager mySensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        Sensor LightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (LightSensor != null) {
            textLIGHT_available.setText("Sensor.TYPE_LIGHT Available");
            mySensorManager.registerListener(
                    LightSensorListener,
                    LightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        } else {
            textLIGHT_available.setText("Sensor.TYPE_LIGHT NOT Available");
        }


        return rootView;
    }


    private SensorEventListener LightSensorListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {

                int v = (int) (event.values[0] * 6);
                v = (v > 255 ? 255 : v); //to prevent overflow
                light_game.setBackgroundColor(Color.argb(255, v, v, v));
                textLIGHT_reading.setText("LIGHT: " + event.values[0] + " -> " + v);

                if (((toDark && v <= 5) || (!toDark && v >= 250))) {
                    textLIGHT_reading.setText("a");

                    Activity gActivity = getActivity();
                    if(gActivity instanceof GameActivity) {
                        ((GameActivity) gActivity).incCompleted();
                    }
                }

            }
        }
    };

}

