package kr.ac.cnu.computer.reviewfinal;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    EditText reviewEdit;
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reviewEdit = findViewById(R.id.review);
        listView = findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        //파일이름,허용범위,팩토리 사용유무
        db = openOrCreateDatabase("testdb.db",MODE_PRIVATE,null);
        Log.d("Sqllite","testdb 데이터베이스 생성 완료!");
        String sql = "create table if not exists test (idx integer primary key, title varchar(10))";
        db.execSQL(sql);
        Log.d("Sqllite","test테이블 생성 완료!");

        select();
    }

    public void Insert(View view) {
        String reviewData = reviewEdit.getText().toString();
        if (reviewData != null && reviewData.trim().length()>0) {
            String sql2 = "insert into test (title) values ('" + reviewData + "')";
            db.execSQL(sql2);
            Log.d("Sqllite","test테이블에 " + reviewData + " 저장 완료!");
            reviewEdit.setText("");

            select();
        }
    }

    @SuppressLint("Range")
    private void select() {
        String sql = "select * from test order by idx";
        Cursor c1 = db.rawQuery(sql, new String[]{});

        list.clear();//리스트 비우기
        while (c1.moveToNext()) {
            String dbText = c1.getInt(0) + ". ";
            dbText += c1.getString(c1.getColumnIndex("title"));

            list.add(dbText);
        }
            adapter.notifyDataSetChanged();
    }
}