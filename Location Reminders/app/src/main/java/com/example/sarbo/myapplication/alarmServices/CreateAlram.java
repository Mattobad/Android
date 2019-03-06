package com.example.sarbo.myapplication;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.playlog.internal.LogEvent;


// Here user can add alarm related information

public class CreateAlram extends Activity implements ConfirmAlarm.Communicator {



    private final String TAG = CreateAlram.class.getSimpleName();
    //RelativeLayout r;
    EditText EDTaskname;
    EditText EDlocation;
    EditText EDnote;
    MyDBHandler dbHandler;
    SQLiteDatabase sqLiteDatabase;
    Context context = this;
    String taskName,locationName,note;
    ViewGroup viewGroup;

    double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alram);

        setInit();
        Intent intent = getIntent();
        locationName = intent.getStringExtra(Constants.LOC_NAME);
         latitude = intent.getDoubleExtra("lat", 00.00);
        longitude = intent.getDoubleExtra("lon", 00.00);
        EDlocation.setText(locationName);


    }




   private void setInit(){

        EDTaskname = (EditText) findViewById(R.id.EdtTask);
        EDlocation = (EditText) findViewById(R.id.EDLocation);
        EDnote = (EditText) findViewById(R.id.EDNote);
       Button dt= (Button) findViewById(R.id.dtButton);
       dt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.e(TAG,"date and time button click");
               startActivity(new Intent(CreateAlram.this,AlarmActivity.class));
           }
       });


       Button sd = (Button) findViewById(R.id.ButtonSearch);
        sd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                /* TODO Auto-generated method stub */
                int i=1;
                Intent intent = new Intent(CreateAlram.this,MapsActivity.class);
                intent.putExtra(Constants.ID,i);
                Log.e(TAG, "onClick ");
                startActivity(intent);
            }
        });


    }

    // add button onclick listener

    public void addButtonClicked(View view) {
         taskName  = EDTaskname.getText().toString();
         locationName = EDlocation.getText().toString();
         note = EDnote.getText().toString();

        if (taskName.isEmpty() && locationName.isEmpty()&& note.isEmpty()){
            Toast.makeText(CreateAlram.this,"Nothing is entered!!",Toast.LENGTH_LONG).show();
        }
        else if(taskName.isEmpty())
        {
                EDTaskname.setError("Task name is empty");
        } else if(locationName.isEmpty())
        {
            EDlocation.setError("Location name is empty");
        } else if(note.isEmpty())
        {
            EDnote.setError("Note  is empty");
        }
        else {
            FragmentManager manager = getFragmentManager();
            ConfirmAlarm confirmAlarm = new ConfirmAlarm();
            confirmAlarm.show(manager,"ConfirmMessage");

        }
    }









    public void insertAlarm(){

        dbHandler = new MyDBHandler(context);
        sqLiteDatabase = dbHandler.getWritableDatabase();

        dbHandler.addAlarm(taskName, locationName, note,latitude,longitude, sqLiteDatabase);// calling function to add data to database
//        listDataAdapter.notifyDataSetChanged();
        Toast.makeText(getBaseContext(), "Data saved with "+latitude+" "+longitude, Toast.LENGTH_LONG).show();
        dbHandler.close();
    }

    @Override
    public void onDialogMessage(String Message) {
       //Toast.makeText(this,Message,Toast.LENGTH_LONG).show();
        String msg = Message.toString();
        if(msg == "yes"){
            insertAlarm();
        }else{
            clearEditTextGroup(viewGroup);
            //Toast.makeText(CreateAlram.this,"no result",Toast.LENGTH_LONG).show();

        }

    }


    public void clearEditTextGroup(ViewGroup group){
        group = (ViewGroup) findViewById(R.id.Rlayout);

        for(int i=0 ; i< group.getChildCount(); i++){
            View view = group.getChildAt(i);
            if(view instanceof EditText){

                // use one of clear code
                ((EditText)view).setText("");
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearEditTextGroup((ViewGroup)view);

        }


    }

}
