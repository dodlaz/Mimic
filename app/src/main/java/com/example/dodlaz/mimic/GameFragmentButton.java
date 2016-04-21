package com.example.dodlaz.mimic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by dodlaz on 2016-04-07.
 */

public class GameFragmentButton extends Fragment {
    private static final String TAG = "GameFragmentButton";
    private static final int[] BT_IDS = {
            R.id.b1_1, R.id.b1_2, R.id.b1_3,
            R.id.b2_1, R.id.b2_2, R.id.b2_3,
            R.id.b3_1, R.id.b3_2, R.id.b3_3,
    };
    private static Button[] bt = new Button[BT_IDS.length];
    private TextView guidanceText;

    private Map<String, Integer> team1 = new HashMap<String, Integer>();

    /*
    private static String[][] colors = {{"White", ""}, {"Red", ""}, {"Green", ""},
            {"Yellow", "Blue", "Pink",
            "Cyan", "Gray", "Black"};*/

/*
    List<String> colors = Arrays.asList("White", "Red", "Green",
            "Yellow", "Blue", "Pink",
            "Cyan", "Gray", "Black");*/


    private static boolean longPressAnswer;
    private static String buttonColorAnswer;
    private static boolean buttonTextAnser;
    private static Random rnd = new Random();


    //==========================================================
    public GameFragmentButton() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gamefragment_button, container, false);


        for (int i = 0; i < BT_IDS.length; i++) {
            Button button = (Button) rootView.findViewById(BT_IDS[i]);
            button.setOnClickListener(buttonClick);
            button.setOnLongClickListener(longButtonClick);
            bt[i] = button;
        }
        ButtonShuffle();


        team1.put(getResources().getString(R.string.White), getResources().getColor(R.color.White));
        team1.put(getResources().getString(R.string.Red), getResources().getColor(R.color.Red));
        team1.put(getResources().getString(R.string.Green), getResources().getColor(R.color.Green));
        team1.put(getResources().getString(R.string.Yellow), getResources().getColor(R.color.Yellow));
        team1.put(getResources().getString(R.string.Blue), getResources().getColor(R.color.Blue));
        team1.put(getResources().getString(R.string.Pink), getResources().getColor(R.color.Pink));
        team1.put(getResources().getString(R.string.Cyan), getResources().getColor(R.color.Cyan));
        team1.put(getResources().getString(R.string.Gray), getResources().getColor(R.color.Gray));
        team1.put(getResources().getString(R.string.Black), getResources().getColor(R.color.Black));

        List keys = new ArrayList(team1.keySet());
        Collections.shuffle(keys);
        Collections.shuffle(keys);
        for (Object o : keys) {
            // Access keys/values in a random order
            team1.get(o);
        }



        /*
        Log.v(TAG, colors.get(0));
        Collections.shuffle(colors);
        Log.v(TAG, colors.get(0));*/


        longPressAnswer = Math.random() < 0.5;
        buttonColorAnswer = "RED";//colors.get(rnd.nextInt(8 - 0) + 0);
        buttonTextAnser = Math.random() < 0.5;


        String text;
        if (longPressAnswer) {
            text = "Long press the ";
        } else {
            text = "Press the ";
        }
        if (buttonTextAnser) {
            text += "text saying " + buttonColorAnswer + ".";
        } else {
            text += buttonColorAnswer + " button.";
        }


        guidanceText = (TextView) rootView.findViewById(R.id.guidance_text);
        guidanceText.setText(text);


        return rootView;
    }


    View.OnClickListener buttonClick = new View.OnClickListener() {
        public void onClick(View v) {
            if (longPressAnswer) {
                return;
            }
            for (int i = 0; i < BT_IDS.length; i++) {
                if (v.getId() == BT_IDS[i]) {
                    //bt[i].setText("Click: " + i);
                    break;
                }
            }
        }
    };
    View.OnLongClickListener longButtonClick = new View.OnLongClickListener() {
        public boolean onLongClick(View v) {
            if (!longPressAnswer) {
                return true;
            }
            for (int i = 0; i < BT_IDS.length; i++) {
                if (v.getId() == BT_IDS[i]) {
                    bt[i].setText("LongClick: " + i);
                    return true;
                }
            }
            return false;
        }
    };

    private void ButtonShuffle() {
        int[] rainbow = getResources().getIntArray(R.array.ButtonGameColors);
        shuffleArray(rainbow);
        for (int i = 0; i < BT_IDS.length; i++) {
            bt[i].setBackgroundColor(rainbow[i]);
            bt[i].setText("RED");//String.format("#%06X", 0xFFFFFF & rainbow[i]));
        }
    }

    static void shuffleArray(int[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }


}
