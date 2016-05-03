package com.example.dodlaz.mimic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by dodlaz on 2016-04-07.
 */
public class GameFragmentLocate extends Fragment {
    private static final String TAG = "GameFragmentLocate";
    Button button;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gamefragment_locate, container, false);

        Random r = new Random();
        int top = r.nextInt(50000)+1;
        int bottom = r.nextInt(50000)+1;



        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        imageView.setOnClickListener(click);


        button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(click);
        LinearLayout.LayoutParams lp_b = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lp_b.topMargin = top;
        lp_b.gravity = Gravity.CENTER;
        button.setLayoutParams(lp_b);

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
