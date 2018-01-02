package com.example.jmmar.flashstudy;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static DBHelper db;

    private static FragmentManager mFragmentManager;
    public static final String FRAG_MAIN = "MainFragment";
    public static final String FRAG_STUDY = "StudyFragment";
    public static final String FRAG_ADD_CARDS = "AddCardsFragment";
    public static final String FRAG_EDIT_SET = "EditSetFragment";
    public static final String FRAG_CHOOSE_SET = "ChooseSetFragment";

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

    public void beginStudying(View v){
        mFragmentManager.beginTransaction().replace(R.id.big_fragment_container,
                new ChooseSetFragment(),FRAG_CHOOSE_SET).addToBackStack(FRAG_MAIN).commit();
    }

    public void openAddCardsFrag(View v){
        mFragmentManager.beginTransaction().replace(R.id.big_fragment_container,
                new AddCardsFragment(),FRAG_ADD_CARDS).addToBackStack(FRAG_MAIN).commit();
    }

    // TODO: Create EditSetFragment and implement openEditSetFrag()
    public void openEditSetFrag(View v){
        Toast.makeText(this,"Not Yet Available!",Toast.LENGTH_LONG).show();
    }

    // Listener for button_enter to add a card
    public void addCard(View v){
        EditText setname = (EditText) findViewById(R.id.edit_text_setname);
        EditText term = (EditText) findViewById(R.id.edit_text_term);
        EditText definition = (EditText) findViewById(R.id.edit_text_definition);

        String setnameStr = setname.getText().toString(),
                termStr = term.getText().toString(),
                defStr = definition.getText().toString();

        IndexCard card = new IndexCard(setnameStr,termStr,defStr);

        db.insertCard(card);

        Toast.makeText(this,"Card Successfully Added!",Toast.LENGTH_LONG).show();
    }

}
