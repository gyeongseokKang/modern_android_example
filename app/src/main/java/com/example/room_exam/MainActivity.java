package com.example.room_exam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText mTodoEditText;
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTodoEditText = findViewById(R.id.todo_edit);
        mResultTextView = findViewById(R.id.result_text);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "todo-db")
                .build();

        //UI 갱신
        db.todoDao().getAll().observe(this, todos -> {
            mResultTextView.setText(todos.toString() );
        });

        //버튼 클릭시 DB에 insert
        findViewById(R.id.add_button).setOnClickListener(v -> {
            new InsertAsyncTask(db.todoDao()).execute(new Todo(mTodoEditText.getText().toString()));
        });
    }

    private static class InsertAsyncTask extends AsyncTask<Todo, Void, Void>{
        private TodoDao mTodoDao;

        public InsertAsyncTask(TodoDao todoDao) {
            this.mTodoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            mTodoDao.insert(todos[0]);
            return null;
        }
    }
}
