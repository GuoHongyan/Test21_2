/**
 * 21：编程项目-[上机实践]ContentProvider-具体内容
 * created by hyl on 2015/9/5
 */
package com.example.test21;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyWordsTag";
    private ContentResolver resolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resolver = this.getContentResolver();

        Button buttonAll = (Button) findViewById(R.id.btn_get_all);
        Button buttonInsert = (Button) findViewById(R.id.buttonInsert);
        Button buttonDelete = (Button) findViewById(R.id.btn_del);
        Button buttonDeleteAll = (Button) findViewById(R.id.btn_del_all);
        Button buttonUpdate = (Button) findViewById(R.id.btn_update);
        Button buttonQuery = (Button) findViewById(R.id.btn_query);

        //得到全部
        buttonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = resolver.query(Words.T_word.CONTENT_URI,
                        new String[]{Words.T_word._ID, Words.T_word.COLUMN_NAME_WORD,
                                Words.T_word.COLUMN_NAME_MEANING, Words.T_word.COLUMN_NAME_SAMPLE},
                        null, null, null);
                if (cursor == null) {
                    Toast.makeText(MainActivity.this, "没有找到记录", Toast.LENGTH_LONG).show();
                    return;
                }

                //找到记录，这里简单起见，使用Log输出

                String msg = "";
                int flag = 1;
                if (cursor.moveToFirst()) {
                    do {
                        if (flag == 1) {
                            flag = 0;
                        }
                        msg += "ID:" + cursor.getInt(cursor.getColumnIndex(Words.T_word._ID)) + ",";
                        msg += "单词：" + cursor.getString(cursor.getColumnIndex(Words.T_word.COLUMN_NAME_WORD)) + ",";
                        msg += "含义：" + cursor.getString(cursor.getColumnIndex(Words.T_word.COLUMN_NAME_MEANING)) + ",";
                        msg += "示例" + cursor.getString(cursor.getColumnIndex(Words.T_word.COLUMN_NAME_SAMPLE)) + "\n";
                    } while (cursor.moveToNext());
                }

                Log.v(TAG, msg);

            }
        });

        //增加
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strWord = "Banana";
                String strMeaning = "banana";
                String strSample = "This banana is very nice.";
                ContentValues values = new ContentValues();

                values.put(Words.T_word.COLUMN_NAME_WORD, strWord);
                values.put(Words.T_word.COLUMN_NAME_MEANING, strMeaning);
                values.put(Words.T_word.COLUMN_NAME_SAMPLE, strSample);

                Uri newUri = resolver.insert(Words.T_word.CONTENT_URI, values);
                Toast.makeText(MainActivity.this, "add word banana", Toast.LENGTH_SHORT).show();
            }
        });

        //删除
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id =  "";//简单起见，这里指定ID，用户可在程序中设置id的实际值
                Uri uri = Uri.parse(Words.T_word.CONTENT_URI_STRING + "/" + id);
                int result = resolver.delete(uri, null, null);
                Toast.makeText(MainActivity.this, "del word " + id, Toast.LENGTH_SHORT).show();
            }
        });

        //删除全部
        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resolver.delete(Words.T_word.CONTENT_URI, null, null);
                Toast.makeText(MainActivity.this, "del all", Toast.LENGTH_SHORT).show();
            }
        });

        //修改
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id =  "";
                String strWord = "Banana";
                String strMeaning = "banana123";
                String strSample = "This banana is very nice.";
                ContentValues values = new ContentValues();
                values.put(Words.T_word.COLUMN_NAME_WORD, strWord);
                values.put(Words.T_word.COLUMN_NAME_MEANING, strMeaning);
                values.put(Words.T_word.COLUMN_NAME_SAMPLE, strSample);

                Uri uri = Uri.parse(Words.T_word.CONTENT_URI_STRING + "/" + id);
                int result = resolver.update(uri, values, null, null);
                Toast.makeText(MainActivity.this, "update words" + id, Toast.LENGTH_SHORT).show();
            }
        });

        //根据id查询
        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id =  "";
                Uri uri = Uri.parse(Words.T_word.CONTENT_URI_STRING + "/" + id);
                Cursor cursor = resolver.query(Words.T_word.CONTENT_URI,
                        new String[]{Words.T_word._ID, Words.T_word.COLUMN_NAME_WORD,
                                Words.T_word.COLUMN_NAME_MEANING, Words.T_word.COLUMN_NAME_SAMPLE},
                        "_ID=?", new String[]{id}, null);
                if (cursor == null) {
                    Toast.makeText(MainActivity.this, "没有找到记录", Toast.LENGTH_LONG).show();
                    return;
                }

                //找到记录，这里简单起见，使用Log输出

                String msg = "";
                if (cursor.moveToFirst()) {
                    do {
                        msg += "ID:" + cursor.getInt(cursor.getColumnIndex(Words.T_word._ID)) + ",";
                        msg += "单词：" + cursor.getString(cursor.getColumnIndex(Words.T_word.COLUMN_NAME_WORD)) + ",";
                        msg += "含义：" + cursor.getString(cursor.getColumnIndex(Words.T_word.COLUMN_NAME_MEANING)) + ",";
                        msg += "示例" + cursor.getString(cursor.getColumnIndex(Words.T_word.COLUMN_NAME_SAMPLE)) + "\n";
                    } while (cursor.moveToNext());
                }
                Log.v(TAG, msg);
            }
        });
    }

}
