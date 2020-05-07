package com.checkcheck.ui.dialog_boxes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.checkcheck.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class add_dialog extends AppCompatDialogFragment implements DatePickerFragment.Date_Listener, TimePickerFragment.TimeListenr {

    EditText title, description;
    TextView setDate;
    private static Date date;
    AddDialogListener listener;

    public add_dialog(AddDialogListener listener){
        this.listener = listener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_task, null);
        title =view.findViewById(R.id.dialog_title);
        description = view.findViewById(R.id.dialog_description);
        setDate = view.findViewById(R.id.date);
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment(add_dialog.this);
                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
                newFragment = new DatePickerFragment(add_dialog.this);
                newFragment.show(getActivity().getSupportFragmentManager(),"datePicker");
            }
        });
        builder.setView(view)
                .setTitle("Add Task")
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(date==null) {
                            setDate.callOnClick();
                            Toast.makeText(getContext(),"Must select a Deadline!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            listener.setAddData(title.getText().toString(), description.getText().toString(), date);


                        }

                    }
                });
        return  builder.create();

    }

    @Override
    public void setDate(Date date1) {
        date = date1;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setTime(int hour, int minute) {
        date.setHours(hour);
        date.setMinutes(minute);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        setDate.setText("Deadline: "+dateFormat.format(date));
    }

    public interface AddDialogListener{
        void setAddData(String title, String desc, Date deadline);
    }
}
