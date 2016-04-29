package com.example.dodlaz.mimic;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by dodlaz on 2016-04-07.
 */
public class GameFragmentGameOver extends Fragment {
    private static final String TAG = "GameFragmentGameOver";
    private TextView guidance_text;
    private TableLayout scoreTable;
    private View rootView;
    private MyDB db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.gamefragment_gameover, container, false);

        //getScore
        int score = ((GameActivity) getActivity()).getCompleted();

        //Score Text
        guidance_text = (TextView) rootView.findViewById(R.id.guidance_text);
        guidance_text.setText(getResources().getString(R.string.your_score) + ": " + score);

        //DB
        db = new MyDB(rootView.getContext());
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
        TextView date = new TextView(getActivity());
        TextView p = new TextView(getActivity());
        p.setGravity(Gravity.RIGHT);//TODO set it to END
        date.setText(time);
        p.setText(point);

        TableRow row = new TableRow(getActivity());
        row.addView(date);
        row.addView(p);

        scoreTable = (TableLayout) rootView.findViewById(R.id.scoreTable);
        scoreTable.addView(row);
    }


}

