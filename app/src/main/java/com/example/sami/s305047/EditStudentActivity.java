package com.example.sami.s305047;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class EditStudentActivity extends AppCompatActivity {

    DBHelper db;
    Contact displayContact;
    TextView name, surname, phone;
    long currentStudentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);


        displayStudent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_save:
                String editName = name.getText().toString();
                String editSurName = surname.getText().toString();
                String editPhoneNumber = phone.getText().toString();

                displayContact.setName(editName);
                displayContact.setSurname(editSurName);
                displayContact.setNumber(editPhoneNumber);

                db.updateContact(displayContact);
                finish();

                return true;
            case R.id.icon_delete:
                db.deleteContact(currentStudentID);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void displayStudent() {
        Bundle editStudent = getIntent().getExtras();
        currentStudentID = editStudent.getLong("studentID");
        db = new DBHelper(this);

        displayContact = db.findContact((int)currentStudentID);

        name = (TextView) findViewById(R.id.edit_name);
        surname = (TextView) findViewById(R.id.edit_surname);
        phone = (TextView) findViewById(R.id.edit_phone);
        name.setText(displayContact.getName());
        surname.setText(displayContact.getSurname());
        phone.setText(displayContact.getNumber());


    }



}
