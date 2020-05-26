package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.byted.camp.todolist.beans.Priority;
import com.byted.camp.todolist.beans.State;
import com.byted.camp.todolist.db.TodoContract;
import com.byted.camp.todolist.db.TodoDbHelper;

public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private Button addBtn;
    private TodoDbHelper dbHelper;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);
        dbHelper = new TodoDbHelper(this);

        editText = findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }

        addBtn = findViewById(R.id.btn_add);
        radioGroup = findViewById(R.id.radio_group);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence content = editText.getText();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean succeed = saveNote2Database(content.toString().trim(), getPriority());
                if (succeed) {
                    Toast.makeText(NoteActivity.this,
                            "Note added", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                } else {
                    Toast.makeText(NoteActivity.this,
                            "Error", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    private Priority getPriority() {
        int chosen = radioGroup.getCheckedRadioButtonId();
        switch (chosen) {
            case R.id.high:
                return Priority.HIGH;
            case R.id.medium:
                return Priority.MEDIUM;
            default:
                return Priority.LOW;
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    private boolean saveNote2Database(String content, Priority priority) {
        // TODO 插入一条新数据，返回是否插入成功
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoContract.TodoTable.COLUMN_CONTENT, content);
        values.put(TodoContract.TodoTable.COLUMN_STATE, State.TODO.intValue);
        values.put(TodoContract.TodoTable.COLUMN_DATE, System.currentTimeMillis());
        values.put(TodoContract.TodoTable.COLUMN_PRIORITY, priority.intValue);
        if (db.insert(TodoContract.TodoTable.TABLE_NAME, null, values) != -1) {
            db.close();
            return true;
        }
        else {
            db.close();
            return false;
        }
    }
}
