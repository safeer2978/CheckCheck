package com.checkcheck.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.checkcheck.R;
import com.checkcheck.ui.task_list.TaskListAdapter;
import com.checkcheck.ui.task_list.TaskListViewModel;
import com.checkcheck.model.Task;

import java.util.Calendar;

public class HomeFragment extends Fragment implements TaskListAdapter.TaskListListener {

    RecyclerView recyclerView,completed;
    TaskListViewModel taskListViewModel;
    TextView count;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //fab = view.findViewById(R.id.floatingActionButton);
        recyclerView = view.findViewById(R.id.rv_today);
        completed = view.findViewById(R.id.completed_rv);
        count =view.findViewById(R.id.count);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         taskListViewModel= ViewModelProviders.of(this).get(TaskListViewModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final TaskListAdapter adapter = new TaskListAdapter(HomeFragment.this);
        recyclerView.setAdapter(adapter);

        taskListViewModel.getTasks().observe(this, tasks -> adapter.setTasks(tasks,Calendar.getInstance().getTime(),1));
        completed.setLayoutManager(new LinearLayoutManager(getContext()));
        final TaskListAdapter completedAdapter = new TaskListAdapter(HomeFragment.this);
        completed.setAdapter(completedAdapter);

        taskListViewModel.getTasks().observe(this, tasks -> completedAdapter.setTasks(tasks,Calendar.getInstance().getTime(),2));
        adapter.notifyDataSetChanged();
        completedAdapter.notifyDataSetChanged();
        //count.setText(completedAdapter.getItemCount());

    }


    @Override
    public void updateTask(Task task) {
        taskListViewModel.updateTask(task);
    }
}