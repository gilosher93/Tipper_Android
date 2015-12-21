package com.example.mysc.tiper;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class CreateManualShift extends AppCompatActivity {


    TimePicker startPicker;
    TimePicker endPicker;
    DatePicker datePicker;
    EditText txtTips;
    Button btnOk;
    Button btnCancel;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_manual_shift);

        sharedPreferences = getSharedPreferences(HomeActivity.prefName, MODE_PRIVATE);
        startPicker = (TimePicker) findViewById(R.id.startPicker);
        endPicker = (TimePicker) findViewById(R.id.endPicker);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        txtTips = (EditText) findViewById(R.id.txtManualTips);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));

        startPicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        startPicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        endPicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        endPicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        datePicker.setFirstDayOfWeek(Calendar.DAY_OF_WEEK);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startHour = String.valueOf(startPicker.getCurrentHour());
                String startMinutes = String.valueOf(startPicker.getCurrentMinute());
                String endHour = String.valueOf(endPicker.getCurrentHour());
                String endMinutes = String.valueOf(endPicker.getCurrentMinute());
                String day = String.valueOf(datePicker.getDayOfMonth());
                String month = String.valueOf(datePicker.getMonth()+1);
                String year = String.valueOf(datePicker.getYear());
                String tips = txtTips.getText().toString();
                float salary = sharedPreferences.getFloat(SettingsActivity.SALARY_PER_HOURS,25);
                int manualTips = tips.length() == 0 ? 0 : Integer.valueOf(tips);
                long startTime = Shift.getFullDateInLong(year + "-" + month + "-" + day + ", " + startHour + ":" + startMinutes);
                long endTime = Shift.getFullDateInLong(year + "-" + month + "-" + day + ", " + endHour + ":" + endMinutes);
                Shift shift = new Shift(startTime, endTime, salary, manualTips);
                //TODO: SEND THE SHIFT!!!
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_manual_shift, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}