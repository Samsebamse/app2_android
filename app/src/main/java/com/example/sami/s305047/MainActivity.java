package com.example.sami.s305047;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Contact> allContacts;
    private ListView list;
    private ArrayAdapter<Contact> adapter;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        db.onCreate(db.getWritableDatabase());
        populateListView();
        longClickListViewHandler();
        clickListViewHandler();


    }

    @Override
    protected void onResume() {
        super.onResume();
        populateListView();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent settingsActivity = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsActivity);
                return true;
            case R.id.new_message:
                Intent newMessageActivity = new Intent(getApplicationContext(), NewMessageActivity.class);
                startActivity(newMessageActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onFabClicked(View v){
        Intent addStudentActivity = new Intent(getApplicationContext(), NewStudentActivity.class);
        startActivityForResult(addStudentActivity, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void dialogBoxHandler(final Contact clickedContact){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Erase this entity")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        db.getWritableDatabase();
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
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                Contact clickedContact = allContacts.get(position);
                dialogBoxHandler(clickedContact);
                return true;
            }
        });

    }

    public void clickListViewHandler(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                long studId =  allContacts.get(position).getID();
                Intent editStudentActivity = new Intent(getApplicationContext(), EditStudentActivity.class);
                editStudentActivity.putExtra("studentID", studId);
                startActivity(editStudentActivity);

            }
        });



    }

    public void populateListView() {
        allContacts = db.findAllContacts();
        adapter = new MyListAdapter();
        list = (ListView) findViewById(R.id.studentListView);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setItemsCanFocus(false);
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
