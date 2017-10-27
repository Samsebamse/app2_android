package com.example.sami.s305047;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sami on 26-Oct-17.
 */

public class DBHelper2 extends SQLiteOpenHelper{

    public static final String DB_NAME = "Messages";

    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "message_table";

    public static final String COL_1 = "ID";

    public static final String COL_2 = "MESSAGE_DATA";

    public static final String COL_3 = "MESSAGE_SAVED";

    public static final String COL_4 = "MESSAGE_SENT";


    public DBHelper2(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + COL_1 + " INTEGER PRIMARY KEY, "
                + COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void addMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, message.getMessageData());
        cv.put(COL_3, message.getMessageSaved());
        cv.put(COL_4, message.getMessageSent());
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public List<Message> getAllMessages() {
        List<Message> messageList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Message message = new Message();
                message.setID(cursor.getLong(0));
                message.setMessageData(cursor.getString(1));
                message.setMessageSaved(cursor.getString(2));
                message.setMessageSent(cursor.getString(3));
                messageList.add(message);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return messageList;
    }

    public void deleteMessage(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_1 + " =? ",
                new String[]{Long.toString(id)});
        db.close();
    }

    public int countAllMessages() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public Message findMessage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{COL_1,
                        COL_2, COL_3, COL_4}, COL_1 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Message message = new Message(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return message;
    }

}
