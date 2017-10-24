package com.example.sami.s305047;

import android.app.DatePickerDialog;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import static com.example.sami.s305047.R.id.datetime;


public class SettingsActivity extends AppCompatActivity {

    private Calendar myCalendar;
    private EditText editTextDate;
    private EditText editTextTime;
    private DatePickerDialog.OnDateSetListener date;
    private TimePickerDialog time;

    private CheckBox setPreference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        checkBoxHandler(R.id.checkBox);
        calendarHandler();
        timeHandler();
    }

    private void checkBoxHandler(int rId) {


        setPreference = (CheckBox) findViewById(rId);
        setPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setPreference.isChecked()){
                    Intent intent = new Intent(SettingsActivity.this, MyService.class);
                    startService(intent);
                    Toast.makeText(getApplicationContext(), "SERVICE IS ON", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "SERVICE IS OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void timeHandler() {
        editTextTime = (EditText) findViewById(datetime);
        editTextTime.setInputType(InputType.TYPE_NULL);
        editTextTime.setHint("Choose time...");

        editTextTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar calendar = myCalendar;
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                time = new TimePickerDialog(SettingsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editTextTime.setText( selectedHour + ":" + selectedMinute);

                    }
                }, hour, minute, true);
                time.show();

            }
        });
    }

    private void updateLabel() {
        String timeFormat = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);

        editTextDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void calendarHandler() {

        editTextDate = (EditText) findViewById(R.id.calendar);
        editTextDate.setInputType(InputType.TYPE_NULL);
        editTextDate.setHint("Choose date...");
        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        editTextDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog datePicker = new DatePickerDialog(SettingsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                datePicker.show();

            }
        });
    }



}
