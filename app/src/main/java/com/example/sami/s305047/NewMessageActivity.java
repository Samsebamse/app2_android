package com.example.sami.s305047;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class NewMessageActivity extends AppCompatActivity {

    private DBHelper2 db;

    private Calendar timeFuture;
    private EditText editTextDate;
    private EditText editTextTime;
    private EditText smsInput;
    private DatePickerDialog.OnDateSetListener date;
    private TimePickerDialog time;

    private int nYear, nMonth, nDay, nHour, nMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        db = new DBHelper2(this);

        timeFuture = Calendar.getInstance();
        calendarHandler();
        timeHandler();
        smsInputHandler();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_send:
                if(inputValidated())
                    sendMessage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_send, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void smsInputHandler(){
        smsInput = (EditText) findViewById(R.id.sms_input);

        smsInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                System.out.println(actionId);
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    System.out.println(smsInput.getText().toString());
                }
                return false;
            }
        });
    }

    private void timeHandler() {
        editTextTime = (EditText) findViewById(R.id.datetime);
        editTextTime.setInputType(InputType.TYPE_NULL);
        editTextTime.setHint("Choose time...");

        editTextTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                time = new TimePickerDialog(NewMessageActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        nHour = selectedHour;
                        nMinute = selectedMinute;

                        editTextTime.setText(selectedHour + ":" + selectedMinute);

                        if(!checkDateValidity()){
                            editTextTime.setText("");
                            nHour = nMinute = 0;
                        }

                    }
                }, hour, minute, true);
                time.show();
            }
        });
    }

    private void updateLabel() {
        String timeFormat = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);

        editTextDate.setText(sdf.format(timeFuture.getTime()));
    }

    private void calendarHandler() {
        editTextDate = (EditText) findViewById(R.id.calendar);
        editTextDate.setInputType(InputType.TYPE_NULL);
        editTextDate.setHint("Choose date...");


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                nYear = year;
                nMonth = monthOfYear;
                nDay = dayOfMonth;

                if(!checkDateValidity()){
                    editTextTime.setText("");
                    nHour = nMinute = 0;
                }

                updateLabel();
            }

        };

        editTextDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog datePicker = new DatePickerDialog(NewMessageActivity.this, date, timeFuture
                        .get(Calendar.YEAR), timeFuture.get(Calendar.MONTH),
                        timeFuture.get(Calendar.DAY_OF_MONTH));

                datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                datePicker.show();
            }
        });
    }

    private boolean checkDateValidity(){
        timeFuture.set(nYear, nMonth, nDay, nHour, nMinute, 0);

        Calendar timeNow = Calendar.getInstance();

        String dateText = editTextDate.getText().toString();
        String timeText = editTextTime.getText().toString();

        if(!TextUtils.isEmpty(dateText) && !TextUtils.isEmpty(timeText)) {
            if (timeFuture.getTimeInMillis() > timeNow.getTimeInMillis())
                return true;
            else {
                AlertDialog alertDialog = new AlertDialog.Builder(NewMessageActivity.this).create();
                alertDialog.setTitle("Feilmelding");
                alertDialog.setMessage("Den valgte tiden må være i fremtiden!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return false;
            }
        }

        return true;
    }

    private void setAlarm(){
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        intent.putExtra("Message", smsInput.getText().toString());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeFuture.getTimeInMillis(), pendingIntent);
    }

    private boolean inputValidated(){
        String dateText = editTextDate.getText().toString();
        String timeText = editTextTime.getText().toString();
        String messageText = smsInput.getText().toString();

        if(!TextUtils.isEmpty(dateText) && !TextUtils.isEmpty(timeText) && !TextUtils.isEmpty(messageText))
            return true;
        else {
            AlertDialog alertDialog = new AlertDialog.Builder(NewMessageActivity.this).create();
            alertDialog.setTitle("Feilmelding");
            alertDialog.setMessage("Du må fylle inn alle feltene!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return false;
        }
    }

    private void sendMessage(){
        String msgData = smsInput.getText().toString();
        Calendar c = Calendar.getInstance();

        String timeFormat = "dd.MM.yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);

        Message msg = new Message(msgData, c.getTimeInMillis());
        db.addMessage(msg);
        setAlarm();
        Toast.makeText(this, "Message scheduled for " + sdf.format(timeFuture.getTime()), Toast.LENGTH_LONG).show();
        finish();
    }

}
