package com.example.bugdroid.lolquests.Outros;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.bugdroid.lolquests.R;


/**
 * Created by BugDroid on 19/12/2016.
 */

public class ViewDialog {

    public void showDialog(Activity activity, String msg, long csg, long csu){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.costum_dialog);

        TextView csgoal = (TextView) dialog.findViewById(R.id.csgoal);
        TextView csuser = (TextView) dialog.findViewById(R.id.csuser);
        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        csgoal.setText(String.valueOf(csg));
        csuser.setText(String.valueOf(csu));
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}