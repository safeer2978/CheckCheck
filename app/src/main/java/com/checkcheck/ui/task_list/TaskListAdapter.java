package com.checkcheck.ui.task_list;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.checkcheck.R;
import com.checkcheck.model.SubTask;
import com.checkcheck.model.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.checkcheck.ui.settings.SettingsFragment.setNotifs;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    //TaskListViewModel viewModel;
    List<Task> tasksList= new ArrayList<>();
    TaskListListener listener;
    static String subtaskTitle;

    AddSubtask_show addSubtask_show;

    public TaskListAdapter(TaskListListener listener) {
        this.listener = listener;
    }

    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_task, parent, false);

        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasksList.get(position);
        holder.cardView.setVisibility(View.GONE);
        try{
            holder.time.setText(task.getDeadline().getHours()+""+task.getDeadline().getMinutes()+" hrs");
        }catch (NullPointerException e){

        }
        holder.title.setText(task.getTitle());
        if(task.isCheck())
        {
            holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.checkBox.setChecked(task.isCheck());

        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            task.setCheck(b);
            listener.updateTask(task);

        });

        holder.description.setText(task.getDescription());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.cardView.getVisibility()==View.GONE){
                    holder.constraintLayout.setBackgroundResource(R.color.colorPrimaryDark);
                    holder.cardView.setVisibility(View.VISIBLE);}
                else{
                    holder.cardView.setVisibility(View.GONE);
                holder.constraintLayout.setBackgroundResource(R.color.colorPrimary);
                }
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view2 = inflater.inflate(R.layout.subtask_dialog, null);
                EditText title;
                title =view2.findViewById(R.id.subtask_dialog_Title);
                builder.setView(view2)
                        .setTitle("Add SubTask")
                        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //listener.setAddData(title.getText().toString());
                                //TaskListAdapter adapter = new TaskListAdapter(this)
                                subtaskTitle = title.getText().toString();
                                SubTask subTask = new SubTask();
                                subTask.setTitle(subtaskTitle);
                                subTask.setParentTitle(task.getTitle());
                                task.subTasks.add(subTask);
                                listener.updateTask(task);
                                notifyDataSetChanged();
                            }
                        });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        SubTaskAdapter adapter = new SubTaskAdapter();
        adapter.setSubTasks(task.getSubTasks());
        holder.recyclerView.setAdapter(adapter);
        updateSubtask(adapter.getSubTasks());
        //notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public void setTasks(List<Task> tasksList){
        this.tasksList=tasksList;
        notifyDataSetChanged();
    }
    public void setTasks(List<Task> tasksList, Date date,int type){
        this.tasksList = new ArrayList<>();
        for(Task t : tasksList){
           /* if(t.getDeadline().getMonth() == date.getMonth() && (t.getDeadline().getDay()>(date.getDay()-1) && t.getDeadline().getDay()<(date.getDay()+1))){
                if(type==0)
                    this.tasksList.add(t);
                else if(type==1){
                    if(!t.isCheck())
                        this.tasksList.add(t);
                else if(type ==2)
                    if(t.isCheck())
                        this.tasksList.add(t);
                }
            }*/
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date.getTime());
           if(t.getDeadline().getTime()>(date.getTime()-86400000) && t.getDeadline().getTime()<(date.getTime()+86400000)) {
               if (type == 0)
                   this.tasksList.add(t);
               else if (type == 1) {
                   if (!t.isCheck())
                       this.tasksList.add(t);

               }else if (type == 2)
                       if (t.isCheck())
                           this.tasksList.add(t);
           }
           if(t.getDeadline().getTime()>Calendar.getInstance().getTime().getTime()){
            setNotifs(context,t.getTitle(),t.getDeadline());
           }

        }
        notifyDataSetChanged();

    }

    public void updateSubtask(List<SubTask> subTask) {
        for(Task task:tasksList){
            if(task.getTitle().equals(subTask)){
                task.setSubTasks(subTask);
                listener.updateTask(task);
            }
        }
    }


    public interface TaskListListener{
        void updateTask(Task task);

    }

    interface AddSubtask_show{
        String showDialog();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, time, description;
         CheckBox checkBox;
         RecyclerView recyclerView;
         LinearLayout layout;
         LinearLayout cardView;

         ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_title);
            time = itemView.findViewById(R.id.task_deadline);
            checkBox = itemView.findViewById(R.id.checkBox);
            recyclerView =itemView.findViewById(R.id.rv_subtask);
            layout = itemView.findViewById(R.id.addSubtaskLayout);
            cardView =itemView.findViewById(R.id.cardView);
            description = itemView.findViewById(R.id.description);
            constraintLayout = itemView.findViewById(R.id.constraintlayout);
        }
    }
}
