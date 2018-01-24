package com.example.jmmar.flashstudy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static com.example.jmmar.flashstudy.MainActivity.FRAG_ADD_SET;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_CHOOSE_SET;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_MAIN;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    public static final String TAG = "MainFragment";

    private DBHelper db;
    private FragmentManager mFragmentManager;

    private Button mChooseSet;
    private Button mAddSet;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        db = MainActivity.getDB();
        mFragmentManager = MainActivity.getFragManager();

        mChooseSet = (Button) v.findViewById(R.id.button_choose_set);
        mAddSet = (Button) v.findViewById(R.id.button_add_set);

        mChooseSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager.beginTransaction().replace(R.id.big_fragment_container,
                        new ChooseSetFragment(),FRAG_CHOOSE_SET).addToBackStack(FRAG_MAIN).commit();
            }
        });

        mAddSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager.beginTransaction().replace(R.id.big_fragment_container,
                        new AddSetFragment(),FRAG_ADD_SET).addToBackStack(FRAG_MAIN).commit();
            }
        });

        return v;
    }
}
