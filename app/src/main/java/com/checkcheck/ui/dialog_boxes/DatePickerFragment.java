package com.checkcheck.ui.dialog_boxes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public  class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    Date date = new Date();
    Date_Listener listener;
    Calendar c;


   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (Date_Listener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    public DatePickerFragment(Date_Listener listener) {
        this.listener = listener;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
         c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        date = calendar.getTime();
        listener.setDate(date);
        // Do something with the date chosen by the user
    }


    public interface Date_Listener{
        void setDate(Date date);
    }
}