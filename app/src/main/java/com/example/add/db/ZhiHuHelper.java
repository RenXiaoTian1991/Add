package com.example.add.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by myself on 15/9/10.
 */
public class ZhiHuHelper extends SQLiteOpenHelper {
    private static final String BD_NAME = "zhihu";
    private static final int VERSION = 1;
    public static final String TABLE_NAME = "news";

    public ZhiHuHelper(Context context) {
        super(context, BD_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table "+ TABLE_NAME+" ( _id integer primary key autoincrement , "
                + " image text , title text , time text);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

