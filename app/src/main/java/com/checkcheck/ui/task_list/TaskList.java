package com.checkcheck.ui.task_list;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.checkcheck.R;
import com.checkcheck.ui.dialog_boxes.add_dialog;
import com.checkcheck.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class TaskList extends Fragment implements add_dialog.AddDialogListener, TaskListAdapter.TaskListListener {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    TaskListViewModel taskListViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        fab = view.findViewById(R.id.floatingActionButton);
        recyclerView = view.findViewById(R.id.taskListRv);
        //size = view.findViewById(R.id.size);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .showMonthName(false)
                //.datesNumberOnScreen(7)
                .centerToday(true)
                .selectedDateBackground(R.color.colorAccent)
                .build();

        taskListViewModel = ViewModelProviders.of(this).get(TaskListViewModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final TaskListAdapter adapter = new TaskListAdapter(TaskList.this);
        recyclerView.setAdapter(adapter);


        taskListViewModel.getTasks().observe(TaskList.this, tasks -> adapter.setTasks(tasks, Calendar.getInstance().getTime(), 0));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_dialog a = new add_dialog(TaskList.this);
                a.show(getActivity().getSupportFragmentManager(),"example");
            }
        });


        try {
           horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
               @Override
               public void onDateSelected(Date date, int position) {
                   taskListViewModel.getTasks().observe(TaskList.this, tasks -> adapter.setTasks(tasks, date, 0));
                   adapter.notifyDataSetChanged();
               }
           });
       }catch (ArrayIndexOutOfBoundsException e){
           e.getCause();
       }catch (Exception e){}


        //size.setText(String.valueOf(adapter.getItemCount()));
    }

    @Override
    public void setAddData(String title, String desc, Date deadline) {
        Task task = new Task(title,desc,deadline);
        taskListViewModel.addTask(task);
    }

    @Override
    public void updateTask(Task task) {
        taskListViewModel.updateTask(task);
    }

}
