package com.example.dodlaz.mimic;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

/**
 * Created by dodlaz on 2016-04-07.
 */
public class GameFragmentShake extends Fragment {
    private static final String TAG = "GameFragmentShake";
    private static final float SHAKE_THRESHOLD = 3.25f;
    private static final int MIN_TIME_BETWEEN_SHAKES_MILLISECS = 700;
    private static final int COUNTER_GOAL = 2;
    private int counter = 0;

    private long lastTime;

    private ProgressBar progressBar;
    private int progressBarStatus = 0;
    private Handler mHandler = new Handler();
    private MediaPlayer musik;

    private SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gamefragment_shake, container, false);

        SensorManager mSensorMgr = (SensorManager) getActivity()
                .getSystemService(Context.SENSOR_SERVICE);

        // Listen for shakes
        Sensor accelerometer = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            mSensorMgr.registerListener(shakeListener,
                    accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        //Musik
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(sp.getBoolean("Sound", false)) {
            musik = MediaPlayer.create(getContext(), R.raw.dance);
            musik.setLooping(true);
            musik.start();
        }

        //Animate
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        rootView.findViewById(R.id.imageView).startAnimation(shake);

        progressBar = (ProgressBar) rootView.findViewById(R.id.shake_game_progressBar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressBarStatus < 100) {
                    if (counter >= COUNTER_GOAL) {
                        progressBar.setProgress(0);
                        progressBar.setSecondaryProgress(0);
                        break;
                    }

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if ((100 / COUNTER_GOAL) * counter > progressBarStatus) {
                        progressBarStatus += 1;
                    } else {
                        progressBarStatus -= 1;
                    }

                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });

                }
            }
        }).start();


        return rootView;
    }


    private SensorEventListener shakeListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
                return;
            }

            long time = System.currentTimeMillis();
            if ((time - lastTime) > MIN_TIME_BETWEEN_SHAKES_MILLISECS) {
                double acceleration = Math.sqrt(
                        Math.pow(event.values[0], 2) + //X
                                Math.pow(event.values[1], 2) + //Y
                                Math.pow(event.values[2], 2)   //Z
                ) - SensorManager.GRAVITY_EARTH;

                if (acceleration > SHAKE_THRESHOLD) {
                    lastTime = time;
                    counter += 1;

                    if (counter >= COUNTER_GOAL) {

                        Activity gActivity = getActivity();
                        if (gActivity instanceof GameActivity) {
                            ((GameActivity) gActivity).incCompleted();
                        }

                    }
                } else {
                    counter = (counter - 2 < 0 ? 0 : counter - 2);
                }
                progressBar.setSecondaryProgress((100 / COUNTER_GOAL) * counter);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    public void onStop() {
        if(sp.getBoolean("Sound", false)) {
            musik.stop();
        }
        super.onStop();
    }
}
