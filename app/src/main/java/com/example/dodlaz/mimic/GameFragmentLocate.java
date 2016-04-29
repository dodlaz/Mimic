package com.example.dodlaz.mimic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by dodlaz on 2016-04-07.
 */
public class GameFragmentLocate extends Fragment {
    private static final String TAG = "GameFragmentLocate";
    TextView guidance_text;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gamefragment_locate, container, false);
        guidance_text = (TextView) rootView.findViewById(R.id.guidance_text);
        guidance_text.setText("Finde me");

        button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(click);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        Random r = new Random();
        int top = r.nextInt(50000)+1;
        int bottom = r.nextInt(50000)+1;

        lp.topMargin = top;
        lp.bottomMargin = bottom;
        lp.gravity = Gravity.CENTER;
        button.setLayoutParams(lp);

        guidance_text.setText("Top: "+top+", Bottom: "+bottom);



        return rootView;
    }

    View.OnClickListener click = new View.OnClickListener() {
        public void onClick(View v) {

            ScrollView sv = (ScrollView) getActivity().findViewById(R.id.scrollView);
            sv.scrollTo(0, sv.getTop());

            Activity gActivity = getActivity();
            if(gActivity instanceof GameActivity) {
                ((GameActivity) gActivity).incCompleted();
            }
        }
    };

}
