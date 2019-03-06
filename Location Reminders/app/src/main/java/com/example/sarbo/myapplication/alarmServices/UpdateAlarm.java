package com.example.sarbo.myapplication;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class UpdateAlarm extends AppCompatActivity  {

    private final String TAG = UpdateAlarm.class.getSimpleName();
    EditText EDTaskname;
    EditText EDlocation;
    EditText EDnote;
    MyDBHandler dbHandler;
    SQLiteDatabase sqLiteDatabase;
    Context context = this;
    Button  setDateButton;
    String taskName, locationName, note, Id;
    ViewGroup viewGroup;
    MapsActivity mapsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_alarm);
        UpdateSetInit();
        Intent intent = getIntent();
        taskName = intent.getStringExtra(Constants.TASK_NAME);
        locationName = intent.getStringExtra(Constants.LOC_NAME);
        note = intent.getStringExtra(Constants.NOTE);
        EDTaskname.setText(taskName);
        EDlocation.setText(locationName);
        EDnote.setText(note);

        addingLocationForUpdate();


    }

    private void addingLocationForUpdate() {

        Intent intent = getIntent();
        locationName = intent.getStringExtra(Constants.LOC_NAME);

        EDlocation.setText(locationName);
    }


    private void UpdateSetInit() {

        EDTaskname = (EditText) findViewById(R.id.edtTask);
        EDlocation = (EditText) findViewById(R.id.edtLocation);
        EDnote = (EditText) findViewById(R.id.edtNote);
        // Button ad = (Button) findViewById(R.id.Baddalarm);


        Button sd = (Button) findViewById(R.id.searchButton);
        sd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                /* TODO Auto-generated method stub */
                Intent intent = new Intent(UpdateAlarm.this,MapsActivity.class);
                startActivity(intent);
            }
        });


    }



    public void updateButton(View view) {
        String oldTaskName = taskName;
        String newTaskName = EDTaskname.getText().toString();
        locationName = EDlocation.getText().toString();
        note = EDnote.getText().toString();

        if (newTaskName.length() == 0 && locationName.length() == 0 && note.length() == 0) {
            Toast.makeText(UpdateAlarm.this, "Nothing is entered!!", Toast.LENGTH_LONG).show();
        } else if (taskName.length() == 0) {
            EDTaskname.setError("Task name is empty");
        } else if (locationName.length() == 0) {
            EDlocation.setError("Location name is empty");
        } else if (note.length() == 0) {
            EDnote.setError("Note  is empty");
        } else {
            mapsActivity = new MapsActivity();
            double lat = mapsActivity.gettingLatitude();
            double log = mapsActivity.gettingLongitude();
            dbHandler = new MyDBHandler(getApplicationContext());
            sqLiteDatabase = dbHandler.getWritableDatabase();
            int count = dbHandler.updateAlarm(oldTaskName, newTaskName, locationName, note,lat,log, sqLiteDatabase);
            Toast.makeText(getApplicationContext(), "Alarm is updated with " + count + " row", Toast.LENGTH_LONG).show();
            finish();
        }

    }
}
