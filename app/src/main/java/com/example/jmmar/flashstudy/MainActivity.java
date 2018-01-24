package com.example.jmmar.flashstudy;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private static DBHelper db;

    private static FragmentManager mFragmentManager;
    public static final String FRAG_MAIN = "MainFragment";
    public static final String FRAG_STUDY = "StudyFragment";
    public static final String FRAG_ADD_CARDS = "AddCardsFragment";
    public static final String FRAG_EDIT_SET = "EditSetFragment";
    public static final String FRAG_CHOOSE_SET = "ChooseSetFragment";
    public static final String FRAG_ADD_SET = "AddSetFragment";
    public static final String FRAG_DELETE_SET = "DeleteSetFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(getApplicationContext());
        mFragmentManager = getSupportFragmentManager();

        mFragmentManager.beginTransaction().add(R.id.big_fragment_container,
                new MainFragment(),FRAG_MAIN).commit();
    }

    public static DBHelper getDB(){
        return db;
    }

    public static FragmentManager getFragManager(){
        return mFragmentManager;
    }

}
