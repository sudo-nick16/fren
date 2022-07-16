package com.example.temp;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.time.Instant;
import java.util.ArrayList;

public class AlarmReceiver extends BroadcastReceiver {

    @SuppressLint("Range")
    public static boolean deleteContact(Context ctx, String phone, String name) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone));
        Cursor cur = ctx.getContentResolver().query(contactUri, null, null, null, null);
        try {
            if (cur.moveToFirst()) {
                do {
                    if (cur.getString(cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)).equalsIgnoreCase(name)) {
                        String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                        ctx.getContentResolver().delete(uri, null, null);
                        return true;
                    }

                } while (cur.moveToNext());
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return false;
    }

    public void deleteAllContact(Context ctx){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(ctx);
        String currTime = String.valueOf(Instant.now().getEpochSecond()/60);
        ArrayList<Contact> contacts = dataBaseHelper.find(currTime);
        dataBaseHelper.delete(currTime);

        for(Contact c: contacts){
            deleteContact(ctx, c.phone_number, c.name);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        deleteAllContact(context.getApplicationContext());
    }
}
