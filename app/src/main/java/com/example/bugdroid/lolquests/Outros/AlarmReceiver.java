package com.example.bugdroid.lolquests.Outros;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.bugdroid.lolquests.Activities.MainActivity;
import com.example.bugdroid.lolquests.BD.QuestsHelper;
import com.example.bugdroid.lolquests.R;

import java.util.Random;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bugdroid.lolquests.Activities.MainActivity.appContext;
import static com.example.bugdroid.lolquests.Activities.MainActivity.newQuest;

public class AlarmReceiver extends BroadcastReceiver {

    QuestsHelper db = new QuestsHelper(MainActivity.appContext);

	@Override
	public void onReceive(Context context, Intent intent) {

        // Gera id random para usar na funcao getRandomQuest
        Random rand = new Random();
        long id = rand.nextInt(10);

        newQuest = db.getRandomQuest(id);

        // mostra os valores obtifos na main
        MainActivity.desc.setText(newQuest.getDesc());
        MainActivity.status.setText(String.valueOf(newQuest.getProgress()));
        MainActivity.exp.setText(String.valueOf(newQuest.getExp()));

        // guarda nm sharedpreferences, para se o utilizador fechar e abrir a app os valores esta sempre la
        SharedPreferences DadosNewQuest = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = DadosNewQuest.edit();
        editor.putString("desc", newQuest.getDesc());
        editor.putInt("status", 0);
        editor.putInt("exp", newQuest.getExp());
        editor.apply();

        SharedPreferences shareprefs = context.getSharedPreferences("cscs", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = shareprefs.edit();
        editor2.putLong("cscs", newQuest.getCsGoal());
        editor2.commit();

        // executa esta função que cria uma notificação de que a quest foi atualizada.
        showNewQuestNotification();

        Log.d("AlarmReceiver", "TRIGGER");

    }

    private void showNewQuestNotification() {

        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(appContext)
                        .setSmallIcon(R.drawable.lpq)
                        .setContentTitle("New Quest available")
                        .setContentText("You have a new quest to do !");

        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        Intent notificationIntent = new Intent(appContext, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(appContext, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        // Add as notification
        NotificationManager manager = (NotificationManager) appContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
