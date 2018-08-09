package com.kejarkoding.crudsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kejarkoding.crudsqlite.model.Usermodel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static DatabaseHandler sInstance;
    public static synchronized DatabaseHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    public DatabaseHandler(Context context) {
        super(context, "db_manajer", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_buat_table = "create table talls(" +
                "id integer primary key," +
                "nama text, " +
                "tall text)";
        db.execSQL(sql_buat_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists talls");
    }

    public void addRecord(Usermodel usermodels) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nama", usermodels.getName());
        values.put("tall", usermodels.getTall());

        db.insert("talls", null, values);
        db.close();
    }

    public Usermodel getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("talls", new String[]{"id",
                        "nama", "tall"}, "id" + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Usermodel contact = new Usermodel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }

    public List<Usermodel> getAllrecord() {
        List<Usermodel> contactlist = new ArrayList<Usermodel>();
        String selectQuery = "select * from talls";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Usermodel usermodels = new Usermodel();
                usermodels.setId(Integer.parseInt(cursor.getString(0)));
                usermodels.setName(cursor.getString(1));
                usermodels.setTall(cursor.getString(2));
                contactlist.add(usermodels);
            } while (cursor.moveToNext());
        }
        return contactlist;
    }

    public int getUserModelCount() {
        String countQuery = "SELECT  * FROM " + " talls ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int counts = cursor.getCount();
        cursor.close();
        return counts;
    }

    public int updateContact(Usermodel contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nama", contact.getName());
        values.put("tall", contact.getTall());

        // updating row
        return db.update("talls ", values, "id" + " = ?",
                new String[]{String.valueOf(contact.getId())});
    }

    public void deleteModel(Usermodel contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("talls ", "id" + " = ?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }
    public void deleteAllrc(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from talls");
        db.close();
    }
}
