package com.example.jmmar.flashstudy;

import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private static DBHelper sDb;

    private static FragmentManager sFragmentManager;
    public static final String FRAG_MAIN = "MainFragment";
    public static final int FRAG_MAIN_ID = 0;
    public static final String FRAG_CHOOSE_SET = "ChooseSetFragment";
    public static final int FRAG_CHOOSE_SET_ID = 1;
    public static final String FRAG_ADD_SET = "AddSetFragment";
    public static final int FRAG_ADD_SET_ID = 2;
    public static final String FRAG_SET_VIEW = "SetViewFragment";
    public static final int FRAG_SET_VIEW_ID = 3;
    public static final String FRAG_ADD_CARDS = "AddCardsFragment";
    public static final int FRAG_ADD_CARDS_ID = 4;
    public static final String FRAG_EDIT_SET = "EditSetFragment";
    public static final int FRAG_EDIT_SET_ID = 5;
    public static final String FRAG_DELETE_SET = "DeleteSetFragment";
    public static final int FRAG_DELETE_SET_ID = 6;
    public static final String FRAG_EDIT_CARD = "EditCardFragment";
    public static final int FRAG_EDIT_CARD_ID = 7;

    private static int sCurrFragDisplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sDb = new DBHelper(getApplicationContext());
        sFragmentManager = getSupportFragmentManager();

        if (sCurrFragDisplayed != 0){
            displayCurrFragment();
        }
        else {
            // Set current displayed fragment to MainFragment
            sCurrFragDisplayed = FRAG_MAIN_ID;
            sFragmentManager.beginTransaction().add(R.id.big_fragment_container,
                    new MainFragment(), FRAG_MAIN).commit();
        }
    }

    public static DBHelper getDB(){
        return sDb;
    }

    public static FragmentManager getFragManager(){
        return sFragmentManager;
    }

    public static void setCurrFragDisplayed(int fragId){
        sCurrFragDisplayed = fragId;
    }

    public void displayCurrFragment(){
        switch (sCurrFragDisplayed){

            case FRAG_CHOOSE_SET_ID:
                launchFragment(new EditCardFragment(),FRAG_CHOOSE_SET,FRAG_MAIN);
                break;

            case FRAG_ADD_SET_ID:
                launchFragment(new AddSetFragment(),FRAG_ADD_SET,FRAG_MAIN);
                break;

            case FRAG_SET_VIEW_ID:
                launchFragment(new SetViewFragment(),FRAG_SET_VIEW,FRAG_CHOOSE_SET);
                break;

            case FRAG_ADD_CARDS_ID:
                launchFragment(new AddCardsFragment(),FRAG_ADD_CARDS,FRAG_SET_VIEW);
                break;

            case FRAG_EDIT_SET_ID:
                launchFragment(new EditSetFragment(),FRAG_EDIT_SET,FRAG_SET_VIEW);
                break;

            case FRAG_DELETE_SET_ID:
                launchFragment(new DeleteSetFragment(),FRAG_DELETE_SET,FRAG_SET_VIEW);
                break;

            case FRAG_EDIT_CARD_ID:
                launchFragment(new EditCardFragment(),FRAG_EDIT_CARD,FRAG_EDIT_SET);
                break;

        }
    }

    public static void launchFragment(Fragment frag,String tag,String backStackTag){
        sFragmentManager.beginTransaction().replace(R.id.big_fragment_container,
                frag,tag).addToBackStack(backStackTag).commit();
    }

    public  void msg(String str){
        Log.i(TAG,str);
    }

    @Override
    protected void onStart() {
        super.onStart();
        msg("MainActivity: onStart...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        msg("MainActivity: onStop...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        msg("MainActivity: onDestroy...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        msg("MainActivity: onPause...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        msg("MainActivity: onResume...");
    }
}
