package com.example.sami.s305047;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class NewStudentActivity extends AppCompatActivity {

    DBHelper db;
    EditText inputName, inputSurName, inputPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);
        db = new DBHelper(this);

        inputName = (EditText) findViewById(R.id.student_name);
        inputSurName = (EditText) findViewById(R.id.student_surname);
        inputPhone = (EditText) findViewById(R.id.student_phone);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_save2:
                inputHandler(
                        inputName.getText().toString(),
                        inputSurName.getText().toString(),
                        inputPhone.getText().toString()
                );
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void inputHandler(String name, String surName, String phone){
        Contact addNewStudent = new Contact(name, surName, phone);
        db.addContact(addNewStudent);
        finish();
    }


}
