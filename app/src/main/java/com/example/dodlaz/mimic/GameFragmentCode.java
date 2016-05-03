package com.example.dodlaz.mimic;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by dodlaz on 2016-04-07.
 */
public class GameFragmentCode extends Fragment {
    private static final String TAG = "GameFragmentCode";
    private TextView editText_code;
    private int code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gamefragment_code, container, false);

        code = (int)(Math.random()*9000)+1000; //New Code

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Uri soundUri = null;
        if(sp.getBoolean("Sound", false)) {
            soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        long[] vibrate = { 0, 100, 200, 300 };
        Notification mNotification = new NotificationCompat.Builder(getActivity())
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Code: " + code)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(soundUri)
                .setVibrate(vibrate)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getActivity()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NotificationCompat.PRIORITY_MAX, mNotification);


        editText_code = (TextView) rootView.findViewById(R.id.editText_code);
        editText_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (editText_code.length() > 1
                        && Integer.parseInt(editText_code.getText().toString()) == code) {

                    //Reset for next game
                    editText_code.setText("");
                    cancelNotification(getContext(), NotificationCompat.PRIORITY_MAX);

                    Activity activity = getActivity();
                    Utils.hideKeyboard(activity);
                    if (activity instanceof GameActivity) {
                        ((GameActivity) activity).incCompleted();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


        return rootView;
    }


    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
    }
}
