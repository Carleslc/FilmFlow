package com.and119_idi.myfilmdatabase.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by albert on 9/12/16.
 */


public abstract class DialogActivity extends Activity {
    protected  AlertDialog alertDialog;
    protected  boolean unsavedData;


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

    protected void showDialog(View view) {
        alertDialog.show();
    }

    protected boolean isEmpty(TextView text) {
        if (text.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    protected abstract boolean checkData(boolean showErrors);



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
            alertAndFinish();
            return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        alertAndFinish();
    }

    protected void alertAndFinish() {
        if (unsavedData) alertDialog.show();
        else fi();
    }


}
