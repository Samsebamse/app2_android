package com.example.sami.s305047;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class SettingsActivity extends AppCompatActivity {

    private Calendar myCalendar;
    private EditText editTextDate;
    private EditText editTextTime;
    private DatePickerDialog.OnDateSetListener date;
    private TimePickerDialog time;

    private CheckBox setPreference;

    private EditText SMSInput;
    private TextView SMSPreview;

    private SharedPreferences prefs;

    private int year, month, day, hour, minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = getSharedPreferences("StudentSettings", MODE_PRIVATE);

        checkBoxHandler(R.id.checkBox);
        calendarHandler();
        timeHandler();
        smsHandler();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_save2:
                saveSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void smsHandler() {
        SMSInput = (EditText) findViewById(R.id.sms_input);
        SMSPreview = (TextView) findViewById(R.id.sms_preview);

        String smsSavedText = prefs.getString("message", "");

        SMSInput.setText(smsSavedText);
        SMSPreview.setText(smsSavedText);

        if(!setPreference.isChecked())
            SMSInput.setEnabled(false);

        SMSInput.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                SMSPreview.setText(SMSInput.getText().toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void checkBoxHandler(int rId) {

        boolean savedValue = prefs.getBoolean("weekly_msg", false);

        setPreference = (CheckBox) findViewById(rId);
        setPreference.setChecked(savedValue);

        setPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setPreference.isChecked()){
                    SMSInput.setEnabled(true);
                    Intent intent = new Intent(SettingsActivity.this, MyService.class);
                    startService(intent);
                    Toast.makeText(getApplicationContext(), "SERVICE IS ON", Toast.LENGTH_SHORT).show();

                    //SmsManager smsManager = SmsManager.getDefault();
                    //smsManager.sendTextMessage("12341234", null, smsData, null, null);

                    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

                }
                else{
                    SMSInput.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "SERVICE IS OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setAlarm(){
        Intent intent = new Intent(this, MyBroadcastReceiverWeekly.class);
        intent.putExtra("Message", SMSInput.getText().toString());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),2 * 1000, pendingIntent);
    }

    private void timeHandler() {
        editTextTime = (EditText) findViewById(R.id.datetime);
        editTextTime.setInputType(InputType.TYPE_NULL);
        editTextTime.setHint("Choose time...");

        editTextTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(setPreference.isChecked()) {
                    // TODO Auto-generated method stub
                    Calendar calendar = myCalendar;
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    time = new TimePickerDialog(SettingsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            editTextTime.setText(selectedHour + ":" + selectedMinute);

                        }
                    }, hour, minute, true);
                    time.show();
                }
            }
        });
    }

    private void updateLabel() {
        String timeFormat = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);

        editTextDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void calendarHandler() {
        myCalendar = Calendar.getInstance();
        editTextDate = (EditText) findViewById(R.id.calendar);
        editTextDate.setInputType(InputType.TYPE_NULL);
        editTextDate.setHint("Choose date...");


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
                if(setPreference.isChecked()) {
                    // TODO Auto-generated method stub
                    DatePickerDialog datePicker = new DatePickerDialog(SettingsActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));

                    datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                    datePicker.show();
                }
            }
        });
    }

    private void saveSettings(){
        System.out.println("HELELRKALSKDLSADK");
        SharedPreferences.Editor editor = getSharedPreferences("StudentSettings", MODE_PRIVATE).edit();
        editor.putBoolean("weekly_msg", setPreference.isChecked());
        editor.putLong("date", myCalendar.getTimeInMillis());
        editor.putString("message", SMSPreview.getText().toString());
        editor.apply();

        if(setPreference.isChecked())
            setAlarm();

        Toast.makeText(getApplicationContext(), "Settings Saved!", Toast.LENGTH_SHORT).show();
    }

}
