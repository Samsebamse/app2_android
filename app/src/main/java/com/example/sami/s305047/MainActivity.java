package com.example.sami.s305047;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Contact> allContacts;
    ListView list;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        //populateContactList();
        populateListView();
    }

    public void populateContactList() {
        /*
        Contact contact1 = new Contact("Sami", "Rashiti", "12345678");
        db.addContact(contact1);
        Contact contact2 = new Contact("Abu", "Iqbal", "12345678");
        db.addContact(contact2);
        Contact contact3 = new Contact("Ibra", "Diallo", "12345678");
        db.addContact(contact3);
        Contact contact4 = new Contact("Vignesh", "Viggy", "12345678");
        db.addContact(contact4);
        Contact contact5 = new Contact("Mustafe", "Farah", "12345678");
        db.addContact(contact5);
        */

    }

    public void populateListView() {
        allContacts = db.findAllContacts();
        ArrayAdapter<Contact> adapter = new MyListAdapter();
        list = (ListView) findViewById(R.id.studentListView);
        list.setAdapter(adapter);

    }


    private class MyListAdapter extends ArrayAdapter<Contact> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_view, allContacts);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;

            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }
            Contact currentContact = allContacts.get(position);

            TextView itemId = itemView.findViewById(R.id.item_id);
            itemId.setText(String.valueOf(currentContact.getID()));

            TextView itemName = itemView.findViewById(R.id.item_name);
            itemName.setText(currentContact.getName());

            TextView itemSurName = itemView.findViewById(R.id.item_surname);
            itemSurName.setText(currentContact.getSurname());

            TextView itemNumber = itemView.findViewById(R.id.item_number);
            itemNumber.setText(currentContact.getNumber());

            return itemView;

        }
    }

}
