package com.example.dodlaz.mimic;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.example.dodlaz.mimic.R.color.White;

/**
 * Created by dodlaz on 2016-04-07.
 */
public class GameFragmentGameOver extends Fragment {
    private static final String TAG = "GameFragmentGameOver";
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.gamefragment_gameover, container, false);

        //getScore
        int score = ((GameActivity) getActivity()).getCompleted();

        //Score Text
        TextView guidance_text = (TextView) rootView.findViewById(R.id.guidance_text);
        guidance_text.setText(getResources().getString(R.string.your_score) + ": " + score);

        //DB
        MyDB db = new MyDB(rootView.getContext());
        db.insScore(score);
        outPutScore(db.getScore());

        return rootView;
    }

    private void outPutScore(Cursor cursor){
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            addRow(
                    cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("score"))
            );
            cursor.moveToNext();
        }
        cursor.close();
    }



    private void addRow(String time, String point) {
        //Init
        TextView date = new TextView(getActivity());
        TextView p = new TextView(getActivity());

        //Gravity
        p.setGravity(Gravity.END);

        //Color
        date.setTextColor(Color.GRAY);
        p.setTextColor(Color.GRAY);

        //Text
        date.setText(time);
        p.setText(point);

        //Table
        TableRow row = new TableRow(getActivity());
        row.addView(date);
        row.addView(p);

        TableLayout scoreTable = (TableLayout) rootView.findViewById(R.id.scoreTable);
        scoreTable.addView(row);
    }


}

