package com.example.temp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Contact> contacts;
    LayoutInflater inflater;

    public CustomListAdapter(Context ctx, ArrayList<Contact> contacts){
        this.context = ctx;
        this.contacts = contacts;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_custom_list_view, null);
        TextView name =  view.findViewById(R.id.name);
        TextView phone =  view.findViewById(R.id.phone);
        name.setText(contacts.get(i).getName());
        phone.setText(contacts.get(i).getPhone_number());
        return view;
    }
}
