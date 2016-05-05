package com.example.dodlaz.mimic;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by dodlaz on 2016-04-07.
 */
public class GameFragmentSound extends Fragment {
    private static final String TAG = "GameFragmentSound";
    private static final double MAX_VOLUME = 10000.0d;
    private static final int MAX_AMOUNT = 3;
    private int amount = 0;
    private double volume;
    private TextView guidance_text;
    private SoundMeter soundMeter;

    private Handler mHandler = new Handler();
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gamefragment_sound, container, false);

        guidance_text = (TextView) rootView.findViewById(R.id.guidance_text);
        activity = getActivity();

        soundMeter = new SoundMeter();

        new Thread(new Runnable() {
            @Override
            public void run() {
                soundMeter.start();
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mHandler.post(new Runnable() {
                        public void run() {
                            volume = soundMeter.getAmplitude();
                            if (MAX_VOLUME <= volume) {
                                amount++;
                            } else {
                                guidance_text.setText("getMaxAmplitude: " + volume);
                            }
                            if(MAX_AMOUNT<=amount){
                                volume = 0.0d;
                                if (activity instanceof GameActivity) {
                                    ((GameActivity) activity).incCompleted();
                                }
                            }
                        }
                    });
                }
            }
        }).start();


        return rootView;
    }

    @Override
    public void onStop() {
        soundMeter.stop();
        super.onStop();
    }


    private class SoundMeter {

        private MediaRecorder mRecorder = null;

        public void start() {
            if (mRecorder == null) {
                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mRecorder.setOutputFile("/dev/null");
                try {
                    mRecorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mRecorder.start();
            }
        }

        public void stop() {
            if (mRecorder != null) {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
            }
        }

        public double getAmplitude() {
            if (mRecorder != null)
                return mRecorder.getMaxAmplitude();
            else
                return 0;

        }
    }

}
