package com.example.temp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class addContact extends AppCompatActivity {
    Spinner duration;
    EditText nameEt, contactEt;
    Button add;
    DataBaseHelper dataBaseHelper;

    public ArrayList<Contact> arrayList(List<Contact> l){
        ArrayList<Contact> arr = new ArrayList<>(l.size());
        arr.addAll(l);
        return arr;
    }

    public void setContactList(){
        ListView contacts = findViewById(R.id.contact_list);
        CustomListAdapter cust = new CustomListAdapter(getApplicationContext(), arrayList(dataBaseHelper.getAll()));
        contacts.setAdapter(cust);
    }

    public int getDuration(String str){
        String[] arr = str.split(" ");
        return Integer.parseInt(arr[0]);
    }

    public void createContactIntent(String name, String contact){
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);

        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME, name)
                .putExtra(ContactsContract.Intents.Insert.PHONE, contact)
                .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        startActivity(intent);
    }

    private void addCont(String name, String contact, String duration) {
        if(name.trim().length() == 0 || contact.trim().length() == 0){
            Toast.makeText(this, "Please enter name and contact details", Toast.LENGTH_SHORT).show();
            return;
        }
        if(contact.trim().length() < 10){
            Toast.makeText(this, "Please enter a valid contact.", Toast.LENGTH_SHORT).show();
            return;
        }

        String till = String.valueOf(Instant.now().getEpochSecond()/60 + getDuration(duration));

        Contact contactModel = new Contact(-1, name, contact, till);

        try{
            boolean success = dataBaseHelper.add(contactModel);
            if(success){
                Toast.makeText(addContact.this, "Contact added.", Toast.LENGTH_SHORT).show();
                nameEt.setText("");
                contactEt.setText("");
            }else{
                Toast.makeText(addContact.this, "Couldn't add contact", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception err){
            Toast.makeText(addContact.this, "Couldn't add contact", Toast.LENGTH_SHORT).show();
        }

        createContactIntent(name, contact);

        setContactList();

    }

    public void setSpinner(){
        duration = (Spinner) findViewById(R.id.duration);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.duration_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        duration.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        nameEt = findViewById(R.id.name);
        contactEt = findViewById(R.id.contact);
        add = findViewById(R.id.add);
        setSpinner();

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String n = nameEt.getText().toString();
                String c = contactEt.getText().toString();
                String d = duration.getSelectedItem().toString();
                try{
                    addCont(n,c, d);
                }catch(Exception e){
                    Log.d("D",e.toString());
                }
            }
        });

    }
}