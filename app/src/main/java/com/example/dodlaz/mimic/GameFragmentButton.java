package com.example.dodlaz.mimic;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by dodlaz on 2016-04-07.
 */

public class GameFragmentButton extends Fragment {
    private class ButtonColor {
        private String text;
        private int color;

        ButtonColor(String t, int c) {
            text = t;
            color = c;
        }

        public int getColor() {
            return color;
        }

        public String getText() {
            return text;
        }
    }

    private static final String TAG = "GameFragmentButton";
    private static final int[] BT_IDS = {
            R.id.b1_1, R.id.b1_2, R.id.b1_3,
            R.id.b2_1, R.id.b2_2, R.id.b2_3,
            R.id.b3_1, R.id.b3_2, R.id.b3_3,
    };
    private static Button[] bt = new Button[BT_IDS.length];
    private TextView guidanceText;

    private ButtonColor[] colors;


    private static boolean longPressAnswer;
    private static ButtonColor buttonColorAnswer;
    private static boolean buttonTextAnser;
    private static Random rnd = new Random();


    //==========================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gamefragment_button, container, false);

        colors = new ButtonColor[]{
                new ButtonColor("White",  ContextCompat.getColor(getContext(), R.color.White)),
                new ButtonColor("Red",    ContextCompat.getColor(getContext(), R.color.Red)),
                new ButtonColor("Green",  ContextCompat.getColor(getContext(), R.color.Green)),
                new ButtonColor("Yellow", ContextCompat.getColor(getContext(), R.color.Yellow)),
                new ButtonColor("Blue",   ContextCompat.getColor(getContext(), R.color.Blue)),
                new ButtonColor("Pink",   ContextCompat.getColor(getContext(), R.color.Pink)),
                new ButtonColor("Cyan",   ContextCompat.getColor(getContext(), R.color.Cyan)),
                new ButtonColor("Gray",   ContextCompat.getColor(getContext(), R.color.Gray)),
                new ButtonColor("Black",  ContextCompat.getColor(getContext(), R.color.Black))
        };

        ButtonColor[] b_colors = colors.clone();

        shuffleArray(colors);
        shuffleArray(b_colors);
        for (int i = 0; i < BT_IDS.length; i++) {
            Button button = (Button) rootView.findViewById(BT_IDS[i]);
            button.setOnClickListener(buttonClick);
            button.setOnLongClickListener(longButtonClick);
            button.setBackgroundColor(b_colors[i].getColor());
            button.setText(colors[i].getText());
            bt[i] = button;
        }


        longPressAnswer = Math.random() < 0.5;
        buttonColorAnswer = colors[rnd.nextInt(colors.length - 0) + 0];
        buttonTextAnser = Math.random() < 0.;


        String text;
        if (longPressAnswer) {
            text = "Long press the ";
        } else {
            text = "Press the ";
        }
        if (buttonTextAnser) {
            text += "text saying " + buttonColorAnswer.getText() + ".";
        } else {
            text += buttonColorAnswer.getText() + " button.";
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
                    if ((buttonTextAnser
                            && buttonColorAnswer.getText() == bt[i].getText())
                            || (!buttonTextAnser
                            && buttonColorAnswer.getColor() == ((ColorDrawable) bt[i].getBackground()).getColor()
                    )) {
                        Activity activity123 = getActivity();
                        if (activity123 instanceof GameActivity) {
                            ((GameActivity) activity123).incCompleted();
                        }
                        break;
                    }
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
                    if ((buttonTextAnser
                            && buttonColorAnswer.getText() == bt[i].getText())
                            || (!buttonTextAnser
                            && buttonColorAnswer.getColor() == ((ColorDrawable) bt[i].getBackground()).getColor()
                    )) {
                        Activity activity123 = getActivity();
                        if (activity123 instanceof GameActivity) {
                            ((GameActivity) activity123).incCompleted();
                        }
                        break;
                    }
                }
            }
            return false;
        }
    };


    static void shuffleArray(ButtonColor[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            ButtonColor a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }


}
