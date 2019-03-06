package com.example.sarbo.myapplication;

import android.app.Activity;
import android.app.DialogFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ConfirmAlarm extends DialogFragment implements View.OnClickListener {
    Button yesButton,noButton;
    Communicator communicator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_confirm_alarm, null);
        yesButton = (Button)view.findViewById(R.id.yes);
        noButton =(Button)view.findViewById(R.id.no);
        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);
        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.yes){
           // Toast.makeText(getActivity(),"yes",Toast.LENGTH_LONG).show();
           communicator.onDialogMessage("yes");
            dismiss();

        }else
        {  communicator.onDialogMessage("no");
            dismiss();
        }
    }

    interface Communicator{

        public void onDialogMessage(String Message);
    }
}
