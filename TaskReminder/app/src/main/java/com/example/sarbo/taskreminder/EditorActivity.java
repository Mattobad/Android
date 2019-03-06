package com.example.sarbo.taskreminder;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sarbo.taskreminder.controller.NotesProvider;
import com.example.sarbo.taskreminder.database.DBOpenHelper;


public class EditorActivity extends AppCompatActivity {

    private String action;
    private EditText editor;
    private String noteFilter; // where clause for SQL statement
    private String oldText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        editor = (EditText) findViewById(R.id.editText);

            Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(NotesProvider.CONTENT_ITEM_TYPE);

        if (uri == null) {
            action = Intent.ACTION_INSERT;
            setTitle(getString(R.string.new_task));
        }else{
            action = Intent.ACTION_EDIT;
            noteFilter = DBOpenHelper.TASK_ID + "=" + uri.getLastPathSegment();

            //for retrieving one column from the list
            Cursor cursor = getContentResolver().query(uri,
                    DBOpenHelper.ALL_COLUMNS,noteFilter,null,null);
            cursor.moveToFirst();
            oldText = cursor.getString(cursor.getColumnIndex(DBOpenHelper.TASK_TEXT));
            editor.setText(oldText);
            editor.requestFocus(); //it'll move the cursor to the end of the existing text
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (action.equals(Intent.ACTION_EDIT)) {
            getMenuInflater().inflate(R.menu.menu_editor, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finishEditing();
                break;
            case R.id.action_delete:
                deleteNote();
                break;
        }

        return true;
    }

    private void deleteNote() {
        getContentResolver().delete(NotesProvider.CONTENT_URI,
                noteFilter,null);
        Toast.makeText(this, R.string.note_deleted,
                Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    private void finishEditing() {
        String newText = editor.getText().toString().trim(); // trim() method that eliminates any whitespaces

        switch (action){
            case Intent.ACTION_INSERT:
                if (newText.length() == 0){
                    setResult(RESULT_CANCELED);
                }else{
                    insertNote(newText);
                }
                break;
            case Intent.ACTION_EDIT:
                if (newText.length() == 0){
                            deleteNote();
                }else if(oldText.equals(newText)){
                    setResult(RESULT_CANCELED);
            }else {
                    updateNote(newText);
                }
        }
        finish();
    }

    private void updateNote(String noteText) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.TASK_TEXT,noteText);
        getContentResolver().update(NotesProvider.CONTENT_URI,values,noteFilter,null);

        Toast.makeText(this, R.string.note_updated, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
    }

    private void insertNote(String noteText) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.TASK_TEXT,noteText);
        getContentResolver().insert(NotesProvider.CONTENT_URI,
                values);
        setResult(RESULT_OK);
    }

    @Override
    public void onBackPressed() {
        finishEditing();
    }
}
