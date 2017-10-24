package com.example.sami.s305047;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sami on 16-Oct-17.
 */


public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Contacts";

    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "student_table";

    public static final String COL_1 = "ID";

    public static final String COL_2 = "NAME";

    public static final String COL_3 = "SURNAME";

    public static final String COL_4 = "PHONE";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + COL_1 + " INTEGER PRIMARY KEY, "
                + COL_2 + " TEXT, " + COL_3
                + " TEXT, " + COL_4 + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, contact.getName());
        cv.put(COL_3, contact.getSurname());
        cv.put(COL_4, contact.getNumber());
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public List<Contact> findAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(cursor.getLong(0));
                contact.setName(cursor.getString(1));
                contact.setSurname(cursor.getString(2));
                contact.setNumber(cursor.getString(3));
                contactList.add(contact);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return contactList;
    }

    public void deleteContact(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_1 + " =? ",
                new String[]{Long.toString(id)});
        db.close();
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, contact.getName());
        cv.put(COL_3, contact.getSurname());
        cv.put(COL_4, contact.getNumber());
        int updated = db.update(TABLE_NAME, cv, COL_1 + "= ?", new String[]{String.valueOf(contact.getID())});
        db.close();
        return updated;
    }

    public int countAllContacts() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public Contact findContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{COL_1,
                        COL_2, COL_3, COL_4}, COL_1 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return contact;
    }
}
