package com.example.sarbo.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.Task;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.example.sarbo.myapplication.R.layout.item_layout;


// Here user can create alarm and see the created alarms in listview
public class MainActivity extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    MyDBHandler dbHandler;
    Cursor cursor;
    ListDataAdapter listDataAdapter;
    ListDataAdapter.LayoutHandler layoutHandler;

    private AlertDialog.Builder dialogBuilder;

    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  setHasOptionsMenu(true);


        setInit();
        singleListItemSelector();

    }




    private void setInit() {
        listView = (ListView) findViewById(R.id.LValarms);
        listDataAdapter = new ListDataAdapter(getApplicationContext(), item_layout);
        listView.setAdapter(listDataAdapter);

        dbHandler = new MyDBHandler(getApplicationContext());
        sqLiteDatabase = dbHandler.getReadableDatabase();

        cursor = dbHandler.getInformationWithDayDate(sqLiteDatabase);
        cursorHandlerClass();

    }


    public void cursorHandlerClass() {
        if (cursor.moveToFirst()) {
            do {

                String task, location, note;
                double lat,lon;
                task = cursor.getString(1);
                location = cursor.getString(4);
                note = cursor.getString(5);
                lat = cursor.getDouble(2);
                lon =cursor.getDouble(3);
                DataProvider dataProvider = new DataProvider(task, location, note,lat,lon); // passing the information to DataProvider class
                listDataAdapter.add(dataProvider);
            } while (cursor.moveToNext());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //listener to single listView item
    public void singleListItemSelector() {


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            String selected;

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("What to do with Alarm?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected = ((TextView) view.findViewById(R.id.TVtask)).getText().toString();
                    String loc =((TextView) view.findViewById(R.id.TVloc)).getText().toString();
                        String note =((TextView) view.findViewById(R.id.TVnote)).getText().toString();
                        Intent intent = new Intent(MainActivity.this, UpdateAlarm.class);
                        intent.putExtra(Constants.TASK_NAME, selected);
                        intent.putExtra(Constants.LOC_NAME,loc);
                        intent.putExtra(Constants.NOTE,note);
                        //todo: send project information as parameter
                        startActivity(intent);
                        listDataAdapter.notifyDataSetChanged();
                    }
                });
                //**!!!Here I delete project item from database!!!**
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected = ((TextView) view.findViewById(R.id.TVtask)).getText().toString();
                        // int projId = dbHandler.findIdByName(selected);
                        dbHandler.deleteAlarm(selected);
                        Toast.makeText(getApplicationContext(), "Alarm " + selected + " deleted", Toast.LENGTH_SHORT).show();
                        //cursorHandlerClass();
                      //  listDataAdapter.notify();
                    }
                });
                builder.show();
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                   //create new Reminder
                startActivity(new Intent(MainActivity.this, CreateAlram.class));
                Log.d(getLocalClassName(), "create new Reminder");

                return true;
            case R.id.map:
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
                return true;

            case R.id.action_exit:
                finish();
                return true;
            default:
                return false;
        }
    }

}
