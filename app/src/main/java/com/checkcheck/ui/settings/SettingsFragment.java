package com.checkcheck.ui.settings;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.checkcheck.R;
import com.checkcheck.ui.start.StartActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.checkcheck.repository.TaskRepository.flag_fire;


public class SettingsFragment extends Fragment {



    static AlarmManager am;
    static PendingIntent pendingIntent;

    Switch notif_switch,fire_switch;
    Button signout;

    static boolean flag =false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        notif_switch = root.findViewById(R.id.notif_switch);
        signout = root.findViewById(R.id.signout);
        fire_switch = root.findViewById(R.id.fire_switch);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        flag = sharedPreferences.getBoolean("notif",true);
        notif_switch.setChecked(flag);
        notif_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean("notif",b).commit();
            }
        });

        flag_fire = sharedPreferences.getBoolean("fire",true);
        fire_switch.setChecked(flag_fire);
        fire_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean("fire",b).commit();
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getActivity(), StartActivity.class));
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


            }
        });




    }

    public static void setNotifs(Context context, String title, Date date){
        try{if(flag)
        {
            Intent intent = new Intent(context, NotificationPublisher.class);
            Bundle bundle = new Bundle();
            bundle.putString("title",title);
            intent.putExtras(bundle);
            pendingIntent = PendingIntent.getService(context, 001, intent, 0);
            am = (AlarmManager)context.getSystemService(ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
    }}
        catch (Exception e){}
    }


}