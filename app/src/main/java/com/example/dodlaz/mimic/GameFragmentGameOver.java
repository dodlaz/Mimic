package com.example.dodlaz.mimic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.gamefragment_gameover, container, false);

        guidance_text = (TextView) rootView.findViewById(R.id.guidance_text);
        guidance_text.setText("Count: 2");




        SharedPreferences sp = getActivity().getSharedPreferences("db", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        //Copy the LinkedHashSet because stored data is not guaranteed
        Set<String> history = new LinkedHashSet<>(sp.getStringSet("CompletedHistory", new LinkedHashSet<String>()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        history.add(currentDateandTime + "/" + ((GameActivity) getActivity()).getCompleted());
        //edit.putStringSet("CompletedHistory", history);
        edit.apply();

        ((GameActivity) getActivity()).incCompleted();
        for (int i=0; i<300; i++) {
            addRow(currentDateandTime, ((GameActivity) getActivity()).getCompleted()+"p");
        }


        guidance_text.setText(getResources().getString(R.string.your_score)+": "+((GameActivity) getActivity()).getCompleted());
        return rootView;
    }

    public void addRow(String time, String point){
        TextView date = new TextView(getActivity());
        TextView p = new TextView(getActivity());
        p.setGravity(Gravity.RIGHT);
        date.setText(time);
        p.setText(point);

        TableRow row = new TableRow(getActivity());
        row.addView(date);
        row.addView(p);


        scoreTable = (TableLayout) rootView.findViewById(R.id.scoreTable);
        scoreTable.addView(row);
    }




}

