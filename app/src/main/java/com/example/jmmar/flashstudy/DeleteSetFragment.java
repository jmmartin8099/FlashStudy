package com.example.jmmar.flashstudy;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jmmar.flashstudy.R;

import static com.example.jmmar.flashstudy.MainActivity.FRAG_DELETE_SET;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_MAIN;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_SET_VIEW;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteSetFragment extends Fragment {
    public static final String TAG = "DeleteSetFragment";

    private DBHelper db;
    private FragmentManager mFragmentManager;

    private TextView mDisplayMsg;
    private EditText mDeleteInput;
    private Button mCancel;
    private Button mDelete;

    public DeleteSetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_delete_set, container, false);

        db = MainActivity.getDB();
        mFragmentManager = MainActivity.getFragManager();

        mDisplayMsg = (TextView) v.findViewById(R.id.text_delete_set);
        mDisplayMsg.append(ChooseSetFragment.getSetName());

        mDeleteInput = (EditText) v.findViewById(R.id.edit_text_delete_set);
        mCancel = (Button) v.findViewById(R.id.button_cancel_delete_set);
        mDelete = (Button) v.findViewById(R.id.button_final_delete_set);

        // Listener to cancel deleting a set
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hideKeyboard();
                MainActivity.launchFragment(new SetViewFragment(),FRAG_SET_VIEW,FRAG_DELETE_SET);
            }
        });

        // Listener to delete the set
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setName = mDeleteInput.getText().toString();
                if (setName.equals(""))
                    Toast.makeText(v.getContext(),"Please Enter a Set Name.",Toast.LENGTH_SHORT)
                            .show();
                else if (!setName.equals(ChooseSetFragment.getSetName()))
                    Toast.makeText(v.getContext(),"Please Enter a Valid Set Name.",
                            Toast.LENGTH_SHORT).show();
                else {
                    db.deleteSet(setName);
                    hideKeyboard();
                    MainActivity.launchFragment(new MainFragment(),FRAG_MAIN,null);
                }
            }
        });

        return v;
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
    }

    public void msg(String str){
        Log.i(MainActivity.TAG,str);
    }

    @Override
    public void onStart() {
        super.onStart();
        msg("DeleteSetFragment: onStart...");
    }

    @Override
    public void onStop() {
        super.onStop();
        msg("DeleteSetFragment: onStop...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        msg("DeleteSetFragment: onDestroy...");
    }

    @Override
    public void onPause() {
        super.onPause();
        msg("DeleteSetFragment: onPause...");
    }

    @Override
    public void onResume() {
        super.onResume();
        msg("DeleteSetFragment: onResume...");
    }

}
