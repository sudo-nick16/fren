package com.example.temp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String CONTACTS_TABLE = "CONTACTS_TABLE";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_PHONE_NUMBER = "PHONE_NUMBER";
    public static final String COLUMN_TILL = "TILL";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "contacts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + CONTACTS_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_PHONE_NUMBER + " TEXT, " + COLUMN_TILL + " DATETIME)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean add(Contact contactModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, contactModel.getName());
        cv.put(COLUMN_PHONE_NUMBER, contactModel.getPhone_number());
        cv.put(COLUMN_TILL, contactModel.getTill());

        long insert = db.insert(CONTACTS_TABLE, null, cv);
        db.close();
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<Contact> find(String tillTime){
        String query = "SELECT * FROM "+ CONTACTS_TABLE + " WHERE " + COLUMN_TILL + " = " + tillTime;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Contact> toBeDeleted = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                String till = cursor.getString(3);

                Contact c = new Contact(id, name, phone, till);

                toBeDeleted.add(c);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return toBeDeleted;
    }

    public boolean delete(String tillTime){
        String query = "DELETE FROM "+ CONTACTS_TABLE + " WHERE " + COLUMN_TILL + " = " + tillTime;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
        return true;
    }

    public List<Contact> getAll(){
        List<Contact> contactList = new ArrayList<Contact>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM "+ CONTACTS_TABLE;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                String till = cursor.getString(3);

                Contact c = new Contact(id, name, phone, till);
                contactList.add(c);

            }while(cursor.moveToNext());
        }else{
            //sad boi
        }
        cursor.close();
        db.close();
        return contactList;
    }


}