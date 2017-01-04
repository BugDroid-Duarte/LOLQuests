package com.example.bugdroid.lolquests.Outros;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.bugdroid.lolquests.Activities.InitialActivity;
import com.example.bugdroid.lolquests.BD.QuestsHelper;

import java.util.Random;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bugdroid.lolquests.Activities.InitialActivity.newQuest;

public class AlarmReceiver extends BroadcastReceiver {


    QuestsHelper db = new QuestsHelper(InitialActivity.appContext);

	@Override
	public void onReceive(Context context, Intent intent) {
		// For our recurring task, we'll just display a message
		Toast.makeText(context, "!! Quest updated !!", Toast.LENGTH_SHORT).show();

        Random rand = new Random();
        long id = rand.nextInt(7);

        newQuest = db.getRandomQuest(id+1);

        InitialActivity.desc.setText(newQuest.getDesc());
        InitialActivity.status.setText(String.valueOf(newQuest.getProgress()));
        InitialActivity.exp.setText(String.valueOf(newQuest.getExp()));
        Log.d("FODASS", String.valueOf(newQuest.getCsGoal()));
        //Log.d(TAG, "run: TRIGGER");

        SharedPreferences shareprefs = context.getSharedPreferences("cscs", MODE_PRIVATE);
        SharedPreferences.Editor editor = shareprefs.edit();
        editor.putLong("cscs", newQuest.getCsGoal());
        editor.commit();
    }
}
