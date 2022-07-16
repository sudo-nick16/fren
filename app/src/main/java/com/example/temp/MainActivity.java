package com.example.temp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    private static final String DEBUG_TAG = "MainActivity";
    ImageButton add;
    ListView contacts;
    RelativeLayout view;
    DataBaseHelper dataBaseHelper;

    private void openAddLayout() {
        Intent openAddActivity = new Intent(this, addContact.class);
        startActivity(openAddActivity);
    }

    public ArrayList<Contact> arrayList(List<Contact> l){
        ArrayList<Contact> arr = new ArrayList<>(l.size());
        arr.addAll(l);
        return arr;
    }

    public void setContactList(){
        CustomListAdapter cust = new CustomListAdapter(getApplicationContext(), arrayList(dataBaseHelper.getAll()));
        contacts.setAdapter(cust);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.contacts);
        add = findViewById(R.id.addContact);
        contacts = findViewById(R.id.contact_list);
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        setContactList();

        Intent myIntent = new Intent(getApplicationContext(),
                AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 30);
        long frequency = 30 * 1000;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frequency, pendingIntent);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddLayout();
            }
        });
        }

        @Override
        public void onResume() {
            setContactList();
            super.onResume();
        }
}