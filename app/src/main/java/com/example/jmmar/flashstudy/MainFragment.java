package com.example.jmmar.flashstudy;


import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import static com.example.jmmar.flashstudy.MainActivity.FRAG_ADD_SET;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_ADD_SET_ID;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_CHOOSE_SET;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_CHOOSE_SET_ID;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_MAIN;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_SETTINGS;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_SETTINGS_ID;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    public static final String TAG = "MainFragment";

    private DBHelper db;
    private FragmentManager mFragManager;

    private Button mChooseSet;
    private Button mAddSet;
    private Button mSettings;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        db = MainActivity.getDB();
        mFragManager = MainActivity.getFragManager();



        mChooseSet = (Button) v.findViewById(R.id.button_choose_set);
        mAddSet = (Button) v.findViewById(R.id.button_add_set);
        mSettings = (Button) v.findViewById(R.id.button_settings);

        mChooseSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.numSets() == 0)
                    Toast.makeText(v.getContext(),"No Available Sets Exist.\nPlease Add a New Set."
                            ,Toast.LENGTH_SHORT).show();
                else {
                    MainActivity.setCurrFragDisplayed(FRAG_CHOOSE_SET_ID);
                    MainActivity.launchFragment(new ChooseSetFragment(),FRAG_CHOOSE_SET,FRAG_MAIN);
                }
            }
        });

        mAddSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setCurrFragDisplayed(FRAG_ADD_SET_ID);
                MainActivity.launchFragment(new AddSetFragment(),FRAG_ADD_SET,FRAG_MAIN);
            }
        });

        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"This Feature has not yet been added.",
                        Toast.LENGTH_SHORT).show();
                // MainActivity.setCurrFragDisplayed(FRAG_SETTINGS_ID);
                // MainActivity.launchFragment(new SettingsFragment(),FRAG_SETTINGS);
            }
        });

        return v;
    }

    public void msg(String str){
        Log.i(MainActivity.TAG,str);
    }

    @Override
    public void onStart() {
        super.onStart();
        msg("MainFragment: onStart...");
    }

    @Override
    public void onStop() {
        super.onStop();
        msg("MainFragment: onStop...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        msg("MainFragment: onDestroy...");
    }

    @Override
    public void onPause() {
        super.onPause();
        msg("MainFragment: onPause...");
    }

    @Override
    public void onResume() {
        super.onResume();
        msg("MainFragment: onResume...");
    }
}
