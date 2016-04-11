package com.example.dodlaz.mimic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dodlaz on 2016-04-07.
 */
public class GameFragmentButton extends Fragment {
    public GameFragmentButton() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gamefragment_button, container, false);

        return rootView;
    }

}
