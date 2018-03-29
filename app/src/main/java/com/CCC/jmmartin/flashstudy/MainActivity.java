package com.CCC.jmmartin.flashstudy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private static DBHelper sDb;

    // Sets Info
    private ArrayList<String> mSets;
    private String []mSetNames;
    private String mCurrSetName;
    private int mCurrSetNumCards;
    private int mNumSets;

    // Cards
    private ArrayList<IndexCard> mCards;
    private String []mTerms;

    // Study Button
    private TextView mBeginStudying;

    // Options Menu
    private ImageButton mOptionsButton;
    private LinearLayout mOptionsView;

    // Creating Set
    private TextView mCreateSet;
    private LinearLayout mCreateSetView;
    private EditText mSetNameInput;
    private Button mCancelSet;
    private Button mEnterSet;

    // Deleting Set
    private TextView mDeleteSet;
    private LinearLayout mDeleteSetView;
    private EditText mDeleteSetInput;
    private Button mCancelDeleteSet;
    private Button mDeleteSetButton;

    // Help Menu
    private TextView mHelp;

    // Settings Menu
    private TextView mSettings;

    // Changing Sets
    private TextView mDisplaySet;
    private TextView mNumCards;
    private FrameLayout mSetView;
    private ListView mList;

    // Adding Cards
    private TextView mAddCards;
    private LinearLayout mAddCardView;
    private EditText mTermInput;
    private EditText mDefInput;
    private Button mClearInput;
    private Button mEnterInput;

    // Editing Cards
    private TextView mEditCards;
    private LinearLayout mEditView;
    private EditText mEditTermInput;
    private EditText mEditDefInput;
    private Button mDeleteCard;
    private Button mUpdateCard;
    private GridView mGrid;
    private IndexCard mCurrCard;
    private int mCurrCardPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sDb = new DBHelper(getApplicationContext());

        // Begin Studying
        mBeginStudying = (TextView) findViewById(R.id.begin_studying);

        mBeginStudying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sDb.numCardsInSet(mCurrSetName) != 0) {
                    Intent startIntent = new Intent(getApplicationContext(), StudyActivity.class);
                    startIntent.putExtra("setName", mCurrSetName);
                    startActivity(startIntent);
                }
                else
                    createToast("No Cards to Study.\nAdd Cards to Begin Studying.");
            }
        });

        // Options Menu
        mOptionsButton = (ImageButton) findViewById(R.id.options_button);
        mOptionsView = (LinearLayout) findViewById(R.id.options_view);

        mHelp = (TextView) findViewById(R.id.text_help);
        mSettings = (TextView) findViewById(R.id.text_settings);

        // Creating Set Start
        mCreateSet = (TextView) findViewById(R.id.text_create_set);
        mCreateSetView = (LinearLayout) findViewById(R.id.view_add_set);
        mSetNameInput = (EditText) findViewById(R.id.input_set_name);
        mCancelSet = (Button) findViewById(R.id.cancel_add_set);
        mEnterSet = (Button) findViewById(R.id.enter_add_set);

        // OnClickListener to open CreateSetView
        mCreateSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOptionsView.setVisibility(View.GONE);
                mCreateSetView.setVisibility(View.VISIBLE);
            }
        });

        // OnClickListener to cancel adding a set
        mCancelSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCreateSetView.setVisibility(View.GONE);
                mSetNameInput.setText("");
                mDisplaySet.setClickable(true);
            }
        });

        // OnClickListener to enter a set
        mEnterSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mSetNameInput.getText().toString();
                if (mNumSets == 0) {
                    mSetNames = new String[1];
                    mSets = new ArrayList<>(1);
                }
                if (input.equals(""))
                    createToast("Please Enter a Set Name.");
                else if (!input.equals("")){
                    for (int i = 0;i<mSetNames.length;i++)
                        if (input.equals(mSetNames[i]))
                            createToast("Set Already Exists.");
                    sDb.addSet(input);
                    mSets.add(input);
                    castListToArray();
                    mNumSets++;
                    mList.setAdapter(new ArrayAdapter<String>(getApplicationContext()
                            ,R.layout.list_item,mSetNames));
                    hideKeyboard();
                }
                mDisplaySet.setClickable(true);
            }
        });
        // Creating Set End

        // Deleting Set Start
        mDeleteSet = (TextView) findViewById(R.id.text_delete_set);
        mDeleteSetView = (LinearLayout) findViewById(R.id.delete_set_view);
        mDeleteSetInput = (EditText) findViewById(R.id.delete_set_input);
        mCancelDeleteSet = (Button) findViewById(R.id.cancel_delete_set);
        mDeleteSetButton = (Button) findViewById(R.id.delete_set_button);

        mDeleteSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOptionsView.setVisibility(View.GONE);
                mDeleteSetView.setVisibility(View.VISIBLE);
            }
        });

        mCancelDeleteSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteSetView.setVisibility(View.GONE);
                mDisplaySet.setClickable(true);
            }
        });

        mDeleteSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mDeleteSetInput.getText().toString();
                boolean notFound = true;
                if (input.equals(""))
                    createToast("Please Enter a Valid Set Name");
                else{
                    for (int i = 0;i<mNumSets;i++)
                        if (input.equals(mSetNames[i])) {
                            sDb.deleteSet(input);
                            notFound = false;
                            createToast("Set Successfully Deleted.");
                            getSetNames();
                        }
                    if (notFound)
                        createToast("\"" + input + "\" Does Not Exist.");
                }
                mDisplaySet.setClickable(true);
            }
        });

        // Changing Sets Start
        mDisplaySet = (TextView) findViewById(R.id.text_set_name);
        mNumCards = (TextView) findViewById(R.id.text_num_cards);
        mSetView = (FrameLayout) findViewById(R.id.set_view);
        mList = (ListView) findViewById(R.id.list_sets);

        // Set onClickListener to change sets
        mDisplaySet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.setAdapter(new ArrayAdapter<String>(getApplicationContext()
                        ,R.layout.list_item,mSetNames));
                mSetView.setVisibility(View.VISIBLE);
            }
        });


        // Create list of sets and set onClickListener
        getSetNames();

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrSetName = mSetNames[position];
                mDisplaySet.setText(mCurrSetName);
                mCurrSetNumCards = sDb.numCardsInSet(mCurrSetName);
                mNumCards.setText(mCurrSetNumCards + " Cards");
                mSetView.setVisibility(View.GONE);
                if (sDb.numCardsInSet(mCurrSetName) != 0) {
                    getCards();
                    updateGridView();
                }
                else{
                    mGrid.setVisibility(View.GONE);
                    mEditTermInput.setText("");
                    mEditTermInput.setHint("No Cards in Set.");
                    mEditDefInput.setText("");
                    mEditDefInput.setHint("Add Cards to Edit Cards.");
                    createToast("No Cards in Set.");
                }
            }
        });
        // Changing Sets End

        // Adding Cards Start
        mAddCards = (TextView) findViewById(R.id.open_add_cards);
        mAddCardView = (LinearLayout) findViewById(R.id.view_add_cards);
        mTermInput = (EditText) findViewById(R.id.input_term);
        mDefInput = (EditText) findViewById(R.id.input_definition);
        mClearInput = (Button) findViewById(R.id.clear_add_card);
        mEnterInput = (Button) findViewById(R.id.enter_add_card);

        // Set onClickListener for adding a card
        mAddCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddCardView.getVisibility() == View.VISIBLE)
                    mAddCardView.setVisibility(View.GONE);
                else {
                    mAddCardView.setVisibility(View.VISIBLE);
                    mEditView.setVisibility(View.GONE);
                }
            }
        });

        mClearInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTermInput.setText("");
                mDefInput.setText("");
            }
        });

        mEnterInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTerm = mTermInput.getText().toString(),
                        newDef = mDefInput.getText().toString();

                if (!newTerm.equals("") && !newDef.equals("")){
                    IndexCard temp = new IndexCard(newTerm,newDef);
                    if(sDb.addCard(mCurrSetName,temp)) {
                        createToast("Card Added Successfully!");
                        getCards();
                        updateGridView();
                        hideKeyboard();
                    }
                    else
                        createToast("Error Adding Card!");
                }
                else
                    createToast("Please Enter Both a Term and Definition.");
            }
        });
        // Adding Cards End

        // Editing Cards Start
        mEditCards = (TextView) findViewById(R.id.edit_cards);
        mEditView = (LinearLayout) findViewById(R.id.edit_card_view);
        mEditTermInput = (EditText) findViewById(R.id.edit_term_input);
        mEditDefInput = (EditText) findViewById(R.id.edit_definition_input);
        mDeleteCard = (Button) findViewById(R.id.delete_edit_card);
        mUpdateCard = (Button) findViewById(R.id.update_edit_card);
        mGrid = (GridView) findViewById(R.id.grid_view_cards);

        // Set onClickListener to open Edit Cards
        mEditCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditView.getVisibility() == View.VISIBLE)
                    mEditView.setVisibility(View.GONE);
                else {
                    mEditView.setVisibility(View.VISIBLE);
                    mAddCardView.setVisibility(View.GONE);
                }
            }
        });

        // Create grid of cards and set onClickListener
        if (mNumSets != 0) {
            if (sDb.numCardsInSet(mCurrSetName) != 0) {
                getCards();
                mCurrCardPos = 0;
                mCurrCard = mCards.get(mCurrCardPos);
                mGrid.setAdapter(new ArrayAdapter<String>(getApplicationContext()
                        , R.layout.grid_item, mTerms));
                mEditTermInput.setText(mCards.get(mCurrCardPos).getTerm());
                mEditDefInput.setText(mCards.get(mCurrCardPos).getDef());
            }
        }


        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrCardPos = position;
                mCurrCard = mCards.get(position);
                mEditTermInput.setText(mCards.get(position).getTerm());
                mEditDefInput.setText(mCards.get(position).getDef());
            }
        });

        mDeleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDb.deleteCard(mCurrSetName,mCards.get(mCurrCardPos).getTerm());
                getCards();
                updateGridView();
                createToast("Card Successfully Deleted.");
            }
        });

        mUpdateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDb.deleteCard(mCurrSetName,mCards.get(mCurrCardPos).getTerm());
                sDb.addCard(mCurrSetName,new IndexCard(mEditTermInput.getText().toString(),
                        mEditDefInput.getText().toString()));
                getCards();
                updateGridView();
                createToast("Card Successfully Updated.");
            }
        });

        // Editing Cards End
    }

    public static DBHelper getDB(){
        return sDb;
    }

    private void getSetNames(){
        // Get list of sets
        Cursor cursor = sDb.getSets();
        mNumSets = cursor.getCount();
        if (mNumSets == 0){
            mDisplaySet.setText("No Sets Available");
        }
        else {
            mSets = new ArrayList<>(cursor.getCount());

            // Check if any sets exist
            if (cursor.getCount() != 0) {
                int setnameIndex = cursor.getColumnIndex(DBHelper.SETS_COLUMN_SET_NAME);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    mSets.add(cursor.getString(setnameIndex));
                    cursor.moveToNext();
                }
                // Set the current set name and number of cards
                castListToArray();
                mCurrSetName = mSetNames[0];
                mDisplaySet.setText(mCurrSetName);
                mCurrSetNumCards = sDb.numCardsInSet(mCurrSetName);
                mNumCards.setText(mCurrSetNumCards + " Cards");
            } else
                createToast("Create a New Set to Begin.");
        }
    }

    private void getCards(){
        Cursor cursor = sDb.getCards(mCurrSetName);
        mCards = new ArrayList<>(cursor.getCount());
        int termIndex = cursor.getColumnIndex(DBHelper.CARDS_COLUMN_TERM),
                defIndex = cursor.getColumnIndex(DBHelper.CARDS_COLUMN_DEFINITION);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            IndexCard temp = new IndexCard(cursor.getString(termIndex),cursor.getString(defIndex));
            mCards.add(temp);
            cursor.moveToNext();
        }
        mTerms = new String[mCards.size()];
        for (int i = 0;i<mCards.size();i++)
            mTerms[i] = mCards.get(i).getTerm();
    }

    private void updateGridView(){
        mGrid.setAdapter(new ArrayAdapter<String>(getApplicationContext()
                ,R.layout.grid_item,mTerms));
        mEditTermInput.setText(mCards.get(0).getTerm());
        mEditDefInput.setText(mCards.get(0).getDef());
    }

    private void castListToArray(){
        mSetNames = new String[mSets.size()];
        for (int i = 0;i<mSets.size();i++){
            mSetNames[i] = mSets.get(i);
        }
    }

    private void createToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    // OnClickListener for OptionsButton
    public void openOptions(View v){
        if (mOptionsView.getVisibility() == View.GONE) {
            mOptionsView.setVisibility(View.VISIBLE);
            mDisplaySet.setClickable(false);
        }
        else {
            mOptionsView.setVisibility(View.GONE);
            mDisplaySet.setClickable(true);
        }
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
    }

    // Methods for debugging
    public void msg(String str){
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
