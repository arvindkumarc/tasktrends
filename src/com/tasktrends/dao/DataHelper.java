package com.tasktrends.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import com.tasktrends.domain.Task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DataHelper {

    private static final String DATABASE_NAME = "tasktrends.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tasks";
    private static DataHelper dataHelper;
    private static final String INSERT = "insert into " + TABLE_NAME + "(taskName, taskDate) values (?, ?)";

    private SQLiteDatabase db;
    private SQLiteStatement insertStmt;
    private Context context;

    public static DataHelper getInstance(Context context) {
        if (null == dataHelper)
            dataHelper = new DataHelper(context);
        return dataHelper;
    }

    private DataHelper(Context context) {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);
    }

    public long insert(String taskName, String taskDate) {
        this.insertStmt.bindString(1, taskName);
        this.insertStmt.bindString(2, getDate(taskDate));
        return this.insertStmt.executeInsert();
    }

    private String getDate(String taskDate) {
        Log.i("Date: ", taskDate);
        DateFormat iso8601Format = new SimpleDateFormat("EEE, MMM d, yyyy");
        Date date = null;
        try {
            date = iso8601Format.parse(taskDate);
        } catch (ParseException e) {
            Log.e(DataHelper.class.getCanonicalName() + ", insert: ", "Parsing ISO8601 datetime failed", e);
        }

        long when = date.getTime();
        int flags = 0;
        flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
        flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
        flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
        flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;

        return android.text.format.DateUtils.formatDateTime(context,
                when + TimeZone.getDefault().getOffset(when), flags);
    }

    public void deleteAll() {
        this.db.delete(TABLE_NAME, null, null);
    }

    public List<Task> selectAllTasks() {
        List<Task> tasks = new ArrayList<Task>();
        Cursor cursor = this.db.query(TABLE_NAME, new String[]{"taskName", "taskDate"},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = iso8601Format.parse(cursor.getString(1));
                } catch (ParseException e) {
                    Log.e(DataHelper.class.getCanonicalName(), "Parsing ISO8601 datetime failed", e);
                }

                tasks.add(new Task(cursor.getString(0), date));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return tasks;
    }

    private static class OpenHelper extends SQLiteOpenHelper {
        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY, taskName TEXT, taskDate TIMESTAMP)");
            Log.i(OpenHelper.class.getCanonicalName(), "Tables Created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Example", "Upgrading database, this will drop tables and recreate.");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
