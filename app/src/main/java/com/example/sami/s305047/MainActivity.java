package com.example.sami.s305047;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Contact> allContacts;
    ListView list;
    ArrayAdapter<Contact> adapter;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        //db.onCreate(db.getWritableDatabase());

        //populateContactList();
        populateListView();
        longClickListViewHandler();
        deleteTable(R.id.delete_all);

    }


    private void deleteTable(int buttonId) {
        Button deleteAllButton = (Button) findViewById(buttonId);
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.dropTable("student_table");
            }
        });

    }


    public void dialogBoxHandler(final Contact clickedContact) {
        //Put up the Yes/No message box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Erase this entity")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteContact(clickedContact.getID());
                        adapter.remove(clickedContact);
                    }
                })
                .setNegativeButton("No", null)
                .show();


    }

    public void longClickListViewHandler() {
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Contact clickedContact = allContacts.get(position);
                dialogBoxHandler(clickedContact);
                return false;
            }
        });

    }

    public void populateContactList() {

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


    }

    public void populateListView() {
        allContacts = db.findAllContacts();
        adapter = new MyListAdapter();
        list = (ListView) findViewById(R.id.studentListView);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
