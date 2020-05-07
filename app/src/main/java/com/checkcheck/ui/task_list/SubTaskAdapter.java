package com.checkcheck.ui.task_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.checkcheck.R;
import com.checkcheck.model.SubTask;

import java.util.ArrayList;
import java.util.List;

public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.SubTaskViewHolder>{

    List<SubTask> subTasks = new ArrayList<>();


    @NonNull
    @Override
    public SubTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.subtasks, parent, false);

        return new SubTaskAdapter.SubTaskViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SubTaskViewHolder holder, int position) throws NullPointerException{
        SubTask subTask = subTasks.get(position);
        holder.checkBox.setText(subTask.getTitle());
//        holder.checkBox.setChecked(subTask.getCheck());
        //viewModel = ViewModelProviders.of((FragmentActivity) context).get(TaskListViewModel.class);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                subTask.setCheck(compoundButton.isChecked());

                //listener.updateSubtask(subTasks);
            }
        });
    }

    List<SubTask> getSubTasks(){
        return subTasks;
    }

    void setSubTasks(List<SubTask> subTasks){
        this.subTasks = subTasks;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return subTasks.size();
    }

    class SubTaskViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        public SubTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.subCheck);
        }
    }
}
