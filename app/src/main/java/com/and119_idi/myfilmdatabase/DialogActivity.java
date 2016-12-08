package com.and119_idi.myfilmdatabase;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

/**
 * Created by albert on 9/12/16.
 */

//Praise Cristina profesora de AS.

public abstract class DialogActivity extends Activity {
    protected  AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        setFinishOnTouchOutside(false);
        initAlertDialog();
    }

    protected void initAlertDialog() {

        alertDialog = new AlertDialog.Builder(DialogActivity.this).create();
        alertDialog.setTitle("Confirm");
        alertDialog.setMessage("Are you sure you want to cancel? \nIntroduced information will be lost");
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        fi();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    protected void fi() {
        this.finish();
        Log.d("Finished activity","EXITING WITH CONFIRMATION");
    }

    protected abstract boolean checkBeforeClosing();

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
            alertDialog.show();
            return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        alertDialog.show();
    }


}
